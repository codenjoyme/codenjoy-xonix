package com.codenjoy.dojo.xonix.model;

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

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.xonix.services.Event;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest extends AbstractGameTest {

    @Test
    public void shouldBeKilled_whenMeetsMarineEnemy() {
        // given
        givenFl("##O##" +
                "#...#" +
                "#...#" +
                "#.M.#" +
                "#####");

        shouldEnemiesGo(null);

        // when
        hero.down();
        game.tick();
        game.tick();

        // then
        fired(Event.DIE);
    }

    @Test
    public void shouldBeKilled_whenMeetsLandEnemy() {
        // given
        givenFl("##O##" +
                "#...#" +
                "#L###" +
                "#####" +
                "#####");

        shouldEnemiesGo(null);

        // when
        hero.down();
        game.tick();
        game.tick();

        // then
        fired(Event.DIE);
    }

    @Test
    public void shouldWin_whenMakeRightAmountOfLand() {
        // given
        givenFl("##O##" +
                "#...#" +
                "#...#" +
                "#...#" +
                "#####");

        // when
        hero.down();
        game.tick();
        game.tick();
        hero.left();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#.###" +
                "O####" +
                "#####" +
                "#####");
        fired(Event.WIN);
    }

    @Test
    public void shouldDecreaseOnly1Life_whenHeroIsHittingOwnTraceAndWhenBeingKilledByEnemySimultaneously() {
        // given
        givenFl("##O###" +
                "#....#" +
                "#...M#" +
                "#....#" +
                "#....#" +
                "######");

        shouldEnemiesGo(null);
        int lives = hero.lives();

        hero.down();
        game.tick();
        game.tick();
        hero.left();
        game.tick();
        hero.up();
        game.tick();

        assertE("######" +
                "#Oo..#" +
                "#oo.M#" +
                "#....#" +
                "#....#" +
                "######");

        shouldEnemiesGo(Direction.LEFT);

        // when
        hero.right();
        game.tick();

        // then
        fired(Event.DIE);
        assertEquals(lives - 1, hero.lives());
    }

    @Test
    public void shouldNotBeKilledByLandEnemy_whenFloating() {
        // given
        givenFl("LLL#O#" +
                "L....L" +
                "L....L" +
                "L....L" +
                "L....L" +
                "LLLLLL");

        shouldEnemiesGo(null);

        // when
        hero.down();
        game.tick();
        game.tick();
        game.tick();
        game.tick();

        assertE("LLL###" +
                "L...oL" +
                "L...oL" +
                "L...oL" +
                "L...OL" +
                "LLLLLL");

        hero.left();
        game.tick();
        game.tick();
        game.tick();
        hero.up();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("LLL###" +
                "LO..oL" +
                "Lo..oL" +
                "Lo..oL" +
                "LooooL" +
                "LLLLLL");

        neverFired(Event.DIE);
    }

    @Test
    public void shouldNotSeizeSea_whenLandedOnEnemy() {
        // given
        shouldNotBeKilledByLandEnemy_whenFloating();

        // when
        hero.up();
        game.tick();

        // then
        assertE("LLL#O#" +
                "L....L" +
                "L....L" +
                "L....L" +
                "L....L" +
                "LLLLLL");

        fired(Event.DIE);
    }
}