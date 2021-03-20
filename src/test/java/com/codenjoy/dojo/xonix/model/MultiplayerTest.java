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

        // then
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

        // then
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

    @Test
    public void shouldDieIfEnemyHits() {

        // given
        givenFl(".M..XX..M." +
                "....XX...." +
                ".O..XX...." +
                "....XX...." +
                "XXXXXLXXXX" +
                "XXXXXXXXXX" +
                "....XX...." +
                "....XX.O.." +
                ".M..XX...." +
                "....XX..M.");
        givenPlayer();
        givenPlayer();
        dice(3);

        // when
        game(0).getJoystick().right();

        tick();
        tick();
        tick();
        tick();

        // then
        assertF("....XL...." +
                "M...XX...." +
                ".O..XX..M." +
                "....XX...." +
                "XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "....XX...." +
                "....XX.A.." +
                "...MXX...." +
                "....XX..M.", game(0));
        fired(listener(0), Event.KILLED);
    }

    @Test
    public void landEnemyShouldBeAbleToStepOnXonixLand() {
        givenFl("....XX...." +
                "....XX...." +
                ".O..XX...." +
                "XXXXXX...." +
                "LXXXXX...." +
                ".........." +
                ".........." +
                ".......O.." +
                ".........." +
                "..........");
        givenPlayer();
        givenPlayer();


        // when
        game(0).getJoystick().right();
        tick();
        tick();
        tick();

        game(0).getJoystick().up();
        tick();
        tick();


        game(0).getJoystick().left();
        tick();
        tick();
        tick();

        game(0).getJoystick().down();
        tick();
        tick();

        field.getEnemies().forEach(e -> e.setDirection(Direction.UP));

        tick();
        tick();

        assertF(".####X...." +
                ".####X...." +
                ".OL##X...." +
                "XXXXXX...." +
                "XXXXXX...." +
                ".........." +
                ".........." +
                ".......A.." +
                ".........." +
                "..........", game(0));

    }

    @Test
    public void landEnemyShouldKillXonixOnFreeLand() {
        givenFl("XXXXXXXXXX" +
                "XXOXXXXXXX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "LXXXXXXXXX" +
                ".........." +
                ".......O.." +
                ".........." +
                "..........");
        givenPlayer();
        givenPlayer();
        field.getEnemies().forEach(e -> e.setDirection(Direction.UP));


        // when
        game(0).getJoystick().right();
        tick();
        tick();
        tick();

        assertF("XXXXXXXXXX" +
                "XXOXXXXXXX" +
                "XXXLXXXXXX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "XXXXXXXXXX" +
                ".........." +
                ".......A.." +
                ".........." +
                "..........", game(0));
        fired(listener(0), Event.KILLED);
    }

    @Test
    public void marineEnemyShouldKill_whenXonixOnLandButTraceAtSea() {

        // given
        givenFl("XXXXXXXXXX" +
                "XXO......X" +
                "X........X" +
                "X..M.....X" +
                "X........X" +
                "XXXXXXXXXX" +
                ".........." +
                ".......O.." +
                ".........." +
                "..........");

        givenPlayer();
        givenPlayer();
        field.getEnemies().forEach(e -> e.setDirection(null));

        // when
        game(0).getJoystick().right();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        field.getEnemies().forEach(e -> e.setDirection(Direction.UP));
        tick();

        // then
        assertF("XXXXXXXXXX" +
                "XXO......X" +
                "X...M....X" +
                "X........X" +
                "X........X" +
                "XXXXXXXXXX" +
                ".........." +
                ".......A.." +
                ".........." +
                "..........", game(0));
        fired(listener(0), Event.KILLED);
    }

    @Test
    public void shouldNotSeizeSea_whenLandedOnEnemiesLand() {

        // given
        givenFl("XXXXXXXXXX" +
                ".........." +
                ".........." +
                "....O....." +
                ".........." +
                "....O....." +
                ".........." +
                ".........." +
                ".........." +
                "XXXXXXXXXX");

        givenPlayer();
        givenPlayer();

        // when
        game(0).getJoystick().down();
        tick();
        tick();

        // then
        assertF("XXXXXXXXXX" +
                ".........." +
                ".........." +
                "....O....." +
                ".........." +
                "....A....." +
                ".........." +
                ".........." +
                ".........." +
                "XXXXXXXXXX", game(0));
        fired(listener(0), Event.KILLED);
        fired(listener(1), Event.KILLED);
    }
}
