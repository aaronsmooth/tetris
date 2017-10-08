
package model;

/**
 * Defines behaviors for Tetris pieces.
 * 
 * @author Alan Fowler
 * @version Spring 2012
 */
public interface Piece extends Cloneable {

  /** Shifts the piece one space to the left. */
  void moveLeft();

  /** Shifts the piece one space to the right. */
  void moveRight();

  /** Shifts the piece one space down. */
  void moveDown();

  /** Rotates the piece one quarter turn CCW. */
  void rotate();

  /**
   * @return the x coordinate of this Piece.
   */
  int getX();

  /**
   * @return the y coordinate of this Piece.
   */
  int getY();

  /**
   * @return the current state of this Piece translated to board coordinates.
   */
  int[][] getBoardCoordinates();
  
  /**
   * @return The block type of this piece
   */
  Block getBlock();
  
  /**
   * Gets the current rotations block locations.
   * 
   * @return The block locations
   */
  int[][] getBlockLocations();

}
