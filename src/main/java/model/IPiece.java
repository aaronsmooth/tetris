/*
 * TCSS 305 - Project Tetris - Part 1
 */

package model;

/**
 * Defines the Tetris IPiece.
 * 
 * @author Alan Fowler
 * @version Spring 2012
 */
public final class IPiece extends AbstractPiece {

  /**
   * The x and y-coordinates for all rotations of a IPiece.
   */
  private static final int[][][] MY_ROTATIONS = {{{0, 0}, {1, 0}, {2, 0}, {3, 0}},
                                                 {{0, 0}, {0, 1}, {0, 2}, {0, 3}}};

  /**
   * Creates a new I piece at the given coordinates.
   * 
   * @param the_x The x coordinate of the Piece.
   * @param the_y The y coordinate of the piece
   */
  public IPiece(final int the_x, final int the_y) {
    super(MY_ROTATIONS, the_x, the_y, Block.I);
  }

}
