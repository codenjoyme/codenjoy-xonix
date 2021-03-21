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
import com.codenjoy.dojo.services.joystick.NoActJoystick;
import com.codenjoy.dojo.services.multiplayer.PlayerHero;
import com.codenjoy.dojo.xonix.model.items.Trace;

import java.util.ArrayList;
import java.util.List;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.xonix.model.Elements.HERO;
import static com.codenjoy.dojo.xonix.model.Elements.HOSTILE;
import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.LIVES_COUNT;

public class Hero extends PlayerHero<Field> implements State<Elements, Player>, NoActJoystick {

    private Point start;
    private Player player;
    private Direction direction;
    private List<Trace> trace = new ArrayList<>();
    private boolean killed = false;
    private boolean won = false;
    private int lives;
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
    public Elements state(Player painter, Object... alsoAtPoint) {
        return player.equals(painter)
                ? HERO
                : HOSTILE;
    }

    public void respawn(Point point) {
        move(point);
        killed = false;
    }


    public boolean isLanded() {
        return !trace.isEmpty() && isOnOwnLand();
    }

    public void die() {
        if (killed) {
            return;
        }
        killed = true;
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

    public void setWon(boolean value) {
        this.won = value;
    }

    public boolean isWon() {
        return !killed && won;
    }

    public int lives() {
        return lives;
    }

    public boolean isKilled() {
        return killed;
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
}
