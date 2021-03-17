package com.codenjoy.dojo.xonix.model;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.xonix.services.Event;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XonixGameTest extends AbstractGameTest {

    @Test
    public void xonixShouldBeKilled_whenMeetsMarineEnemy() {

        // given
        givenFl("##O##" +
                "#...#" +
                "#...#" +
                "#.M.#" +
                "#####");
        game.getEnemies().forEach(e -> e.setDirection(null));

        // when
        hero.down();
        game.tick();
        game.tick();

        // then
        fired("[KILLED]");
    }

    @Test
    public void xonixShouldBeKilled_whenMeetsLandEnemy() {

        // given
        givenFl("##O##" +
                "#...#" +
                "#L###" +
                "#####" +
                "#####");
        game.getEnemies().forEach(e -> e.setDirection(null));

        // when
        hero.down();
        game.tick();
        game.tick();

        // then
        fired("[KILLED]");
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
        fired("[WIN]");
    }

    @Test
    public void shouldDecreaseOnly1Life_whenXonixIsHittingOwnTraceAndWhenBeingKilledByEnemySimultaneously() {

        // given
        givenFl("##O###" +
                "#....#" +
                "#...M#" +
                "#....#" +
                "#....#" +
                "######");
        game.getEnemies().forEach(e -> e.setDirection(null));
        int lives = hero.getLives();

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

        game.getEnemies().forEach(e -> e.setDirection(Direction.LEFT));

        // when
        hero.right();
        game.tick();

        // then
        fired("[KILLED]");
        assertEquals(lives - 1, hero.getLives());
    }

    @Test
    public void xonixShouldNotBeKilledByLandEnemy_whenFloating() {

        // given
        givenFl("LLL#O#" +
                "L....L" +
                "L....L" +
                "L....L" +
                "L....L" +
                "LLLLLL");
        game.getEnemies().forEach(e -> e.setDirection(null));

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

        neverFired(Event.KILLED);
    }

    @Test
    public void xonixShouldNotSeizeSea_whenLandedOnEnemy() {

        // given
        xonixShouldNotBeKilledByLandEnemy_whenFloating();

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

        fired("[KILLED]");
    }
}
