package hw4;

import api.Icon;
import api.Position;

/**
 * @author Alexander Young
 */
public class IPiece extends AbstractPiece {
	/**
	 * This is the length of this piece, 3.
	 */
	private static final int totalPieceLenght = 3;
	
	/**
	 * This is the initial positions of the cells.
	 */
	private static final Position[] initialPosition = new Position[] {
		new Position(0, 1),
		new Position(1, 1),
		new Position(2, 1)
	};
	
	/**
	 * This constructs a new IPiece with the given position, and icon set.
	 * 
	 * @param p		The initial position.
	 * @param i		The icons to use.
	 * 
	 * @throws IllegalArgumentException
	 */
	public IPiece(Position p, Icon[] i) throws IllegalArgumentException {
		super(p, i, totalPieceLenght, initialPosition);
	}

	/**
	 * This piece does not transform.
	 */
	@Override
	public void transform() {
		
	}
}
