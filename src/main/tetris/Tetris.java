package main.tetris;

import com.sun.istack.internal.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class Tetris {
    public static final int GRID_WIDTH = 4;
    public static final int GRID_HEIGHT = 4;

    @NotNull
    public Set<Matrix> solve(
            final int gridWidth,
            final int gridHeight,
            @NotNull final Figure... figures
    ) {
        return getMatrixWithPossibleFigureVariants(
                gridWidth,
                gridHeight,
                figures.length - 1,
                figures
        );
    }

    /**
     * Solution uses slight modification of dynamic programming approach:
     * we find all possible position values for 1st figure, then use this
     * positions to find all possible position values for 1st and 2nd figures and so on.
     *
     * Solution produces some garbage due to immutability of {@link Matrix}, so in case of
     * increasing grid value, performance may decrease.
     *
     * Due to time economy, there is no other optimizations, such as checking, whether
     * s-figure was placed in the corner, like this:
     * 0 X X 0
     * X X 0 0
     * 0 0 0 0
     * 0 0 0 0
     *
     * Obviously, there is no such figure, that can be placed in the top left corner
     *
     * Another possible optimization:
     *
     * 0 0 0 0
     * 0 X X 0
     * X X 0 0
     * 0 0 0 0
     *
     * If we want to extend conditions and add another figures, we need to check,
     * is there _-figure available, which can be placed in top row. Otherwise, we
     * waste this free space.
     *
     * @param gridWidth
     * @param gridHeight
     * @param figureId id of used figure
     * @param figures figures, which will be used for placing in {@link Matrix}
     * @return possible variants of placing {@link Figure} in {@link Matrix}
     */
    @NotNull
    private Set<Matrix> getMatrixWithPossibleFigureVariants(
            final int gridWidth,
            final int gridHeight,
            final int figureId,
            @NotNull final Figure... figures
    ) {
        if (figureId < 0) {
            final Set<Matrix> result = new HashSet<>();
            result.add(new Matrix(gridHeight, gridWidth));
            return result;
        }

        final Set<Matrix> variantsWithPreviousFigures = getMatrixWithPossibleFigureVariants(
                gridWidth,
                gridHeight,
                figureId - 1,
                figures
        );
        final Set<Matrix> variantsWithCurrentFigure = new HashSet<>();

        for (Matrix variant : variantsWithPreviousFigures) {
            final Figure currentFigure = figures[figureId];
            final Set<Point> possibleCoordinates = variant.getPossibleCoordinates(currentFigure);
            for (Point possibleCoordinate : possibleCoordinates) {
                final Matrix possibleVariantWithCurrentFigure = variant.put(
                        currentFigure,
                        possibleCoordinate.getX(),
                        possibleCoordinate.getY()
                );

                if (possibleVariantWithCurrentFigure != null) {
                    variantsWithCurrentFigure.add(possibleVariantWithCurrentFigure);
                }
            }
        }

        return variantsWithCurrentFigure;
    }
}
