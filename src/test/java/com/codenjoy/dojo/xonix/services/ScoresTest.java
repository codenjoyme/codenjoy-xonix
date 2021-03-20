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

    public void die() {
        scores.event(Event.DIE);
    }

    public void win() {
        scores.event(Event.WIN);
    }

    public void gameOver() {
        scores.event(Event.GAME_OVER);
    }

    public void annihilation() {
        scores.event(Event.ANNIHILATION);
    }

    @Before
    public void setup() {
        settings = new GameSettings();
        scores = new Scores(0, settings);
    }

    @Test
    public void shouldCollectScores() {
        // given
        scores = new Scores(140, settings);

        // when
        win();
        win();
        win();
        win();

        die();
        die();

        gameOver();

        annihilation();
        annihilation();

        // then
        assertEquals(140
                    + 4 * settings.integer(WIN_SCORES)
                    - 2 * settings.integer(DIE_PENALTY),
                scores.getScore());
    }

    @Test
    public void shouldNotLessThanZero() {
        // when
        die();
        die();
        die();
        die();
        die();

        // then
        assertEquals(Scores.MIN_SCORE, scores.getScore());
    }

    @Test
    public void shouldClearScore() {
        // when
        win();

        scores.clear();

        // then
        assertEquals(Scores.MIN_SCORE, scores.getScore());
    }
}