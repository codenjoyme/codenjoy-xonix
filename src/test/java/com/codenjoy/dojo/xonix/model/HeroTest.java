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

import com.codenjoy.dojo.xonix.services.Event;
import org.junit.Test;


public class HeroTest extends AbstractGameTest {

    @Test
    public void shouldLeaveTrace_whenGoThroughSea() {
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

        // then
        assertE("#####" +
                "#.o.#" +
                "#.O.#" +
                "#...#" +
                "#####");
    }

    @Test
    public void shouldStop_whenHitsFieldBorders() {
        // given
        givenFl("##O##" +
                "#...#" +
                "#...#" +
                "#...#" +
                "#####");

        // when
        hero.right();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("####O" +
                "#...#" +
                "#...#" +
                "#...#" +
                "#####");
    }

    @Test
    public void shouldBeAbleToTurn_whenFloating() {
        // given
        shouldLeaveTrace_whenGoThroughSea();

        // when
        hero.left();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#.###" +
                "O####" +
                "#####" +
                "#####");
    }

    @Test
    // https://www.youtube.com/watch?v=POhMfAFZ_6c (1:38)
    public void shouldMakeLandOnlyTrace_whenTraceLengthIsOne() {
        // given
        givenFl("##O##" +
                "#...#" +
                "#####" +
                "#...#" +
                "#####");

        // when
        hero.down();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#.#.#" +
                "##O##" +
                "#...#" +
                "#####");
    }

    @Test
    public void shouldNotStop_whenFloating() {
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
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#.###" +
                "#.###" +
                "#.###" +
                "##O##");
    }

    @Test
    public void shouldStop_whenLanded() {
        // given
        givenFl("##O##" +
                "#...#" +
                "#####" +
                "#...#" +
                "#####");

        // when
        hero.down();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#.#.#" +
                "##O##" +
                "#...#" +
                "#####");
    }

    @Test
    public void shouldNotStop_whenMovingOnLand() {
        // given
        givenFl("#####" +
                "#...#" +
                "##O##" +
                "#####" +
                "#####");

        // when
        hero.down();
        game.tick();
        game.tick();
        hero.left();
        game.tick();

        // then
        assertE("#####" +
                "#...#" +
                "#####" +
                "#####" +
                "#O###");
    }

    @Test
    public void shouldSeizeThatPartOfTheSea_whereAreNoEnemies1() {
        // given
        givenFl("##O#######" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#M.......#" +
                "##########");

        game.enemies().forEach(e -> e.setDirection(null));

        // when
        hero.down();
        game.tick();
        game.tick();
        hero.right();
        game.tick();
        game.tick();
        game.tick();
        hero.up();
        game.tick();
        hero.right();
        game.tick();
        game.tick();

        // then
        assertE("##########" +
                "#.o..ooO.#" +
                "#.oooo...#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#M.......#" +
                "##########");

        hero.up();
        game.tick();

        assertE("#######O##" +
                "#.#..###.#" +
                "#.####...#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#M.......#" +
                "##########");
    }

    @Test
    public void shouldSeizeThatPartOfTheSea_whereAreNoEnemies2() {
        // given
        givenFl("####O#####" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#........#" +
                "#M......M#" +
                "##########");

        game.enemies().forEach(e -> e.setDirection(null));

        // when
        hero.down();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("##########" +
                "#...#....#" +
                "#...#....#" +
                "#...#....#" +
                "#...#....#" +
                "#...#....#" +
                "#...#....#" +
                "#...#....#" +
                "#M..#...M#" +
                "####O#####");
    }

    @Test
    public void shouldDie_whenTurnsAroundAtSea() {
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
        hero.up();
        game.tick();

        // then
        fired(Event.DIE);
    }

    @Test
    public void shouldNotDie_whenTurnsAroundAtLand() {
        // given
        givenFl("##O##" +
                "#...#" +
                "#...#" +
                "#...#" +
                "#####");

        // when
        hero.left();
        game.tick();
        game.tick();
        hero.right();
        game.tick();

        // then
        neverFired(Event.DIE);
    }

    @Test
    public void shouldDie_whenHitsOwnTrace() {
        // given
        givenFl("####O#" +
                "#....#" +
                "#....#" +
                "#....#" +
                "#....#" +
                "######");

        // when
        hero.down();
        game.tick();
        game.tick();
        game.tick();
        hero.left();
        game.tick();
        hero.up();
        game.tick();
        hero.right();
        game.tick();

        // then
        fired(Event.DIE);
    }
}