package hw4;

import api.Cell;
import api.Icon;
import api.Position;

/**
 * @author Alexander Young
 */
public class SnakePiece extends AbstractPiece {
	/**
	 * This is the length of this piece, 3.
	 */
	private static final int totalPieceLenght = 4;
	
	/**
	 * This is the initial positions of the cells.
	 */
	private static final Position[] initialPosition = new Position[] {
		new Position(0, 0),
		new Position(1, 0),
		new Position(1, 1),
		new Position(1, 2),
	};
	
	/**
	 * This constructs a new SnakePiece with the given position, and icon set.
	 * 
	 * @param p		The initial position.
	 * @param i		The icons to use.
	 * 
	 * @throws IllegalArgumentException
	 */
	public SnakePiece(Position p, Icon[] i) throws IllegalArgumentException {
		super(p, i, totalPieceLenght, initialPosition);
	}

	/**
	 * This piece rotates by first moving the cells except the head to the position of the previous one. Then it tracks the head location.
	 */
	@Override
	public void transform() {
		Cell[] cells = getCells();
		
		Position tail = new Position(cells[3].getRow(), cells[3].getCol());
		
		// Move the previous cells up to the cell before it.
		for (int i = cells.length - 1; i > 0; i--) {
			cells[i].setPosition(new Position(cells[i - 1].getRow(), cells[i - 1].getCol()));
		}
		
		Cell head = cells[0];
		int row = head.getRow();
		int col = head.getCol();
		
		// Move the head based on where it is.
		switch (row) {
			case 0:
				if (col == 2) {
					head.setRow(1);
					break;
				}
				
				head.setCol(col + 1);
				break;
			
			case 1:
				if (tail.row() == 2 && col == 0) {
					head.setRow(0);
					break;
				}
				
				if (col == 0) {
					head.setRow(2);
					break;
				}
				
				head.setCol(col - 1);
				break; 
			
			case 2:
				if (col == 2) {
					head.setRow(1);
					break;
				}
				
				head.setCol(col + 1);
				break;
		}
		
		setCells(cells);
	}
}
