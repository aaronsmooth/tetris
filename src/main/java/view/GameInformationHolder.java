/*
 * Aaron Nelson
 * 
 * TCSS 305 - Spring 2012
 * Tetris Project
 */
package view;

import model.AbstractPiece;
import model.Piece;

/**
 * This class is used to hold information about a game.
 * 
 * @author Aaron Nelson
 * @version 6/1/2012
 */
public class GameInformationHolder {
  /**
   * The number of lines it takes to clear to go up a level.
   */
  private static final int LINENUMBER = 10;
  /**
   * The increment amount of the score for each line cleared.
   */
  private static final int SCORE = 100;
  /**
   * This field holds the current score of a tetris game.
   */
  private int my_score;
  /**
   * This field holds the current gamve over status. True = game still going, False = game 
   * over.
   */
  private boolean my_game_over_status;
  /**
   * This field holds the current difficulty level.
   */
  private int my_level;
  /**
   * This field holds the next piece that will be used on a tetris board.
   */
  private Piece my_piece;
  /**
   * The current lines cleared count. It resets at ten.
   */
  private int my_line_count;
  /**
   * Constructs a game information holder.
   * 
   * @param the_piece The next piece that will be used on a tetris board.
   */
  public GameInformationHolder(final Piece the_piece) {
    my_score = 0;
    my_game_over_status = true;
    my_level = 1;
    my_piece = the_piece;
    my_line_count = 0;
  }
  /**
   * Gets the current line count.
   * 
   * @return returns the number of lines cleared so far.
   */
  public int getLineCount() {
    return my_line_count;
  }
  /**
   * Increments the line count.
   */
  public void updateLineCount() {
    my_line_count = my_line_count++ % LINENUMBER;
    updateScore(SCORE);
  }
  /**
   * Updates the piece type that is the next piece if it is different than the current one.
   * 
   * @param the_piece The next piece that will be used.
   */
  public void updatePiece(final Piece the_piece) {
    if (!the_piece.equals(my_piece)) {
      my_piece = the_piece;
    }
  }
  /**
   * Returns the next piece to be used by a board.
   * 
   * @return The next piece used on a board.
   */
  public Piece getPiece() {
    
    return ((AbstractPiece) my_piece).clone();
  }
  /**
   * Increments the level by 1.
   */
  public void levelUp() {
    my_level++;
  }
  /**
   * Ends the game by setting status to false.
   */
  public void endGame() {
    my_game_over_status = false;
  }
  /**
   * Increases the current score.
   * 
   * @param the_amount The amount to add to the current score.
   */
  public void updateScore(final int the_amount) {
    my_score = my_score + the_amount;
    if (my_level < LINENUMBER - 1) {
      my_level++;
    } else {
      my_level = LINENUMBER;
    }
    my_line_count = (my_line_count + 1) % LINENUMBER;
  }
  /**
   * Retrieves the current score.
   * 
   * @return The current score value.
   */
  public int getScore() {
    return my_score;
  }
  /**
   * Retrieves the current game over status.
   * 
   * @return The current game over value.
   */
  public boolean isGameOver() {
    return my_game_over_status;
  }
  /**
   * Retrieves the current games difficulty level.
   * 
   * @return The current level of difficulty.
   */
  public int getLevel() {
    return my_level;
  }
}
