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
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.State;
import com.codenjoy.dojo.services.multiplayer.PlayerHero;
import com.codenjoy.dojo.xonix.model.items.Trace;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.LIVES_COUNT;

public class Hero extends PlayerHero<Field> implements State<Elements, Player> {

    private Direction direction;
    private List<Trace> trace = new ArrayList<>();
    private boolean isKilled = false;
    private boolean isWon = false;
    private int lives;

    public Hero(Point pt) {
        super(pt);
    }

    @Override
    public void init(Field field) {
        this.field = field;
        lives = settings().integer(LIVES_COUNT);
    }

    @Override
    public void up() {
        direction = Direction.UP;
    }

    @Override
    public void down() {
        direction = Direction.DOWN;
    }

    @Override
    public void left() {
        direction = Direction.LEFT;
    }

    @Override
    public void right() {
        direction = Direction.RIGHT;
    }

    @Override
    public void act(int... p) {
        // The hero can only move
    }

    @Override
    public void tick() {
        if (direction == null) {
            return;
        }
        Point destination = direction.change(this);
        if (field.isOutOfBounds(destination)) {
            direction = null;
            return;
        }
        if (getTrace().contains(destination)) {
            die();
            return;
        }
        if (isFloating()) {
            trace.add(new Trace(getPosition()));
        }
        move(destination);
    }

    @Override
    public Elements state(Player player, Object... alsoAtPoint) {
        return Elements.XONIX;
    }

    public void respawn(Point point) {
        move(point);
        isKilled = false;
    }

    public boolean isLanded() {
        return !trace.isEmpty() && !isFloating();
    }

    public void die() {
        if (isKilled) {
            return;
        }
        isKilled = true;
        lives--;
        direction = null;
        clearTrace();
    }

    public List<Point> getHitbox() {
        Point position = getPosition();
        Point left = Direction.LEFT.change(position);
        Point up = Direction.UP.change(position);
        Point right = Direction.RIGHT.change(position);
        Point down = Direction.DOWN.change(position);
        HashSet<Point> hitbox = Sets.newHashSet(position, left, up, right, down);
        hitbox.addAll(getTraceHitbox());
        return Lists.newArrayList(hitbox);
    }

    public boolean isFloating() {
        return field.isSea(getPosition());
    }

    public void clearTrace() {
        trace = new ArrayList<>();
    }

    public void clearDirection() {
        this.direction = null;
    }

    public void setWon(boolean value) {
        this.isWon = value;
    }

    public boolean isWon() {
        return !isKilled && isWon;
    }

    public int getLives() {
        return lives;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public List<Trace> getTrace() {
        return trace;
    }

    public Direction getDirection() {
        return direction;
    }

    public Point getPosition() {
        return PointImpl.pt(x, y);
    }

    private List<Point> getTraceHitbox() {
        return trace.stream()
                .flatMap(tr -> Stream.of(
                        tr,
                        Direction.LEFT.change(tr),
                        Direction.UP.change(tr),
                        Direction.RIGHT.change(tr),
                        Direction.DOWN.change(tr))
                ).filter(field::isSea)
                .collect(Collectors.toList());
    }
}
