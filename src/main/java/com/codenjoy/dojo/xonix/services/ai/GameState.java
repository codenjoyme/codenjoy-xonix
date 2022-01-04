package com.codenjoy.dojo.xonix.services.ai;

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

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.games.xonix.Board;
import com.codenjoy.dojo.games.xonix.Element;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameState {

    private final Board board;
    private final Point xonix;
    private final List<Point> trace;
    private final List<Point> sea;
    private final List<Point> land;
    private final List<Point> landEnemies;
    private final List<Point> marineEnemies;

    private final List<Point> dangerZone;

    public GameState(Board board) {
        this.board = board;
        xonix = board.getHero();
        trace = board.getHeroTrace();
        sea = board.getSea();
        land = board.getHeroLand();
        landEnemies = board.getLandEnemies();
        marineEnemies = board.getMarineEnemies();
        dangerZone = determineDangerZone();
    }

    public Direction howToAvoidDanger() {
        return isXonixFloating() ? howToAvoidDangerAtSea() : howToAvoidDangerOnLand();
    }

    public Direction howToAvoidDangerOnLand() {
        Point enemy = landEnemies.stream()
                .filter(dangerZone::contains)
                .findAny().orElse(null);

        if (enemy == null) {
            return null;
        }

        int x = enemy.getX();
        int y = enemy.getY();

        List<Direction> possible;

        if (x < xonix.getX()) {
            if (y < xonix.getY()) {
                possible = Arrays.asList(Direction.RIGHT, Direction.UP);
            } else {
                possible = Arrays.asList(Direction.RIGHT, Direction.DOWN);
            }
        } else {
            if (y < xonix.getY()) {
                possible = Arrays.asList(Direction.LEFT, Direction.UP);
            } else {
                possible = Arrays.asList(Direction.LEFT, Direction.DOWN);
            }
        }

        for (Direction direction : possible) {
            if (!isLand(direction.change(xonix))) {
                Point fourSteps = direction.change(direction.change(direction.change(direction.change(xonix))));
                boolean canGoToSea = getPointsAround(fourSteps, 4).stream()
                        .filter(this::isInBounds)
                        .noneMatch(marineEnemies::contains);
                if (canGoToSea) {
                    return direction;
                }
            }
        }

        // if there is no way to stay on land
        return possible.get(
                new Random().nextInt(possible.size() - 1)
        );
    }

    public Direction howToAvoidDangerAtSea() {
        Point enemy = marineEnemies.stream()
                .filter(dangerZone::contains)
                .findAny().orElse(null);

        if (enemy == null) {
            return null;
        }

        for (Direction direction : Direction.getValues()) {
            if (isLand(direction.change(xonix))) {
                return direction;
            }
        }

        if (enemy.getX() > xonix.getX()) {
            if (enemy.getY() > xonix.getY()) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        } else {
            return Direction.RIGHT;
        }
    }

    private List<Point> getPointsAround(Point point, int areaSize) {
        List<Point> result = Arrays.asList(point);
        for (int i = 0; i < areaSize; i++) {
            result = result.stream()
                    .flatMap(p -> {
                        int x = p.getX();
                        int y = p.getY();
                        return Stream.of(
                                PointImpl.pt(x - 1, y),
                                PointImpl.pt(x - 1, y - 1),
                                PointImpl.pt(x - 1, y + 1),

                                PointImpl.pt(x + 1, y),
                                PointImpl.pt(x + 1, y - 1),
                                PointImpl.pt(x + 1, y + 1),

                                PointImpl.pt(x, y - 1),
                                PointImpl.pt(x - 1, y - 1),
                                PointImpl.pt(x + 1, y - 1),

                                PointImpl.pt(x, y + 1),
                                PointImpl.pt(x - 1, y + 1),
                                PointImpl.pt(x + 1, y + 1)
                        );
                    }).distinct()
                    .collect(Collectors.toList());
        }
        return result;
    }

    private List<Point> determineDangerZone() {
        List<Point> vulnerable = new LinkedList<>();
        vulnerable.add(xonix);
        vulnerable.addAll(trace);
        return vulnerable.stream()
                .flatMap(p -> getPointsAround(p, 2).stream())
                .distinct()
                .filter(this::isInBounds)
                .filter(p -> isXonixFloating() ? isSea(p) : isLand(p))
                .collect(Collectors.toList());
    }

    public boolean isSea(Point point) {
        return sea.contains(point) || marineEnemies.contains(point) || trace.contains(point);
    }

    public boolean isLand(Point point) {
        return land.contains(point) || landEnemies.contains(point);
    }

    public boolean isInBounds(Point point) {
        return !board.isOutOfField(point.getX(), point.getY());
    }

    public Element getAt(Point point) {
        return board.getAt(point);
    }

    public boolean isXonixFloating() {
        return sea.contains(xonix) || !trace.isEmpty();
    }

    public boolean isXonixOnLand() {
        return land.contains(xonix);
    }

    public Point getXonix() {
        return xonix;
    }

    public List<Point> getTrace() {
        return trace;
    }

    public List<Point> getSea() {
        return sea;
    }

    public List<Point> getLand() {
        return land;
    }

    public List<Point> getLandEnemies() {
        return landEnemies;
    }

    public List<Point> getMarineEnemies() {
        return marineEnemies;
    }
}
