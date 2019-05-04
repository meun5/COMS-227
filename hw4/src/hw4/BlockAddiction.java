package hw4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import api.AbstractGame;
import api.Generator;
import api.Icon;
import api.Position;

/**
 * @author Alexander Young
 */
public class BlockAddiction extends AbstractGame {
	
	/**
	 * This constructs a new BlockAddiction Game with the specified width, height, and generator.
	 * 
	 * @param givenHeight	The height of the game panel
	 * @param givenWidth	The width of the game panel
	 * @param generator		The generator to use for the piece generation
	 */
	public BlockAddiction(int givenHeight, int givenWidth, Generator generator) {
		super(givenHeight, givenWidth, generator);
	}

	/**
	 * This also constructs a BlockAddiction Game, but will prefill the specified rows with a random Icon.
	 * 
	 * @param givenHeight	The height of the game panel
	 * @param givenWidth	The width of the game panel
	 * @param generator		The generator to use for the piece generation
	 * @param preFilledRows	The amount of rows to fill
	 */
	public BlockAddiction(int givenHeight, int givenWidth, Generator generator, int preFilledRows) {
		super(givenHeight, givenWidth, generator);

		// For each row and column, determine if both are even or are both odd. If so, set a random Icon to that coordinate.
		for (int row = 0; row < preFilledRows; row++) {
			for (int col = 0; col < givenWidth; col++) {
				if (row % 2 == 0 && col % 2 == 0) {
					setBlock(givenHeight - (row + 1), col, generator.randomIcon());
				}

				if (row % 2 != 0 && col % 2 != 0) {
					setBlock(givenHeight - (row + 1), col, generator.randomIcon());
				}
			}
		}
	}

	/**
	 * This returns a list of positions which are a part of a collapsible set.
	 * A collapsible set is defined as a group of three or more adjacent cells that share the same icon colour.
	 * 
	 * @return A list of collapsible pieces.
	 */
	@Override
	public List<Position> determinePositionsToCollapse() {
		List<Position> positionsToDelete = new ArrayList<Position>();

		// For each row, column get all matching neighbors.
		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				positionsToDelete.addAll(getMatchingNeighbors(row, col, getIcon(row, col)));
			}
		}

		// This gets only the unique elements in the List by calling distinct on the array stream.
		positionsToDelete = positionsToDelete.stream().distinct().collect(Collectors.toList());

		Collections.sort(positionsToDelete);

		return positionsToDelete;
	}

	/**
	 * This is a helper method that returns the neighbors around the current piece that match its icon.
	 * 
	 * @param row		The row of the current cell.
	 * @param col		The column of the current cell.
	 * @param icon		The icon of the current cell that we are testing.
	 * 
	 * @return			A list of neighbors that match.
	 */
	private List<Position> getMatchingNeighbors(int row, int col, Icon icon) {
		List<Position> neighbors = new ArrayList<Position>();

		// If the icon is null, there are no matching neighbors.
		if (icon == null) {
			return neighbors;
		}

		if (row != 0 && getIcon(row - 1, col) != null && getIcon(row - 1, col).matches(icon)) {
			neighbors.add(new Position(row - 1, col));
		}

		if (col != 0 && getIcon(row, col - 1) != null && getIcon(row, col - 1).matches(icon)) {
			neighbors.add(new Position(row, col - 1));
		}

		if (row != getHeight() - 1 && getIcon(row + 1, col) != null && getIcon(row + 1, col).matches(icon)) {
			neighbors.add(new Position(row + 1, col));
		}

		if (col != getWidth() - 1 && getIcon(row, col + 1) != null && getIcon(row, col + 1).matches(icon)) {
			neighbors.add(new Position(row, col + 1));
		}

		// If there are less than two matching neighbors, clear the list. Else, add the current icon itself.
		if (neighbors.size() >= 2) {
			neighbors.add(new Position(row, col));
		} else {
			neighbors.clear();
		}

		return neighbors;
	}
}
