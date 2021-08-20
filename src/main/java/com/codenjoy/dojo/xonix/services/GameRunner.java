package com.codenjoy.dojo.xonix.services;

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


import com.codenjoy.dojo.client.ClientBoard;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.AbstractGameType;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.PlayerScores;
import com.codenjoy.dojo.services.multiplayer.GameField;
import com.codenjoy.dojo.services.multiplayer.GamePlayer;
import com.codenjoy.dojo.services.multiplayer.MultiplayerType;
import com.codenjoy.dojo.services.printer.CharElement;
import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.SimpleParameter;
import com.codenjoy.dojo.games.xonix.Board;
import com.codenjoy.dojo.xonix.services.ai.AISolver;
import com.codenjoy.dojo.games.xonix.Element;
import com.codenjoy.dojo.xonix.model.Player;
import com.codenjoy.dojo.xonix.model.Xonix;

import static com.codenjoy.dojo.services.multiplayer.MultiplayerType.DISPOSABLE;
import static com.codenjoy.dojo.services.multiplayer.MultiplayerType.RELOAD_ALONE;
import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.*;

public class GameRunner extends AbstractGameType<GameSettings> {

    @Override
    public GameSettings getSettings() {
        return new GameSettings();
    }

    @Override
    public PlayerScores getPlayerScores(Object score, GameSettings settings) {
        return new Scores(Integer.valueOf(score.toString()), settings);
    }

    @Override
    public GameField createGame(int levelNumber, GameSettings settings) {
        return new Xonix(settings.level(levelNumber), settings, getDice());
    }

    @Override
    public Parameter<Integer> getBoardSize(GameSettings settings) {
        return SimpleParameter.v(settings.level(1).size());
    }

    @Override
    public String name() {
        return "xonix";
    }

    @Override
    public CharElement[] getPlots() {
        return Element.values();
    }

    @Override
    public Class<? extends Solver> getAI() {
        return AISolver.class;
    }

    @Override
    public Class<? extends ClientBoard> getBoard() {
        return Board.class;
    }

    @Override
    public MultiplayerType getMultiplayerType(GameSettings settings) {
        if (settings.single()) {
            return MultiplayerType.LEVELS.apply(
                    settings.integer(ROOM_SIZE),
                    settings.integer(LEVELS_COUNT),
                    DISPOSABLE, RELOAD_ALONE);
        } else {
            return MultiplayerType.SINGLE_LEVELS.apply(
                    settings.integer(LEVELS_COUNT));
        }
    }

    @Override
    public GamePlayer createPlayer(EventListener listener, int teamId, String playerId, GameSettings settings) {
        return new Player(listener, settings).inTeam(teamId);
    }
}
