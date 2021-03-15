package com.codenjoy.dojo.xonix.model;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.xonix.model.items.AbstractItem;

public class LandEnemy extends AbstractItem implements Enemy {
    private Field field;
    private Direction direction;

    public LandEnemy(Point pt) {
        super(pt, Elements.LAND_ENEMY);
        this.direction = Direction.random();
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

    @Override
    public void tick() {
        if (direction == null) {
            return;
        }
        Point position = getPosition();
        int limiter = 4;
        while ((field.isSea(diagonalStep(position))
                || field.isSea(direction.change(position))
                || field.isSea(direction.clockwise().change(position))
                || !field.isInBounds(diagonalStep(position)))
                && limiter > 0) {
            if (field.isSea(direction.change(position))) {
                direction = direction.clockwise();
            } else if (field.isSea(direction.clockwise().change(position))) {
                direction = direction.counterClockwise();
            } else if (field.isSea(diagonalStep(position))) {
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
