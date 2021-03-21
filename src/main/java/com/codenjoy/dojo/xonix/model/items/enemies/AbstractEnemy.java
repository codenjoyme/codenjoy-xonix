package com.codenjoy.dojo.xonix.model.items.enemies;

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

import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.xonix.model.Elements;
import com.codenjoy.dojo.xonix.model.Field;
import com.codenjoy.dojo.xonix.model.items.AbstractItem;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.codenjoy.dojo.services.Direction.*;
import static java.util.stream.Collectors.toList;

public abstract class AbstractEnemy extends AbstractItem implements Enemy {

    private final Function<Point, Boolean> barrier;
    protected Direction direction;
    protected Field field;

    protected AbstractEnemy(Point pt, Elements element, Dice dice, Field field, Function<Point, Boolean> barrier) {
        super(pt, element);
        this.field = field;
        this.barrier = barrier;
        this.direction = Direction.random(dice);
    }

    @Override
    public void direction(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction direction() {
        return direction;
    }

    @Override
    public List<Point> dangerArea() {
        Point pos = position();
        return Stream.of(
                pos,
                LEFT.change(pos),
                UP.change(pos),
                RIGHT.change(pos),
                DOWN.change(pos)
        ).filter(pt -> !barrier.apply(pt))
                .filter(pt -> !field.isOutOfBounds(pt))
                .collect(toList());
    }

    @Override
    public void tick() {
        if (direction == null) {
            return;
        }
        Point pt = position();
        int limiter = 4;
        do {
            if (barrier.apply(direction.change(pt))) {
                direction = direction.clockwise();
            } else if (barrier.apply(direction.clockwise().change(pt))) {
                direction = direction.counterClockwise();
            } else if (barrier.apply(diagonal(pt))) {
                direction = direction.inverted();
            } else if (field.isOutOfBounds(diagonal(pt))) {
                direction = direction.counterClockwise();
            } else {
                break;
            }
        } while (--limiter > 0);
        if (limiter != 0) {
            move(diagonal(pt));
        }
    }

    private Point diagonal(Point from) {
        Point dest = direction.change(from);
        switch (direction) {
            case LEFT:
                return UP.change(dest);
            case UP:
                return RIGHT.change(dest);
            case RIGHT:
                return DOWN.change(dest);
            case DOWN:
                return LEFT.change(dest);
            default:
                throw new IllegalStateException("Direction should be LEFT, RIGHT, UP or DOWN");
        }
    }
}
