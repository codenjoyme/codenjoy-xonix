package com.codenjoy.dojo.xonix.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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


import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.services.printer.PrinterFactoryImpl;
import com.codenjoy.dojo.utils.TestUtils;
import com.codenjoy.dojo.xonix.model.level.Level;
import com.codenjoy.dojo.xonix.services.Event;
import com.codenjoy.dojo.xonix.services.GameSettings;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Arrays;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.IS_MULTIPLAYER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public abstract class AbstractGameTest {

    protected Xonix game;
    protected Hero hero;
    protected EventListener listener;
    protected Player player;
    protected PrinterFactory printer;
    protected GameSettings settings;
    private Dice dice;

    @Before
    public void setup() {
        dice = mock(Dice.class);
        printer = new PrinterFactoryImpl();
        settings = new GameSettings()
                .bool(IS_MULTIPLAYER, false);
    }

    public void dice(int... ints) {
        OngoingStubbing<Integer> when = when(dice.next(anyInt()));
        for (int i : ints) {
            when = when.thenReturn(i);
        }
    }

    protected void givenFl(String board) {
        when(dice.next(anyInt())).thenReturn(2); // Direction always will be UP
        Level level = new Level(board);
        game = new Xonix(level, settings, dice);

        listener = mock(EventListener.class);
        player = new Player(listener, settings);
        game.newGame(player);
        hero = player.getHero();
    }

    protected void assertE(String expected) {
        assertEquals(TestUtils.injectN(expected),
                printer.getPrinter(game.reader(), player).print());
    }

    protected void neverFired(Event event) {
        verify(listener, never()).event(event);
    }

    public void fired(String expected) {
        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(listener, times(expected.split(",").length)).event(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    public void fired(Event event) {
        fired(1, event);
    }

    public void fired(int times, Event event) {
        verify(listener, times(times)).event(event);
    }

    public void fired(Event... events) {
        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(listener, times(events.length)).event(captor.capture());
        assertEquals(Arrays.asList(events), captor.getAllValues());
    }

    public void shouldEnemiesGo(Direction direction) {
        game.enemies().forEach(enemy -> enemy.setDirection(direction));
    }
}
