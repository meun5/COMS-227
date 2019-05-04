package hw4;

import java.util.Arrays;
import java.util.List;

import api.Cell;
import api.Icon;
import api.Position;

/**
 * @author Alexander Young
 */
public class CornerPiece extends AbstractPiece {
	/**
	 * This is the length of this piece, 3.
	 */
	private static final int totalPieceLenght = 3;
	
	/**
	 * This is the initial positions of the cells.
	 */
	private static final Position[] initialPosition = new Position[] {
		new Position(0, 0),
		new Position(1, 0),
		new Position(1, 1)
	};
	
	/**
	 * This constructs a new CornerPiece with the given position, and icon set.
	 * 
	 * @param p		The initial position.
	 * @param i		The icons to use.
	 * 
	 * @throws IllegalArgumentException
	 */
	public CornerPiece(Position p, Icon[] i) throws IllegalArgumentException {
		super(p, i, totalPieceLenght, initialPosition);
	}

	/**
	 * Rotates the piece by determining where the "blank" is.
	 */
	@Override
	public void transform() {
		// TODO: Cleanup to use head tracking method.
		Cell[] cells = getCells();
		
		// Get a list of positions.
		List<Position> positions = Arrays.asList(Arrays.stream(cells).map(cell -> {
			return new Position(cell.getRow(), cell.getCol());
		}).toArray(Position[]::new));
	
		if (!positions.contains(new Position(0, 1))) {
			cells[0].setRowCol(0, 1);
			cells[1].setRowCol(0, 0);
			cells[2].setRowCol(1, 0);
		}
		
		if (!positions.contains(new Position(1, 1))) {
			cells[0].setRowCol(1, 1);
			cells[1].setRowCol(0, 1);
			cells[2].setRowCol(0, 0);
		}
		
		if (!positions.contains(new Position(1, 0))) {
			cells[0].setRowCol(1, 0);
			cells[1].setRowCol(1, 1);
			cells[2].setRowCol(0, 1);
		}
		
		if (!positions.contains(new Position(0, 0))) {
			cells[0].setRowCol(0, 0);
			cells[1].setRowCol(1, 0);
			cells[2].setRowCol(1, 1);
		}
		
		setCells(cells);
	}
}
