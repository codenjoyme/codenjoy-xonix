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
import com.codenjoy.dojo.services.State;
import com.codenjoy.dojo.services.joystick.NoActJoystick;
import com.codenjoy.dojo.services.multiplayer.PlayerHero;
import com.codenjoy.dojo.games.xonix.Element;
import com.codenjoy.dojo.xonix.model.items.Trace;

import java.util.*;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.games.xonix.Element.HERO;
import static com.codenjoy.dojo.games.xonix.Element.HOSTILE;
import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.LIVES_COUNT;

public class Hero extends PlayerHero<Field> implements State<Element, Player>, NoActJoystick {

    private final Point start;
    private final Player player;

    private List<Trace> trace = new ArrayList<>();
    private boolean alive = true;
    private boolean win = false;
    private int lives;
    private Direction direction;
    private Hero victim;

    public Hero(Point pt, Player player) {
        super(pt);
        this.start = pt;
        this.player = player;
    }

    @Override
    public void init(Field field) {
        this.field = field;
        lives = settings().integer(LIVES_COUNT);
    }

    @Override
    public void up() {
        direction = UP;
    }

    @Override
    public void down() {
        direction = DOWN;
    }

    @Override
    public void left() {
        direction = LEFT;
    }

    @Override
    public void right() {
        direction = RIGHT;
    }

    @Override
    public void tick() {
        victim = null;

        if (direction == null) {
            return;
        }
        Point dest = direction.change(this);
        if (dest.isOutOf(field.size())) {
            direction = null;
            return;
        }
        if (trace().contains(dest)) {
            die();
            return;
        }
        if (!isOnOwnLand()) {
            trace.add(new Trace(this));
        }
        move(dest);
    }

    @Override
    public Element state(Player painter, Object... alsoAtPoint) {
        return player.equals(painter)
                ? HERO
                : HOSTILE;
    }

    public void respawn(Point point) {
        move(point);
        alive = true;
    }

    public boolean isLanded() {
        return !trace.isEmpty() && isOnOwnLand();
    }

    public void die() {
        if (!alive) {
            return;
        }
        alive = false;
        direction = null;
        clearTrace();
        lives--;
    }

    public Point start() {
        return start;
    }

    public Player player() {
        return player;
    }

    public boolean isOnOwnLand() {
        return field.isHeroLand(this, this);
    }

    public void clearTrace() {
        trace = new ArrayList<>();
    }

    public void clearDirection() {
        this.direction = null;
    }

    public void win(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        return isAlive() && win;
    }

    public int lives() {
        return lives;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public List<Trace> trace() {
        return trace;
    }

    public Direction direction() {
        return direction;
    }

    public Hero victim() {
        return victim;
    }

    public void kill(Hero enemy) {
        victim = enemy;
        enemy.die();
    }

    public void capture() {
        if (trace.size() == 1) {
            field.turnToLand(trace.get(0), this);
            return;
        }
        Direction wave = direction.counterClockwise();
        Trace last = trace.get(trace.size() - 1);
        Collection<Point> area = area(wave.change(last));
        if (area.isEmpty()) {
            area = area(wave.inverted().change(last));
        }
        area.addAll(trace);
        area.forEach(pt -> field.turnToLand(pt, this));
    }

    private Set<Point> area(Point start) {
        if (trace.contains(start)) {
            return new HashSet<>();
        }
        Set<Point> visited = new HashSet<>();
        Queue<Point> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            if (field.enemies().contains(point)) {
                return new HashSet<>();
            }
            around(point).stream()
                    .filter(pt -> !visited.contains(pt))
                    .filter(pt -> !field.isTrace(pt))
                    .filter(pt -> !field.isHeroLand(pt, this))
                    .filter(pt -> !pt.isOutOf(field.size()))
                    .forEach(pt -> {
                        queue.offer(pt);
                        visited.add(pt);
                    });
        }
        return visited;
    }

    private List<Point> around(Point point) {
        Point left = LEFT.change(point);
        Point up = UP.change(point);
        Point right = RIGHT.change(point);
        Point down = DOWN.change(point);
        return Arrays.asList(up, down, left, right);
    }
}