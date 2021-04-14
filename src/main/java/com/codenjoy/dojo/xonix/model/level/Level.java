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
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.utils.LevelUtils;
import com.codenjoy.dojo.xonix.model.Hero;
import com.codenjoy.dojo.xonix.model.items.Land;
import com.codenjoy.dojo.xonix.model.items.Sea;

import java.util.List;

import static com.codenjoy.dojo.utils.LevelUtils.getObjects;
import static com.codenjoy.dojo.xonix.model.Elements.*;

public class Level {

    private String map;
    private LengthToXY xy;

    public Level(String map) {
        this.map = LevelUtils.clear(map);
        this.xy = new LengthToXY(size());
    }

    public int size() {
        return (int) Math.sqrt(map.length());
    }

    public List<Land> freeLand() {
        return getObjects(xy, map, Land::new,
                LAND, LAND_ENEMY);
    }

    public List<Land> heroLand(Hero hero) {
        List<Land> land = getObjects(xy, map, Land::new,
                HERO_LAND, HERO);
        land.forEach(l -> l.owner(hero));
        return land;
    }

    public List<Sea> sea() {
        return getObjects(xy, map, Sea::new,
                SEA, MARINE_ENEMY);
    }

    public List<Point> start() {
        return getObjects(xy, map, pt -> pt,
                HERO);
    }

    public List<Point> marineEnemy() {
        return getObjects(xy, map, pt -> pt,
                MARINE_ENEMY);
    }

    public List<Point> landEnemy() {
        return getObjects(xy, map, pt -> pt,
                LAND_ENEMY);
    }
}