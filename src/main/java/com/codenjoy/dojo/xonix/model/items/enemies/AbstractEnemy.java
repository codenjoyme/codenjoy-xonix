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
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.QDirection;
import com.codenjoy.dojo.xonix.model.Elements;
import com.codenjoy.dojo.xonix.model.Field;
import com.codenjoy.dojo.xonix.model.items.AbstractItem;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.codenjoy.dojo.services.Direction.*;
import static java.util.stream.Collectors.toList;

public abstract class AbstractEnemy extends AbstractItem implements Enemy {

    private final Function<Point, Boolean> isBarrier;
    protected QDirection direction;
    protected Field field;

    protected AbstractEnemy(Point pt, Elements element, Dice dice, Field field, Function<Point, Boolean> isBarrier) {
        super(pt, element);
        this.field = field;
        this.isBarrier = isBarrier;
        this.direction = QDirection.valueOf(4 + dice.next(4));
    }

    @Override
    public void direction(QDirection direction) {
        this.direction = direction;
    }

    @Override
    public QDirection direction() {
        return direction;
    }

    @Override
    public List<Point> dangerArea() {
        return Stream.of(
                this.copy(),
                LEFT.change(this),
                UP.change(this),
                RIGHT.change(this),
                DOWN.change(this)
        ).filter(pt -> !isBarrier.apply(pt))
                .filter(pt -> !pt.isOutOf(field.size()))
                .collect(toList());
    }

    // WARNING: QDirection's method clockwise is counterClockwise and contrClockwise is clockwise!
    @Override
    public void tick() {
        if (direction == null) {
            return;
        }
        Boolean lastTurnClockwise = null;
        int limit = 4;
        do {
            Point destination = direction.change(this);
            Point left = direction.clockwise().change(this);
            Point right = direction.contrClockwise().change(this);
            if (!blocked(destination) && !blocked(left) && !blocked(right)) {
                move(direction.change(this));
                return;
            }
            // trying to find a way to go
            if (blocked(left) && blocked(right)) {
                direction = direction.inverted();
            } else if (blocked(right)) {
                direction = direction.clockwise().clockwise();
                lastTurnClockwise = false;
            } else if (blocked(left)) {
                direction = direction.contrClockwise().contrClockwise();
                lastTurnClockwise = true;
            } else {
                if (lastTurnClockwise != null && lastTurnClockwise) {
                    direction = direction.contrClockwise().contrClockwise();
                } else {
                    direction = direction.clockwise().clockwise();
                }
            }
        } while (limit-- > 0);
    }

    private boolean blocked(Point point) {
        if (point.isOutOf(field.size())) {
            return true;
        }
        return isBarrier.apply(point);
    }
}
