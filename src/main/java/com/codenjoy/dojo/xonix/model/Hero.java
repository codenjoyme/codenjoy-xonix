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
import com.codenjoy.dojo.services.multiplayer.PlayerHero;
import com.codenjoy.dojo.xonix.model.items.Trace;

import java.util.ArrayList;
import java.util.List;

public class Hero extends PlayerHero<Field> implements State<Elements, Player> {

    private Direction direction;
    private Player player;
    private Point lastPointOnLand;
    private List<Trace> trace = new ArrayList<>();

    public List<Trace> getTrace() {
        return trace;
    }

    public Hero(Point pt) {
        super(pt);
    }

    public void init(Player player) {
        this.player = player;
    }

    @Override
    public void init(Field field) {
        this.field = field;
    }

    @Override
    public void down() {
        direction = Direction.DOWN;
    }

    @Override
    public void up() {
        direction = Direction.UP;
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
        if (direction != null) {
            tryMove(direction.change(this));
        }
    }

    public void tryMove(Point destination) {
        if (!isFloating() && field.isSea(destination)) {
            lastPointOnLand = this;
        } else if (isFloating() && field.isSea(destination)) {
            trace.add(new Trace(this));
        } else if (isFloating() && field.isLand(destination)) {
            trace.add(new Trace(this));
        }
        move(destination);
    }

    public boolean isFloating() {
        return lastPointOnLand != null;
    }

    @Override
    public Elements state(Player player, Object... alsoAtPoint) {
        return Elements.XONIX;
    }
}
