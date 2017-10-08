/*
 * Aaron Nelson
 * 
 * TCSS 305 - Spring 2012
 * Tetris Part 4
 */
package view;

/**
 * This initiates the gui for a tetris game.
 * 
 * @author Aaron Nelson
 * @version 5/25/2012
 */
public final class TetrisGUIMain {
  /**
   * Prevents creating an object of type TetrisGUIMain.
   */
  private TetrisGUIMain() {
    //throw new NullPointerException();
  }
  /**
   * The main method, invokes the Tetris GUI.
   * Command line arguments are ignored.
   * 
   * @param the_args Command line arguments.
   */
  public static void main(final String[] the_args) {
    final TetrisGUI gui = new TetrisGUI();
    gui.start();
  }
}
