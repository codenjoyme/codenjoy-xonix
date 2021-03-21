package com.codenjoy.dojo.xonix;

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

/*-
 * X%L
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
 * XL%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.local.LocalGameRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.xonix.client.Board;
import com.codenjoy.dojo.xonix.client.ai.AISolver;
import com.codenjoy.dojo.xonix.services.GameRunner;
import com.codenjoy.dojo.xonix.services.GameSettings;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.IS_MULTIPLAYER;
import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.LEVELS_COUNT;
import static org.junit.Assert.assertEquals;

public class SmokeTest {

    @Test
    public void testMultiplayer() {
        List<String> messages = new LinkedList<>();

        LocalGameRunner.timeout = 0;
        LocalGameRunner.out = messages::add;
        LocalGameRunner.countIterations = 50;
        LocalGameRunner.printDice = false;
        LocalGameRunner.printConversions = false;
        LocalGameRunner.printTick = true;
        LocalGameRunner.printBoardOnly = true;

        String soul = "580763458903465890346";
        Dice dice = LocalGameRunner.getDice(LocalGameRunner.generateXorShift(soul, 100, 200));

        GameRunner gameRunner = new GameRunner() {
            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            public GameSettings getSettings() {
                GameSettings settings = new GameSettings();
                return new GameSettings()
                        .bool(IS_MULTIPLAYER, true)
                        .integer(LEVELS_COUNT, 1)
                        .string(() -> settings.levelName(1),
                                "XXXXXXXXXXOXXXXXXXXX" +
                                "XXXXXXXXXXXXXXXXXXXX" +
                                "XX................XX" +
                                "XX................XX" +
                                "XX................XX" +
                                "XX................XX" +
                                "XX................XX" +
                                "XX................XX" +
                                "XX...M............XX" +
                                "XX................XX" +
                                "LX................XL" +
                                "XX..........M.....XX" +
                                "XX................XX" +
                                "XX................XX" +
                                "XX................XX" +
                                "XX................XX" +
                                "XX....M...........XX" +
                                "XX................XX" +
                                "XXXXXXXXXXXXXXXXXXXX" +
                                "XXXXXXXXXXOXXXXXXXXX");
            }
        };

        LocalGameRunner.run(gameRunner,
                new LinkedList<Solver>() {{
                    add(new AISolver(dice));
                    add(new AISolver(dice));
                }},
                new LinkedList<>(){{
                    add(new Board());
                    add(new Board());
                }});

        assertEquals("1: 1:XXXXXXXXXXOXXXXXXXXX\n" +
                        "1: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX...M............XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:LX................XL\n" +
                        "1: 1:XX..........M.....XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XX....M...........XX\n" +
                        "1: 1:XX................XX\n" +
                        "1: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "1: 1:XXXXXXXXXXAXXXXXXXXX\n" +
                        "1: 1:\n" +
                        "1: 1:Scores: 0\n" +
                        "1: 1:Answer: RIGHT\n" +
                        "1: 2:XXXXXXXXXXAXXXXXXXXX\n" +
                        "1: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX...M............XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:LX................XL\n" +
                        "1: 2:XX..........M.....XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XX....M...........XX\n" +
                        "1: 2:XX................XX\n" +
                        "1: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "1: 2:XXXXXXXXXXOXXXXXXXXX\n" +
                        "1: 2:\n" +
                        "1: 2:Scores: 0\n" +
                        "1: 2:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "2: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "2: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX..M.............LX\n" +
                        "2: 1:XX.........M......XX\n" +
                        "2: 1:XL................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX.....M..........XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XX................XX\n" +
                        "2: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "2: 1:XXXXXXXXXA@XXXXXXXXX\n" +
                        "2: 1:\n" +
                        "2: 1:Scores: 0\n" +
                        "2: 1:Answer: LEFT\n" +
                        "2: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "2: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX..M.............LX\n" +
                        "2: 2:XX.........M......XX\n" +
                        "2: 2:XL................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX.....M..........XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XX................XX\n" +
                        "2: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "2: 2:XXXXXXXXXO#XXXXXXXXX\n" +
                        "2: 2:\n" +
                        "2: 2:Scores: 0\n" +
                        "2: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "3: 1:XXXXXXXXXXO#XXXXXXXX\n" +
                        "3: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX................XL\n" +
                        "3: 1:XX........M.......XX\n" +
                        "3: 1:XX.M..............XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:LX................XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX......M.........XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XX................XX\n" +
                        "3: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "3: 1:XXXXXXXXX@AXXXXXXXXX\n" +
                        "3: 1:\n" +
                        "3: 1:Scores: 0\n" +
                        "3: 1:Answer: RIGHT\n" +
                        "3: 2:XXXXXXXXXXA@XXXXXXXX\n" +
                        "3: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX................XL\n" +
                        "3: 2:XX........M.......XX\n" +
                        "3: 2:XX.M..............XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:LX................XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX......M.........XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XX................XX\n" +
                        "3: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "3: 2:XXXXXXXXX#OXXXXXXXXX\n" +
                        "3: 2:\n" +
                        "3: 2:Scores: 0\n" +
                        "3: 2:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "4: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "4: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................LX\n" +
                        "4: 1:XX.......M........XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XXM...............XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XL.......M........XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XX................XX\n" +
                        "4: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "4: 1:XXXXXXXXX@AXXXXXXXXX\n" +
                        "4: 1:\n" +
                        "4: 1:Scores: 0\n" +
                        "4: 1:Answer: UP\n" +
                        "4: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "4: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................LX\n" +
                        "4: 2:XX.......M........XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XXM...............XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XL.......M........XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XX................XX\n" +
                        "4: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "4: 2:XXXXXXXXX#OXXXXXXXXX\n" +
                        "4: 2:\n" +
                        "4: 2:Scores: 0\n" +
                        "4: 2:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "5: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "5: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XL\n" +
                        "5: 1:XX......M.........XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX.M......M.......XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:LX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XX................XX\n" +
                        "5: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "5: 1:XXXXXXXXX@AXXXXXXXXX\n" +
                        "5: 1:\n" +
                        "5: 1:Scores: 0\n" +
                        "5: 1:Answer: UP\n" +
                        "5: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "5: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XL\n" +
                        "5: 2:XX......M.........XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX.M......M.......XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:LX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XX................XX\n" +
                        "5: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "5: 2:XXXXXXXXX#OXXXXXXXXX\n" +
                        "5: 2:\n" +
                        "5: 2:Scores: 0\n" +
                        "5: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "6: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "6: 1:XXXXXXXXXXXXXXXXXXXX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX................LX\n" +
                        "6: 1:XX.....M..........XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX.........M......XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX..M.............XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XL................XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XX................XX\n" +
                        "6: 1:XXXXXXXXXXAXXXXXXXXX\n" +
                        "6: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "6: 1:\n" +
                        "6: 1:Scores: 0\n" +
                        "6: 1:Answer: DOWN\n" +
                        "6: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "6: 2:XXXXXXXXXXXXXXXXXXXX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX................LX\n" +
                        "6: 2:XX.....M..........XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX.........M......XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX..M.............XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XL................XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XX................XX\n" +
                        "6: 2:XXXXXXXXXXOXXXXXXXXX\n" +
                        "6: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "6: 2:\n" +
                        "6: 2:Scores: 0\n" +
                        "6: 2:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "7: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "7: 1:XXXXXXXXXXXOXXXXXXXX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX................XL\n" +
                        "7: 1:XX....M...........XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX..........M.....XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XX...M............XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:LX................XX\n" +
                        "7: 1:XX................XX\n" +
                        "7: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "7: 1:XXXXXXXXX@AXXXXXXXXX\n" +
                        "7: 1:\n" +
                        "7: 1:Scores: 0\n" +
                        "7: 1:Answer: UP\n" +
                        "7: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "7: 2:XXXXXXXXXXXAXXXXXXXX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX................XL\n" +
                        "7: 2:XX....M...........XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX..........M.....XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XX...M............XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:LX................XX\n" +
                        "7: 2:XX................XX\n" +
                        "7: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "7: 2:XXXXXXXXX#OXXXXXXXXX\n" +
                        "7: 2:\n" +
                        "7: 2:Scores: 0\n" +
                        "7: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "8: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "8: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX................LX\n" +
                        "8: 1:XX...M............XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX...........M....XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XX....M...........XX\n" +
                        "8: 1:XX................XX\n" +
                        "8: 1:XL................XX\n" +
                        "8: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "8: 1:XXXXXXXXX@@AXXXXXXXX\n" +
                        "8: 1:\n" +
                        "8: 1:Scores: 0\n" +
                        "8: 1:Answer: DOWN\n" +
                        "8: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "8: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX................LX\n" +
                        "8: 2:XX...M............XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX...........M....XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XX....M...........XX\n" +
                        "8: 2:XX................XX\n" +
                        "8: 2:XL................XX\n" +
                        "8: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "8: 2:XXXXXXXXX##OXXXXXXXX\n" +
                        "8: 2:\n" +
                        "8: 2:Scores: 0\n" +
                        "8: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "9: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "9: 1:XXXXXXXXXXXOXXXXXXXX\n" +
                        "9: 1:XX................XL\n" +
                        "9: 1:XX..M.............XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX............M...XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:XX.....M..........XX\n" +
                        "9: 1:XX................XX\n" +
                        "9: 1:LXXXXXXXXX@XXXXXXXXX\n" +
                        "9: 1:XXXXXXXXX@@aAXXXXXXX\n" +
                        "9: 1:\n" +
                        "9: 1:Scores: 0\n" +
                        "9: 1:Answer: UP\n" +
                        "9: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "9: 2:XXXXXXXXXXXAXXXXXXXX\n" +
                        "9: 2:XX................XL\n" +
                        "9: 2:XX..M.............XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX............M...XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:XX.....M..........XX\n" +
                        "9: 2:XX................XX\n" +
                        "9: 2:LXXXXXXXXX#XXXXXXXXX\n" +
                        "9: 2:XXXXXXXXX##oOXXXXXXX\n" +
                        "9: 2:\n" +
                        "9: 2:Scores: 0\n" +
                        "9: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "10: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "10: 1:XXXXXXXXXXX#XXXXXXLX\n" +
                        "10: 1:XX.M..............XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX.............M..XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX................XX\n" +
                        "10: 1:XX......M.........XX\n" +
                        "10: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "10: 1:XLXXXXXXX@@aaAXXXXXX\n" +
                        "10: 1:\n" +
                        "10: 1:Scores: 0\n" +
                        "10: 1:Answer: RIGHT\n" +
                        "10: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "10: 2:XXXXXXXXXXX@XXXXXXLX\n" +
                        "10: 2:XX.M..............XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX.............M..XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX................XX\n" +
                        "10: 2:XX......M.........XX\n" +
                        "10: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "10: 2:XLXXXXXXX##ooOXXXXXX\n" +
                        "10: 2:\n" +
                        "10: 2:Scores: 0\n" +
                        "10: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "11: 1:XXXXXXXXXX##OXXXXLXX\n" +
                        "11: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XXM...............XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX..............M.XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XX.......M........XX\n" +
                        "11: 1:XX................XX\n" +
                        "11: 1:XXLXXXXXXX@XXXXXXXXX\n" +
                        "11: 1:XXXXXXXXX@@aaaAXXXXX\n" +
                        "11: 1:\n" +
                        "11: 1:Scores: 0\n" +
                        "11: 1:Answer: RIGHT\n" +
                        "11: 2:XXXXXXXXXX@@AXXXXLXX\n" +
                        "11: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XXM...............XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX..............M.XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XX.......M........XX\n" +
                        "11: 2:XX................XX\n" +
                        "11: 2:XXLXXXXXXX#XXXXXXXXX\n" +
                        "11: 2:XXXXXXXXX##oooOXXXXX\n" +
                        "11: 2:\n" +
                        "11: 2:Scores: 0\n" +
                        "11: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "12: 1:XXXXXXXXXX##oOXXXXXX\n" +
                        "12: 1:XXXXXXXXXXX#XXXXLXXX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX.M..............XX\n" +
                        "12: 1:XX...............MXX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX........M.......XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XX................XX\n" +
                        "12: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "12: 1:XXXLXXXXX@@aaaaAXXXX\n" +
                        "12: 1:\n" +
                        "12: 1:Scores: 0\n" +
                        "12: 1:Answer: RIGHT\n" +
                        "12: 2:XXXXXXXXXX@@aAXXXXXX\n" +
                        "12: 2:XXXXXXXXXXX@XXXXLXXX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX.M..............XX\n" +
                        "12: 2:XX...............MXX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX........M.......XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XX................XX\n" +
                        "12: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "12: 2:XXXLXXXXX##ooooOXXXX\n" +
                        "12: 2:\n" +
                        "12: 2:Scores: 0\n" +
                        "12: 2:Answer: RIGHT\n" +
                        "12: 1:Fire Event: DIE\n" +
                        "------------------------------------------\n" +
                        "13: 1:XXXXXXXXXXO#XXXLXXXX\n" +
                        "13: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX..............M.XX\n" +
                        "13: 1:XX..M.............XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX.........M......XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XX................XX\n" +
                        "13: 1:XXXXLXXXXX@XXXXXXXXX\n" +
                        "13: 1:XXXXXXXXX@@aaaaaAXXX\n" +
                        "13: 1:\n" +
                        "13: 1:Scores: 0\n" +
                        "13: 1:Answer: RIGHT\n" +
                        "13: 2:XXXXXXXXXXA@XXXLXXXX\n" +
                        "13: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX..............M.XX\n" +
                        "13: 2:XX..M.............XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX.........M......XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XX................XX\n" +
                        "13: 2:XXXXLXXXXX#XXXXXXXXX\n" +
                        "13: 2:XXXXXXXXX##oooooOXXX\n" +
                        "13: 2:\n" +
                        "13: 2:Scores: 0\n" +
                        "13: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "14: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "14: 1:XXXXXXXXXXX#XXLXXXXX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX.............M..XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX...M............XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX..........M.....XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XX................XX\n" +
                        "14: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "14: 1:XXXXXLXXX@@aaaaaaAXX\n" +
                        "14: 1:\n" +
                        "14: 1:Scores: 0\n" +
                        "14: 1:Answer: UP\n" +
                        "14: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "14: 2:XXXXXXXXXXX@XXLXXXXX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX.............M..XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX...M............XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX..........M.....XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XX................XX\n" +
                        "14: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "14: 2:XXXXXLXXX##ooooooOXX\n" +
                        "14: 2:\n" +
                        "14: 2:Scores: 0\n" +
                        "14: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "15: 1:XXXXXXXXXX#OXLXXXXXX\n" +
                        "15: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "15: 1:XX............M...XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX....M...........XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX...........M....XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XX................XX\n" +
                        "15: 1:XXXXXXLXXX@XXXXXXXXX\n" +
                        "15: 1:XXXXXXXXX@@aaaaaaaAX\n" +
                        "15: 1:\n" +
                        "15: 1:Scores: 0\n" +
                        "15: 1:Answer: LEFT\n" +
                        "15: 2:XXXXXXXXXX@AXLXXXXXX\n" +
                        "15: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "15: 2:XX............M...XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX....M...........XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX...........M....XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XX................XX\n" +
                        "15: 2:XXXXXXLXXX#XXXXXXXXX\n" +
                        "15: 2:XXXXXXXXX##oooooooOX\n" +
                        "15: 2:\n" +
                        "15: 2:Scores: 0\n" +
                        "15: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "16: 1:XXXXXXXXXXO#XXXXXXXX\n" +
                        "16: 1:XXXXXXXXXXX#LXXXXXXX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX...........M....XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX.....M..........XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX............M...XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XX................XX\n" +
                        "16: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "16: 1:XXXXXXXLX@@aaaaaaaaA\n" +
                        "16: 1:\n" +
                        "16: 1:Scores: 0\n" +
                        "16: 1:Answer: LEFT\n" +
                        "16: 2:XXXXXXXXXXA@XXXXXXXX\n" +
                        "16: 2:XXXXXXXXXXX@LXXXXXXX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX...........M....XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX.....M..........XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX............M...XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XX................XX\n" +
                        "16: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "16: 2:XXXXXXXLX##ooooooooO\n" +
                        "16: 2:\n" +
                        "16: 2:Scores: 0\n" +
                        "16: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "17: 1:XXXXXXXXXO#LXXXXXXXX\n" +
                        "17: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX..........M.....XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX......M.........XX\n" +
                        "17: 1:XX.............M..XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XX................XX\n" +
                        "17: 1:XXXXXXXXLX@XXXXXXXXX\n" +
                        "17: 1:XXXXXXXXX@@aaaaaaaaA\n" +
                        "17: 1:\n" +
                        "17: 1:Scores: 0\n" +
                        "17: 1:Answer: LEFT\n" +
                        "17: 2:XXXXXXXXXA@LXXXXXXXX\n" +
                        "17: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX..........M.....XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX......M.........XX\n" +
                        "17: 2:XX.............M..XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XX................XX\n" +
                        "17: 2:XXXXXXXXLX#XXXXXXXXX\n" +
                        "17: 2:XXXXXXXXX##ooooooooO\n" +
                        "17: 2:\n" +
                        "17: 2:Scores: 0\n" +
                        "17: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "18: 1:XXXXXXXXOo##XXXXXXXX\n" +
                        "18: 1:XXXXXXXXXXL#XXXXXXXX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX.........M......XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX..............M.XX\n" +
                        "18: 1:XX.......M........XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XX................XX\n" +
                        "18: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "18: 1:XXXXXXXXXL@aaaaaaaaA\n" +
                        "18: 1:\n" +
                        "18: 1:Scores: 0\n" +
                        "18: 1:Answer: LEFT\n" +
                        "18: 2:XXXXXXXXAa@@XXXXXXXX\n" +
                        "18: 2:XXXXXXXXXXL@XXXXXXXX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX.........M......XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX..............M.XX\n" +
                        "18: 2:XX.......M........XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XX................XX\n" +
                        "18: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "18: 2:XXXXXXXXXL#ooooooooO\n" +
                        "18: 2:\n" +
                        "18: 2:Scores: 0\n" +
                        "18: 2:Answer: RIGHT\n" +
                        "18: 1:Fire Event: DIE\n" +
                        "------------------------------------------\n" +
                        "19: 1:XXXXXXXXXLO#XXXXXXXX\n" +
                        "19: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX........M.......XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX...............MXX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX........M.......XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XX................XX\n" +
                        "19: 1:XXXXXXXXXXLXXXXXXXXX\n" +
                        "19: 1:XXXXXXXXX@@aaaaaaaaA\n" +
                        "19: 1:\n" +
                        "19: 1:Scores: 0\n" +
                        "19: 1:Answer: RIGHT\n" +
                        "19: 2:XXXXXXXXXLA@XXXXXXXX\n" +
                        "19: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX........M.......XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX...............MXX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX........M.......XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XX................XX\n" +
                        "19: 2:XXXXXXXXXXLXXXXXXXXX\n" +
                        "19: 2:XXXXXXXXX##ooooooooO\n" +
                        "19: 2:\n" +
                        "19: 2:Scores: 0\n" +
                        "19: 2:Answer: RIGHT\n" +
                        "19: 2:Fire Event: DIE\n" +
                        "------------------------------------------\n" +
                        "20: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "20: 1:XXXXXXXXLXX#XXXXXXXX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX.......M......M.XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX.........M......XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XX................XX\n" +
                        "20: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "20: 1:XXXXXXXXX@ALXXXXXXXX\n" +
                        "20: 1:\n" +
                        "20: 1:Scores: 0\n" +
                        "20: 1:Answer: LEFT\n" +
                        "20: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "20: 2:XXXXXXXXLXX@XXXXXXXX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX.......M......M.XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX.........M......XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XX................XX\n" +
                        "20: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "20: 2:XXXXXXXXX#OLXXXXXXXX\n" +
                        "20: 2:\n" +
                        "20: 2:Scores: 0\n" +
                        "20: 2:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "21: 1:XXXXXXXLXXO#XXXXXXXX\n" +
                        "21: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX.............M..XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX......M.........XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX..........M.....XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XX................XX\n" +
                        "21: 1:XXXXXXXXXX@XLXXXXXXX\n" +
                        "21: 1:XXXXXXXXX@AXXXXXXXXX\n" +
                        "21: 1:\n" +
                        "21: 1:Scores: 0\n" +
                        "21: 1:Answer: UP\n" +
                        "21: 2:XXXXXXXLXXA@XXXXXXXX\n" +
                        "21: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX.............M..XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX......M.........XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX..........M.....XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XX................XX\n" +
                        "21: 2:XXXXXXXXXX#XLXXXXXXX\n" +
                        "21: 2:XXXXXXXXX#OXXXXXXXXX\n" +
                        "21: 2:\n" +
                        "21: 2:Scores: 0\n" +
                        "21: 2:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "22: 1:XXXXXXXXXXO#XXXXXXXX\n" +
                        "22: 1:XXXXXXLXXXX#XXXXXXXX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX............M...XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX.....M..........XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX...........M....XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XX................XX\n" +
                        "22: 1:XXXXXXXXXX@XXXXXXXXX\n" +
                        "22: 1:XXXXXXXXX@AXXLXXXXXX\n" +
                        "22: 1:\n" +
                        "22: 1:Scores: 0\n" +
                        "22: 1:Answer: UP\n" +
                        "22: 2:XXXXXXXXXXA@XXXXXXXX\n" +
                        "22: 2:XXXXXXLXXXX@XXXXXXXX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX............M...XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX.....M..........XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX...........M....XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XX................XX\n" +
                        "22: 2:XXXXXXXXXX#XXXXXXXXX\n" +
                        "22: 2:XXXXXXXXX#OXXLXXXXXX\n" +
                        "22: 2:\n" +
                        "22: 2:Scores: 0\n" +
                        "22: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "23: 1:XXXXXLXXXXO#XXXXXXXX\n" +
                        "23: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX...........M....XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX....M...........XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX............M...XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XX................XX\n" +
                        "23: 1:XXXXXXXXXXAXXXLXXXXX\n" +
                        "23: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "23: 1:\n" +
                        "23: 1:Scores: 0\n" +
                        "23: 1:Answer: RIGHT\n" +
                        "23: 2:XXXXXLXXXXA@XXXXXXXX\n" +
                        "23: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX...........M....XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX....M...........XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX............M...XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XX................XX\n" +
                        "23: 2:XXXXXXXXXXOXXXLXXXXX\n" +
                        "23: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "23: 2:\n" +
                        "23: 2:Scores: 0\n" +
                        "23: 2:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "24: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "24: 1:XXXXLXXXXXX#XXXXXXXX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX..........M.....XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX...M............XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XX.............M..XX\n" +
                        "24: 1:XX................XX\n" +
                        "24: 1:XXXXXXXXXA@XXXXXXXXX\n" +
                        "24: 1:XXXXXXXXX@@XXXXLXXXX\n" +
                        "24: 1:\n" +
                        "24: 1:Scores: 0\n" +
                        "24: 1:Answer: LEFT\n" +
                        "24: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "24: 2:XXXXLXXXXXX@XXXXXXXX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX..........M.....XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX...M............XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XX.............M..XX\n" +
                        "24: 2:XX................XX\n" +
                        "24: 2:XXXXXXXXXO#XXXXXXXXX\n" +
                        "24: 2:XXXXXXXXX##XXXXLXXXX\n" +
                        "24: 2:\n" +
                        "24: 2:Scores: 0\n" +
                        "24: 2:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "25: 1:XXXLXXXXXXO#XXXXXXXX\n" +
                        "25: 1:XXXXXXXXXXX#XXXXXXXX\n" +
                        "25: 1:XX.........M......XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX..M.............XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX................XX\n" +
                        "25: 1:XX..............M.XX\n" +
                        "25: 1:XXXXXXXXX@@XXXXXLXXX\n" +
                        "25: 1:XXXXXXXXXA@XXXXXXXXX\n" +
                        "25: 1:\n" +
                        "25: 1:Scores: 0\n" +
                        "25: 1:Answer: RIGHT\n" +
                        "25: 2:XXXLXXXXXXA@XXXXXXXX\n" +
                        "25: 2:XXXXXXXXXXX@XXXXXXXX\n" +
                        "25: 2:XX.........M......XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX..M.............XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX................XX\n" +
                        "25: 2:XX..............M.XX\n" +
                        "25: 2:XXXXXXXXX##XXXXXLXXX\n" +
                        "25: 2:XXXXXXXXXO#XXXXXXXXX\n" +
                        "25: 2:\n" +
                        "25: 2:Scores: 0\n" +
                        "25: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "26: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "26: 1:XXLXXXXXXXX#XXXXXXXX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX........M.......XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX.M..............XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XX...............MXX\n" +
                        "26: 1:XX................XX\n" +
                        "26: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "26: 1:XXXXXXXXX@AXXXXXXLXX\n" +
                        "26: 1:\n" +
                        "26: 1:Scores: 0\n" +
                        "26: 1:Answer: DOWN\n" +
                        "26: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "26: 2:XXLXXXXXXXX@XXXXXXXX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX........M.......XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX.M..............XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XX...............MXX\n" +
                        "26: 2:XX................XX\n" +
                        "26: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "26: 2:XXXXXXXXX#OXXXXXXLXX\n" +
                        "26: 2:\n" +
                        "26: 2:Scores: 0\n" +
                        "26: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "27: 1:XLXXXXXXXX##XXXXXXXX\n" +
                        "27: 1:XXXXXXXXXXXOXXXXXXXX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX.......M........XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XXM...............XX\n" +
                        "27: 1:XX..............M.XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XX................XX\n" +
                        "27: 1:XXXXXXXXX@AXXXXXXXLX\n" +
                        "27: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "27: 1:\n" +
                        "27: 1:Scores: 0\n" +
                        "27: 1:Answer: RIGHT\n" +
                        "27: 2:XLXXXXXXXX@@XXXXXXXX\n" +
                        "27: 2:XXXXXXXXXXXAXXXXXXXX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX.......M........XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XXM...............XX\n" +
                        "27: 2:XX..............M.XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XX................XX\n" +
                        "27: 2:XXXXXXXXX#OXXXXXXXLX\n" +
                        "27: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "27: 2:\n" +
                        "27: 2:Scores: 0\n" +
                        "27: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "28: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "28: 1:LXXXXXXXXXX#OXXXXXXX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX......M.........XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX.............M..XX\n" +
                        "28: 1:XX.M..............XX\n" +
                        "28: 1:XX................XX\n" +
                        "28: 1:XX................XL\n" +
                        "28: 1:XXXXXXXXX@@AXXXXXXXX\n" +
                        "28: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "28: 1:\n" +
                        "28: 1:Scores: 0\n" +
                        "28: 1:Answer: LEFT\n" +
                        "28: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "28: 2:LXXXXXXXXXX@AXXXXXXX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX......M.........XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX.............M..XX\n" +
                        "28: 2:XX.M..............XX\n" +
                        "28: 2:XX................XX\n" +
                        "28: 2:XX................XL\n" +
                        "28: 2:XXXXXXXXX##OXXXXXXXX\n" +
                        "28: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "28: 2:\n" +
                        "28: 2:Scores: 0\n" +
                        "28: 2:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "29: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "29: 1:XXXXXXXXXXXO#XXXXXXX\n" +
                        "29: 1:XL................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX.....M..........XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX............M...XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XX..M.............LX\n" +
                        "29: 1:XX................XX\n" +
                        "29: 1:XXXXXXXXX@A@XXXXXXXX\n" +
                        "29: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "29: 1:\n" +
                        "29: 1:Scores: 0\n" +
                        "29: 1:Answer: RIGHT\n" +
                        "29: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "29: 2:XXXXXXXXXXXA@XXXXXXX\n" +
                        "29: 2:XL................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX.....M..........XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX............M...XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XX..M.............LX\n" +
                        "29: 2:XX................XX\n" +
                        "29: 2:XXXXXXXXX#O#XXXXXXXX\n" +
                        "29: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "29: 2:\n" +
                        "29: 2:Scores: 0\n" +
                        "29: 2:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "30: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "30: 1:XXXXXXXXXXX#OXXXXXXX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:LX................XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX....M...........XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX...........M....XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX................XL\n" +
                        "30: 1:XX................XX\n" +
                        "30: 1:XX...M............XX\n" +
                        "30: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "30: 1:XXXXXXXXX@AXXXXXXXXX\n" +
                        "30: 1:\n" +
                        "30: 1:Scores: 0\n" +
                        "30: 1:Answer: DOWN\n" +
                        "30: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "30: 2:XXXXXXXXXXX@AXXXXXXX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:LX................XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX....M...........XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX...........M....XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX................XL\n" +
                        "30: 2:XX................XX\n" +
                        "30: 2:XX...M............XX\n" +
                        "30: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "30: 2:XXXXXXXXX#OXXXXXXXXX\n" +
                        "30: 2:\n" +
                        "30: 2:Scores: 0\n" +
                        "30: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "31: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "31: 1:XXXXXXXXXXX##XXXXXXX\n" +
                        "31: 1:XX..........O.....XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XL................XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XX...M............XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XX..........M.....XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XX................LX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XX....M...........XX\n" +
                        "31: 1:XX................XX\n" +
                        "31: 1:XXXXXXXXX@A@XXXXXXXX\n" +
                        "31: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "31: 1:\n" +
                        "31: 1:Scores: 0\n" +
                        "31: 1:Answer: UP\n" +
                        "31: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "31: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "31: 2:XX..........A.....XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XL................XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XX...M............XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XX..........M.....XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XX................LX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XX....M...........XX\n" +
                        "31: 2:XX................XX\n" +
                        "31: 2:XXXXXXXXX#O#XXXXXXXX\n" +
                        "31: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "31: 2:\n" +
                        "31: 2:Scores: 0\n" +
                        "31: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "32: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "32: 1:XXXXXXXXXXX#OXXXXXXX\n" +
                        "32: 1:XX..........#.....XX\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:LX................XX\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:XX..M.............XX\n" +
                        "32: 1:XX.........M......XX\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:XX................XL\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:XX.....M..........XX\n" +
                        "32: 1:XX................XX\n" +
                        "32: 1:XX........A.......XX\n" +
                        "32: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "32: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "32: 1:\n" +
                        "32: 1:Scores: 0\n" +
                        "32: 1:Answer: DOWN\n" +
                        "32: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "32: 2:XXXXXXXXXXX@AXXXXXXX\n" +
                        "32: 2:XX..........@.....XX\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:LX................XX\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:XX..M.............XX\n" +
                        "32: 2:XX.........M......XX\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:XX................XL\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:XX.....M..........XX\n" +
                        "32: 2:XX................XX\n" +
                        "32: 2:XX........O.......XX\n" +
                        "32: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "32: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "32: 2:\n" +
                        "32: 2:Scores: 0\n" +
                        "32: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "33: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "33: 1:XXXXXXXXXXX##XXXXXXX\n" +
                        "33: 1:XX..........O.....XX\n" +
                        "33: 1:XX................XX\n" +
                        "33: 1:XX................XX\n" +
                        "33: 1:XX................XX\n" +
                        "33: 1:XL................XX\n" +
                        "33: 1:XX................XX\n" +
                        "33: 1:XX................XX\n" +
                        "33: 1:XX........M.......XX\n" +
                        "33: 1:XX.M..............XX\n" +
                        "33: 1:XX................XX\n" +
                        "33: 1:XX................LX\n" +
                        "33: 1:XX................XX\n" +
                        "33: 1:XX......M.........XX\n" +
                        "33: 1:XX................XX\n" +
                        "33: 1:XX........A.......XX\n" +
                        "33: 1:XX........a.......XX\n" +
                        "33: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "33: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "33: 1:\n" +
                        "33: 1:Scores: 0\n" +
                        "33: 1:Answer: LEFT\n" +
                        "33: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "33: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "33: 2:XX..........A.....XX\n" +
                        "33: 2:XX................XX\n" +
                        "33: 2:XX................XX\n" +
                        "33: 2:XX................XX\n" +
                        "33: 2:XL................XX\n" +
                        "33: 2:XX................XX\n" +
                        "33: 2:XX................XX\n" +
                        "33: 2:XX........M.......XX\n" +
                        "33: 2:XX.M..............XX\n" +
                        "33: 2:XX................XX\n" +
                        "33: 2:XX................LX\n" +
                        "33: 2:XX................XX\n" +
                        "33: 2:XX......M.........XX\n" +
                        "33: 2:XX................XX\n" +
                        "33: 2:XX........O.......XX\n" +
                        "33: 2:XX........o.......XX\n" +
                        "33: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "33: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "33: 2:\n" +
                        "33: 2:Scores: 0\n" +
                        "33: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "34: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "34: 1:XXXXXXXXXXX##XXXXXXX\n" +
                        "34: 1:XX.........O#.....XX\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:LX................XX\n" +
                        "34: 1:XX.......M........XX\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:XXM...............XL\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:XX.......M........XX\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:XX................XX\n" +
                        "34: 1:XX........aA......XX\n" +
                        "34: 1:XX........a.......XX\n" +
                        "34: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "34: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "34: 1:\n" +
                        "34: 1:Scores: 0\n" +
                        "34: 1:Answer: LEFT\n" +
                        "34: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "34: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "34: 2:XX.........A@.....XX\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:LX................XX\n" +
                        "34: 2:XX.......M........XX\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:XXM...............XL\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:XX.......M........XX\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:XX................XX\n" +
                        "34: 2:XX........oO......XX\n" +
                        "34: 2:XX........o.......XX\n" +
                        "34: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "34: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "34: 2:\n" +
                        "34: 2:Scores: 0\n" +
                        "34: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "35: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "35: 1:XXXXXXXXXXX##XXXXXXX\n" +
                        "35: 1:XX........Oo#.....XX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX......M.........XX\n" +
                        "35: 1:XL................XX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX................LX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX.M......M.......XX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX................XX\n" +
                        "35: 1:XX........aaA.....XX\n" +
                        "35: 1:XX........a.......XX\n" +
                        "35: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "35: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "35: 1:\n" +
                        "35: 1:Scores: 0\n" +
                        "35: 1:Answer: LEFT\n" +
                        "35: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "35: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "35: 2:XX........Aa@.....XX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX......M.........XX\n" +
                        "35: 2:XL................XX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX................LX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX.M......M.......XX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX................XX\n" +
                        "35: 2:XX........ooO.....XX\n" +
                        "35: 2:XX........o.......XX\n" +
                        "35: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "35: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "35: 2:\n" +
                        "35: 2:Scores: 0\n" +
                        "35: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "36: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "36: 1:XXXXXXXXXXX##XXXXXXX\n" +
                        "36: 1:XX.......Ooo#.....XX\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:XX.....M..........XX\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:LX................XL\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:XX.........M......XX\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:XX..M.............XX\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:XX................XX\n" +
                        "36: 1:XX........aaaA....XX\n" +
                        "36: 1:XX........a.......XX\n" +
                        "36: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "36: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "36: 1:\n" +
                        "36: 1:Scores: 0\n" +
                        "36: 1:Answer: LEFT\n" +
                        "36: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "36: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "36: 2:XX.......Aaa@.....XX\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:XX.....M..........XX\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:LX................XL\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:XX.........M......XX\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:XX..M.............XX\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:XX................XX\n" +
                        "36: 2:XX........oooO....XX\n" +
                        "36: 2:XX........o.......XX\n" +
                        "36: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "36: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "36: 2:\n" +
                        "36: 2:Scores: 0\n" +
                        "36: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "37: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "37: 1:XXXXXXXXXXX##XXXXXXX\n" +
                        "37: 1:XX......Oooo#.....XX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XX....M...........XX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XX................LX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XL..........M.....XX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XX...M............XX\n" +
                        "37: 1:XX................XX\n" +
                        "37: 1:XX........aaaaA...XX\n" +
                        "37: 1:XX........a.......XX\n" +
                        "37: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "37: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "37: 1:\n" +
                        "37: 1:Scores: 0\n" +
                        "37: 1:Answer: LEFT\n" +
                        "37: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "37: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "37: 2:XX......Aaaa@.....XX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XX....M...........XX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XX................LX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XL..........M.....XX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XX...M............XX\n" +
                        "37: 2:XX................XX\n" +
                        "37: 2:XX........ooooO...XX\n" +
                        "37: 2:XX........o.......XX\n" +
                        "37: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "37: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "37: 2:\n" +
                        "37: 2:Scores: 0\n" +
                        "37: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "38: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "38: 1:XXXXXXXXXXX##XXXXXXX\n" +
                        "38: 1:XX.....Ooooo#.....XX\n" +
                        "38: 1:XX................XX\n" +
                        "38: 1:XX...M............XX\n" +
                        "38: 1:XX................XX\n" +
                        "38: 1:XX................XX\n" +
                        "38: 1:XX................XL\n" +
                        "38: 1:XX................XX\n" +
                        "38: 1:XX...........M....XX\n" +
                        "38: 1:XX................XX\n" +
                        "38: 1:LX................XX\n" +
                        "38: 1:XX................XX\n" +
                        "38: 1:XX................XX\n" +
                        "38: 1:XX................XX\n" +
                        "38: 1:XX....M...........XX\n" +
                        "38: 1:XX........aaaaaA..XX\n" +
                        "38: 1:XX........a.......XX\n" +
                        "38: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "38: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "38: 1:\n" +
                        "38: 1:Scores: 0\n" +
                        "38: 1:Answer: RIGHT\n" +
                        "38: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "38: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "38: 2:XX.....Aaaaa@.....XX\n" +
                        "38: 2:XX................XX\n" +
                        "38: 2:XX...M............XX\n" +
                        "38: 2:XX................XX\n" +
                        "38: 2:XX................XX\n" +
                        "38: 2:XX................XL\n" +
                        "38: 2:XX................XX\n" +
                        "38: 2:XX...........M....XX\n" +
                        "38: 2:XX................XX\n" +
                        "38: 2:LX................XX\n" +
                        "38: 2:XX................XX\n" +
                        "38: 2:XX................XX\n" +
                        "38: 2:XX................XX\n" +
                        "38: 2:XX....M...........XX\n" +
                        "38: 2:XX........oooooO..XX\n" +
                        "38: 2:XX........o.......XX\n" +
                        "38: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "38: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "38: 2:\n" +
                        "38: 2:Scores: 0\n" +
                        "38: 2:Answer: RIGHT\n" +
                        "38: 1:Fire Event: GAME_OVER\n" +
                        "------------------------------------------\n" +
                        "39: 1:XXXXXXXXXX##XXXXXXXX\n" +
                        "39: 1:XXXXXXXXXXX##XXXXXXX\n" +
                        "39: 1:XX.....O....#.....XX\n" +
                        "39: 1:XX..M.............XX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XX................LX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XX............M...XX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XL................XX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XX................XX\n" +
                        "39: 1:XX.....M..aaaaaaA.XX\n" +
                        "39: 1:XX........a.......XX\n" +
                        "39: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "39: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "39: 1:\n" +
                        "39: 1:Scores: 0\n" +
                        "39: 1:Answer: RIGHT\n" +
                        "39: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "39: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "39: 2:XX.....A....@.....XX\n" +
                        "39: 2:XX..M.............XX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XX................LX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XX............M...XX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XL................XX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XX................XX\n" +
                        "39: 2:XX.....M..ooooooO.XX\n" +
                        "39: 2:XX........o.......XX\n" +
                        "39: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "39: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "39: 2:\n" +
                        "39: 2:Scores: 0\n" +
                        "39: 2:Answer: RIGHT\n" +
                        "39: 1:PLAYER_GAME_OVER -> START_NEW_GAME\n" +
                        "------------------------------------------\n" +
                        "40: 1:XXXXXXXXXXO@XXXXXXXX\n" +
                        "40: 1:XXXXXXXXXXX@@XXXXXXX\n" +
                        "40: 1:XX.M........@.....XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX................XL\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX.............M..XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:LX................XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX................XX\n" +
                        "40: 1:XX........aaaaaaaAXX\n" +
                        "40: 1:XX......M.a.......XX\n" +
                        "40: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "40: 1:XXXXXXXXX@@XXXXXXXXX\n" +
                        "40: 1:\n" +
                        "40: 1:Scores: 0\n" +
                        "40: 1:Answer: UP\n" +
                        "40: 2:XXXXXXXXXXA@XXXXXXXX\n" +
                        "40: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "40: 2:XX.M........@.....XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX................XL\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX.............M..XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:LX................XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX................XX\n" +
                        "40: 2:XX........oooooooOXX\n" +
                        "40: 2:XX......M.o.......XX\n" +
                        "40: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "40: 2:XXXXXXXXX##XXXXXXXXX\n" +
                        "40: 2:\n" +
                        "40: 2:Scores: 0\n" +
                        "40: 2:Answer: RIGHT\n" +
                        "40: 2:Fire Event: DIE\n" +
                        "------------------------------------------\n" +
                        "41: 1:XXXXXXXXXXO@XXXXXXXX\n" +
                        "41: 1:XXXXXXXXXXX@@XXXXXXX\n" +
                        "41: 1:XX..........@.....XX\n" +
                        "41: 1:XXM...............XX\n" +
                        "41: 1:XX................LX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XX..............M.XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XL................XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XX.......M........XX\n" +
                        "41: 1:XX................XX\n" +
                        "41: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "41: 1:XXXXXXXXX@AXXXXXXXXX\n" +
                        "41: 1:\n" +
                        "41: 1:Scores: 0\n" +
                        "41: 1:Answer: UP\n" +
                        "41: 2:XXXXXXXXXXA@XXXXXXXX\n" +
                        "41: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "41: 2:XX..........@.....XX\n" +
                        "41: 2:XXM...............XX\n" +
                        "41: 2:XX................LX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XX..............M.XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XL................XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XX.......M........XX\n" +
                        "41: 2:XX................XX\n" +
                        "41: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "41: 2:XXXXXXXXX#OXXXXXXXXX\n" +
                        "41: 2:\n" +
                        "41: 2:Scores: 0\n" +
                        "41: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "42: 1:XXXXXXXXXXO@XXXXXXXX\n" +
                        "42: 1:XXXXXXXXXXX@@XXXXXXX\n" +
                        "42: 1:XX..........@.....XX\n" +
                        "42: 1:XX................XL\n" +
                        "42: 1:XX.M..............XX\n" +
                        "42: 1:XX...............MXX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:LX........M.......XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XX................XX\n" +
                        "42: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "42: 1:XXXXXXXXX@@AXXXXXXXX\n" +
                        "42: 1:\n" +
                        "42: 1:Scores: 0\n" +
                        "42: 1:Answer: RIGHT\n" +
                        "42: 2:XXXXXXXXXXA@XXXXXXXX\n" +
                        "42: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "42: 2:XX..........@.....XX\n" +
                        "42: 2:XX................XL\n" +
                        "42: 2:XX.M..............XX\n" +
                        "42: 2:XX...............MXX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:LX........M.......XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XX................XX\n" +
                        "42: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "42: 2:XXXXXXXXX##OXXXXXXXX\n" +
                        "42: 2:\n" +
                        "42: 2:Scores: 0\n" +
                        "42: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "43: 1:XXXXXXXXXX#OXXXXXXXX\n" +
                        "43: 1:XXXXXXXXXXX@@XXXXXXX\n" +
                        "43: 1:XX..........@.....LX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX..............M.XX\n" +
                        "43: 1:XX..M.............XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XX.........M......XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XL................XX\n" +
                        "43: 1:XX................XX\n" +
                        "43: 1:XXXXXXXXX@@AXXXXXXXX\n" +
                        "43: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "43: 1:\n" +
                        "43: 1:Scores: 0\n" +
                        "43: 1:Answer: LEFT\n" +
                        "43: 2:XXXXXXXXXX@AXXXXXXXX\n" +
                        "43: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "43: 2:XX..........@.....LX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX..............M.XX\n" +
                        "43: 2:XX..M.............XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XX.........M......XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XL................XX\n" +
                        "43: 2:XX................XX\n" +
                        "43: 2:XXXXXXXXX##OXXXXXXXX\n" +
                        "43: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "43: 2:\n" +
                        "43: 2:Scores: 0\n" +
                        "43: 2:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "44: 1:XXXXXXXXXXO@XXXXXXXX\n" +
                        "44: 1:XXXXXXXXXXX@@XXXXXXL\n" +
                        "44: 1:XX..........@.....XX\n" +
                        "44: 1:XX.............M..XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX...M............XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX..........M.....XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:XX................XX\n" +
                        "44: 1:LX................XX\n" +
                        "44: 1:XXXXXXXXX@A@XXXXXXXX\n" +
                        "44: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "44: 1:\n" +
                        "44: 1:Scores: 0\n" +
                        "44: 1:Answer: UP\n" +
                        "44: 2:XXXXXXXXXXA@XXXXXXXX\n" +
                        "44: 2:XXXXXXXXXXX@@XXXXXXL\n" +
                        "44: 2:XX..........@.....XX\n" +
                        "44: 2:XX.............M..XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX...M............XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX..........M.....XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:XX................XX\n" +
                        "44: 2:LX................XX\n" +
                        "44: 2:XXXXXXXXX#O#XXXXXXXX\n" +
                        "44: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "44: 2:\n" +
                        "44: 2:Scores: 0\n" +
                        "44: 2:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "45: 1:XXXXXXXXXXO@XXXXXXLX\n" +
                        "45: 1:XXXXXXXXXXX@@XXXXXXX\n" +
                        "45: 1:XX..........@.M...XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX....M...........XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX...........M....XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XX................XX\n" +
                        "45: 1:XLXXXXXXX@@AXXXXXXXX\n" +
                        "45: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "45: 1:\n" +
                        "45: 1:Scores: 0\n" +
                        "45: 1:Answer: DOWN\n" +
                        "45: 2:XXXXXXXXXXA@XXXXXXLX\n" +
                        "45: 2:XXXXXXXXXXX@@XXXXXXX\n" +
                        "45: 2:XX..........@.M...XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX....M...........XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX...........M....XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XX................XX\n" +
                        "45: 2:XLXXXXXXX##OXXXXXXXX\n" +
                        "45: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "45: 2:\n" +
                        "45: 2:Scores: 0\n" +
                        "45: 2:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "46: 1:XXXXXXXXXX#@XXXXXXXX\n" +
                        "46: 1:XXXXXXXXXXO@@XXXXLXX\n" +
                        "46: 1:XX..........@.....XX\n" +
                        "46: 1:XX...........M....XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX.....M..........XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX............M...XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XX................XX\n" +
                        "46: 1:XXXXXXXXX@A@XXXXXXXX\n" +
                        "46: 1:XXLXXXXXX@@@XXXXXXXX\n" +
                        "46: 1:\n" +
                        "46: 1:Scores: 0\n" +
                        "46: 1:Answer: LEFT\n" +
                        "46: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "46: 2:XXXXXXXXXXA@@XXXXLXX\n" +
                        "46: 2:XX..........@.....XX\n" +
                        "46: 2:XX...........M....XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX.....M..........XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX............M...XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XX................XX\n" +
                        "46: 2:XXXXXXXXX#O#XXXXXXXX\n" +
                        "46: 2:XXLXXXXXX###XXXXXXXX\n" +
                        "46: 2:\n" +
                        "46: 2:Scores: 0\n" +
                        "46: 2:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "47: 1:XXXXXXXXXX#@XXXXLXXX\n" +
                        "47: 1:XXXXXXXXXOo@@XXXXXXX\n" +
                        "47: 1:XX..........@.....XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX..........M.....XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX......M.........XX\n" +
                        "47: 1:XX.............M..XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XX................XX\n" +
                        "47: 1:XXXLXXXXXA@@XXXXXXXX\n" +
                        "47: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "47: 1:\n" +
                        "47: 1:Scores: 0\n" +
                        "47: 1:Answer: LEFT\n" +
                        "47: 2:XXXXXXXXXX@@XXXXLXXX\n" +
                        "47: 2:XXXXXXXXXAa@@XXXXXXX\n" +
                        "47: 2:XX..........@.....XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX..........M.....XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX......M.........XX\n" +
                        "47: 2:XX.............M..XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XX................XX\n" +
                        "47: 2:XXXLXXXXXO##XXXXXXXX\n" +
                        "47: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "47: 2:\n" +
                        "47: 2:Scores: 0\n" +
                        "47: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "48: 1:XXXXXXXXXX#@XXXXXXXX\n" +
                        "48: 1:XXXXXXXXOoo@@XXLXXXX\n" +
                        "48: 1:XX..........@.....XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX.........M......XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX..............M.XX\n" +
                        "48: 1:XX.......M........XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX................XX\n" +
                        "48: 1:XX.......A........XX\n" +
                        "48: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "48: 1:XXXXLXXXX@@@XXXXXXXX\n" +
                        "48: 1:\n" +
                        "48: 1:Scores: 0\n" +
                        "48: 1:Answer: LEFT\n" +
                        "48: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "48: 2:XXXXXXXXAaa@@XXLXXXX\n" +
                        "48: 2:XX..........@.....XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX.........M......XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX..............M.XX\n" +
                        "48: 2:XX.......M........XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX................XX\n" +
                        "48: 2:XX.......O........XX\n" +
                        "48: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "48: 2:XXXXLXXXX###XXXXXXXX\n" +
                        "48: 2:\n" +
                        "48: 2:Scores: 0\n" +
                        "48: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "49: 1:XXXXXXXXXX#@XXLXXXXX\n" +
                        "49: 1:XXXXXXXOooo@@XXXXXXX\n" +
                        "49: 1:XX..........@.....XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX........M.......XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX...............MXX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX........M.......XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX................XX\n" +
                        "49: 1:XX.......A........XX\n" +
                        "49: 1:XX.......a........XX\n" +
                        "49: 1:XXXXXLXXX@@@XXXXXXXX\n" +
                        "49: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "49: 1:\n" +
                        "49: 1:Scores: 0\n" +
                        "49: 1:Answer: LEFT\n" +
                        "49: 2:XXXXXXXXXX@@XXLXXXXX\n" +
                        "49: 2:XXXXXXXAaaa@@XXXXXXX\n" +
                        "49: 2:XX..........@.....XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX........M.......XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX...............MXX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX........M.......XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX................XX\n" +
                        "49: 2:XX.......O........XX\n" +
                        "49: 2:XX.......o........XX\n" +
                        "49: 2:XXXXXLXXX###XXXXXXXX\n" +
                        "49: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "49: 2:\n" +
                        "49: 2:Scores: 0\n" +
                        "49: 2:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "50: 1:XXXXXXXXXX#@XXXXXXXX\n" +
                        "50: 1:XXXXXXOoooo@@LXXXXXX\n" +
                        "50: 1:XX..........@.....XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX.......M......M.XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX.........M......XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX................XX\n" +
                        "50: 1:XX.......A........XX\n" +
                        "50: 1:XX.......a........XX\n" +
                        "50: 1:XX.......a........XX\n" +
                        "50: 1:XXXXXXXXX@@@XXXXXXXX\n" +
                        "50: 1:XXXXXXLXX@@@XXXXXXXX\n" +
                        "50: 1:\n" +
                        "50: 1:Scores: 0\n" +
                        "50: 1:Answer: LEFT\n" +
                        "50: 2:XXXXXXXXXX@@XXXXXXXX\n" +
                        "50: 2:XXXXXXAaaaa@@LXXXXXX\n" +
                        "50: 2:XX..........@.....XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX.......M......M.XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX.........M......XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX................XX\n" +
                        "50: 2:XX.......O........XX\n" +
                        "50: 2:XX.......o........XX\n" +
                        "50: 2:XX.......o........XX\n" +
                        "50: 2:XXXXXXXXX###XXXXXXXX\n" +
                        "50: 2:XXXXXXLXX###XXXXXXXX\n" +
                        "50: 2:\n" +
                        "50: 2:Scores: 0\n" +
                        "50: 2:Answer: UP\n" +
                        "------------------------------------------",
                String.join("\n", messages));

    }

    @Test
    public void testSingleplayer() {
        List<String> messages = new LinkedList<>();

        LocalGameRunner.timeout = 0;
        LocalGameRunner.out = messages::add;
        LocalGameRunner.countIterations = 100;
        LocalGameRunner.printDice = false;
        LocalGameRunner.printConversions = false;
        LocalGameRunner.printTick = true;
        LocalGameRunner.printBoardOnly = true;

        String soul = "580763458903465890346";
        Dice dice = LocalGameRunner.getDice(LocalGameRunner.generateXorShift(soul, 100, 200));

        GameRunner gameType = new GameRunner() {
            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            public GameSettings getSettings() {
                GameSettings settings = new GameSettings();
                return new GameSettings()
                        .bool(IS_MULTIPLAYER, false)
                        .integer(LEVELS_COUNT, 1)
                        .string(() -> settings.levelName(1),
                                "##########O#########" +
                                "####################" +
                                "##................##" +
                                "##................##" +
                                "##................##" +
                                "##................##" +
                                "##................##" +
                                "##................##" +
                                "#L...M............##" +
                                "##................##" +
                                "##................##" +
                                "##..........M.....##" +
                                "##................##" +
                                "##................##" +
                                "##................##" +
                                "##................##" +
                                "##....M...........##" +
                                "##................##" +
                                "############L#######" +
                                "####################");
            }
        };

        LocalGameRunner.run(gameType,
                new AISolver(dice),
                new Board());

        assertEquals("1: 1:##########O#########\n" +
                        "1: 1:####################\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:#L...M............##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##..........M.....##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:##....M...........##\n" +
                        "1: 1:##................##\n" +
                        "1: 1:############L#######\n" +
                        "1: 1:####################\n" +
                        "1: 1:\n" +
                        "1: 1:Scores: 0\n" +
                        "1: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "2: 1:###########O########\n" +
                        "2: 1:####################\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:#X................##\n" +
                        "2: 1:L#..M.............##\n" +
                        "2: 1:##.........M......##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##.....M..........##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:##................##\n" +
                        "2: 1:############X#######\n" +
                        "2: 1:#############L######\n" +
                        "2: 1:\n" +
                        "2: 1:Scores: 0\n" +
                        "2: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "3: 1:##########O#########\n" +
                        "3: 1:####################\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:#X................##\n" +
                        "3: 1:##........M.......##\n" +
                        "3: 1:#L.M..............##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##......M.........##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:##................##\n" +
                        "3: 1:############X#L#####\n" +
                        "3: 1:####################\n" +
                        "3: 1:\n" +
                        "3: 1:Scores: 0\n" +
                        "3: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "4: 1:#########O##########\n" +
                        "4: 1:####################\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:#X.......M........##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:L#M...............##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##.......M........##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:##................##\n" +
                        "4: 1:############X#######\n" +
                        "4: 1:###############L####\n" +
                        "4: 1:\n" +
                        "4: 1:Scores: 0\n" +
                        "4: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "5: 1:##########O#########\n" +
                        "5: 1:####################\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##......M.........##\n" +
                        "5: 1:#X................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:#L.M......M.......##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:##................##\n" +
                        "5: 1:############X###L###\n" +
                        "5: 1:####################\n" +
                        "5: 1:\n" +
                        "5: 1:Scores: 0\n" +
                        "5: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "6: 1:###########O########\n" +
                        "6: 1:####################\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##.....M..........##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:#X................##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##.........M......##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:L#..M.............##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:##................##\n" +
                        "6: 1:############X#######\n" +
                        "6: 1:#################L##\n" +
                        "6: 1:\n" +
                        "6: 1:Scores: 0\n" +
                        "6: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "7: 1:####################\n" +
                        "7: 1:###########O########\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##....M...........##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:#X................##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##..........M.....##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:#L...M............##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:##................##\n" +
                        "7: 1:############X#####L#\n" +
                        "7: 1:####################\n" +
                        "7: 1:\n" +
                        "7: 1:Scores: 0\n" +
                        "7: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "8: 1:###########O########\n" +
                        "8: 1:####################\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##...M............##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:#X................##\n" +
                        "8: 1:##...........M....##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:L#....M...........##\n" +
                        "8: 1:##................##\n" +
                        "8: 1:##................#L\n" +
                        "8: 1:############X#######\n" +
                        "8: 1:####################\n" +
                        "8: 1:\n" +
                        "8: 1:Scores: 0\n" +
                        "8: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "9: 1:####################\n" +
                        "9: 1:###########O########\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##..M.............##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:#X............M...##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:##................##\n" +
                        "9: 1:#L.....M..........L#\n" +
                        "9: 1:##................##\n" +
                        "9: 1:############X#######\n" +
                        "9: 1:####################\n" +
                        "9: 1:\n" +
                        "9: 1:Scores: 0\n" +
                        "9: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "10: 1:###########O########\n" +
                        "10: 1:####################\n" +
                        "10: 1:##.M..............##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##.............M..##\n" +
                        "10: 1:#X................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................##\n" +
                        "10: 1:##................#L\n" +
                        "10: 1:##................##\n" +
                        "10: 1:L#......M.........##\n" +
                        "10: 1:############X#######\n" +
                        "10: 1:####################\n" +
                        "10: 1:\n" +
                        "10: 1:Scores: 0\n" +
                        "10: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "11: 1:###########O########\n" +
                        "11: 1:####################\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##M...............##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##..............M.##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:#X................##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##................L#\n" +
                        "11: 1:##................##\n" +
                        "11: 1:##.......M........##\n" +
                        "11: 1:##................##\n" +
                        "11: 1:#L##########X#######\n" +
                        "11: 1:####################\n" +
                        "11: 1:\n" +
                        "11: 1:Scores: 0\n" +
                        "11: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "12: 1:####################\n" +
                        "12: 1:###########O########\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##.M..............##\n" +
                        "12: 1:##...............M##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:#X................##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##................#L\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##........M.......##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:##................##\n" +
                        "12: 1:############X#######\n" +
                        "12: 1:##L#################\n" +
                        "12: 1:\n" +
                        "12: 1:Scores: 0\n" +
                        "12: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "13: 1:####################\n" +
                        "13: 1:####################\n" +
                        "13: 1:##.........O......##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:##..............M.##\n" +
                        "13: 1:##..M.............##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:#X................##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:##................L#\n" +
                        "13: 1:##................##\n" +
                        "13: 1:##.........M......##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:##................##\n" +
                        "13: 1:###L########X#######\n" +
                        "13: 1:####################\n" +
                        "13: 1:\n" +
                        "13: 1:Scores: 0\n" +
                        "13: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "14: 1:####################\n" +
                        "14: 1:###########O########\n" +
                        "14: 1:##.........#......##\n" +
                        "14: 1:##.............M..##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:##...M............##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:#X................##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:##................#L\n" +
                        "14: 1:##................##\n" +
                        "14: 1:##..........M.....##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:##................##\n" +
                        "14: 1:############X#######\n" +
                        "14: 1:####L###############\n" +
                        "14: 1:\n" +
                        "14: 1:Scores: 0\n" +
                        "14: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "15: 1:####################\n" +
                        "15: 1:############O#######\n" +
                        "15: 1:##.........#..M...##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##....M...........##\n" +
                        "15: 1:#X................##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##................L#\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##...........M....##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:##................##\n" +
                        "15: 1:#####L######X#######\n" +
                        "15: 1:####################\n" +
                        "15: 1:\n" +
                        "15: 1:Scores: 0\n" +
                        "15: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "16: 1:####################\n" +
                        "16: 1:####################\n" +
                        "16: 1:##.........#O.....##\n" +
                        "16: 1:##...........M....##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:#X.....M..........##\n" +
                        "16: 1:##................#L\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##............M...##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:##................##\n" +
                        "16: 1:############X#######\n" +
                        "16: 1:######L#############\n" +
                        "16: 1:\n" +
                        "16: 1:Scores: 0\n" +
                        "16: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "17: 1:####################\n" +
                        "17: 1:####################\n" +
                        "17: 1:##.........#oO....##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##..........M.....##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:#X................L#\n" +
                        "17: 1:##......M.........##\n" +
                        "17: 1:##.............M..##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:##................##\n" +
                        "17: 1:#######L####X#######\n" +
                        "17: 1:####################\n" +
                        "17: 1:\n" +
                        "17: 1:Scores: 0\n" +
                        "17: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "18: 1:####################\n" +
                        "18: 1:#############O######\n" +
                        "18: 1:##.........###....##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##.........M......##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##................#L\n" +
                        "18: 1:#X................##\n" +
                        "18: 1:##..............M.##\n" +
                        "18: 1:##.......M........##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:##................##\n" +
                        "18: 1:############X#######\n" +
                        "18: 1:########L###########\n" +
                        "18: 1:\n" +
                        "18: 1:Scores: 0\n" +
                        "18: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "19: 1:#############O######\n" +
                        "19: 1:####################\n" +
                        "19: 1:##.........###....##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##........M.......L#\n" +
                        "19: 1:##................##\n" +
                        "19: 1:#X...............M##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##........M.......##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:##................##\n" +
                        "19: 1:#########L##X#######\n" +
                        "19: 1:####################\n" +
                        "19: 1:\n" +
                        "19: 1:Scores: 0\n" +
                        "19: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "20: 1:##############O#####\n" +
                        "20: 1:####################\n" +
                        "20: 1:##.........###....##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##................#L\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##.......M......M.##\n" +
                        "20: 1:#X................##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##.........M......##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:##................##\n" +
                        "20: 1:############X#######\n" +
                        "20: 1:##########L#########\n" +
                        "20: 1:\n" +
                        "20: 1:Scores: 0\n" +
                        "20: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "21: 1:###############O####\n" +
                        "21: 1:####################\n" +
                        "21: 1:##.........###....##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##................L#\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##.............M..##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:#X......M.........##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##..........M.....##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:##................##\n" +
                        "21: 1:###########LX#######\n" +
                        "21: 1:####################\n" +
                        "21: 1:\n" +
                        "21: 1:Scores: 0\n" +
                        "21: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "22: 1:################O###\n" +
                        "22: 1:####################\n" +
                        "22: 1:##.........###....##\n" +
                        "22: 1:##................#L\n" +
                        "22: 1:##................##\n" +
                        "22: 1:##............M...##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:#X................##\n" +
                        "22: 1:##.....M..........##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:##...........M....##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:##................##\n" +
                        "22: 1:############X#######\n" +
                        "22: 1:############L#######\n" +
                        "22: 1:\n" +
                        "22: 1:Scores: 0\n" +
                        "22: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "23: 1:################O###\n" +
                        "23: 1:####################\n" +
                        "23: 1:##.........###....L#\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##...........M....##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:#X................##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##....M...........##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##............M...##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:##................##\n" +
                        "23: 1:############XL######\n" +
                        "23: 1:####################\n" +
                        "23: 1:\n" +
                        "23: 1:Scores: 0\n" +
                        "23: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "24: 1:################O###\n" +
                        "24: 1:###################L\n" +
                        "24: 1:##.........###....##\n" +
                        "24: 1:##..........M.....##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:#X................##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##...M............##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:##.............M..##\n" +
                        "24: 1:##................##\n" +
                        "24: 1:############X#######\n" +
                        "24: 1:##############L#####\n" +
                        "24: 1:\n" +
                        "24: 1:Scores: 0\n" +
                        "24: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "25: 1:###############O##L#\n" +
                        "25: 1:####################\n" +
                        "25: 1:##.........###....##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##.........M......##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:#X................##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##..M.............##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##................##\n" +
                        "25: 1:##..............M.##\n" +
                        "25: 1:############X##L####\n" +
                        "25: 1:####################\n" +
                        "25: 1:\n" +
                        "25: 1:Scores: 0\n" +
                        "25: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "26: 1:###############O####\n" +
                        "26: 1:#################L##\n" +
                        "26: 1:##.........###....##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##........M.......##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:#X................##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##.M..............##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:##...............M##\n" +
                        "26: 1:##................##\n" +
                        "26: 1:############X#######\n" +
                        "26: 1:################L###\n" +
                        "26: 1:\n" +
                        "26: 1:Scores: 0\n" +
                        "26: 1:Answer: UP\n" +
                        "26: 1:Fire Event: DIE\n" +
                        "------------------------------------------\n" +
                        "27: 1:##########O#########\n" +
                        "27: 1:####################\n" +
                        "27: 1:##.........###....##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##.......M........##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:#L................##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##M...............##\n" +
                        "27: 1:##..............M.##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:##................##\n" +
                        "27: 1:############L#######\n" +
                        "27: 1:####################\n" +
                        "27: 1:\n" +
                        "27: 1:Scores: 0\n" +
                        "27: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "28: 1:###########O########\n" +
                        "28: 1:####################\n" +
                        "28: 1:##.........###....##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:L#......M.........##\n" +
                        "28: 1:#X................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##.............M..##\n" +
                        "28: 1:##.M..............##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:##................##\n" +
                        "28: 1:############X#######\n" +
                        "28: 1:#############L######\n" +
                        "28: 1:\n" +
                        "28: 1:Scores: 0\n" +
                        "28: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "29: 1:##########O#########\n" +
                        "29: 1:####################\n" +
                        "29: 1:##.........###....##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:#L.....M..........##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##............M...##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:##..M.............##\n" +
                        "29: 1:##................##\n" +
                        "29: 1:############X#L#####\n" +
                        "29: 1:####################\n" +
                        "29: 1:\n" +
                        "29: 1:Scores: 0\n" +
                        "29: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "30: 1:#########O##########\n" +
                        "30: 1:####################\n" +
                        "30: 1:##.........###....##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:#X................##\n" +
                        "30: 1:L#....M...........##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##...........M....##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##................##\n" +
                        "30: 1:##...M............##\n" +
                        "30: 1:############X#######\n" +
                        "30: 1:###############L####\n" +
                        "30: 1:\n" +
                        "30: 1:Scores: 0\n" +
                        "30: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "31: 1:####################\n" +
                        "31: 1:#########O##########\n" +
                        "31: 1:##.........###....##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:#X................##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:#L...M............##\n" +
                        "31: 1:##..........M.....##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:##....M...........##\n" +
                        "31: 1:##................##\n" +
                        "31: 1:############X###L###\n" +
                        "31: 1:####################\n" +
                        "31: 1:\n" +
                        "31: 1:Scores: 0\n" +
                        "31: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "32: 1:####################\n" +
                        "32: 1:##########O#########\n" +
                        "32: 1:##.........###....##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:#X................##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##.........M......##\n" +
                        "32: 1:L#..M.............##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##.....M..........##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:##................##\n" +
                        "32: 1:############X#######\n" +
                        "32: 1:#################L##\n" +
                        "32: 1:\n" +
                        "32: 1:Scores: 0\n" +
                        "32: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "33: 1:####################\n" +
                        "33: 1:###########O########\n" +
                        "33: 1:##.........###....##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:#X................##\n" +
                        "33: 1:##........M.......##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:#L.M..............##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:##......M.........##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:##................##\n" +
                        "33: 1:############X#####L#\n" +
                        "33: 1:####################\n" +
                        "33: 1:\n" +
                        "33: 1:Scores: 0\n" +
                        "33: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "34: 1:####################\n" +
                        "34: 1:####################\n" +
                        "34: 1:##.........O##....##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:#X.......M........##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:L#M......M........##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................##\n" +
                        "34: 1:##................#L\n" +
                        "34: 1:############X#######\n" +
                        "34: 1:####################\n" +
                        "34: 1:\n" +
                        "34: 1:Scores: 0\n" +
                        "34: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "35: 1:####################\n" +
                        "35: 1:###########O########\n" +
                        "35: 1:##.........###....##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:##......M.........##\n" +
                        "35: 1:#X................##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:##........M.......##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:#L.M..............##\n" +
                        "35: 1:##................##\n" +
                        "35: 1:##................L#\n" +
                        "35: 1:##................##\n" +
                        "35: 1:############X#######\n" +
                        "35: 1:####################\n" +
                        "35: 1:\n" +
                        "35: 1:Scores: 0\n" +
                        "35: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "36: 1:####################\n" +
                        "36: 1:############O#######\n" +
                        "36: 1:##.........###....##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:##.....M..........##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:#X................##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:##.........M......##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:L#..M.............#L\n" +
                        "36: 1:##................##\n" +
                        "36: 1:##................##\n" +
                        "36: 1:############X#######\n" +
                        "36: 1:####################\n" +
                        "36: 1:\n" +
                        "36: 1:Scores: 0\n" +
                        "36: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "37: 1:####################\n" +
                        "37: 1:#############O######\n" +
                        "37: 1:##.........###....##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:##....M...........##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:#X................##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:##..........M.....##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:##................L#\n" +
                        "37: 1:##................##\n" +
                        "37: 1:#L...M............##\n" +
                        "37: 1:##................##\n" +
                        "37: 1:############X#######\n" +
                        "37: 1:####################\n" +
                        "37: 1:\n" +
                        "37: 1:Scores: 0\n" +
                        "37: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "38: 1:####################\n" +
                        "38: 1:############O#######\n" +
                        "38: 1:##.........###....##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:##...M............##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:#X................##\n" +
                        "38: 1:##...........M....##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:##................#L\n" +
                        "38: 1:##................##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:##................##\n" +
                        "38: 1:L#....M...........##\n" +
                        "38: 1:############X#######\n" +
                        "38: 1:####################\n" +
                        "38: 1:\n" +
                        "38: 1:Scores: 0\n" +
                        "38: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "39: 1:####################\n" +
                        "39: 1:###########O########\n" +
                        "39: 1:##.........###....##\n" +
                        "39: 1:##..M.............##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:#X............M...##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##................L#\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:##.....M..........##\n" +
                        "39: 1:##................##\n" +
                        "39: 1:#L##########X#######\n" +
                        "39: 1:####################\n" +
                        "39: 1:\n" +
                        "39: 1:Scores: 0\n" +
                        "39: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "40: 1:####################\n" +
                        "40: 1:############O#######\n" +
                        "40: 1:##.M.......###....##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##.............M..##\n" +
                        "40: 1:#X................##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##................#L\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##......M.........##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:##................##\n" +
                        "40: 1:############X#######\n" +
                        "40: 1:##L#################\n" +
                        "40: 1:\n" +
                        "40: 1:Scores: 0\n" +
                        "40: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "41: 1:####################\n" +
                        "41: 1:####################\n" +
                        "41: 1:##.........#O#....##\n" +
                        "41: 1:##M...............##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:##..............M.##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:#X................##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:##................L#\n" +
                        "41: 1:##................##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:##.......M........##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:##................##\n" +
                        "41: 1:###L########X#######\n" +
                        "41: 1:####################\n" +
                        "41: 1:\n" +
                        "41: 1:Scores: 0\n" +
                        "41: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "42: 1:####################\n" +
                        "42: 1:####################\n" +
                        "42: 1:##.........###....##\n" +
                        "42: 1:##..........O.....##\n" +
                        "42: 1:##.M..............##\n" +
                        "42: 1:##...............M##\n" +
                        "42: 1:##................##\n" +
                        "42: 1:##................##\n" +
                        "42: 1:#X................##\n" +
                        "42: 1:##................#L\n" +
                        "42: 1:##................##\n" +
                        "42: 1:##................##\n" +
                        "42: 1:##................##\n" +
                        "42: 1:##........M.......##\n" +
                        "42: 1:##................##\n" +
                        "42: 1:##................##\n" +
                        "42: 1:##................##\n" +
                        "42: 1:##................##\n" +
                        "42: 1:############X#######\n" +
                        "42: 1:####L###############\n" +
                        "42: 1:\n" +
                        "42: 1:Scores: 0\n" +
                        "42: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "43: 1:####################\n" +
                        "43: 1:####################\n" +
                        "43: 1:##.........#O#....##\n" +
                        "43: 1:##..........#.....##\n" +
                        "43: 1:##..............M.##\n" +
                        "43: 1:##..M.............##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:#X................L#\n" +
                        "43: 1:##................##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:##.........M......##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:##................##\n" +
                        "43: 1:#####L######X#######\n" +
                        "43: 1:####################\n" +
                        "43: 1:\n" +
                        "43: 1:Scores: 0\n" +
                        "43: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "44: 1:####################\n" +
                        "44: 1:############O#######\n" +
                        "44: 1:##.........###....##\n" +
                        "44: 1:##..........#..M..##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##...M............##\n" +
                        "44: 1:##................#L\n" +
                        "44: 1:#X................##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##..........M.....##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:##................##\n" +
                        "44: 1:############X#######\n" +
                        "44: 1:######L#############\n" +
                        "44: 1:\n" +
                        "44: 1:Scores: 0\n" +
                        "44: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "45: 1:############O#######\n" +
                        "45: 1:####################\n" +
                        "45: 1:##.........###M...##\n" +
                        "45: 1:##..........#.....##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##................L#\n" +
                        "45: 1:##....M...........##\n" +
                        "45: 1:#X................##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##...........M....##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:##................##\n" +
                        "45: 1:#######L####X#######\n" +
                        "45: 1:####################\n" +
                        "45: 1:\n" +
                        "45: 1:Scores: 0\n" +
                        "45: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "46: 1:####################\n" +
                        "46: 1:############O#######\n" +
                        "46: 1:##.........###....##\n" +
                        "46: 1:##..........#..M..##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................#L\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:#X.....M..........##\n" +
                        "46: 1:##............M...##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:##................##\n" +
                        "46: 1:############X#######\n" +
                        "46: 1:########L###########\n" +
                        "46: 1:\n" +
                        "46: 1:Scores: 0\n" +
                        "46: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "47: 1:############O#######\n" +
                        "47: 1:####################\n" +
                        "47: 1:##.........###....##\n" +
                        "47: 1:##..........#.....##\n" +
                        "47: 1:##..............M.L#\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:#X.............M..##\n" +
                        "47: 1:##......M.........##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:##................##\n" +
                        "47: 1:#########L##X#######\n" +
                        "47: 1:####################\n" +
                        "47: 1:\n" +
                        "47: 1:Scores: 0\n" +
                        "47: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "48: 1:###########O########\n" +
                        "48: 1:####################\n" +
                        "48: 1:##.........###....##\n" +
                        "48: 1:##..........#.....#L\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##...............M##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##..............M.##\n" +
                        "48: 1:#X................##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##.......M........##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:##................##\n" +
                        "48: 1:############X#######\n" +
                        "48: 1:##########L#########\n" +
                        "48: 1:\n" +
                        "48: 1:Scores: 0\n" +
                        "48: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "49: 1:##########O#########\n" +
                        "49: 1:####################\n" +
                        "49: 1:##.........###....L#\n" +
                        "49: 1:##..........#.....##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##..............MM##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:#X................##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##........M.......##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:##................##\n" +
                        "49: 1:###########LX#######\n" +
                        "49: 1:####################\n" +
                        "49: 1:\n" +
                        "49: 1:Scores: 0\n" +
                        "49: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "50: 1:###########O########\n" +
                        "50: 1:###################L\n" +
                        "50: 1:##.........###....##\n" +
                        "50: 1:##..........#.....##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##..............M.##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##.............M..##\n" +
                        "50: 1:#X................##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##.........M......##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:##................##\n" +
                        "50: 1:############X#######\n" +
                        "50: 1:############L#######\n" +
                        "50: 1:\n" +
                        "50: 1:Scores: 0\n" +
                        "50: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "51: 1:###########O######L#\n" +
                        "51: 1:####################\n" +
                        "51: 1:##.........###....##\n" +
                        "51: 1:##..........#.....##\n" +
                        "51: 1:##.............M..##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:#X............M...##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##..........M.....##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:##................##\n" +
                        "51: 1:############XL######\n" +
                        "51: 1:####################\n" +
                        "51: 1:\n" +
                        "51: 1:Scores: 0\n" +
                        "51: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "52: 1:###########O########\n" +
                        "52: 1:#################L##\n" +
                        "52: 1:##.........###....##\n" +
                        "52: 1:##..........#.M...##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:#X................##\n" +
                        "52: 1:##...........M....##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##...........M....##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:##................##\n" +
                        "52: 1:############X#######\n" +
                        "52: 1:##############L#####\n" +
                        "52: 1:\n" +
                        "52: 1:Scores: 0\n" +
                        "52: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "53: 1:############O###L###\n" +
                        "53: 1:####################\n" +
                        "53: 1:##.........###....##\n" +
                        "53: 1:##..........#.....##\n" +
                        "53: 1:##.............M..##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:#X................##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:##..........M.....##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:##............M...##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:##................##\n" +
                        "53: 1:############X##L####\n" +
                        "53: 1:####################\n" +
                        "53: 1:\n" +
                        "53: 1:Scores: 0\n" +
                        "53: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "54: 1:#############O######\n" +
                        "54: 1:###############L####\n" +
                        "54: 1:##.........###....##\n" +
                        "54: 1:##..........#.....##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:##..............M.##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:#X................##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:##.........M......##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:##.............M..##\n" +
                        "54: 1:##................##\n" +
                        "54: 1:############X#######\n" +
                        "54: 1:################L###\n" +
                        "54: 1:\n" +
                        "54: 1:Scores: 0\n" +
                        "54: 1:Answer: UP\n" +
                        "54: 1:Fire Event: DIE\n" +
                        "------------------------------------------\n" +
                        "55: 1:##########O#########\n" +
                        "55: 1:####################\n" +
                        "55: 1:##.........###....##\n" +
                        "55: 1:##..........#.....##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##...............M##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:#L................##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##........M.......##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##................##\n" +
                        "55: 1:##..............M.##\n" +
                        "55: 1:############L#######\n" +
                        "55: 1:####################\n" +
                        "55: 1:\n" +
                        "55: 1:Scores: 0\n" +
                        "55: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "56: 1:#########O##########\n" +
                        "56: 1:####################\n" +
                        "56: 1:##.........###....##\n" +
                        "56: 1:##..........#.....##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:L#..............M.##\n" +
                        "56: 1:#X................##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:##.......M........##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:##...............M##\n" +
                        "56: 1:##................##\n" +
                        "56: 1:############X#######\n" +
                        "56: 1:###########L########\n" +
                        "56: 1:\n" +
                        "56: 1:Scores: 0\n" +
                        "56: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "57: 1:#########O##########\n" +
                        "57: 1:####################\n" +
                        "57: 1:##.........###....##\n" +
                        "57: 1:##..........#.....##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:#L.............M..##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##......M.........##\n" +
                        "57: 1:##..............M.##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:##................##\n" +
                        "57: 1:############L#######\n" +
                        "57: 1:####################\n" +
                        "57: 1:\n" +
                        "57: 1:Scores: 0\n" +
                        "57: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "58: 1:##########O#########\n" +
                        "58: 1:####################\n" +
                        "58: 1:##.........###....##\n" +
                        "58: 1:##..........#.....##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:#X................##\n" +
                        "58: 1:L#............M...##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:##.............M..##\n" +
                        "58: 1:##.....M..........##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:##................##\n" +
                        "58: 1:############X#######\n" +
                        "58: 1:#############L######\n" +
                        "58: 1:\n" +
                        "58: 1:Scores: 0\n" +
                        "58: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "59: 1:####################\n" +
                        "59: 1:##########O#########\n" +
                        "59: 1:##.........###....##\n" +
                        "59: 1:##..........#.....##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:#X................##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:#L...........M....##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:##............M...##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:##....M...........##\n" +
                        "59: 1:##................##\n" +
                        "59: 1:############X#L#####\n" +
                        "59: 1:####################\n" +
                        "59: 1:\n" +
                        "59: 1:Scores: 0\n" +
                        "59: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "60: 1:####################\n" +
                        "60: 1:#########O##########\n" +
                        "60: 1:##.........###....##\n" +
                        "60: 1:##..........#.....##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:#X................##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:L#..........M.....##\n" +
                        "60: 1:##...........M....##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:##................##\n" +
                        "60: 1:##...M............##\n" +
                        "60: 1:############X#######\n" +
                        "60: 1:###############L####\n" +
                        "60: 1:\n" +
                        "60: 1:Scores: 0\n" +
                        "60: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "61: 1:####################\n" +
                        "61: 1:########O###########\n" +
                        "61: 1:##.........###....##\n" +
                        "61: 1:##..........#.....##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:#X................##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:##..........M.....##\n" +
                        "61: 1:#L.........M......##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:##..M.............##\n" +
                        "61: 1:##................##\n" +
                        "61: 1:############X###L###\n" +
                        "61: 1:####################\n" +
                        "61: 1:\n" +
                        "61: 1:Scores: 0\n" +
                        "61: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "62: 1:####################\n" +
                        "62: 1:#######O############\n" +
                        "62: 1:##.........###....##\n" +
                        "62: 1:##..........#.....##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:#X................##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:##.........M......##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:L#........M.......##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:##.M..............##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:##................##\n" +
                        "62: 1:############X#######\n" +
                        "62: 1:#################L##\n" +
                        "62: 1:\n" +
                        "62: 1:Scores: 0\n" +
                        "62: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "63: 1:#######O############\n" +
                        "63: 1:####################\n" +
                        "63: 1:##.........###....##\n" +
                        "63: 1:##..........#.....##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:#X................##\n" +
                        "63: 1:##........M.......##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:#LM......M........##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:##................##\n" +
                        "63: 1:############X#####L#\n" +
                        "63: 1:####################\n" +
                        "63: 1:\n" +
                        "63: 1:Scores: 0\n" +
                        "63: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "64: 1:#######O############\n" +
                        "64: 1:####################\n" +
                        "64: 1:##.........###....##\n" +
                        "64: 1:##..........#.....##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:#X.......M........##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:##.M..............##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:L#......M.........##\n" +
                        "64: 1:##................##\n" +
                        "64: 1:##................#L\n" +
                        "64: 1:############X#######\n" +
                        "64: 1:####################\n" +
                        "64: 1:\n" +
                        "64: 1:Scores: 0\n" +
                        "64: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "65: 1:########O###########\n" +
                        "65: 1:####################\n" +
                        "65: 1:##.........###....##\n" +
                        "65: 1:##..........#.....##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:##......M.........##\n" +
                        "65: 1:#X................##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:##..M.............##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:##................##\n" +
                        "65: 1:#L.....M..........L#\n" +
                        "65: 1:##................##\n" +
                        "65: 1:############X#######\n" +
                        "65: 1:####################\n" +
                        "65: 1:\n" +
                        "65: 1:Scores: 0\n" +
                        "65: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "66: 1:#######O############\n" +
                        "66: 1:####################\n" +
                        "66: 1:##.........###....##\n" +
                        "66: 1:##..........#.....##\n" +
                        "66: 1:##................##\n" +
                        "66: 1:##................##\n" +
                        "66: 1:##.....M..........##\n" +
                        "66: 1:##................##\n" +
                        "66: 1:#X................##\n" +
                        "66: 1:##................##\n" +
                        "66: 1:##................##\n" +
                        "66: 1:##...M............##\n" +
                        "66: 1:##................##\n" +
                        "66: 1:##................##\n" +
                        "66: 1:##................##\n" +
                        "66: 1:##................#L\n" +
                        "66: 1:##................##\n" +
                        "66: 1:L#....M...........##\n" +
                        "66: 1:############X#######\n" +
                        "66: 1:####################\n" +
                        "66: 1:\n" +
                        "66: 1:Scores: 0\n" +
                        "66: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "67: 1:####################\n" +
                        "67: 1:#######O############\n" +
                        "67: 1:##.........###....##\n" +
                        "67: 1:##..........#.....##\n" +
                        "67: 1:##................##\n" +
                        "67: 1:##....M...........##\n" +
                        "67: 1:##................##\n" +
                        "67: 1:##................##\n" +
                        "67: 1:#X................##\n" +
                        "67: 1:##................##\n" +
                        "67: 1:##....M...........##\n" +
                        "67: 1:##................##\n" +
                        "67: 1:##................##\n" +
                        "67: 1:##................##\n" +
                        "67: 1:##................L#\n" +
                        "67: 1:##................##\n" +
                        "67: 1:##...M............##\n" +
                        "67: 1:##................##\n" +
                        "67: 1:#L##########X#######\n" +
                        "67: 1:####################\n" +
                        "67: 1:\n" +
                        "67: 1:Scores: 0\n" +
                        "67: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "68: 1:####################\n" +
                        "68: 1:####################\n" +
                        "68: 1:##.....O...###....##\n" +
                        "68: 1:##..........#.....##\n" +
                        "68: 1:##...M............##\n" +
                        "68: 1:##................##\n" +
                        "68: 1:##................##\n" +
                        "68: 1:##................##\n" +
                        "68: 1:#X................##\n" +
                        "68: 1:##.....M..........##\n" +
                        "68: 1:##................##\n" +
                        "68: 1:##................##\n" +
                        "68: 1:##................##\n" +
                        "68: 1:##................#L\n" +
                        "68: 1:##................##\n" +
                        "68: 1:##..M.............##\n" +
                        "68: 1:##................##\n" +
                        "68: 1:##................##\n" +
                        "68: 1:############X#######\n" +
                        "68: 1:##L#################\n" +
                        "68: 1:\n" +
                        "68: 1:Scores: 0\n" +
                        "68: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "69: 1:####################\n" +
                        "69: 1:####################\n" +
                        "69: 1:##.....oO..###....##\n" +
                        "69: 1:##..M.......#.....##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:#X......M.........##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##................L#\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##.M..............##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:##................##\n" +
                        "69: 1:###L########X#######\n" +
                        "69: 1:####################\n" +
                        "69: 1:\n" +
                        "69: 1:Scores: 0\n" +
                        "69: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "70: 1:####################\n" +
                        "70: 1:####################\n" +
                        "70: 1:##.M...ooO.###....##\n" +
                        "70: 1:##..........#.....##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##.......M........##\n" +
                        "70: 1:#X................##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##................#L\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##M...............##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:##................##\n" +
                        "70: 1:############X#######\n" +
                        "70: 1:####L###############\n" +
                        "70: 1:\n" +
                        "70: 1:Scores: 0\n" +
                        "70: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "71: 1:####################\n" +
                        "71: 1:####################\n" +
                        "71: 1:##.....oooO###....##\n" +
                        "71: 1:##M.........#.....##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:##........M.......##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:#X................##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:##................L#\n" +
                        "71: 1:##................##\n" +
                        "71: 1:##.M..............##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:##................##\n" +
                        "71: 1:#####L######X#######\n" +
                        "71: 1:####################\n" +
                        "71: 1:\n" +
                        "71: 1:Scores: 0\n" +
                        "71: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "72: 1:####################\n" +
                        "72: 1:####################\n" +
                        "72: 1:##.....####O##....##\n" +
                        "72: 1:##..........#.....##\n" +
                        "72: 1:##.M..............##\n" +
                        "72: 1:##.........M......##\n" +
                        "72: 1:##................##\n" +
                        "72: 1:##................##\n" +
                        "72: 1:#X................##\n" +
                        "72: 1:##................#L\n" +
                        "72: 1:##................##\n" +
                        "72: 1:##..M.............##\n" +
                        "72: 1:##................##\n" +
                        "72: 1:##................##\n" +
                        "72: 1:##................##\n" +
                        "72: 1:##................##\n" +
                        "72: 1:##................##\n" +
                        "72: 1:##................##\n" +
                        "72: 1:############X#######\n" +
                        "72: 1:######L#############\n" +
                        "72: 1:\n" +
                        "72: 1:Scores: 0\n" +
                        "72: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "73: 1:####################\n" +
                        "73: 1:####################\n" +
                        "73: 1:##.....###O###....##\n" +
                        "73: 1:##..........#.....##\n" +
                        "73: 1:##..........M.....##\n" +
                        "73: 1:##..M.............##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:#X................L#\n" +
                        "73: 1:##................##\n" +
                        "73: 1:##...M............##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:##................##\n" +
                        "73: 1:#######L####X#######\n" +
                        "73: 1:####################\n" +
                        "73: 1:\n" +
                        "73: 1:Scores: 0\n" +
                        "73: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "74: 1:####################\n" +
                        "74: 1:##########O#########\n" +
                        "74: 1:##.....#######....##\n" +
                        "74: 1:##..........#.....##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:##...........M....##\n" +
                        "74: 1:##...M............##\n" +
                        "74: 1:##................#L\n" +
                        "74: 1:#X................##\n" +
                        "74: 1:##....M...........##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:##................##\n" +
                        "74: 1:############X#######\n" +
                        "74: 1:########L###########\n" +
                        "74: 1:\n" +
                        "74: 1:Scores: 0\n" +
                        "74: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "75: 1:####################\n" +
                        "75: 1:###########O########\n" +
                        "75: 1:##.....#######....##\n" +
                        "75: 1:##..........#.....##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##............M...L#\n" +
                        "75: 1:##....M...........##\n" +
                        "75: 1:#X.....M..........##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:##................##\n" +
                        "75: 1:#########L##X#######\n" +
                        "75: 1:####################\n" +
                        "75: 1:\n" +
                        "75: 1:Scores: 0\n" +
                        "75: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "76: 1:###########O########\n" +
                        "76: 1:####################\n" +
                        "76: 1:##.....#######....##\n" +
                        "76: 1:##..........#.....##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................#L\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##......M......M..##\n" +
                        "76: 1:#X.....M..........##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:##................##\n" +
                        "76: 1:############X#######\n" +
                        "76: 1:##########L#########\n" +
                        "76: 1:\n" +
                        "76: 1:Scores: 0\n" +
                        "76: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "77: 1:##########O#########\n" +
                        "77: 1:####################\n" +
                        "77: 1:##.....#######....##\n" +
                        "77: 1:##..........#.....##\n" +
                        "77: 1:##................L#\n" +
                        "77: 1:##................##\n" +
                        "77: 1:##.......M........##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:#X..............M.##\n" +
                        "77: 1:##......M.........##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:##................##\n" +
                        "77: 1:###########LX#######\n" +
                        "77: 1:####################\n" +
                        "77: 1:\n" +
                        "77: 1:Scores: 0\n" +
                        "77: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "78: 1:#########O##########\n" +
                        "78: 1:####################\n" +
                        "78: 1:##.....#######....##\n" +
                        "78: 1:##..........#.....#L\n" +
                        "78: 1:##................##\n" +
                        "78: 1:##........M.......##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:#X................##\n" +
                        "78: 1:##...............M##\n" +
                        "78: 1:##.......M........##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:##................##\n" +
                        "78: 1:############X#######\n" +
                        "78: 1:############L#######\n" +
                        "78: 1:\n" +
                        "78: 1:Scores: 0\n" +
                        "78: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "79: 1:########O###########\n" +
                        "79: 1:####################\n" +
                        "79: 1:##.....#######....L#\n" +
                        "79: 1:##..........#.....##\n" +
                        "79: 1:##.........M......##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:#X................##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:##..............M.##\n" +
                        "79: 1:##........M.......##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:##................##\n" +
                        "79: 1:############XL######\n" +
                        "79: 1:####################\n" +
                        "79: 1:\n" +
                        "79: 1:Scores: 0\n" +
                        "79: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "80: 1:####################\n" +
                        "80: 1:########O##########L\n" +
                        "80: 1:##.....#######....##\n" +
                        "80: 1:##..........#.....##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:##........M.......##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:#X................##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:##.............M..##\n" +
                        "80: 1:##.........M......##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:##................##\n" +
                        "80: 1:############X#######\n" +
                        "80: 1:##############L#####\n" +
                        "80: 1:\n" +
                        "80: 1:Scores: 0\n" +
                        "80: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "81: 1:##################L#\n" +
                        "81: 1:####################\n" +
                        "81: 1:##.....#O#####....##\n" +
                        "81: 1:##..........#.....##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:##.......M........##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:#X................##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:##............M...##\n" +
                        "81: 1:##..........M.....##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:##................##\n" +
                        "81: 1:############X##L####\n" +
                        "81: 1:####################\n" +
                        "81: 1:\n" +
                        "81: 1:Scores: 0\n" +
                        "81: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "82: 1:####################\n" +
                        "82: 1:#################L##\n" +
                        "82: 1:##.....##O####....##\n" +
                        "82: 1:##..........#.....##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##......M.........##\n" +
                        "82: 1:#X................##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##...........M....##\n" +
                        "82: 1:##...........M....##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:##................##\n" +
                        "82: 1:############X#######\n" +
                        "82: 1:################L###\n" +
                        "82: 1:\n" +
                        "82: 1:Scores: 0\n" +
                        "82: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "83: 1:################L###\n" +
                        "83: 1:####################\n" +
                        "83: 1:##.....###O###....##\n" +
                        "83: 1:##..........#.....##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:#X.....M..........##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##..........M.....##\n" +
                        "83: 1:##............M...##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:##................##\n" +
                        "83: 1:############X####L##\n" +
                        "83: 1:####################\n" +
                        "83: 1:\n" +
                        "83: 1:Scores: 0\n" +
                        "83: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "84: 1:####################\n" +
                        "84: 1:###############L####\n" +
                        "84: 1:##.....####O##....##\n" +
                        "84: 1:##..........#.....##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:#X................##\n" +
                        "84: 1:##....M...........##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:##.........M......##\n" +
                        "84: 1:##.............M..##\n" +
                        "84: 1:##................##\n" +
                        "84: 1:############X#######\n" +
                        "84: 1:##################L#\n" +
                        "84: 1:\n" +
                        "84: 1:Scores: 0\n" +
                        "84: 1:Answer: UP\n" +
                        "------------------------------------------\n" +
                        "85: 1:##############L#####\n" +
                        "85: 1:###########O########\n" +
                        "85: 1:##.....#######....##\n" +
                        "85: 1:##..........#.....##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:#X................##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##...M............##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##................##\n" +
                        "85: 1:##........M.......##\n" +
                        "85: 1:##..............M.##\n" +
                        "85: 1:############X######L\n" +
                        "85: 1:####################\n" +
                        "85: 1:\n" +
                        "85: 1:Scores: 0\n" +
                        "85: 1:Answer: RIGHT\n" +
                        "85: 1:Fire Event: GAME_OVER\n" +
                        "------------------------------------------\n" +
                        "86: 1:####################\n" +
                        "86: 1:############OL######\n" +
                        "86: 1:##.....#######....##\n" +
                        "86: 1:##..........#.....##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:#X................##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##..M.............##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##................##\n" +
                        "86: 1:##...............M##\n" +
                        "86: 1:##.......M........L#\n" +
                        "86: 1:############X#######\n" +
                        "86: 1:####################\n" +
                        "86: 1:\n" +
                        "86: 1:Scores: 0\n" +
                        "86: 1:Answer: LEFT\n" +
                        "86: 1:PLAYER_GAME_OVER -> START_NEW_GAME\n" +
                        "------------------------------------------\n" +
                        "87: 1:@@@@@@@@@@O@@@@@@@@@\n" +
                        "87: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "87: 1:@@.....@@@@@L@....@@\n" +
                        "87: 1:@@..........@.....@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@X................@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@.M..............@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@..............M.@@\n" +
                        "87: 1:@@......M.........@L\n" +
                        "87: 1:@@................@@\n" +
                        "87: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "87: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "87: 1:\n" +
                        "87: 1:Scores: 0\n" +
                        "87: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "88: 1:@@@@@@@@@O#@@@@@@@@@\n" +
                        "88: 1:@@@@@@@@@@@@@L@@@@@@\n" +
                        "88: 1:@@.....@@@@@@@....@@\n" +
                        "88: 1:@@..........@.....@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@X................@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@M...............@@\n" +
                        "88: 1:@@.............M..@@\n" +
                        "88: 1:@@.....M..........L@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@................@@\n" +
                        "88: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "88: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "88: 1:\n" +
                        "88: 1:Scores: 0\n" +
                        "88: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "89: 1:@@@@@@@@@@O@@@L@@@@@\n" +
                        "89: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "89: 1:@@.....@@@@@@@....@@\n" +
                        "89: 1:@@..........@.....@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@X................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@............M...@@\n" +
                        "89: 1:@@.M..M...........@L\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@................@@\n" +
                        "89: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "89: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "89: 1:\n" +
                        "89: 1:Scores: 0\n" +
                        "89: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "90: 1:@@@@@@@@@@#@@@@@@@@@\n" +
                        "90: 1:@@@@@@@@@@O@@L@@@@@@\n" +
                        "90: 1:@@.....@@@@@@@....@@\n" +
                        "90: 1:@@..........@.....@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@X................@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@...........M....@@\n" +
                        "90: 1:@@...M............L@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@..M.............@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@................@@\n" +
                        "90: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "90: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "90: 1:\n" +
                        "90: 1:Scores: 0\n" +
                        "90: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "91: 1:@@@@@@@@@@#@@@@@@@@@\n" +
                        "91: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "91: 1:@@.....@@@O@L@....@@\n" +
                        "91: 1:@@..........@.....@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@X................@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@..........M.....@@\n" +
                        "91: 1:@@..M.............@L\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@...M............@@\n" +
                        "91: 1:@@................@@\n" +
                        "91: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "91: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "91: 1:\n" +
                        "91: 1:Scores: 0\n" +
                        "91: 1:Answer: LEFT\n" +
                        "------------------------------------------\n" +
                        "92: 1:@@@@@@@@@@#@@@@@@@@@\n" +
                        "92: 1:@@@@@@@@@@@@@L@@@@@@\n" +
                        "92: 1:@@.....@@O@@@@....@@\n" +
                        "92: 1:@@..........@.....@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@X................@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@.........M......@@\n" +
                        "92: 1:@@.M..............L@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@................@@\n" +
                        "92: 1:@@....M...........@@\n" +
                        "92: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "92: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "92: 1:\n" +
                        "92: 1:Scores: 0\n" +
                        "92: 1:Answer: RIGHT\n" +
                        "------------------------------------------\n" +
                        "93: 1:@@@@@@@@@@#@@@L@@@@@\n" +
                        "93: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "93: 1:@@.....@@@O@@@....@@\n" +
                        "93: 1:@@..........@.....@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@X................@@\n" +
                        "93: 1:@@........M.......@@\n" +
                        "93: 1:@@M...............@L\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@.....M..........@@\n" +
                        "93: 1:@@................@@\n" +
                        "93: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "93: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "93: 1:\n" +
                        "93: 1:Scores: 0\n" +
                        "93: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "94: 1:@@@@@@@@@@#@@@@@@@@@\n" +
                        "94: 1:@@@@@@@@@@@@@L@@@@@@\n" +
                        "94: 1:@@.....@@@@@@@....@@\n" +
                        "94: 1:@@........O.@.....@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@X.......M........@@\n" +
                        "94: 1:@@.M..............L@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@......M.........@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@................@@\n" +
                        "94: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "94: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "94: 1:\n" +
                        "94: 1:Scores: 0\n" +
                        "94: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "95: 1:@@@@@@@@@@#@@@@@@@@@\n" +
                        "95: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "95: 1:@@.....@@@@@L@....@@\n" +
                        "95: 1:@@........o.@.....@@\n" +
                        "95: 1:@@........O.......@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@......M.........@@\n" +
                        "95: 1:@X..M.............@L\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@.......M........@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@................@@\n" +
                        "95: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "95: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "95: 1:\n" +
                        "95: 1:Scores: 0\n" +
                        "95: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "96: 1:@@@@@@@@@@#@@@@@@@@@\n" +
                        "96: 1:@@@@@@@@@@@@@L@@@@@@\n" +
                        "96: 1:@@.....@@@@@@@....@@\n" +
                        "96: 1:@@........o.@.....@@\n" +
                        "96: 1:@@........o.......@@\n" +
                        "96: 1:@@........O.......@@\n" +
                        "96: 1:@@.....M..........@@\n" +
                        "96: 1:@@...M............L@\n" +
                        "96: 1:@X................@@\n" +
                        "96: 1:@@................@@\n" +
                        "96: 1:@@................@@\n" +
                        "96: 1:@@................@@\n" +
                        "96: 1:@@................@@\n" +
                        "96: 1:@@........M.......@@\n" +
                        "96: 1:@@................@@\n" +
                        "96: 1:@@................@@\n" +
                        "96: 1:@@................@@\n" +
                        "96: 1:@@................@@\n" +
                        "96: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "96: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "96: 1:\n" +
                        "96: 1:Scores: 0\n" +
                        "96: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "97: 1:@@@@@@@@@@#@@@L@@@@@\n" +
                        "97: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "97: 1:@@.....@@@@@@@....@@\n" +
                        "97: 1:@@........o.@.....@@\n" +
                        "97: 1:@@........o.......@@\n" +
                        "97: 1:@@....M...o.......@@\n" +
                        "97: 1:@@....M...O.......@L\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@X................@@\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@@.........M......@@\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@@................@@\n" +
                        "97: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "97: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "97: 1:\n" +
                        "97: 1:Scores: 0\n" +
                        "97: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "98: 1:@@@@@@@@@@#@@@@@@@@@\n" +
                        "98: 1:@@@@@@@@@@@@@L@@@@@@\n" +
                        "98: 1:@@.....@@@@@@@....@@\n" +
                        "98: 1:@@........o.@.....@@\n" +
                        "98: 1:@@...M....o.......@@\n" +
                        "98: 1:@@.....M..o.......L@\n" +
                        "98: 1:@@........o.......@@\n" +
                        "98: 1:@@........O.......@@\n" +
                        "98: 1:@X................@@\n" +
                        "98: 1:@@................@@\n" +
                        "98: 1:@@................@@\n" +
                        "98: 1:@@..........M.....@@\n" +
                        "98: 1:@@................@@\n" +
                        "98: 1:@@................@@\n" +
                        "98: 1:@@................@@\n" +
                        "98: 1:@@................@@\n" +
                        "98: 1:@@................@@\n" +
                        "98: 1:@@................@@\n" +
                        "98: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "98: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "98: 1:\n" +
                        "98: 1:Scores: 0\n" +
                        "98: 1:Answer: DOWN\n" +
                        "------------------------------------------\n" +
                        "99: 1:@@@@@@@@@@#@@@@@@@@@\n" +
                        "99: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "99: 1:@@.....@@@@@L@....@@\n" +
                        "99: 1:@@..M.....o.@.....@@\n" +
                        "99: 1:@@......M.o.......@L\n" +
                        "99: 1:@@........o.......@@\n" +
                        "99: 1:@@........o.......@@\n" +
                        "99: 1:@@........o.......@@\n" +
                        "99: 1:@X........O.......@@\n" +
                        "99: 1:@@................@@\n" +
                        "99: 1:@@...........M....@@\n" +
                        "99: 1:@@................@@\n" +
                        "99: 1:@@................@@\n" +
                        "99: 1:@@................@@\n" +
                        "99: 1:@@................@@\n" +
                        "99: 1:@@................@@\n" +
                        "99: 1:@@................@@\n" +
                        "99: 1:@@................@@\n" +
                        "99: 1:@@@@@@@@@@@@X@@@@@@@\n" +
                        "99: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "99: 1:\n" +
                        "99: 1:Scores: 0\n" +
                        "99: 1:Answer: RIGHT\n" +
                        "99: 1:Fire Event: DIE\n" +
                        "------------------------------------------\n" +
                        "100: 1:@@@@@@@@@@O@@@@@@@@@\n" +
                        "100: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "100: 1:@@.M...@@@@@@@....@@\n" +
                        "100: 1:@@.......M..@.....@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@L................@@\n" +
                        "100: 1:@@............M...@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@................@@\n" +
                        "100: 1:@@@@@@@@@@@@L@@@@@@@\n" +
                        "100: 1:@@@@@@@@@@@@@@@@@@@@\n" +
                        "100: 1:\n" +
                        "100: 1:Scores: 0\n" +
                        "100: 1:Answer: DOWN\n" +
                        "------------------------------------------",
                String.join("\n", messages));
    }
}
