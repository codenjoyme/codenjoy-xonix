package com.codenjoy.dojo.xonix.model.level;

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

import com.codenjoy.dojo.services.LengthToXY;
import com.codenjoy.dojo.xonix.model.Hero;
import com.codenjoy.dojo.xonix.model.items.Land;
import com.codenjoy.dojo.xonix.model.LandEnemy;
import com.codenjoy.dojo.xonix.model.MarineEnemy;
import com.codenjoy.dojo.xonix.model.items.Sea;

import java.util.List;

import static com.codenjoy.dojo.utils.LevelUtils.getObjects;
import static com.codenjoy.dojo.xonix.model.Elements.*;

public class Level {

    private final String map;
    private final LengthToXY xy;

    public Level(String map) {
        this.map = map;
        this.xy = new LengthToXY(size());
    }

    public int size() {
        return (int) Math.sqrt(map.length());
    }


    public List<Land> land() {
        return getObjects(xy, map, Land::new, LAND, XONIX, LAND_ENEMY);
    }

    public List<Sea> sea() {
        return getObjects(xy, map, Sea::new, SEA, MARINE_ENEMY);
    }

    public Hero hero() {
        return getObjects(xy, map, Hero::new, XONIX).get(0);
    }

    public List<MarineEnemy> marineEnemies() {
        return getObjects(xy, map, MarineEnemy::new, MARINE_ENEMY);
    }

    public List<LandEnemy> landEnemies() {
        return getObjects(xy, map, LandEnemy::new, LAND_ENEMY);
    }

    public int seaCellsCount() {
        return getObjects(xy, map, pt -> new Object(), SEA, MARINE_ENEMY).size();
    }

    public int landCellsCount() {
        return getObjects(xy, map, pt -> new Object(), LAND, XONIX, LAND_ENEMY).size();
    }
}