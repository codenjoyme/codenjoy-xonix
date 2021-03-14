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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class XonixGame implements Field {

    private static final Logger LOG = LoggerFactory.getLogger(XonixGame.class);

    private final List<Player> players = new LinkedList<>();
    private final Level level;
    private int size;

    private List<Sea> sea;
    private List<Land> land;
    private List<MarineEnemy> marineEnemies;
    private List<LandEnemy> landEnemies;
    private Hero hero;

    public List<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = Lists.newArrayList(this.marineEnemies);
        enemies.addAll(landEnemies);
        return enemies;
    }

    private final GameSettings settings;

    public XonixGame(Level level, GameSettings settings) {
        this.settings = settings;
        this.level = level;
        reset();
    }

    public void reset() {
        size = level.size();
        sea = level.sea();
        land = level.land();
        hero = level.hero();
        marineEnemies = level.marineEnemies();
        landEnemies = level.landEnemies();
        getEnemies().forEach(e -> e.setField(this));
    }

    @Override
    public Hero getNewHero(Player player) {
        Hero hero = this.hero;
        hero.init(player);
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
    public boolean isInBounds(Point point) {
        return !point.isOutOf(size);
    }

    @Override
    public void tick() {
        players.stream()
                .map(Player::getHero)
                .forEach(hero -> {
                    hero.tick();
                    if (hero.isLanded()) {
                        foo(hero);
                        hero.clearTrace();
                        hero.clearDirection();
                    }
                });
        getEnemies().forEach(Enemy::tick);
        checkKill();
        checkWin();
    }

    private void checkWin() {
        if ((land.size() - level.landCellsCount()) * 1.0 /  level.seaCellsCount() > 0.60) {
            players.get(0).event(Event.WIN);
        }
    }

    private void checkKill() {
        boolean isKilled = getEnemies().stream()
                .anyMatch(e -> hero.getHitbox().contains(e));
        if (isKilled) {
            players.get(0).event(Event.GAME_OVER);
        }
    }

    public <T extends Point> Optional<T> found(List<T> items, Point pt) {
        return items.stream()
                .filter(pt::equals)
                .findFirst();
    }

    public <T extends Point> boolean anyMatch(List<T> items, Point pt) {
        return items.stream()
                .anyMatch(pt::equals);
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean canMove(Point from, Point to) {
        return true;
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

    private void turnToLand(Point point) {
        if (sea.remove(point)) {
            land.add(new Land(point));
        }
    }

    private void foo(Hero hero) {
        List<Trace> trace = hero.getTrace();
        if (trace.size() == 1) {
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

    private boolean isTrace(Point point) {
        return getHeroes().stream()
                .flatMap(hero -> hero.getTrace().stream())
                .anyMatch(trace -> trace.equals(point));
    }
}
