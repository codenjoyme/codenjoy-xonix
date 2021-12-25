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

import com.codenjoy.dojo.services.event.Calculator;
import com.codenjoy.dojo.services.settings.SettingsImpl;
import com.codenjoy.dojo.services.settings.SettingsReader;
import com.codenjoy.dojo.xonix.model.level.Level;
import com.codenjoy.dojo.xonix.model.level.MultipleLevels;
import com.codenjoy.dojo.xonix.model.level.SingleLevels;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static com.codenjoy.dojo.xonix.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements SettingsReader<GameSettings> {

    public enum Keys implements Key {

        WIN_CRITERION("[Game] How much % sea Hero should get"),
        WIN_SCORE("[Score] Scores for winning"),
        DIE_PENALTY("[Score] Die penalty"),
        LIVES_COUNT("[Game] Lives count"),

        LEVELS_COUNT("[Level] Levels count"),
        IS_MULTIPLAYER("[Game] Multiplayer"),
        ROOM_SIZE("[Level] Room size");

        private String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }
    }

    @Override
    public List<Key> allKeys() {
        return Arrays.asList(Keys.values());
    }

    public GameSettings() {
        integer(WIN_CRITERION, 75);
        integer(WIN_SCORE, 100);
        integer(LIVES_COUNT, 3);
        integer(DIE_PENALTY, -30);

        integer(ROOM_SIZE, 4);
        add(IS_MULTIPLAYER, true)
                .onChange(rebuildLevels())
                .update(true);
    }

    public BiConsumer<Boolean, Boolean> rebuildLevels() {
        return (old, multiple) -> {
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

    public boolean single() {
        return !bool(IS_MULTIPLAYER);
    }

    public Calculator<Void> calculator() {
        return new Calculator<>(new Scores(this));
    }
}