package com.codenjoy.dojo.xonix.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
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


import com.codenjoy.dojo.services.PlayerScores;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.*;
import static org.junit.Assert.assertEquals;

public class ScoresTest {

    private PlayerScores scores;
    private GameSettings settings;

    public void killed() {
        scores.event(Event.KILLED);
    }

    public void win() {
        scores.event(Event.WIN);
    }

    public void gameOver() {
        scores.event(Event.GAME_OVER);
    }


    @Before
    public void setup() {
        settings = new GameSettings();
        scores = new Scores(0, settings);
    }

    @Test
    public void shouldCollectScores() {
        scores = new Scores(140, settings);
        settings.getParameter(LIVES_COUNT.key()).update(100);

        win();
        win();
        win();
        win();


        assertEquals(140
                + 4 * settings.integer(WIN_REWARD),
                scores.getScore());
    }

    @Test
    public void shouldNotLessThanZero() {
        killed();
        killed();
        killed();
        killed();
        killed();

        assertEquals(Scores.MIN_SCORE, scores.getScore());
    }

    @Test
    public void shouldClearScore() {
        win();

        scores.clear();

        assertEquals(Scores.MIN_SCORE, scores.getScore());
    }
}