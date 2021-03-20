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
import com.google.common.collect.Queues;

import java.util.*;
import java.util.stream.Collectors;
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

    private List<Sea> sea;
    private List<Land> land;
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

            boolean botEnemyInHitbox = enemies().stream()
                    .map(Enemy::dangerArea)
                    .flatMap(List::stream)
                    .anyMatch(p -> hero.equals(p) || hero.trace().contains(p));

            if (botEnemyInHitbox) {
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
    public Hero createNewHero(Player player) {
        List<Point> starts = level.start();
        Point start = starts.stream()
                .filter(p -> !heroes().contains(p))
                .findAny()
                .orElseThrow(IllegalStateException::new);
        Hero hero = new Hero(start, player);
        hero.init(this);
        land.stream()
                .filter(p -> p.equals(start))
                .findFirst()
                .ifPresent(l -> l.owner(hero));
        land.addAll(level.heroLand(hero));
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
        if (land == null || land.getOwner() == null) {
            return false;
        }
        Point startPosition = land.getOwner().start();
        return hero.start().equals(startPosition);
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
    public List<Enemy> enemies() {
        List<Enemy> enemies = new LinkedList<>();
        enemies.addAll(marineEnemies);
        enemies.addAll(landEnemies);
        return enemies;
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
                return Xonix.this.getSize();
            }

            @Override
            public Iterable<? extends Point> elements() {
                return new LinkedList<>() {{
                    addAll(heroes());
                    addAll(getTraces());
                    addAll(marineEnemies);
                    addAll(landEnemies);
                    addAll(sea);
                    addAll(land);
                }};
            }
        };
    }

    public int getSize() {
        return level.size();
    }

    private List<Hero> heroes() {
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
        marineEnemies = level.marineEnemy().stream()
                .map(p -> new MarineEnemy(p, Xonix.this, dice))
                .collect(Collectors.toList());
    }

    private void resetLandEnemies() {
        landEnemies = level.landEnemy().stream()
                .map(p -> new LandEnemy(p, Xonix.this, dice))
                .collect(Collectors.toList());
    }

    private boolean isHeroWon() {
        Hero lastHero = heroes().get(0);
        long seizedLand = land.stream()
                .filter(l -> lastHero.equals(l.getOwner()))
                .count() - level.heroLand(lastHero).size();
        double percentOfSeized = 100.0 * seizedLand / level.sea().size();
        return percentOfSeized >= settings.integer(WIN_CRITERION);
    }

    private void seizeSea(Hero hero) {
        List<Trace> trace = hero.trace();
        if (trace.size() == 1) { // Like original
            turnToLand(trace.get(0), hero);
            return;
        }
        Direction firstWaveDirection = hero.direction()
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
        Deque<Point> queue = Queues.newArrayDeque();
        HashSet<Point> visited = new HashSet<>();
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

    private void turnToLand(Point point, Hero hero) {
        if (sea.remove(point)) {
            Land land = new Land(point);
            land.owner(hero);
            this.land.add(land);
            return;
        }
        land.stream()
                .filter(pt -> pt.equals(point))
                .findFirst()
                .ifPresent(land -> land.owner(hero));
    }

    private boolean isTrace(Point point) {
        return heroes().stream()
                .flatMap(hero -> hero.trace().stream())
                .anyMatch(trace -> trace.equals(point));
    }
}
