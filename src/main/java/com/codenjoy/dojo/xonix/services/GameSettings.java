package com.codenjoy.dojo.xonix.services;

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

import com.codenjoy.dojo.services.settings.SettingsImpl;
import com.codenjoy.dojo.services.settings.SettingsReader;
import com.codenjoy.dojo.xonix.model.level.Level;
import com.codenjoy.dojo.xonix.model.level.MultipleLevels;
import com.codenjoy.dojo.xonix.model.level.SingleLevels;

import java.util.function.Consumer;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements SettingsReader<GameSettings> {

    public enum Keys implements Key {

        VICTORY_CRITERION("How much sea Xonix should capture for victory in percents"),
        WIN_REWARD("Reward for winning"),
        LIVES_COUNT("Lives count"),
        DIE_PENALTY("Die penalty"),
        LEVELS_COUNT("Levels count"),
        IS_MULTIPLAYER("Multiplayer"),
        ROOM_SIZE("Room size");

        private String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }
    }

    public GameSettings() {
        integer(VICTORY_CRITERION, 75);
        integer(WIN_REWARD, 100);
        integer(LIVES_COUNT, 3);
        integer(DIE_PENALTY, 30);

        integer(ROOM_SIZE, 4);
        add(IS_MULTIPLAYER, true)
                .onChange(rebuildLevels())
                .update(true);
    }

    public Consumer<Boolean> rebuildLevels() {
        return multiple -> {
            if (multiple) {
                useMultiple();
            } else {
                useSingle();
            }
        };
    }

    public void useSingle() {
        integer(LEVELS_COUNT, 4);

        string(() -> levelName(1), SingleLevels.level1());
        string(() -> levelName(2), SingleLevels.level2());
        string(() -> levelName(3), SingleLevels.level3());
        string(() -> levelName(4), SingleLevels.level4());
    }

    public void useMultiple() {
        integer(LEVELS_COUNT, 1);

        string(() -> levelName(1), MultipleLevels.level1());
        string(() -> levelName(2), "");
        string(() -> levelName(3), "");
        string(() -> levelName(4), "");
    }

    public Level level(int levelNumber) {
        return new Level(string(() -> levelName(levelNumber)));
    }

    public String levelName(int levelNumber) {
        return "Level" + levelNumber;
    }

    public Boolean isMultiplayer() {
        return bool(IS_MULTIPLAYER);
    }

}