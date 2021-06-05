package com.codenjoy.dojo.xonix.services.ai;

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

import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.xonix.client.Board;
import com.codenjoy.dojo.xonix.services.ai.AISolver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AISolverTest {

    private Dice dice;
    private Solver ai;

    @Before
    public void setup() {
        dice = mock(Dice.class);
        mockDirection(Direction.UP);
        ai = new AISolver(dice);
    }

    private Board board(String board) {
        return (Board) new Board().forString(board);
    }

    @Test
    public void should() {
        assertAI(
                "##L####" +
                "####O##" +
                "#.....#" +
                "#..M..#" +
                "#.....#" +
                "#.....#" +
                "#######", Direction.RIGHT);

        assertAI(
                "##L###L" +
                "####O##" +
                "#.....#" +
                "#.....#" +
                "#.....#" +
                "#.....#" +
                "#######", Direction.DOWN);

        assertAI(
                "######L" +
                "####O##" +
                "#..M..#" +
                "#.....#" +
                "#.....#" +
                "#.....#" +
                "#######", Direction.LEFT);

        assertAI(
                "######L" +
                "#######" +
                "#.Ooo.#" +
                "#.....#" +
                "#.M...#" +
                "#.....#" +
                "#######", Direction.UP);
    }

    private void assertAI(String board, Direction expected) {
        String actual = ai.get(board(board));

        assertEquals(expected == null ? "" : expected.toString(), actual);
    }

    private void mockDirection(Direction direction) {
        when(dice.next(anyInt())).thenReturn(direction.value());
    }
}
