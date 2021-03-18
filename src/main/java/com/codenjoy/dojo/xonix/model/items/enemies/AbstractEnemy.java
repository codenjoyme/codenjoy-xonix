package com.codenjoy.dojo.xonix.model.items.enemies;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.xonix.model.Elements;
import com.codenjoy.dojo.xonix.model.Field;
import com.codenjoy.dojo.xonix.model.items.AbstractItem;

import java.util.function.Function;

public abstract class AbstractEnemy extends AbstractItem implements Enemy {

    private final Function<Point, Boolean> barrierChecker;
    protected Direction direction;
    protected Field field;

    protected AbstractEnemy(Point pt, Elements element, Field field, Function<Point, Boolean> barrierChecker) {
        super(pt, element);
        this.field = field;
        this.barrierChecker = barrierChecker;
        this.direction = Direction.UP;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void tick() {
        if (direction == null) {
            return;
        }
        Point position = getPosition();
        int limiter = 4;
        while ((barrierChecker.apply(diagonalStep(position))
                || barrierChecker.apply(direction.change(position))
                || barrierChecker.apply(direction.clockwise().change(position))
                || field.isOutOfBounds(diagonalStep(position)))
                && limiter > 0) {
            if (barrierChecker.apply(direction.change(position))) {
                direction = direction.clockwise();
            } else if (barrierChecker.apply(direction.clockwise().change(position))) {
                direction = direction.counterClockwise();
            } else if (barrierChecker.apply(diagonalStep(position))) {
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
                throw new IllegalStateException("Direction should be LEFT, RIGHT, UP or DOWN");
        }
    }
}
