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

import com.codenjoy.dojo.services.QDirection;
import com.codenjoy.dojo.xonix.services.Event;
import org.junit.Test;

public class MultiplayerTest extends AbstractMultiplayerTest {

    @Test
    public void shouldLookDifferently_forEachOther() {
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
        hero(0).down();
        hero(1).right();
        hero(2).up();

        tick();

        // then
        assertF("XX#XX" +
                "X.O.X" +
                "@A..X" +
                "X...A" +
                "XXXX@", game(0));
    }

    @Test
    public void shouldPlayersBeRemovable() {
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
    public void shouldTracesOfEnemiesLookDifferently_forEachPlayer() {
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

        shouldEnemiesGo(null);

        // when
        hero(0).right();
        hero(1).left();
        hero(2).up();

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
    public void shouldSeizedLandOfEnemiesLooksDifferently_forEachPlayer() {
        // given
        shouldTracesOfEnemiesLookDifferently_forEachPlayer();

        // when
        hero(0).up();
        hero(2).left();

        tick();
        hero(0).left();
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

        hero(2).down();
        tick();

        hero(0).down();
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

        hero(2).right();
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
    public void shouldKillEachOther_whenHitsTrace() {
        // given
        shouldSeizedLandOfEnemiesLooksDifferently_forEachPlayer();

        // when
        hero(0).down();
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

        fired(listener(1), Event.DIE);
        fired(listener(0), Event.ANNIHILATION);
    }

    @Test
    public void shouldSeizeEnemyLand() {
        // given
        shouldKillEachOther_whenHitsTrace();

        // when
        tick();
        tick();
        tick();
        hero(0).right();
        tick();
        tick();
        hero(0).up();
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
    public void shouldKillEachOther_whenHitHeads_case1() {
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

        shouldEnemiesGo(null);

        // when
        hero(1).left();

        tick();
        tick();

        hero(0).right();

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
    public void shouldKillEachOther_whenHitHeads_case2() {
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

        shouldEnemiesGo(null);

        // when
        hero(0).left();

        tick();
        hero(1).left();

        tick();

        hero(0).down();

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
    public void shouldDie_whenEnemyHits() {
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
        shouldEnemiesGo(QDirection.LEFT_DOWN);
        givenPlayer();
        givenPlayer();
        dice(3);

        // when
        hero(0).right();

        tick();
        tick();
        tick();

        // then
        assertF("....XX...." +
                "..M.XX...." +
                ".O..XX...." +
                "....XX.M.." +
                "XXXXXXXXXX" +
                "XXXXXXXXXX" +
                "....XX...." +
                "..M.LX.A.." +
                "....XX.M.." +
                "....XX....", game(0));

        fired(listener(0), Event.DIE);
    }

    @Test
    public void shouldLandEnemy_beAbleToStepOnHeroLand() {
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
        hero(0).right();
        tick();
        tick();
        tick();

        hero(0).up();
        tick();
        tick();


        hero(0).left();
        tick();
        tick();
        tick();

        hero(0).down();
        tick();
        tick();

        shouldEnemiesGo(QDirection.RIGHT_UP);

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
    public void shouldLandEnemy_killHeroOnFreeLand() {
        // given
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

        shouldEnemiesGo(QDirection.RIGHT_UP);

        // when
        hero(0).right();
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

        fired(listener(0), Event.DIE);
    }
    
    @Test
    public void shouldKillMarineEnemy_whenHeroOnLandButTraceAtSea() {
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

        shouldEnemiesGo(null);

        // when
        hero(0).right();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        shouldEnemiesGo(QDirection.RIGHT_UP);
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

        fired(listener(0), Event.DIE);
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
        hero(0).down();
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

        fired(listener(0), Event.DIE);
        fired(listener(1), Event.DIE);
    }
}