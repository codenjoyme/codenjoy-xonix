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


import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.xonix.model.items.*;
import com.codenjoy.dojo.xonix.model.level.Level;
import com.codenjoy.dojo.xonix.services.GameSettings;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class XonixGame implements Field {

    private final List<Player> players = new LinkedList<>();
    private final Level level;
    private int size;
    private List<Sea> sea;
    private List<Land> land;

    private GameSettings settings;

    public XonixGame(Level level, GameSettings settings) {
        this.settings = settings;
        this.level = level;
        reset();
    }

    public void reset() {
        size = level.size();
        sea = level.sea();
        land = level.land();
    }

    @Override
    public void tick() {
        players.stream()
            .map(Player::getHero)
            .forEach(Hero::tick);
    }

    @Override
    public boolean isBarrier(Point pt) {
        return pt.isOutOf(size)
                || anyMatch(sea, pt);
    }

    @Override
    public boolean isAllClear() {
        return land.isEmpty();
    }


    public <T extends Point> Optional<T> found(List<T> items, Point pt) {
        return items.stream()
                .filter(pt::equals)
                .findFirst();
    }

    public <T extends Point> boolean anyMatch(List<T> items, Point pt) {
        return items.stream()
                .anyMatch(pt::equals);
    }

    @Override
    public boolean isCleanPoint(Point pt) {
        return true;
    }

    @Override
    public boolean isDust(Point pt) {
        return anyMatch(land, pt);
    }

    @Override
    public void removeDust(Point pt) {
        land.remove(pt);
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean canMove(Point from, Point to) {
        return true;
    }

    public List<Hero> getHeroes() {
        return players.stream()
                .map(Player::getHero)
                .collect(toList());
    }

    @Override
    public void newGame(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
        reset();
        player.newHero(this);
    }

    @Override
    public void remove(Player player) {
        players.remove(player);
    }

    @Override
    public BoardReader reader() {
        return new BoardReader() {

            @Override
            public int size() {
                return XonixGame.this.getSize();
            }

            @Override
            public Iterable<? extends Point> elements() {
                return new LinkedList<>() {{
                    addAll(getHeroes());
                    addAll(XonixGame.this.sea);
                    addAll(XonixGame.this.land);
                }};
            }
        };
    }

    @Override
    public GameSettings settings() {
        return settings;
    }
}
