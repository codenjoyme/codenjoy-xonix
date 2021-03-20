package com.codenjoy.dojo.xonix.model;

import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.Game;
import com.codenjoy.dojo.services.multiplayer.Single;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.services.printer.PrinterFactoryImpl;
import com.codenjoy.dojo.utils.TestUtils;
import com.codenjoy.dojo.xonix.model.level.Level;
import com.codenjoy.dojo.xonix.services.Event;
import com.codenjoy.dojo.xonix.services.GameSettings;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.OngoingStubbing;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.IS_MULTIPLAYER;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public abstract class AbstractMultiplayerTest {

    private List<EventListener> listeners = new LinkedList<>();
    private List<Game> games = new LinkedList<>();
    private Dice dice;
    protected XonixGame field;
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
        field = new XonixGame(new Level(map), settings, dice);
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

    protected void neverFired(EventListener listener, Event event) {
        verify(listener, never()).event(event);
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
        assertEquals(Lists.newArrayList(events), captor.getAllValues());
    }

}