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
import com.codenjoy.dojo.client.local.LocalGameRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.utils.Smoke;
import com.codenjoy.dojo.xonix.client.Board;
import com.codenjoy.dojo.xonix.client.ai.AISolver;
import com.codenjoy.dojo.xonix.services.GameRunner;
import com.codenjoy.dojo.xonix.services.GameSettings;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.IS_MULTIPLAYER;
import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.LEVELS_COUNT;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class SmokeTest {

    @Test
    public void test() {
        Dice dice = LocalGameRunner.getDice("435874345435874365843564398", 1000, 200);

        // about 2.8 sec
        int players = 2;
        int ticks = 1000;
        Supplier<Solver> solver = () -> new AISolver(dice);

        Smoke.play(ticks, "SmokeTest.data",
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
                Stream.generate(solver)
                        .limit(players).collect(toList()),
                Stream.generate(() -> new Board())
                        .limit(players).collect(toList()),
                (o1, o2) -> assertEquals(o1, o2));
    }
}