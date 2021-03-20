package com.codenjoy.dojo.xonix.model.items;

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


import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.xonix.model.Elements;
import com.codenjoy.dojo.xonix.model.Hero;
import com.codenjoy.dojo.xonix.model.Player;

public class Land extends AbstractItem {

    private Hero owner;

    public Land(Point pt) {
        super(pt, Elements.HERO_LAND);
    }

    public void setOwner(Hero hero) {
        this.owner = hero;
    }

    public Hero getOwner() {
        return owner;
    }

    @Override
    public Elements state(Player player, Object... objects) {
        if (owner == null) {
            return Elements.LAND;
        }
        return owner.equals(player.getHero())
                ? Elements.HERO_LAND
                : Elements.HOSTILE_LAND;
    }
}
