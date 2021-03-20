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


import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.xonix.model.items.*;
import com.codenjoy.dojo.xonix.model.items.enemies.Enemy;
import com.codenjoy.dojo.xonix.model.items.enemies.LandEnemy;
import com.codenjoy.dojo.xonix.model.items.enemies.MarineEnemy;
import com.codenjoy.dojo.xonix.model.level.Level;
import com.codenjoy.dojo.xonix.services.Event;
import com.codenjoy.dojo.xonix.services.GameSettings;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.VICTORY_CRITERION;
import static java.util.stream.Collectors.toList;

public class XonixGame implements Field {

    private final List<Player> players = new LinkedList<>();
    private final GameSettings settings;
    private final Level level;
    private final Dice dice;

    private List<Sea> sea;
    private List<Land> land;
    private List<LandEnemy> landEnemies;
    private List<MarineEnemy> marineEnemies;

    public XonixGame(Level level, GameSettings settings, Dice dice) {
        this.settings = settings;
        this.level = level;
        this.dice = dice;
        reset();
    }

    @Override
    public void tick() {
        step();
        act();
        check();
    }

    private void step() {
        getHeroes().forEach(Hero::tick);
        getEnemies().forEach(Enemy::tick);
    }

    private void act() {
        for (Hero hero: getHeroes()) {

            boolean botEnemyInHitbox = hero.isFloating()
                    ? marineEnemies.stream().anyMatch(hero::isInHitbox)
                    : landEnemies.stream().anyMatch(hero::isInHitbox);

            if (botEnemyInHitbox) {
                hero.die();
                return;
            }

            getHeroes().stream()
                    .filter(enemy -> !enemy.getStartPosition().equals(hero.getStartPosition()))
                    .filter(enemy -> enemy.getTrace().contains(hero) || enemy.equals(hero))
                    .forEach(hero::kill);

            if (hero.isLanded()) {
                seizeSea(hero);
                hero.clearTrace();
                hero.clearDirection();
            }
        }
    }

    private void check() {
        getHeroes().forEach(hero -> {
            Player player = hero.getPlayer();
            if (hero.isKilled()) {
                if (hero.getLives() == 0) {
                    player.event(Event.GAME_OVER);
                    return;
                }
                player.event(Event.KILLED);
                hero.respawn(hero.getStartPosition());
                if (!settings.isMultiplayer()) {
                    resetLandEnemies();
                }
                return;
            }
            if (hero.getVictim() != null) {
                hero.getPlayer().event(Event.ANNIHILATION);
            }
            if (!settings.isMultiplayer() && isHeroWon()) {
                player.event(Event.WIN);
                hero.setWon(true);
            }
        });
    }

    @Override
    public GameSettings settings() {
        return settings;
    }

    @Override
    public Hero createNewHero(Player player) {
        List<Point> starts = level.startPositions();
        Point start = starts.stream()
                .filter(p -> !getHeroes().contains(p))
                .findAny()
                .orElseThrow(IllegalStateException::new);
        Hero hero = new Hero(start, player);
        hero.init(this);
        land.stream()
                .filter(p -> p.equals(start))
                .findFirst()
                .ifPresent(l -> l.setOwner(hero));
        land.addAll(level.xonixLand(hero));
        return hero;
    }

    @Override
    public boolean isLand(Point point) {
        return land.contains(point);
    }

    @Override
    public boolean isHeroLand(Point point, Hero hero) {
        Land land = this.land.stream()
                .filter(p -> p.equals(point))
                .findFirst()
                .orElse(null);
        if (land == null) {
            return false;
        }
        return hero.equals(land.getOwner());
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
    public void newGame(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
        player.newHero(this);
    }

    @Override
    public void remove(Player player) {
        players.remove(player);
    }

    @Override
    public List<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = Lists.newArrayList(this.marineEnemies);
        enemies.addAll(landEnemies);
        return enemies;
    }

    @Override
    public BoardReader reader() {
        return new BoardReader() {

            private List<Trace> getTraces() {
                return getHeroes().stream()
                        .filter(Objects::nonNull)
                        .map(Hero::getTrace)
                        .flatMap(Collection::stream)
                        .collect(toList());
            }

            @Override
            public int size() {
                return XonixGame.this.getSize();
            }

            @Override
            public Iterable<? extends Point> elements() {
                return new LinkedList<>() {{
                    addAll(getHeroes());
                    addAll(getTraces());
                    addAll(XonixGame.this.marineEnemies);
                    addAll(XonixGame.this.landEnemies);
                    addAll(XonixGame.this.sea);
                    addAll(XonixGame.this.land);
                }};
            }
        };
    }

    public int getSize() {
        return level.size();
    }

    private List<Hero> getHeroes() {
        return players.stream()
                .map(Player::getHero)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private void reset() {
        sea = level.sea();
        land = level.freeLand();
        resetMarineEnemies();
        resetLandEnemies();
    }

    private void resetMarineEnemies() {
        marineEnemies = level.marineEnemyPositions().stream()
                .map(p -> new MarineEnemy(p, XonixGame.this, dice))
                .collect(Collectors.toList());
    }

    private void resetLandEnemies() {
        landEnemies = level.landEnemyPositions().stream()
                .map(p -> new LandEnemy(p, XonixGame.this, dice))
                .collect(Collectors.toList());
    }

    private boolean isHeroWon() {
        Hero lastHero = getHeroes().get(0);
        long seizedLand = land.stream()
                .filter(l -> lastHero.equals(l.getOwner()))
                .count() - level.xonixLand(lastHero).size();
        double percentOfSeized = 100.0 * seizedLand / level.sea().size();
        return percentOfSeized >= settings.integer(VICTORY_CRITERION);
    }

    private void seizeSea(Hero hero) {
        List<Trace> trace = hero.getTrace();
        if (trace.size() == 1) { // Like original
            turnToLand(trace.get(0), hero);
            return;
        }
        Direction firstWaveDirection = hero.getDirection()
                .counterClockwise();
        Trace lastTraceCell = trace.get(trace.size() - 1);
        Collection<Point> points = breadthFirstSearch(firstWaveDirection.change(lastTraceCell), hero);
        if (points.size() == 0) {
            points = breadthFirstSearch(firstWaveDirection.inverted().change(lastTraceCell), hero);
        }
        points.addAll(trace);
        points.forEach(p -> turnToLand(p, hero));
    }

    private Collection<Point> breadthFirstSearch(Point start, Hero hero) {
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
                    .filter(p -> !isHeroLand(p, hero))
                    .forEach(queue::offer);
        }
        return visited;
    }

    private void turnToLand(Point point, Hero hero) {
        if (sea.remove(point)) {
            Land land = new Land(point);
            land.setOwner(hero);
            this.land.add(land);
        }
        land.stream()
                .filter(p -> p.equals(point))
                .findFirst()
                .ifPresent(l -> l.setOwner(hero));
    }

    private boolean isTrace(Point point) {
        return getHeroes().stream()
                .flatMap(hero -> hero.getTrace().stream())
                .anyMatch(trace -> trace.equals(point));
    }
}
