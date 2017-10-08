/*
 * TCSS 305 - Project Tetris - Part 1
 */

package model;

/**
 * Provides default behavior for Tetris Pieces.
 * 
 * @author Alan Fowler
 * @version Spring 2012
 */
public abstract class AbstractPiece implements Piece, Cloneable {

  /**
   * The number of blocks in a piece.
   */
  private static final int BLOCKS = 4;

  /** The x coordinate of this Piece. */
  private int my_x;

  /** The y coordinate of this Piece. */
  private int my_y;

  /** The rotational states of this Piece. */
  private int[][][] my_rotations;

  /** The index of the current rotational state of this Piece. */
  private int my_current_rotation;
  
  /** The block type representing this piece. */
  private final Block my_block;

  /**
   * Creates a new piece at the given coordinates.
   * 
   * @param the_rotations the rotational states for this Piece
   * @param the_x the initial x coordinate for this piece
   * @param the_y the initial y coordinate for this piece
   * @param the_block the type of block
   */
  protected AbstractPiece(final int[][][] the_rotations,
                          final int the_x,
                          final int the_y,
                          final Block the_block) {
    my_rotations = the_rotations.clone();
    my_current_rotation = 0;
    my_x = the_x;
    my_y = the_y;
    my_block = the_block;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moveLeft() {
    my_x--;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moveRight() {
    my_x++;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moveDown() {
    my_y--;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void rotate() {
    my_current_rotation = (my_current_rotation + 1) % my_rotations.length;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getX() {
    return my_x;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getY() {
    return my_y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int[][] getBoardCoordinates() {
    final int[][] result = new int[BLOCKS][2];

    for (int i = 0; i < BLOCKS; i++) {
      result[i][0] = my_rotations[my_current_rotation][i][0] + my_x;
      result[i][1] = my_rotations[my_current_rotation][i][1] + my_y;
    }
    return result;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Block getBlock() {
    return my_block;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractPiece clone() {
    AbstractPiece result = null;
    try {
      result = (AbstractPiece) super.clone();
    } catch (final CloneNotSupportedException e) {
      e.printStackTrace();
    }
    result.my_rotations = my_rotations.clone();
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    final int width = width();
    final int height = height();
    final StringBuilder sb = new StringBuilder();

    // Construct the string by walking through the piece top to bottom, left to
    // right.
    for (int j = height - 1; j >= 0; j--) {
      for (int i = 0; i < width; i++) {
        boolean found = false;
        for (int b = 0; b < BLOCKS; b++) {
          if (my_rotations[my_current_rotation][b][0] == i &&
              my_rotations[my_current_rotation][b][1] == j) {
            // There is a block here, so print and move on
            sb.append("[]");
            found = true;
            break;
          }
        }
        if (!found) {
          // None of the blocks are here, so put in empty space
          sb.append("  ");
        }
      }
      // Move to the next line
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Returns the width of this piece.
   * 
   * @return The width of this piece
   */
  private int width() {
    int result = 0;
    for (int i = 0; i < BLOCKS; i++) {
      if (my_rotations[my_current_rotation][i][0] > result) {
        result = my_rotations[my_current_rotation][i][0];
      }
    }
    return result + 1;
  }

  /**
   * Returns the height of this piece.
   * 
   * @return The height of this piece
   */
  private int height() {
    int result = 0;
    for (int i = 0; i < BLOCKS; i++) {
      if (my_rotations[my_current_rotation][i][1] > result) {
        result = my_rotations[my_current_rotation][i][1];
      }
    }
    return result + 1;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final int[][] getBlockLocations() {
    return my_rotations[my_current_rotation].clone();
  }

}
