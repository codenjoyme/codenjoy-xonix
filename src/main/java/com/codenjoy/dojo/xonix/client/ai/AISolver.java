package com.codenjoy.dojo.xonix.client.ai;

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


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.algs.DeikstraFindWay;
import com.codenjoy.dojo.xonix.client.Board;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AISolver implements Solver<Board> {

    private DeikstraFindWay way;
    private Dice dice;

    public AISolver(Dice dice) {
        this.dice = dice;
        this.way = new DeikstraFindWay();
    }

    @Override
    public String get(final Board board) {
        return Direction.random(dice).toString();
    }
}
