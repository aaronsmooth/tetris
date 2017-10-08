
package model;

/**
 * The different types of blocks that will be stored in a Board's grid.
 * 
 * @author Alan Fowler
 * @version Spring 2012
 */
public enum Block {
  /** AN empty space in the grid. */
  EMPTY(" "),
  /** A Block from an IPiece. */
  I("I"),
  /** A Block from a JPiece. */
  J("J"),
  /** A Block from an LPiece. */
  L("L"),
  /** A Block from an OPiece. */
  O("O"),
  /** A Block from an SPiece. */
  S("S"),
  /** A Block from a TPiece. */
  T("T"),
  /** A Block from a ZPiece. */
  Z("Z");

  /**
   * The character corresponding to a particular value of the enumeration.
   */
  private String my_letter;

  // Constructor

  /**
   * Constructs a new Block with the specified letter.
   * 
   * @param the_letter The letter.
   */
  private Block(final String the_letter) {
    my_letter = the_letter;
  }

  /**
   * Returns a String representation of this Block.
   * 
   * @return a String representation of this Block.
   */
  @Override
  public String toString() {
    return my_letter;
  }
}
