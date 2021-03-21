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

import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.services.multiplayer.Single;
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
import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.IS_MULTIPLAYER;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public abstract class AbstractMultiplayerTest {

    private List<EventListener> listeners = new LinkedList<>();
    private List<Game> games = new LinkedList<>();
    private Dice dice;
    protected Xonix field;
    private GameSettings settings;
    private PrinterFactory printerFactory;

    // появляется другие игроки, игра становится мультипользовательской
    @Before
    public void setup() {
        dice = mock(Dice.class);
        printerFactory = new PrinterFactoryImpl();
        settings = new GameSettings()
                .bool(IS_MULTIPLAYER, true);
    }

    public void dice(int... ints) {
        OngoingStubbing<Integer> when = when(dice.next(anyInt()));
        for (int i : ints) {
            when = when.thenReturn(i);
        }
    }

    public void givenFl(String map) {
        field = new Xonix(new Level(map), settings, dice);
    }

    public Game game(int index) {
        return games.get(index);
    }

    public Player givenPlayer() {
        EventListener listener = mock(EventListener.class);
        listeners.add(listener);
        Player player = new Player(listener, settings);
        Game game = new Single(player, printerFactory);
        games.add(game);
        game.on(field);
        game.newGame();
        return player;
    }

    public void dice(int x, int y) {
        when(dice.next(anyInt())).thenReturn(x, y);
    }

    public void assertF(String expected, Game game) {
        assertEquals(TestUtils.injectN(expected), game.getBoardAsString());
    }

    public EventListener listener(int index) {
        return listeners.get(index);
    }

    public void tick() {
        field.tick();
    }

    public void neverFired(EventListener listener, Event event) {
        verify(listener, never()).event(event);
    }

    public Joystick hero(int index) {
        return game(index).getJoystick();
    }

    public void fired(EventListener listener, Event event) {
        fired(listener, 1, event);
    }

    public void fired(EventListener listener, int times, Event event) {
        verify(listener, times(times)).event(event);
    }

    public void fired(EventListener listener, Event... events) {
        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(listener, times(events.length)).event(captor.capture());
        assertEquals(Arrays.asList(events), captor.getAllValues());
    }

    public void shouldEnemiesGo(Direction direction) {
        field.enemies().forEach(enemy -> enemy.direction(direction));
    }

}
