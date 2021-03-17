package com.codenjoy.dojo.xonix.model.items;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
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
import com.codenjoy.dojo.xonix.model.Elements;
import com.codenjoy.dojo.xonix.model.Field;

public class MarineEnemy extends AbstractItem implements Enemy {
    private Field field;
    private Direction direction;

    public MarineEnemy(Point pt) {
        super(pt, Elements.MARINE_ENEMY);
        this.direction = Direction.random();
    }

    @Override
    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    private Point diagonalStep(Point point) {
        Point position = direction.change(point);
        switch (direction) {
            case LEFT:
                return Direction.UP.change(position);
            case UP:
                return Direction.RIGHT.change(position);
            case RIGHT:
                return Direction.DOWN.change(position);
            case DOWN:
                return Direction.LEFT.change(position);
            default:
                return null;
        }
    }

    @Override
    public void tick() {
        if (direction == null) {
            return;
        }
        Point position = getPosition();
        int limiter = 4;
        while ((field.isLand(diagonalStep(position))
                || field.isLand(direction.change(position))
                || field.isLand(direction.clockwise().change(position))
                || field.isOutOfBounds(diagonalStep(position)))
                && limiter > 0) {
            if (field.isLand(direction.change(position))) {
                direction = direction.clockwise();
            } else if (field.isLand(direction.clockwise().change(position))) {
                direction = direction.counterClockwise();
            } else if (field.isLand(diagonalStep(position))) {
                direction = direction.inverted();
            } else {
                direction = direction.counterClockwise();
            }
            limiter--;
        }
        if (limiter != 0) {
            move(diagonalStep(position));
        }

    }
}
