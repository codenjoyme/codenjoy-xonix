package com.codenjoy.dojo.xonix.model;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.xonix.services.Event;
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
        game.getEnemies().forEach(e -> e.setDirection(Direction.UP));
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
        game.getEnemies().forEach(e -> e.setDirection(Direction.UP));

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


        fired("[KILLED]");
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
        game.getEnemies().forEach(e -> e.setDirection(null));

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
        game.getEnemies().forEach(e -> e.setDirection(null));

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
}
