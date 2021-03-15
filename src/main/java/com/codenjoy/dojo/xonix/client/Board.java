package com.codenjoy.dojo.xonix.client;

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


import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.xonix.model.Elements;

import java.util.List;

import static com.codenjoy.dojo.xonix.model.Elements.*;

/**
 * Класс, обрабатывающий строковое представление доски.
 * Содержит ряд унаследованных методов {@see AbstractBoard},
 * но ты можешь добавить сюда любые свои методы на их основе.
 */
public class Board extends AbstractBoard<Elements> {

    @Override
    protected int inversionY(int y) {
        return size() - 1 - y;
    }

    @Override
    public Elements valueOf(char ch) {
        return Elements.byCode(ch);
    }

    public Elements getAt(int x, int y) {
        if (isOutOfField(x, y)) {
            return LAND;
        }
        return super.getAt(x, y);
    }

    @Override
    public String toString() {
        return String.format("%s\nXonix at: %s\n", boardAsString(), getMe());
    }

    public Point getMe() {
        return get(XONIX).get(0);
    }
}