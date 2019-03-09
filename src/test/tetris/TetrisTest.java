package test.tetris;

import com.sun.tools.javac.util.Pair;
import main.tetris.Figure;
import main.tetris.Matrix;
import main.tetris.Point;
import main.tetris.Tetris;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static main.tetris.Figure.*;
import static main.tetris.Tetris.GRID_HEIGHT;
import static main.tetris.Tetris.GRID_WIDTH;
import static org.junit.Assert.*;

public class TetrisTest {

    @Test
    public void solve_returns_empty_Matrix_if_no_figures_passed() {
        final Set<Matrix> set = new Tetris().solve(GRID_WIDTH, GRID_HEIGHT);
        final Matrix expected = new Matrix(GRID_HEIGHT, GRID_WIDTH);

        assertEquals(1, set.size());

        final Matrix result = set.iterator().next();

        assertEquals(expected, result);
    }

    @Test
    public void solve_returns_empty_set_if_grid_is_smaller_then_any_figure() {
        final Set<Matrix> set = new Tetris().solve(2, 2, FIGURES);
        assertTrue(set.isEmpty());
    }

    @Test
    public void solve_returns_set_with_all_possible_variants_how_figures_can_be_placed_in_grid() {
        final String result1 = getResult(0, 0, 0, 1);
        final String result2 = getResult(1, 0, 0, 0);
        final String result3 = getResult(1, 0, 0, 1);
        final String result4 = getResult(1, 1, 0, 0);
        final String result5 = getResult(0, 2, 2, 0);
        final String result6 = getResult(1, 2, 0, 1);
        final String result7 = getResult(1, 1, 0, 1);
        final String result8 = getResult(1, 0, 1, 1);

        final HashSet<String> expected = new HashSet<>();
        expected.add(result1);
        expected.add(result2);
        expected.add(result3);
        expected.add(result4);
        expected.add(result5);
        expected.add(result6);
        expected.add(result7);
        expected.add(result8);

        final Set<String> result = new Tetris()
                .solve(GRID_WIDTH, GRID_HEIGHT, A, B)
                .stream()
                .map(Matrix::toString)
                .collect(Collectors.toSet());

        assertEquals(expected, result);
    }

    private void putFigureWithCoordinate(int x, int y, Figure figure, List<Pair<Point, Figure>> list) {
        final Point point = new Point(x, y);
        final Pair<Point, Figure> figureWithCoordinate = new Pair<>(point, figure);
        list.add(figureWithCoordinate);
    }

    private String getResult(int x1, int y1, int x2, int y2) {
        final List<Pair<Point, Figure>> result = new ArrayList<>();
        putFigureWithCoordinate(x1, y1, A, result);
        putFigureWithCoordinate(x2, y2, B, result);

        final StringBuilder sb = new StringBuilder();
        for (Pair<Point, Figure> pair : result) {
            sb.append(pair.snd.getName()).append(":");

            for (Point point : pair.snd.getPoints()) {
                final int x = point.getX() + pair.fst.getX();
                final int y = point.getY() + pair.fst.getY();
                sb.append(x).append(",").append(y).append(";");
            }

            sb.deleteCharAt(sb.length() - 1).append("\n");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}