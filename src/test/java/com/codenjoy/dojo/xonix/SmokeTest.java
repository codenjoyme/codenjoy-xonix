package com.codenjoy.dojo.xonix;

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
import com.codenjoy.dojo.games.xonix.Board;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.utils.Smoke;
import com.codenjoy.dojo.xonix.services.GameRunner;
import com.codenjoy.dojo.xonix.services.GameSettings;
import com.codenjoy.dojo.xonix.services.ai.AISolver;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.IS_MULTIPLAYER;
import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.LEVELS_COUNT;

public class SmokeTest {

    private Smoke smoke;
    private Dice dice;
    private Supplier<Solver> solver;

    @Before
    public void setup() {
        smoke = new Smoke();
        dice = smoke.dice();
        solver = () -> new AISolver(dice);
    }

    @Test
    public void test() {
        // about 2.8 sec
        int players = 2;
        int ticks = 1000;

        smoke.play(ticks, "SmokeTest.data",
                new GameRunner() {
                    @Override
                    public Dice getDice() {
                        return dice;
                    }

                    @Override
                    public GameSettings getSettings() {
                        GameSettings settings = new GameSettings();
                        settings.bool(IS_MULTIPLAYER, true)
                                .integer(LEVELS_COUNT, 1)
                                .string(() -> settings.levelName(1),
                                        "XXXXXXXXXXOXXXXXXXXX\n" +
                                        "XXXXXXXXXXXXXXXXXXXX\n" +
                                        "XX................XX\n" +
                                        "XX................XX\n" +
                                        "XX................XX\n" +
                                        "XX................XX\n" +
                                        "XX................XX\n" +
                                        "XX................XX\n" +
                                        "XX...M............XX\n" +
                                        "XX................XX\n" +
                                        "LX................XL\n" +
                                        "XX..........M.....XX\n" +
                                        "XX................XX\n" +
                                        "XX................XX\n" +
                                        "XX................XX\n" +
                                        "XX................XX\n" +
                                        "XX....M...........XX\n" +
                                        "XX................XX\n" +
                                        "XXXXXXXXXXXXXXXXXXXX\n" +
                                        "XXXXXXXXXXOXXXXXXXXX\n");
                        return settings;
                    }
                },
                players, solver, Board::new);
    }
}