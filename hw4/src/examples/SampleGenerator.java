package examples;

import java.awt.Color;
import java.util.stream.IntStream;

import api.*;
import hw4.LPiece;


/**
 * Very simple implementation of the Generator interface generates
 * only red icons and only instance of SamplePiece.
 */
public class SampleGenerator implements Generator
{
  @Override
  public Piece getNext(int width)
  {
    // start off in the middle of the grid, one row above the top
    return new LPiece(new Position(-1, width / 2), IntStream.rangeClosed(0, 3).mapToObj(i -> randomIcon()).toArray(Icon[]::new));
  }

  @Override
  public Icon randomIcon()
  {
    return new Icon(Color.RED);
  }
  
}
