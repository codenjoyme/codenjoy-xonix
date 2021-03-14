package com.codenjoy.dojo.xonix.model;

import com.codenjoy.dojo.services.Direction;
import org.junit.Test;

public class EnemiesTest extends AbstractGameTest {

    @Test
    public void marineEnemyShouldMove() {

        // given
        givenFl("O####" +
                "#...#" +
                "#M..#" +
                "#...#" +
                "#####");
        game.getEnemies().forEach(e -> e.setDirection(Direction.UP));
        // when
        game.tick();

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
        game.getEnemies().forEach(e -> e.setDirection(Direction.UP));
        // when
        game.tick();

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
        game.getEnemies().forEach(e -> e.setDirection(Direction.UP));
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
    public void marineEnemyShouldProperlyBounce_whenHitTheLand() {

        // given
        marineEnemyShouldMove();

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
    public void marineEnemyShouldProperlyBounce_whenHitCorner() {

        // given
        givenFl("O####" +
                "#..##" +
                "#M..#" +
                "#...#" +
                "#####");
        game.getEnemies().forEach(e -> e.setDirection(Direction.UP));

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
}
