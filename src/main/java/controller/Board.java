/*
 * TCSS305 Spring 2012
 */

package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import model.AbstractPiece;
import model.Block;
import model.IPiece;
import model.JPiece;
import model.LPiece;
import model.OPiece;
import model.Piece;
import model.SPiece;
import model.TPiece;
import model.ZPiece;
import view.GameInformationHolder;

/**
 * A representation of a Tetris game board.
 * 
 * @author Alan Fowler
 * @version Spring 201287
 */
public class Board extends Observable {

  // constants

  /** The width of a standard Tetris board and the number of lines to be cleared to level up.
   */
  private static final int DEFAULT_WIDTH = 10;

  /** The height of a standard Tetris board. */
  private static final int DEFAULT_HEIGHT = 20;

  // for toString()

  /** The representation of the side walls of the board in string output. */
  private static final String WALL = "|";

  /** The representation of the board corners in string output. */
  private static final String CORNER = "+";

  /** The representation of the board floor in string output. */
  private static final String FLOOR = "-";

  /** The representation of an empty grid position in string output. */
  private static final String EMPTY = " ";

  /** The representation of a frozen block in string output. */
  private static final String FROZEN = "X";

  /** The representation of the current piece's position in string output. */
  private static final String CURRENT_PIECE = "*";

  /** The random number generator used for choosing new pieces. */
  private static final Random RANDOM = new Random();

  /** The minimum size of a board. */
  private static final int MIN_SIZE = 5;

  /** The number of rotations to return a piece to initial state. */
  private static final int NUM_ROTATIONS = 4;

  /** The number of extra rows above the board to display in String output. */
  private static final int EXTRA_ROWS = 4;
  


  // instance fields

  /**
   * The width of this Tetris board.
   */
  private int my_width;

  /**
   * The height of this Tetris board.
   */
  private int my_height;

  /**
   * The current state of the board.
   */
  private final List<Block[]> my_blocks;

  /**
   * The piece currently being moved around the board.
   */
  private Piece my_current_piece;
  
  /**
   * The piece that will be used next.
   */
  private Piece my_next_piece;

  /**
   * The predetermined list of pieces to be used by the board.
   */
  private List<Piece> my_pieces;
  /**
   * A field that holds data about a game such as difficulty level, score, and next piece.
   */
  private GameInformationHolder my_holder;

  /**
   * Constructs a Board using the specified dimensions.
   * 
   * @param the_width the height to assign
   * @param the_height the width to assign
   * @param the_pieces the sequence of pieces to use;
   *   null or empty list indicates a random game
   * @throws IllegalArgumentException if the_width or the_height is less than MIN_SIZE
   */
  public Board(final int the_width, final int the_height, final List<Piece> the_pieces)
    throws IllegalArgumentException {
    
    super();
    if (the_width < MIN_SIZE || the_height < MIN_SIZE) {
      throw new IllegalArgumentException();
    }
    my_blocks = new LinkedList<Block[]>();
    my_pieces = new LinkedList<Piece>();
    setupGame(the_width, the_height, the_pieces);
  }

  /**
   * Constructs a Board using default dimensions (10x20).
   */
  public Board() {
    this(DEFAULT_WIDTH, DEFAULT_HEIGHT, null);
  }

  /**
   * Creates a new game of the specified width and height and loads the given
   * set of pieces.
   * 
   * @param the_width The width of the board
   * @param the_height The height of the board
   * @param the_pieces The pieces to use
   */
  public final void setupGame(final int the_width, final int the_height,
                              final List<Piece> the_pieces) {
    my_width = the_width;
    my_height = the_height;
    my_blocks.clear();
    if (the_pieces == null) {
      my_pieces.clear();
      my_next_piece = randomPiece(my_width / 2 - 1, my_height);
    } else {
      my_pieces = the_pieces;
      my_next_piece = my_pieces.remove(0);
    } 
    my_holder = new GameInformationHolder(my_next_piece);
    assignCurrentPiece();
    setChanged();
    notifyObservers(my_holder);
  }

  /**
   * @return the width of the Board
   */
  public int getWidth() {
    return my_width;
  }

  /**
   * @return the height of the Board
   */
  public int getHeight() {
    return my_height;
  }

  /**
   * Attempts to move the current piece to the left.
   * 
   * @return true if it is possible to move the current piece to the left; false
   *         otherwise
   */
  public boolean moveLeft() {
    final int[][] blocks = my_current_piece.getBoardCoordinates();
    boolean can_pass = true;

    // perform bounds checking on each block
    for (int i = 0; i < blocks.length; i++) {
      if (blocks[i][0] == 0 || // block is already at the left wall
          blockAt(blocks[i][0] - 1, blocks[i][1]) != Block.EMPTY)  {
          // block to left is occupied
        can_pass = false;
        break; // can't move, no need to keep checking
      }
    }

    if (can_pass) {
      my_current_piece.moveLeft();
      setChanged();
      notifyObservers();
    }
    return can_pass;
  }

  /**
   * Attempts to move the current piece to the right.
   * 
   * @return true if it is possible to move the current piece to the right;
   *         false otherwise
   */
  public boolean moveRight() {
    final int[][] blocks = my_current_piece.getBoardCoordinates();
    boolean can_pass = true;

    // perform bounds checking on each block
    for (int i = 0; i < blocks.length; i++) {
      if (blocks[i][0] == my_width - 1 || // block is already at the right wall
          blockAt(blocks[i][0] + 1, blocks[i][1]) != Block.EMPTY) {
          // block to right is occupied
        can_pass = false;
        break; // can't move, no need to keep checking
      }
    }

    if (can_pass) {
      my_current_piece.moveRight();
      setChanged();
      notifyObservers();
    }
    return can_pass;
  }

  /**
   * Attempts to move the current piece down.
   * 
   * @return true if it is possible to move the current piece down; false
   *         otherwise
   */
  public boolean moveDown() {
    final int[][] blocks = my_current_piece.getBoardCoordinates();
    boolean can_pass = true;

    // perform bounds checking on each block
    for (int i = 0; i < blocks.length; i++) {
      // Is this block at the bottom, or is there a piece below it?
      if (blocks[i][1] == 0 || // block is at the bottom
          blockAt(blocks[i][0], blocks[i][1] - 1) != Block.EMPTY) { // block below
                                                                    // is occupied
        can_pass = false;
        break; // can't move, no need to keep checking
      }
    }

    if (can_pass) {
      my_current_piece.moveDown();
      setChanged();
      notifyObservers();
    } else {
      freeze();
    }
    return can_pass;
  }

  /**
   * Attempts to rotate the current piece.
   * 
   * @return true if it is possible to rotate the current piece; false otherwise
   */
  public boolean rotate() {
    my_current_piece.rotate();
    final int[][] blocks = my_current_piece.getBoardCoordinates();
    boolean can_pass = true;

    // perform bounds checking on each block
    for (int[] dimension : blocks) {
      if (dimension[0] >= my_width || blockAt(dimension[0], dimension[1]) != Block.EMPTY) {
        // If it overlaps, rotate it back to its original position
        for (int i = 1; i < NUM_ROTATIONS; i++) {
          my_current_piece.rotate();
        }
        can_pass = false;
        break;
      }
    }
    if (can_pass) {
      setChanged();
      notifyObservers();
    }
    return can_pass;
  }

  /**
   * Updates the game by one step.
   */
  public void step() {
    moveDown();
  }

  /**
   * Initializes the current piece.
   */
  private void assignCurrentPiece() {
    my_current_piece = my_next_piece;
    if (my_pieces == null || my_pieces.isEmpty()) {
      // generate a random piece
      my_next_piece = randomPiece(my_width / 2 - 1, my_height);
    } else {
      // get the next piece from the list
      my_next_piece = my_pieces.remove(0);
    }
    my_holder.updatePiece(my_next_piece);
    setChanged();
    notifyObservers(my_holder);
  }

  /**
   * Creates a new piece randomly chosen from the possible pieces at the
   * specified coordinates.
   * 
   * @param the_x The x-coordinate
   * @param the_y The y-coordinate
   * 
   * @return A randomly chosen piece
   */
  private Piece randomPiece(final int the_x, final int the_y) {
    final Block[] blocks = Block.values();
    Piece result;

    switch (blocks[RANDOM.nextInt(blocks.length)]) {
      case I:
        result = new IPiece(the_x, the_y);
        break;

      case J:
        result = new JPiece(the_x, the_y);
        break;

      case L:
        result = new LPiece(the_x, the_y);
        break;

      case O:
        result = new OPiece(the_x, the_y);
        break;

      case S:
        result = new SPiece(the_x, the_y);
        break;

      case T:
        result = new TPiece(the_x, the_y);
        break;

      case Z:
        result = new ZPiece(the_x, the_y);
        break;

      default: // If EMPTY try again
        result = randomPiece(the_x, the_y);
        break;
    }
    return result;
  }

  /**
   * Retrieves the block at the specified coordinates.
   * 
   * @param the_x The x-coordinate
   * @param the_y The y-coordinate
   * 
   * @return The block at the provided position or EMPTY if there is no block
   * 
   * @throws IllegalArgumentException if the requested position is outside the
   *           board.
   */
  private Block blockAt(final int the_x, final int the_y) throws IllegalArgumentException {
    if (the_x >= my_width || the_x < 0 || the_y < 0) { // outside the board
      throw new IllegalArgumentException("x: " + the_x + " y: " + the_y);
    }
    Block result = Block.EMPTY; // blocks above the board are empty
    if (the_y < my_blocks.size()) {
      result = my_blocks.get(the_y)[the_x];
    }
    return result;
  }

  /**
   * Evaluates if the current piece occupies the provided position.
   * 
   * @param the_x The x-coordinate to examine
   * @param the_y The y-coordinate to examine
   * @return true if the current piece occupies the position, false otherwise
   */
  private boolean currentPieceAt(final int the_x, final int the_y) {
    boolean result = false;
    final int[][] blocks = my_current_piece.getBoardCoordinates();

    for (int block = 0; block < blocks.length; block++) {
      if (blocks[block][1] == the_y && blocks[block][0] == the_x) {
        result = true;
      }
    }
    return result;
  }

  /**
   * The current piece cannot move down so add its blocks to the board.
   */
  private void freeze() {
    final int[][] coordinates = my_current_piece.getBoardCoordinates();

    for (int block = 0; block < coordinates.length; block++) {
      final int x = coordinates[block][0];
      final int y = coordinates[block][1];
      
      if (y > this.my_height) {
        my_holder.endGame();
      }

      // Add rows until this block can fit in one.
      while (y >= my_blocks.size()) {
        final Block[] new_row = new Block[my_width];
        for (int i = 0; i < my_width; i++) {
          new_row[i] = Block.EMPTY;
        }
        my_blocks.add(new_row);
      }
      final Block[] row = my_blocks.get(y);
      row[x] = my_current_piece.getBlock();
    }
    clearLines();
    assignCurrentPiece();
    setChanged();
    notifyObservers(my_holder);
  }

  /**
   * Checks if there are any lines that need to be cleared and removes them from
   * the board.
   */
  private void clearLines() {
    for (int i = my_blocks.size() - 1; i >= 0; i--) {
      boolean clear = true;
      final Block[] blocks = my_blocks.get(i);

      for (Block block : blocks) {
        if (block == Block.EMPTY) {
          clear = false;
          break;
        }
      }
      if (clear) {
        my_blocks.remove(i);
        my_holder.updateLineCount();
        setChanged();
        notifyObservers(my_holder);
      }
    }
  }

  /**
   * Returns a string that represents the current state of the board.
   * 
   * @return The string representation of the board
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    for (int i = my_height + EXTRA_ROWS - 1; i > my_height - 1; i--) {
      sb.append(EMPTY);
      sb.append(getRowString(i));
      sb.append('\n');
    }

    for (int i = my_height - 1; i >= 0; i--) {
      sb.append(WALL);
      sb.append(getRowString(i));
      sb.append(WALL);
      sb.append('\n');
    }
    sb.append(CORNER);
    for (int i = 0; i < my_width; i++) {
      sb.append(FLOOR);
    }
    sb.append(CORNER);
    sb.append('\n');
    return sb.toString();
  }

  /**
   * Returns a string representing the blocks in the given row.
   * 
   * @param the_row The row to represent
   * @return The string representation
   */
  private String getRowString(final int the_row) {
    final StringBuilder sb = new StringBuilder();

    if (my_blocks.size() - 1 < the_row) {
      for (int col = 0; col < my_width; col++) {
        if (currentPieceAt(col, the_row)) {
          sb.append(CURRENT_PIECE);
        } else {
          sb.append(EMPTY);
        }
      }
    } else {
      final Block[] row_blocks = my_blocks.get(the_row);

      for (int i = 0; i < my_width; i++) {
        if (currentPieceAt(i, the_row)) {
          sb.append(CURRENT_PIECE);
        } else if (row_blocks[i] == Block.EMPTY) {
          sb.append(EMPTY);
        } else {
          sb.append(FROZEN);
        }
      }
    }
    return sb.toString();
  }
  /**
   * Returns the next piece that will be used on the tetris board.
   * 
   * @return Returns the next piece that will be used.
   */
  public Piece getNextPiece() {
    return my_next_piece;
  }
  /**
   * Returns the current piece that is being used on the tetris board.
   * 
   * @return the currently moving piece.
   */
  public Piece getCurrentPiece() {
    return ((AbstractPiece) my_current_piece).clone();
  }
  /**
   * Returns a copy of the frozen pieces on the current board.
   * 
   * @return Returns a list of frozen pieces.
   */
  public List<Block[]> getBlocks() {
    final List<Block[]> temp_list = new LinkedList<Block[]>();
    for (Block[] block : my_blocks) {
      temp_list.add(block.clone());
    }
    return temp_list;
  }
  /**
   * Drops instantly drops a piece straight down on the board. Part of this code was
   * copied from the frozen method and blockAt methods above.
   */
  public void drop() {
    final int[][] blocks = my_current_piece.getBoardCoordinates();
    boolean can_pass = true;
    while (can_pass) {
      final Piece temp = my_current_piece;
    // perform bounds checking on each block
      for (int i = 0; i < blocks.length; i++) {
      // Is this block at the bottom, or is there a piece below it?
        if (blocks[i][1] == 0 || // block is at the bottom
            blockAt(blocks[i][0], blocks[i][1] - 1) != Block.EMPTY) { // block below
                                                                    // is occupied
          can_pass = false;
          break; // can't move, no need to keep checking
        }
        moveDown();
        if (!my_current_piece.equals(temp)) {
          can_pass = false;
          break;
        }
      } 
    }
  }
  /**
   * Increments the level by 1.
   */
  public void levelUp() {
    my_holder.levelUp();
  }
  /**
   * This method resets all data fields so that the board is fresh for a new game.
   */
  public void newGame() {
    if (my_pieces.isEmpty()) {
      setupGame(my_width, my_height, null);
    } else {
      setupGame(my_width, my_height, my_pieces);
    }
  }
}
