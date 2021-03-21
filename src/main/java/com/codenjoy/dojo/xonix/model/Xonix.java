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

import java.util.*;
import java.util.stream.Stream;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.services.QDirection.RIGHT;
import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.WIN_CRITERION;
import static java.util.stream.Collectors.toList;

public class Xonix implements Field {

    private final List<Player> players = new LinkedList<>();
    private final GameSettings settings;
    private final Level level;
    private final Dice dice;

    private List<Sea> oceans;
    private List<Land> islands;
    private List<LandEnemy> landEnemies;
    private List<MarineEnemy> marineEnemies;

    public Xonix(Level level, GameSettings settings, Dice dice) {
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
        heroes().forEach(Hero::tick);
        enemies().forEach(Enemy::tick);
    }

    private void act() {
        for (Hero hero: heroes()) {

            if (enemyInHitBox(hero)) {
                hero.die();
                return;
            }

            heroes().stream()
                    .filter(enemy -> !enemy.start().equals(hero.start()))
                    .filter(enemy -> enemy.trace().contains(hero) || enemy.equals(hero))
                    .forEach(hero::kill);

            if (hero.isLanded()) {
                seizeSea(hero);
                hero.clearTrace();
                hero.clearDirection();
            }
        }
    }

    private boolean enemyInHitBox(Hero hero) {
        return enemies().stream()
                .map(Enemy::dangerArea)
                .flatMap(List::stream)
                .anyMatch(pt -> hero.equals(pt) || hero.trace().contains(pt));
    }

    private void check() {
        heroes().forEach(hero -> {
            Player player = hero.player();
            if (hero.isKilled()) {
                if (hero.lives() == 0) {
                    player.event(Event.GAME_OVER);
                    return;
                }
                player.event(Event.DIE);
                hero.respawn(hero.start());
                if (!settings.isMultiplayer()) {
                    resetLandEnemies();
                }
                return;
            }
            if (hero.victim() != null) {
                hero.player().event(Event.ANNIHILATION);
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
    public Hero newHero(Player player) {
        Point start = freeStart();
        Hero hero = new Hero(start, player);
        hero.init(this);

        recolorLand(start, hero);
        islands.addAll(level.heroLand(hero));
        return hero;
    }

    public void recolorLand(Point start, Hero hero) {
        islands.stream()
                .filter(pt -> pt.equals(start))
                .findFirst()
                .ifPresent(land -> land.owner(hero));
    }

    public Point freeStart() {
        return level.start().stream()
                .filter(pt -> !heroes().contains(pt))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean isLand(Point pt) {
        return islands.contains(pt);
    }

    @Override
    public boolean isHeroLand(Point pt, Hero hero) {
        Land land = islands.stream()
                .filter(point -> point.equals(pt))
                .findFirst()
                .orElse(null);
        if (land == null || land.owner() == null) {
            return false;
        }
        Point start = land.owner().start();
        return hero.start().equals(start);
    }

    @Override
    public boolean isSea(Point pt) {
        return oceans.contains(pt);
    }

    @Override
    public boolean isOutOfBounds(Point pt) {
        return pt.isOutOf(level.size());
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
    public List<Enemy> enemies() {
        List<Enemy> result = new LinkedList<>();
        result.addAll(marineEnemies);
        result.addAll(landEnemies);
        return result;
    }

    private List<Trace> getTraces() {
        return heroes().stream()
                .filter(Objects::nonNull)
                .map(Hero::trace)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    @Override
    public BoardReader reader() {
        return new BoardReader() {
            @Override
            public int size() {
                return Xonix.this.size();
            }

            @Override
            public Iterable<? extends Point> elements() {
                return new LinkedList<>() {{
                    addAll(heroes());
                    addAll(getTraces());
                    addAll(marineEnemies);
                    addAll(landEnemies);
                    addAll(oceans);
                    addAll(islands);
                }};
            }
        };
    }

    public int size() {
        return level.size();
    }

    private List<Hero> heroes() {
        return players.stream()
                .map(Player::getHero)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private void reset() {
        oceans = level.sea();
        islands = level.freeLand();
        resetMarineEnemies();
        resetLandEnemies();
    }

    private void resetMarineEnemies() {
        marineEnemies = level.marineEnemy().stream()
                .map(pt -> new MarineEnemy(pt, Xonix.this, dice))
                .collect(toList());
    }

    private void resetLandEnemies() {
        landEnemies = level.landEnemy().stream()
                .map(pt -> new LandEnemy(pt, Xonix.this, dice))
                .collect(toList());
    }

    private boolean isHeroWon() {
        Hero last = heroes().get(0);
        long seized = islands.stream()
                .filter(land -> last.equals(land.owner()))
                .count() - level.heroLand(last).size();
        double percent = 100.0 * seized / level.sea().size();
        return percent >= settings.integer(WIN_CRITERION);
    }

    private void seizeSea(Hero hero) {
        List<Trace> trace = hero.trace();
        if (trace.size() == 1) { // Like original
            turnToLand(trace.get(0), hero);
            return;
        }
        Direction firstWave = hero.direction().counterClockwise();
        Trace last = trace.get(trace.size() - 1);
        Collection<Point> points = breadthFirstSearch(firstWave.change(last), hero);
        if (points.size() == 0) {
            points = breadthFirstSearch(firstWave.inverted().change(last), hero);
        }
        points.addAll(trace);
        points.forEach(pt -> turnToLand(pt, hero));
    }

    private Collection<Point> breadthFirstSearch(Point start, Hero hero) {
        Deque<Point> queue = new ArrayDeque<>();
        Set<Point> visited = new HashSet<>();
        if (hero.trace().contains(start)) {
            return visited;
        }
        queue.offer(start);
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            if (enemies().contains(point)) {
                return new LinkedList<>();
            }
            visited.add(point);

            Point left = LEFT.change(point);
            Point up = UP.change(point);
            Point right = RIGHT.change(point);
            Point down = DOWN.change(point);
            Stream.of(up, down, left, right)
                    .filter(pt -> !visited.contains(pt))
                    .filter(pt -> !isTrace(pt))
                    .filter(pt -> !isHeroLand(pt, hero))
                    .forEach(queue::offer);
        }
        return visited;
    }

    private void turnToLand(Point pt, Hero hero) {
        if (oceans.remove(pt)) {
            Land land = new Land(pt);
            land.owner(hero);
            islands.add(land);
            return;
        }
        recolorLand(pt, hero);
    }

    private boolean isTrace(Point pt) {
        return heroes().stream()
                .flatMap(hero -> hero.trace().stream())
                .anyMatch(trace -> trace.equals(pt));
    }
}
