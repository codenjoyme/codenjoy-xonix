package com.codenjoy.dojo.xonix.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
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

import com.codenjoy.dojo.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameSettingsTest {

    @Test
    public void shouldGetAllKeys() {
        assertEquals("WIN_CRITERION  =[Game] How much % sea Hero should get\n" +
                    "WIN_SCORE      =[Score] Scores for winning\n" +
                    "DIE_PENALTY    =[Score] Die penalty\n" +
                    "LIVES_COUNT    =[Game] Lives count\n" +
                    "LEVELS_COUNT   =[Level] Levels count\n" +
                    "IS_MULTIPLAYER =[Game] Multiplayer\n" +
                    "ROOM_SIZE      =[Level] Room size",
                TestUtils.toString(new GameSettings().allKeys()));
    }
}