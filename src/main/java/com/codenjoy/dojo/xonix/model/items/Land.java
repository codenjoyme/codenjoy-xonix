package com.codenjoy.dojo.xonix.model.items;

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
import com.codenjoy.dojo.games.xonix.Element;
import com.codenjoy.dojo.xonix.model.Hero;
import com.codenjoy.dojo.xonix.model.Player;

import static com.codenjoy.dojo.games.xonix.Element.*;

public class Land extends AbstractItem {

    private Hero owner;

    public Land(Point pt) {
        super(pt, LAND);
    }

    public void owner(Hero hero) {
        this.owner = hero;
    }

    public Hero owner() {
        return owner;
    }

    @Override
    public Element state(Player painter, Object... objects) {
        if (owner == null) {
            return element;
        }
        return owner.equals(painter.getHero())
                ? HERO_LAND
                : HOSTILE_LAND;
    }
}
