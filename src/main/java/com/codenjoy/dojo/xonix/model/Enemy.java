package com.codenjoy.dojo.xonix.model;

import com.codenjoy.dojo.services.Direction;

public interface Enemy {
    void tick();
    void setField(Field field);
    void setDirection(Direction direction);
    Direction getDirection();
}
