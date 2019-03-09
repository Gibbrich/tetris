package main;

import main.tetris.Matrix;
import main.tetris.Tetris;

import java.util.Iterator;
import java.util.Set;

import static main.tetris.Figure.FIGURES;
import static main.tetris.Tetris.GRID_HEIGHT;
import static main.tetris.Tetris.GRID_WIDTH;

public class Main {
    public static void main(String[] args) {
        final Set<Matrix> result = new Tetris().solve(GRID_WIDTH, GRID_HEIGHT, FIGURES);

        // take 1st element
        final Iterator<Matrix> iterator = result.iterator();
        if (iterator.hasNext()) {
            final Matrix matrix1 = iterator.next();
            System.out.println(matrix1);
        }
    }
}
