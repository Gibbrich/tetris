package main.tetris;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.sun.tools.javac.util.Pair;

import java.util.*;

/**
 * Holds all {@link Figure} and track their positions
 */
public final class Matrix {
    private final boolean[][] grid;
    private final Set<Pair<Point, Figure>> figuresWithPositions;


    public Matrix(int height, int widht) {
        if (height < 0 || widht < 0) {
            throw new IllegalArgumentException("height and weight parameters must be > 0");
        }

        grid = new boolean[height][widht];
        figuresWithPositions = new HashSet<>();
    }

    /**
     * Returns set of all possible coordinates, where {@link Figure} can be placed
     */
    @NotNull
    public Set<Point> getPossibleCoordinates(@NotNull Figure figure) {
        final Set<Point> possiblePositions = new HashSet<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (!doesFitGrid(figure, x, y)) {
                    continue;
                }

                possiblePositions.add(new Point(x, y));
            }
        }

        return possiblePositions;
    }

    /**
     * Creates copy of this {@link Matrix} and places {@link Figure} in it with
     * specified coordinates. If {@link Figure} can't be placed, returns null.
     * Do not mutate old matrix (due to simplicity of development).
     * @param figure {@link Figure} to place
     * @param x X-coordinate of the grid
     * @param y Y-coordinate of the grid
     */
    @Nullable
    public Matrix put(@NotNull Figure figure, int x, int y) {
        if (!doesFitGrid(figure, x, y)) {
            return null;
        }

        final Matrix copy = copy();

        for (Point point : figure.getPoints()) {
            final int offsetY = point.getY() + y;
            final int offsetX = point.getX() + x;

            copy.grid[offsetY][offsetX] = true;
        }

        copy.figuresWithPositions.add(new Pair<>(new Point(x, y), figure));
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;

        return areGridEquals(matrix) &&
                figuresWithPositions.equals(matrix.figuresWithPositions);
    }

    /**
     * equals() is not defined for array, so to check equality we need manually
     * check each row.
     * @param other matrix we want to check for equality
     * @return true if other matrix's values in each row are equal to current
     */
    private boolean areGridEquals(Matrix other) {
        if (grid.length != other.grid.length) {
            return false;
        }

        for (int i = 0; i < grid.length; i++) {
            final boolean areRowsEqual = Arrays.equals(grid[i], other.grid[i]);
            if (!areRowsEqual) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(figuresWithPositions);
        result = 31 * result + Arrays.hashCode(grid);
        return result;
    }

    /**
     * Returns string representation in the following format:
     * A:x1,y1;x2,y2;x3,y3;x4,y4
     * B:x1,y1;x2,y2;x3,y3;x4,y4
     * ...
     */
    @Override
    public String toString() {
        final ArrayList<Pair<Point, Figure>> figuresWithPositionsList = new ArrayList<>(this.figuresWithPositions);
        figuresWithPositionsList.sort(Comparator.comparing(o -> o.snd.getName()));

        final StringBuilder sb = new StringBuilder();

        for (Pair<Point, Figure> pair : figuresWithPositionsList) {
            sb.append(pair.snd.getName()).append(":");

            for (Point point : pair.snd.getPoints()) {
                final int x = point.getX() + pair.fst.getX();
                final int y = point.getY() + pair.fst.getY();
                sb.append(x).append(",").append(y).append(";");
            }

            sb.deleteCharAt(sb.length() - 1).append("\n");
        }

        // StringBuilder can be empty if figuresWithPositions is empty
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * Check, whether every {@link Point} in {@link Figure} satisfies these conditions:
     * 1) Point X and Y are in grid
     * 2) Grid cell with there coordinates is not already taken
     */
    private boolean doesFitGrid(@NotNull Figure figure, int x, int y) {
        for (Point point : figure.getPoints()) {
            final int offsetX = point.getX() + x;
            if (!isXCoordinateInsideMatrix(offsetX)) {
                return false;
            }

            final int offsetY = point.getY() + y;
            if (!isYCoordinateInsideMatrix(offsetY)) {
                return false;
            }

            if (isCellTaken(offsetY, offsetX)) {
                return false;
            }
        }

        return true;
    }

    private boolean isYCoordinateInsideMatrix(int y) {
        return y >= 0 && y < grid[0].length;
    }

    private boolean isXCoordinateInsideMatrix(int x) {
        return x >= 0 && x < grid.length;
    }

    private boolean isCellTaken(int y, int x) {
        return grid[y][x];
    }

    /**
     * Creates copy of this instance
     */
    @NotNull
    private Matrix copy() {
        final Matrix matrix = new Matrix(grid.length, grid[0].length);

        for (int i = 0; i < matrix.grid.length; i++) {
            System.arraycopy(grid[i], 0, matrix.grid[i], 0, matrix.grid[0].length);
        }

        matrix.figuresWithPositions.addAll(figuresWithPositions);

        return matrix;
    }
}
