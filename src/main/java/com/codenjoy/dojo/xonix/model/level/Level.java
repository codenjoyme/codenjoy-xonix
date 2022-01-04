package com.codenjoy.dojo.xonix.model.level;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
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

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.field.AbstractLevel;
import com.codenjoy.dojo.xonix.model.items.Land;
import com.codenjoy.dojo.xonix.model.items.Sea;

import java.util.List;

import static com.codenjoy.dojo.games.xonix.Element.*;
import static java.util.function.UnaryOperator.identity;

public class Level extends AbstractLevel {

    public Level(String map) {
        super(map);
    }

    public List<Land> land() {
        return find(Land::new,
                LAND,
                HERO_LAND,
                LAND_ENEMY,
                HERO);
    }

    public List<Sea> sea() {
        return find(Sea::new,
                SEA,
                MARINE_ENEMY);
    }

    public List<Point> start() {
        return find(identity(), HERO);
    }

    public List<Point> marineEnemy() {
        return find(identity(), MARINE_ENEMY);
    }

    public List<Point> landEnemy() {
        return find(identity(), LAND_ENEMY);
    }
}