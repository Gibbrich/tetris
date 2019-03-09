package main.tetris;

import java.util.Arrays;
import java.util.Objects;

/**
 * Block, which can be placed in {@link Matrix}
 */
public final class Figure {
    /**
     *   x x
     * x x
     */
    public static final Figure A = new Figure(
            "A",
            new Point(0, 0),
            new Point(1, 0),
            new Point(1, 1),
            new Point(2, 1));

    /**
     * x x
     * x
     * x
     */
    public static final Figure B = new Figure(
            "B",
            new Point(0, 0),
            new Point(0, 1),
            new Point(0, 2),
            new Point(1, 2));

    /**
     *   x
     * x x x
     */
    public static final Figure C = new Figure(
            "C",
            new Point(0, 0),
            new Point(1, 0),
            new Point(2, 0),
            new Point(1, 1));

    /**
     *   x
     * x x
     *   x
     */
    public static final Figure D = new Figure(
            "D",
            new Point(0, 0),
            new Point(1, 0),
            new Point(1, 1),
            new Point(1, -1));

    public static final Figure[] FIGURES = {A, B, C, D};

    private final String name;
    private final Point[] points;

    public Figure(String name, Point... points) {
        this.name = name;
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return name.equals(figure.name) &&
                Arrays.equals(points, figure.points);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(points);
        return result;
    }
}
