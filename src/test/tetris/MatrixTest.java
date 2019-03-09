package test.tetris;

import main.tetris.Matrix;
import main.tetris.Point;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static main.tetris.Figure.A;
import static main.tetris.Figure.B;
import static main.tetris.Tetris.GRID_HEIGHT;
import static main.tetris.Tetris.GRID_WIDTH;
import static org.junit.Assert.*;

public class MatrixTest {

    /**
     * For s-figure (i.e. A) in 4x4 grid avaliable Points with coordinates
     * x in [0, 1] and y in [0, 2]
     */
    @Test
    public void getPossibleCoordinates_returns_points_which_allow_figure_fit_grid() {
        final Matrix matrix = new Matrix(GRID_HEIGHT, GRID_WIDTH);
        final Set<Point> possibleCoordinates = matrix.getPossibleCoordinates(A);

        final Point point1 = new Point(0, 0);
        final Point point2 = new Point(1, 0);
        final Point point3 = new Point(0, 1);
        final Point point4 = new Point(1, 1);
        final Point point5 = new Point(0, 2);
        final Point point6 = new Point(1, 2);

        final HashSet<Point> expected = new HashSet<>();
        expected.add(point1);
        expected.add(point2);
        expected.add(point3);
        expected.add(point4);
        expected.add(point5);
        expected.add(point6);

        Assert.assertEquals(expected, possibleCoordinates);
    }

    @Test
    public void getPossibleCoordinates_returns_empty_set_if_grid_smaller_than_figure() {
        final Matrix matrix = new Matrix(2, 2);
        final Set<Point> possibleCoordinates = matrix.getPossibleCoordinates(A);
        assertTrue(possibleCoordinates.isEmpty());
    }

    @Test
    public void getPossibleCoordinates_returns_emptyset_if_grid_already_taken_by_another_figure() {
        Matrix matrix = new Matrix(3, 3);
        matrix = matrix.put(A, 0, 0);

        final Set<Point> possibleCoordinates = matrix.getPossibleCoordinates(B);
        assertTrue(possibleCoordinates.isEmpty());
    }

    @Test
    public void put_returns_null_if_grid_smaller_than_figure() {
        putFigure(0, 0, 2, 2);
    }

    @Test
    public void put_returns_null_if_figure_x_or_y_coordinates_more_than_matrix_width_and_height_or_less_0() {
        putFigure(0, 10, GRID_WIDTH, GRID_HEIGHT);
        putFigure(0, -10, GRID_WIDTH, GRID_HEIGHT);
        putFigure(10, 0, GRID_WIDTH, GRID_HEIGHT);
        putFigure(-10, 0, GRID_WIDTH, GRID_HEIGHT);

    }

    @Test
    public void put_returns_new_Matrix_instance() {
        final Matrix matrix = new Matrix(GRID_HEIGHT, GRID_WIDTH);
        final Matrix result = matrix.put(A, 0, 0);
        assertNotEquals(matrix, result);
    }

    @Test
    public void toString_returns_string_representation_of_points_sorted_by_figure_name() {
        final String result = new Matrix(GRID_HEIGHT, GRID_WIDTH)
                .put(A, 0, 0)
                .put(B, 0, 1)
                .toString();

        final String expected = "A:0,0;1,0;1,1;2,1\nB:0,1;0,2;0,3;1,3";

        assertEquals(expected, result);
    }

    private void putFigure(int x, int y, int width, int height) {
        final Matrix matrix = new Matrix(height, width);
        final Matrix result = matrix.put(A, x, y);
        assertNull(result);
    }
}