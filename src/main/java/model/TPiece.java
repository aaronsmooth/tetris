/*
 * TCSS 305 - Project Tetris - Part 1
 */

package model;

/**
 * Defines the Tetris TPiece.
 * 
 * @author Alan Fowler
 * @version Spring 2012
 */
public final class TPiece extends AbstractPiece {

  /**
   * The x and y-coordinates for all rotations of a TPiece.
   */
  private static final int[][][] MY_ROTATIONS = {{{0, 0}, {1, 0}, {2, 0}, {1, 1}},
                                                 {{1, 0}, {0, 1}, {1, 1}, {1, 2}},
                                                 {{1, 0}, {0, 1}, {1, 1}, {2, 1}},
                                                 {{0, 0}, {0, 1}, {1, 1}, {0, 2}}};

  /**
   * Creates a new T piece at the given coordinates.
   * 
   * @param the_x The x coordinate of the Piece.
   * @param the_y The y coordinate of the piece
   */
  public TPiece(final int the_x, final int the_y) {
    super(MY_ROTATIONS, the_x, the_y, Block.T);
  }

}
