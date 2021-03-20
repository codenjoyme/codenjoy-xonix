package com.codenjoy.dojo.xonix.model;

import com.codenjoy.dojo.xonix.services.Event;
import org.junit.Test;

public class MultiplayerTest extends AbstractMultiplayerTest {

    @Test
    public void shouldLookDifferentlyForEachOther() {

        // given
        givenFl("XXOXX" +
                "X...X" +
                "O...X" +
                "X...X" +
                "XXXOX");

        givenPlayer();
        givenPlayer();
        givenPlayer();


        // then
        assertF("XXOXX" +
                "X...X" +
                "A...X" +
                "X...X" +
                "XXXAX", game(0));

        assertF("XXAXX" +
                "X...X" +
                "O...X" +
                "X...X" +
                "XXXAX", game(1));

        assertF("XXAXX" +
                "X...X" +
                "A...X" +
                "X...X" +
                "XXXOX", game(2));
    }

    @Test
    public void shouldBeIndependentlyControlled() {

        // given
        givenFl("XXOXX" +
                "X...X" +
                "O...X" +
                "X...X" +
                "XXXXO");
        givenPlayer();
        givenPlayer();
        givenPlayer();

        // when
        game(0).getJoystick().down();
        game(1).getJoystick().right();
        game(2).getJoystick().up();

        tick();

        // then
        assertF("XX#XX" +
                "X.O.X" +
                "@A..X" +
                "X...A" +
                "XXXX@", game(0));
    }

    @Test
    public void playersShouldBeRemovable() {

        // given
        givenFl("XXOXX" +
                "X...X" +
                "O...X" +
                "X...X" +
                "XXXXO");
        givenPlayer();
        givenPlayer();
        givenPlayer();

        // when
        game(2).close();

        tick();

        // then
        assertF("XXOXX" +
                "X...X" +
                "A...X" +
                "X...X" +
                "XXXX@", game(0));
    }

    @Test
    public void tracesOfEnemiesShouldLookDifferentlyForEachPlayer() {

        // given
        givenFl("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "XO......XX" +
                "XX......XX" +
                "XX......OX" +
                "XX......XX" +
                "XX......XX" +
                "XX....M.XX" +
                "XXXOXXXXXX" +
                "XXXXXXXXXX");
        givenPlayer();
        givenPlayer();
        givenPlayer();
        field.getEnemies().forEach(e -> e.setDirection(null));

        // when
        game(0).getJoystick().right();
        game(1).getJoystick().left();
        game(2).getJoystick().up();

        tick();
        tick();

        // then
        assertF("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "X#oO....XX" +
                "XX......XX" +
                "XX....Aa@X" +
                "XX......XX" +
                "XX.A....XX" +
                "XX.a..M.XX" +
                "XXX@XXXXXX" +
                "XXXXXXXXXX", game(0));

        assertF("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "X@aA....XX" +
                "XX......XX" +
                "XX....Oo#X" +
                "XX......XX" +
                "XX.A....XX" +
                "XX.a..M.XX" +
                "XXX@XXXXXX" +
                "XXXXXXXXXX", game(1));

        assertF("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "X@aA....XX" +
                "XX......XX" +
                "XX....Aa@X" +
                "XX......XX" +
                "XX.O....XX" +
                "XX.o..M.XX" +
                "XXX#XXXXXX" +
                "XXXXXXXXXX", game(2));
    }

    @Test
    public void seizedLandOfEnemiesShouldLooksDifferentlyForEachPlayer() {

        // given
        tracesOfEnemiesShouldLookDifferentlyForEachPlayer();

        // when
        game(0).getJoystick().up();
        game(2).getJoystick().left();

        tick();
        game(0).getJoystick().left();
        tick();

        assertF("XXXXXXXXXX" +
                "XXOoXXXXXX" +
                "X#oo....XX" +
                "XX......XX" +
                "XX..Aaaa@X" +
                "XX......XX" +
                "XAaa....XX" +
                "XX.a..M.XX" +
                "XXX@XXXXXX" +
                "XXXXXXXXXX", game(0));

        game(2).getJoystick().down();
        tick();

        game(0).getJoystick().down();
        tick();

        assertF("XXXXXXXXXX" +
                "X###XXXXXX" +
                "XO##....XX" +
                "XX......XX" +
                "XXAaaaaa@X" +
                "XX......XX" +
                "Xaaa....XX" +
                "Xa.a..M.XX" +
                "XAX@XXXXXX" +
                "XXXXXXXXXX", game(0));

        game(2).getJoystick().right();
        tick();
        tick();

        assertF("XXXXXXXXXX" +
                "X###XXXXXX" +
                "XO##....XX" +
                "XX......XX" +
                "Aaaaaaaa@X" +
                "XX......XX" +
                "X@@@....XX" +
                "X@@@..M.XX" +
                "X@@AXXXXXX" +
                "XXXXXXXXXX", game(0));
    }

    @Test
    public void shouldKillEachOtherIfHitsTrace() {

        // given
        seizedLandOfEnemiesShouldLooksDifferentlyForEachPlayer();

        // when
        game(0).getJoystick().down();
        tick();
        tick();


        // then
        assertF("XXXXXXXXXX" +
                "X###XXXXXX" +
                "X###....XX" +
                "Xo......XX" +
                "XO......AX" +
                "XX......XX" +
                "X@@@....XX" +
                "X@@@..M.XX" +
                "X@@AXXXXXX" +
                "XXXXXXXXXX", game(0));

        fired(listener(1), Event.KILLED);
        fired(listener(0), Event.ANNIHILATION);
    }

    @Test
    public void shouldSeizeEnemyLand() {

        // given
        shouldKillEachOtherIfHitsTrace();

        // when
        tick();
        tick();
        tick();
        game(0).getJoystick().right();
        tick();
        tick();
        game(0).getJoystick().up();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("XXXXXXXXXX" +
                "X###XXXXXX" +
                "X##O....XX" +
                "X###....XX" +
                "X###....AX" +
                "X###....XX" +
                "X###....XX" +
                "X###..M.XX" +
                "X@@AXXXXXX" +
                "XXXXXXXXXX", game(0));

    }

    @Test
    public void shouldKillEachOtherWhenHitHeads_case1() {

        // given
        givenFl("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "XX......XX" +
                "XX......XX" +
                "XO......OX" +
                "XX......XX" +
                "XX......XX" +
                "XX....M.XX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX");
        givenPlayer();
        givenPlayer();
        field.getEnemies().forEach(e -> e.setDirection(null));

        // when
        game(1).getJoystick().left();

        tick();
        tick();

        game(0).getJoystick().right();

        tick();
        tick();

        assertF("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "XX......XX" +
                "XX......XX" +
                "X#oOAaaa@X" +
                "XX......XX" +
                "XX......XX" +
                "XX....M.XX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX", game(0));

        tick();

        assertF("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "XX......XX" +
                "XX......XX" +
                "XO......AX" +
                "XX......XX" +
                "XX......XX" +
                "XX....M.XX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX", game(0));
    }

    @Test
    public void shouldKillEachOtherWhenHitHeads_case2() {

        // given
        givenFl("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "XX......XX" +
                "XX......OX" +
                "XX......OX" +
                "XX......XX" +
                "XX......XX" +
                "XX....M.XX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX");
        givenPlayer();
        givenPlayer();
        field.getEnemies().forEach(e -> e.setDirection(null));

        // when
        game(0).getJoystick().left();

        tick();
        game(1).getJoystick().left();

        tick();

        game(0).getJoystick().down();

        tick();

        assertF("XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "XX......XX" +
                "XX......OX" +
                "XX......AX" +
                "XX......XX" +
                "XX......XX" +
                "XX....M.XX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX", game(0));
    }
}
