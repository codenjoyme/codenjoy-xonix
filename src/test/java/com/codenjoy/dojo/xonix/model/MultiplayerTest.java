package com.codenjoy.dojo.xonix.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class MultiplayerTest extends AbstractMultiplayerTest {

    // рисуем несколько игроков
    @Test
    public void shouldPrint() {
        // given
        givenFl("##O##" +
                "#...#" +
                "O...#" +
                "#...#" +
                "###O#");

        dice(0);
        givenPlayer();
        givenPlayer();
        givenPlayer();


        // when then
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

//    // Каждый игрок может упраыляться за тик игры независимо
//    @Test
//    public void shouldJoystick() {
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
//        game(0).getJoystick().act();
//        game(0).getJoystick().down();
//        game(1).getJoystick().right();
//        game(2).getJoystick().down();
//
//        tick();
//
//        // then
//        assertF("☼☼☼☼☼☼\n" +
//                "☼x   ☼\n" +
//                "☼☺ ☻ ☼\n" +
//                "☼  ☻ ☼\n" +
//                "☼    ☼\n" +
//                "☼☼☼☼☼☼\n", game(0));
//    }
//
//    // игроков можно удалять из игры
//    @Test
//    public void shouldRemove() {
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
//        game(2).close();
//
//        tick();
//
//        // then
//        assertF("☼☼☼☼☼☼\n" +
//                "☼☺   ☼\n" +
//                "☼    ☼\n" +
//                "☼ ☻  ☼\n" +
//                "☼    ☼\n" +
//                "☼☼☼☼☼☼\n", game(0));
//    }
//
//    // игрок может взорваться на бомбе
//    @Test
//    public void shouldKill() {
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
//        game(0).getJoystick().down();
//        game(0).getJoystick().act();
//        game(2).getJoystick().left();
//
//        tick();
//
//        assertF("☼☼☼☼☼☼\n" +
//                "☼x☻  ☼\n" +
//                "☼☺   ☼\n" +
//                "☼ ☻  ☼\n" +
//                "☼    ☼\n" +
//                "☼☼☼☼☼☼\n", game(0));
//
//        // when
//        game(2).getJoystick().left();
//        tick();
//
//        // then
//        assertF("☼☼☼☼☼☼\n" +
//                "☼X   ☼\n" +
//                "☼☺   ☼\n" +
//                "☼ ☻  ☼\n" +
//                "☼    ☼\n" +
//                "☼☼☼☼☼☼\n", game(0));
//
////        verify(listener(2)).event(Events.LOOSE);
//        assertTrue(game(2).isGameOver());
//
//        dice(4, 1);
//        game(2).newGame();
//
//        tick();
//
//        assertF("☼☼☼☼☼☼\n" +
//                "☼    ☼\n" +
//                "☼☺   ☼\n" +
//                "☼ ☻  ☼\n" +
//                "☼   ☻☼\n" +
//                "☼☼☼☼☼☼\n", game(0));
//    }
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
