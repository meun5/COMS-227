package hw4;

import java.util.Arrays;

import api.Cell;
import api.Icon;
import api.Position;

public class LPiece extends AbstractPiece {
	/**
	 * This is the length of this piece, 4.
	 */
	private static final int totalPieceLenght = 4;
	
	/**
	 * This is the initial positions of the cells.
	 */
	private static final Position[] initialPosition = new Position[] {
		new Position(0, 0),
		new Position(0, 1),
		new Position(1, 1),
		new Position(2, 1)
	};
	
	/**
	 * This constructs a new LPiece with the given position, and icon set.
	 * 
	 * @param p		The initial position.
	 * @param i		The icons to use.
	 * 
	 * @throws IllegalArgumentException
	 */
	public LPiece(Position p, Icon[] i) throws IllegalArgumentException {
		super(p, i, totalPieceLenght, initialPosition);
	}

	/**
	 * Transforms the piece by moving everything in column 0 to column 2, and vice versa.
	 */
	@Override
	public void transform() {
		setCells(Arrays.stream(getCells()).map(cell -> {
			if (cell.getCol() == 0) {
				cell.setCol(2);
			} else if (cell.getCol() == 2) {
				cell.setCol(0);
			}
			
			return cell;
		}).toArray(Cell[]::new));
	}
}
