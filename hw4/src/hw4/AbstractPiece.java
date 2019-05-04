package hw4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import api.Cell;
import api.Icon;
import api.Piece;
import api.Position;

/**
 * @author Alexander Young
 */
public abstract class AbstractPiece implements Piece {
	/**
	 * currentPosition stores the current position of the piece in the game area.
	 */
	private Position currentPosition;
	
	/**
	 * cells stores the various cells that make up this piece.
	 */
	private Cell[] cells;
	
	/**
	 * totalPieceLenght is the total length of this piece.
	 */
	private int totalPieceLenght;

	/**
	 * This constructs a new AbstractPiece with the given position, icon set, length, and position set.
	 * 
	 * @param initialPosition	The initial position of the piece in the game area.
	 * @param icons				The array of icons that the cells of this piece hold.
	 * @param pieceLength		The length of this piece.
	 * @param cellPositions		The array of relative positions of the cells.
	 */
	protected AbstractPiece(Position initialPosition, Icon[] icons, int pieceLength, Position[] cellPositions) {
		this.currentPosition = initialPosition;

		if (icons.length < pieceLength || cellPositions.length < pieceLength) {
			throw new IllegalArgumentException(
				String.format("The amount of inital icons/position is too low. %d was expected", totalPieceLenght)
			);
		}

		this.totalPieceLenght = pieceLength;
		this.cells = new Cell[totalPieceLenght];

		// Construct the cells from the icon and cell position arrays.
		for (int i = 0; i < pieceLength; i++) {
			cells[i] = new Cell(icons[i], cellPositions[i]);
		}
	}

	/**
	 * This clones the piece with deep copy technology.
	 * 
	 * @return 	The cloned piece.
	 */
	@Override
	public Piece clone() {
		try {
			AbstractPiece clone = (AbstractPiece) super.clone();
			
			// Set the cells to a clone of all the cells in order to complete the deep copy.
			clone.cells = clone.getCells();

			return clone;
		} catch (CloneNotSupportedException e) {
			// This state should never happen.
			return null;
		}
	}

	/**
	 * Cycle shifts the icons of the cells in this piece. It shifts each cell forward one, wrapping the last icon to the first.
	 */
	@Override
	public void cycle() {
		// Get a list of all the icons.
		List<Icon> i = Arrays.asList(Arrays.stream(cells).map(cell -> cell.getIcon()).toArray(Icon[]::new));
		
		// Shift forward one.
		Collections.rotate(i, 1);
		
		// Set the icons back to the cells.
		IntStream.range(0, i.size()).forEach(index -> {
			cells[index].setIcon(i.get(index));
		});
	}

	/**
	 * Returns clones of the Cells in this piece. The cell positions are relative to the upper-left corner of its bounding box.
	 * 
	 * @return 	Array of cell clones.
	 */
	@Override
	public Cell[] getCells() {
		return Arrays.stream(cells).map(cell -> new Cell(cell)).toArray(Cell[]::new);
	}

	/**
	 * This returns a array of cells that have their position in absolute to the game area.
	 * 
	 * @return Array of cells with absolute positioning.
	 */
	@Override
	public Cell[] getCellsAbsolute() {
		// Iterate over the cells and return a new cell with absolute positioning.
		return Arrays.stream(cells).map(cell -> {
			int row = cell.getRow() + currentPosition.row();
			int col = cell.getCol() + currentPosition.col();
			Icon b = cell.getIcon();

			return new Cell(b, new Position(row, col));
		}).toArray(Cell[]::new);
	}

	/**
	 * Returns the position of this piece (upper-left corner of its bounding box).
	 * 
	 * @return The position of this piece.
	 */
	@Override
	public Position getPosition() {
		return currentPosition;
	}
	
	/**
	 * Sets the cells in this piece, making a deep copy of the given array.
	 */
	@Override
	public void setCells(Cell[] givenCells) {
		this.cells = Arrays.stream(givenCells).limit(totalPieceLenght).map(cell -> new Cell(cell)).toArray(Cell[]::new);
	}

	/**
	 * Shifts the position of this piece down by one.
	 */
	@Override
	public void shiftDown() {
		this.currentPosition = new Position(currentPosition.row() + 1, currentPosition.col());
	}

	/**
	 * Shifts the position of this piece left by one.
	 */
	@Override
	public void shiftLeft() {
		this.currentPosition = new Position(currentPosition.row(), currentPosition.col() - 1);
	}

	/**
	 * Shifts the position of this piece right by one.
	 */
	@Override
	public void shiftRight() {
		this.currentPosition = new Position(currentPosition.row(), currentPosition.col() + 1);
	}

	/**
	 * Transforms this piece without altering its position in the game area, according to the rules of the game.
	 */
	@Override
	public abstract void transform();
}
