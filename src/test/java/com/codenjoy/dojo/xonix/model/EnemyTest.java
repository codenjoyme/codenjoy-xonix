package com.codenjoy.dojo.xonix.model;

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

import com.codenjoy.dojo.services.QDirection;
import com.codenjoy.dojo.xonix.services.Event;
import org.junit.Test;

public class EnemyTest extends AbstractGameTest {

    @Test
    public void shouldMove() {
        // given
        givenFl("O####" +
                "#...#" +
                "#M..#" +
                "#...#" +
                "#####");
        shouldEnemiesGo(QDirection.RIGHT_UP);
        // when
        game.tick();

        // then
        assertE("O####" +
                "#.M.#" +
                "#...#" +
                "#...#" +
                "#####");
    }

    @Test
    public void shouldNotJumpOverCorners() {
        // given
        givenFl("O####" +
                "##..#" +
                "#M..#" +
                "#...#" +
                "#####");

        // when
        game.tick();

        // then
        assertE("O####" +
                "##..#" +
                "#...#" +
                "#.M.#" +
                "#####");
    }

    @Test
    public void shouldBounce_whenHitsCorner() {
        // given
        givenFl("O####" +
                "#.#.#" +
                "#M..#" +
                "#...#" +
                "#####");

        // when
        game.tick();

        // then
        assertE("O####" +
                "#.#.#" +
                "#...#" +
                "#.M.#" +
                "#####");
    }

    @Test
    public void shouldBounce_whenInNarrowSpace() {
        // given
        givenFl("O#######" +
                "#......#" +
                "#...####" +
                "#..M...#" +
                "#...####" +
                "#......#" +
                "#......#" +
                "########");

        // when
        game.tick();

        // then
        assertE("O#######" +
                "#......#" +
                "#...####" +
                "#......#" +
                "#.M.####" +
                "#......#" +
                "#......#" +
                "########");
    }

    @Test
    public void shouldProperlyBounce_whenHitTheLand() {
        // given
        shouldMove();

        // when
        game.tick();

        // then
        assertE("O####" +
                "#...#" +
                "#..M#" +
                "#...#" +
                "#####");

        // when
        game.tick();

        // then
        assertE("O####" +
                "#...#" +
                "#...#" +
                "#.M.#" +
                "#####");

        // when
        game.tick();

        // then
        assertE("O####" +
                "#...#" +
                "#M..#" +
                "#...#" +
                "#####");
    }

    @Test
    public void shouldProperlyBounce_whenHitCorner() {
        // given
        givenFl("O####" +
                "#..##" +
                "#M..#" +
                "#...#" +
                "#####");
        shouldEnemiesGo(QDirection.RIGHT_UP);
        // when
        game.tick();

        // then
        assertE("O####" +
                "#.M##" +
                "#...#" +
                "#...#" +
                "#####");

        // when
        game.tick();

        // then
        assertE("O####" +
                "#..##" +
                "#M..#" +
                "#...#" +
                "#####");
    }

    @Test
    public void marineEnemyShouldKill_whenHitTrace() {
        // given
        givenFl("#####O#" +
                "#.....#" +
                "#.....#" +
                "#M....#" +
                "#.....#" +
                "#.....#" +
                "#######");
        shouldEnemiesGo(QDirection.RIGHT_UP);
        // when
        hero.down();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("#####O#" +
                "#.....#" +
                "#...M.#" +
                "#.....#" +
                "#.....#" +
                "#.....#" +
                "#######");


        fired(Event.DIE);
    }

    @Test
    public void marineEnemyShouldNotKill_whenHeroIsOnLand() {
        // given
        givenFl("#######" +
                "#.....#" +
                "#.MMM.#" +
                "#.MOM.#" +
                "#.MMM.#" +
                "#.....#" +
                "#######");

        shouldEnemiesGo(null);

        // when
        game.tick();
        game.tick();

        // then
        neverFired(Event.GAME_OVER);
    }

    @Test
    public void landEnemyShouldNotKill_whenHeroIsAtSea() {
        // given
        givenFl("###O###" +
                "###.###" +
                "##L.L##" +
                "##L.L##" +
                "##L.L##" +
                "##L.L##" +
                "##LLL##");

        shouldEnemiesGo(null);

        // when
        hero.down();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();

        // then
        neverFired(Event.GAME_OVER);
    }

    @Test
    public void landEnemyShouldNotKill_whenHeroIsAtSea1() {
        // given
        givenFl("O......" +
                ".###..." +
                ".#.#..." +
                ".#.#..." +
                ".##L..." +
                ".###..." +
                ".###...");

        shouldEnemiesGo(QDirection.RIGHT_UP);

        // when
        game.tick();

        // then
        assertE("O......" +
                ".###..." +
                ".#.#..." +
                ".#.#..." +
                ".###..." +
                ".#L#..." +
                ".###...");
    }
}