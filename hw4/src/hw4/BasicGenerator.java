package hw4;

import java.util.Random;
import java.util.stream.IntStream;

import api.Generator;
import api.Icon;
import api.Piece;
import api.Position;

/**
 * Generator for Piece objects in BlockAddiction. Icons are always selected
 * uniformly at random, and the Piece types are generated with the following
 * probabilities:
 * 
 * LPiece - 10%
 * DiagonalPiece - 25%
 * CornerPiece - 15%
 * SnakePiece - 10%
 * IPiece - 40%
 * 
 * The initial position of each piece is based on its vertical size as well as
 * the width of the grid (given as an argument to getNext). The initial column
 * is always width/2 - 1. The initial row is: *
 * 
 * LPiece - row = -2
 * DiagonalPiece - row = -1
 * CornerPiece - row = -1
 * SnakePiece - row = -1
 * IPiece - row = -2
 * 
 * @author Alexander Young
 */
public class BasicGenerator implements Generator {
	private Random rand;

	/**
	 * Constructs a BasicGenerator that will use the given Random object as its
	 * source of randomness.
	 * 
	 * @param givenRandom instance of Random to use
	 */
	public BasicGenerator(Random givenRandom) {
		rand = givenRandom;
	}

	/**
	 * Returns a new Piece instance according to the ratios defined.
	 * 
	 * @return A random piece
	 */
	@Override
	public Piece getNext(int width) {
		final int col = width / 2 - 1;

		int r = rand.nextInt(101);

		// The IntStream returns a array of random icons of length of the piece length.
		
		if (r <= 10 && r >= 0) {
			return new LPiece(new Position(-2, col), IntStream.rangeClosed(0, 3).mapToObj(i -> randomIcon()).toArray(Icon[]::new));
		}

		if (r <= 35 && r >= 10) {
			return new DiagonalPiece(new Position(-1, col), IntStream.rangeClosed(0, 1).mapToObj(i -> randomIcon()).toArray(Icon[]::new));
		}

		if (r <= 50 && r >= 35) {
			return new CornerPiece(new Position(-1, col), IntStream.rangeClosed(0, 2).mapToObj(i -> randomIcon()).toArray(Icon[]::new));
		}

		if (r <= 60 && r >= 50) {
			return new SnakePiece(new Position(-1, col), IntStream.rangeClosed(0, 3).mapToObj(i -> randomIcon()).toArray(Icon[]::new));
		}

		if (r <= 100 && r >= 60) {
			return new IPiece(new Position(-2, col), IntStream.rangeClosed(0, 2).mapToObj(i -> randomIcon()).toArray(Icon[]::new));
		}
		
		// This case should not happen, but if it does, return a snake piece.
		return new SnakePiece(new Position(2, col), IntStream.rangeClosed(0, 3).mapToObj(i -> randomIcon()).toArray(Icon[]::new));
	}

	/**
	 * Returns one of the possible Icon types for this generator, selected at random.
	 * 
	 * @return A random icon.
	 */
	@Override
	public Icon randomIcon() {
		return new Icon(Icon.COLORS[rand.nextInt(Icon.COLORS.length)]);
	}
}
