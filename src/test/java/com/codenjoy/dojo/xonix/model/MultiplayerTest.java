package com.codenjoy.dojo.xonix.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class MultiplayerTest extends AbstractMultiplayerTest {

    @Test
    public void shouldLookDifferentlyForEachOther() {

        // given
        givenFl("##O##" +
                "#...#" +
                "O...#" +
                "#...#" +
                "###O#");

        givenPlayer();
        givenPlayer();
        givenPlayer();


        // then
        assertF("##O##" +
                "#...#" +
                "A...#" +
                "#...#" +
                "###A#", game(0));

        assertF("##A##" +
                "#...#" +
                "O...#" +
                "#...#" +
                "###A#", game(1));

        assertF("##A##" +
                "#...#" +
                "A...#" +
                "#...#" +
                "###O#", game(2));
    }

    @Test
    public void shouldBeIndependentlyControlled() {

        // given
        givenFl("##O##" +
                "#...#" +
                "O...#" +
                "#...#" +
                "####O");
        givenPlayer();
        givenPlayer();
        givenPlayer();

        // when
        game(0).getJoystick().down();
        game(1).getJoystick().right();
        game(2).getJoystick().up();

        tick();

        // then
        assertF("#####" +
                "#.O.#" +
                "#A..#" +
                "#...A" +
                "#####", game(0));
    }

    @Test
    public void playersShouldBeRemovable() {

        // given
        givenFl("##O##" +
                "#...#" +
                "O...#" +
                "#...#" +
                "####O");
        givenPlayer();
        givenPlayer();
        givenPlayer();

        // when
        game(2).close();

        tick();

        // then
        assertF("##O##" +
                "#...#" +
                "A...#" +
                "#...#" +
                "#####", game(0));
    }

    @Test
    public void tracesShouldLookDifferentlyForEachPlayer() {

        // given
        givenFl("##########" +
                "##########" +
                "#O......##" +
                "##......##" +
                "##......O#" +
                "##......##" +
                "##......##" +
                "##......##" +
                "###O######" +
                "##########");
        givenPlayer();
        givenPlayer();
        givenPlayer();

        // when
        game(0).getJoystick().right();
        game(1).getJoystick().left();
        game(2).getJoystick().up();

        tick();
        tick();

        // then
        assertF("##########" +
                "##########" +
                "##oO....##" +
                "##......##" +
                "##....Aa##" +
                "##......##" +
                "##.A....##" +
                "##.a....##" +
                "##########" +
                "##########", game(0));

        assertF("##########" +
                "##########" +
                "##aA....##" +
                "##......##" +
                "##....Oo##" +
                "##......##" +
                "##.A....##" +
                "##.a....##" +
                "##########" +
                "##########", game(1));

        assertF("##########" +
                "##########" +
                "##aA....##" +
                "##......##" +
                "##....Aa##" +
                "##......##" +
                "##.O....##" +
                "##.o....##" +
                "##########" +
                "##########", game(2));
    }
//
//    // игрок может подобрать золото
//    @Test
//    public void shouldGetGold() {
//        // given
//        givenFl("☼☼☼☼☼☼" +
//                "☼   $☼" +
//                "☼    ☼" +
//                "☼    ☼" +
//                "☼    ☼" +
//                "☼☼☼☼☼☼");
//
//        givenThreePlayers();
//
//        // when
//        game(2).getJoystick().right();
//
//        dice(1, 2);
//
//        tick();
//
//        // then
//        assertF("☼☼☼☼☼☼\n" +
//                "☼☺  ☻☼\n" +
//                "☼    ☼\n" +
//                "☼$☻  ☼\n" +
//                "☼    ☼\n" +
//                "☼☼☼☼☼☼\n", game(0));
//
////        verify(listener(2)).event(Events.WIN);
//    }
//
//    // игрок не может пойи на другого игрока
//    @Test
//    public void shouldCantGoOnHero() {
//        // given
//        givenFl("☼☼☼☼☼☼" +
//                "☼    ☼" +
//                "☼    ☼" +
//                "☼    ☼" +
//                "☼    ☼" +
//                "☼☼☼☼☼☼");
//
//        givenThreePlayers();
//
//        // when
//        game(0).getJoystick().right();
//        game(2).getJoystick().left();
//
//        tick();
//
//        // then
//        assertF("☼☼☼☼☼☼\n" +
//                "☼ ☺☻ ☼\n" +
//                "☼    ☼\n" +
//                "☼ ☻  ☼\n" +
//                "☼    ☼\n" +
//                "☼☼☼☼☼☼\n", game(0));
//    }
}
