package com.codenjoy.dojo.xonix.model;

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


import com.codenjoy.dojo.services.printer.CharElements;

/**
 * Легенда всех возможных объектов на поле и их состояний
 */
public enum Elements implements CharElements {

    SEA('.'),                   // Море, которое нужно осушать; тут плавают морские враги
    LAND('X'),

    HERO('O'),                 // Сам главный герой игры
    HERO_LAND('#'),            // Земля, тут может ходить Xonix и сухопутый враг
    HERO_TRACE('o'),                 // След, который оставляет Xonix двигаясь по морю; уязвим для морских врагов;
                                // после выхода Xonix'а на сушу, море, очерченное следом, превращается в сушу

    HOSTILE('A'),
    HOSTILE_LAND('@'),
    HOSTILE_TRACE('a'),

    MARINE_ENEMY('M'),          // Морской враг
    LAND_ENEMY('L');            // Сухопутный враг

    final char ch;

    Elements(char ch) {
        this.ch = ch;
    }

    @Override
    public char ch() {
        return ch;
    }

    @Override
    public String toString() {
        return String.valueOf(ch);
    }

    public static Elements byCode(char ch) {
        for (Elements el : Elements.values()) {
            if (el.ch == ch) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element for " + ch);
    }

}
