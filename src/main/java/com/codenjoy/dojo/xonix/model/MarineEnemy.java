package com.codenjoy.dojo.xonix.model;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.xonix.model.items.AbstractItem;

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
                || !field.isInBounds(diagonalStep(position)))
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
