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

import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.settings.SettingsImpl;
import com.codenjoy.dojo.services.settings.SettingsReader;
import com.codenjoy.dojo.xonix.model.level.Level;
import com.codenjoy.dojo.xonix.model.level.Levels;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements SettingsReader<GameSettings> {

    public enum Keys implements Key {

        VICTORY_CRITERION("How much sea Xonix should capture for victory in percents"),
        WIN_REWARD("Reward for winning"),
        LIVES_COUNT("Lives count"),
        DIE_PENALTY("Die penalty"),
        LEVELS_COUNT("Levels count");

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
        integer(VICTORY_CRITERION, 70);
        integer(WIN_REWARD, 100);
        integer(LIVES_COUNT, 3);
        integer(DIE_PENALTY, 30);

        string(() -> levelName(0), Levels.level1());
        string(() -> levelName(1), Levels.level2());
        string(() -> levelName(2), Levels.level3());
        string(() -> levelName(3), Levels.level4());
        integer(LEVELS_COUNT, 4);
    }

    public Level level(int levelNumber) {
        return new Level(string(() -> levelName(levelNumber)));
    }

    public String levelName(int levelNumber) {
        return "level[" + levelNumber + "]";
    }

}