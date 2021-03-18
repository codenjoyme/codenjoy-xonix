package com.codenjoy.dojo.xonix.client;

import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.xonix.client.ai.AISolver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AITest {
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
