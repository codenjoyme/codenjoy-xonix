package com.codenjoy.dojo.xonix.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.xonix.model.items.*;
import com.codenjoy.dojo.xonix.model.level.Level;
import com.codenjoy.dojo.xonix.services.Event;
import com.codenjoy.dojo.xonix.services.GameSettings;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import java.util.*;
import java.util.stream.Stream;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.VICTORY_CRITERION;
import static java.util.stream.Collectors.toList;

public class XonixGame implements Field {

    private final List<Player> players = new LinkedList<>();
    private final GameSettings settings;
    private final Level level;

    private Hero hero;
    private List<Sea> sea;
    private List<Land> land;
    private List<LandEnemy> landEnemies;
    private List<MarineEnemy> marineEnemies;

    public List<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = Lists.newArrayList(this.marineEnemies);
        enemies.addAll(landEnemies);
        return enemies;
    }

    public XonixGame(Level level, GameSettings settings) {
        this.settings = settings;
        this.level = level;
        reset();
    }

    public void reset() {
        sea = level.sea();
        land = level.land();
        hero = level.hero();
        resetMarineEnemies();
        resetLandEnemies();
    }

    private void resetMarineEnemies() {
        marineEnemies = level.marineEnemies();
        marineEnemies.forEach(enemy -> enemy.setField(XonixGame.this));
    }

    private void resetLandEnemies() {
        landEnemies = level.landEnemies();
        landEnemies.forEach(enemy -> enemy.setField(XonixGame.this));
    }

    @Override
    public Hero getNewHero(Player player) {
        Hero hero = this.hero;
        hero.init(this);
        return hero;
    }

    @Override
    public boolean isLand(Point point) {
        return land.contains(point);
    }

    @Override
    public boolean isSea(Point point) {
        return sea.contains(point);
    }

    @Override
    public boolean isOutOfBounds(Point point) {
        return point.isOutOf(level.size());
    }

    @Override
    public void tick() {
        players.stream()
                .map(Player::getHero)
                .forEach(hero -> {
                    hero.tick();
                    if (hero.isLanded()) {
                        seizeSea(hero);
                        hero.clearTrace();
                        hero.clearDirection();
                    }
                });
        getEnemies().forEach(Enemy::tick);
        if (isHeroKilled()) {
            hero.die();
            if (hero.getLives() == 0) {
                players.get(0).event(Event.GAME_OVER);
                return;
            }
            players.get(0).event(Event.KILLED);
            hero.respawn(level.hero().getPosition());
            resetLandEnemies();
        }
        checkWin();
    }

    private void checkWin() {
        if ((land.size() - level.landCellsCount()) * 1.0 / level.seaCellsCount() >= settings.integer(VICTORY_CRITERION) * 0.01) {
            players.get(0).event(Event.WIN);
        }
    }

    private boolean isHeroKilled() {
        if (hero.isKilled()) {
            return true;
        }
        boolean isKilled;
        if (hero.isFloating()) {
            isKilled = marineEnemies.stream()
                    .anyMatch(e -> hero.getHitbox().contains(e));
        } else {
            isKilled = landEnemies.stream()
                    .anyMatch(e -> hero.getHitbox().contains(e));
        }
        return isKilled;
    }

    public int getSize() {
        return level.size();
    }

    public List<Hero> getHeroes() {
        return players.stream()
                .map(Player::getHero)
                .collect(toList());
    }

    @Override
    public void newGame(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
        reset();
        player.newHero(this);
    }

    @Override
    public void remove(Player player) {
        players.remove(player);
    }

    @Override
    public BoardReader reader() {
        return new BoardReader() {

            @Override
            public int size() {
                return XonixGame.this.getSize();
            }

            @Override
            public Iterable<? extends Point> elements() {
                return new LinkedList<>() {{
                    addAll(getHeroes());
                    addAll(XonixGame.this.getHeroes().get(0).getTrace());
                    addAll(XonixGame.this.marineEnemies);
                    addAll(XonixGame.this.landEnemies);
                    addAll(XonixGame.this.sea);
                    addAll(XonixGame.this.land);
                }};
            }
        };
    }

    @Override
    public GameSettings settings() {
        return settings;
    }

    private void seizeSea(Hero hero) {
        List<Trace> trace = hero.getTrace();
        if (trace.size() == 1) { // Like original
            turnToLand(trace.get(0));
            return;
        }
        Direction firstWaveDirection = hero.getDirection()
                .counterClockwise();
        Trace lastTraceCell = trace.get(trace.size() - 1);
        Collection<Point> points = breadthFirstSearch(firstWaveDirection.change(lastTraceCell));
        if (points.size() == 0) {
            points = breadthFirstSearch(firstWaveDirection.inverted().change(lastTraceCell));
        }
        points.addAll(trace);
        points.forEach(this::turnToLand);
    }

    private Collection<Point> breadthFirstSearch(Point start) {
        ArrayDeque<Point> queue = Queues.newArrayDeque();
        queue.offer(start);
        HashSet<Point> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            if (getEnemies().contains(point)) {
                return Lists.newArrayList();
            }
            visited.add(point);
            Point left = Direction.LEFT.change(point);
            Point up = Direction.UP.change(point);
            Point right = Direction.RIGHT.change(point);
            Point down = Direction.DOWN.change(point);
            Stream.of(up, down, left, right)
                    .filter(p -> !visited.contains(p))
                    .filter(p -> !isTrace(p))
                    .filter(this::isSea)
                    .forEach(queue::offer);
        }
        return visited;
    }

    private void turnToLand(Point point) {
        if (sea.remove(point)) {
            land.add(new Land(point));
        }
    }

    private boolean isTrace(Point point) {
        return getHeroes().stream()
                .flatMap(hero -> hero.getTrace().stream())
                .anyMatch(trace -> trace.equals(point));
    }
}
