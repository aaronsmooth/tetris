/*
 * Aaron Nelson
 * 
 * TCSS 305 - Spring 2012
 * Tetris Part 4
 */
package view;

import controller.Board;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Block;

/**
 * This class creates the view for the current board state.
 * 
 * @author Aaron Nelson
 * @version 5/25/2012
 */
public class GamingPanel extends JPanel implements Observer {
  /**
   * The pixel size for drawing the board.
   */
  private static final int PIXELSIZE = 20;
  /**
   * The number of extra rows there are on the board.
   */
  private static final int EXTRAROWS = 4;
  /**
   * The currently frozen blocks on a board.
   */
  private final Board my_board;
  
  /**
   * Constructs a gaming panel for drawing a tetris board.
   * 
   * @param the_board the current tetris game.
   */
  public GamingPanel(final Board the_board) {
    super();
    my_board = the_board;
    this.setBackground(Color.CYAN);
    
    
  }
  @Override
  public void update(final Observable the_source, final Object the_update) {
    repaint();
  }
  @Override
  public void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setColor(Color.RED);
    
    
    
    final List<Block[]> blocks = new LinkedList<Block[]>(my_board.getBlocks());
    final int[][] piece = my_board.getCurrentPiece().getBoardCoordinates();

    for (int single_block = 0; single_block < piece.length; single_block++) {
      g2d.fillRect(piece[single_block][0] * PIXELSIZE, (my_board.getHeight() - 
          piece[single_block][1]) * PIXELSIZE - (EXTRAROWS - 1) * PIXELSIZE + 
          (EXTRAROWS - 1) * PIXELSIZE, PIXELSIZE, PIXELSIZE);
      
    }
    
    for (int rows = 0; rows < blocks.size(); rows++) {
      final Block[] each_row = blocks.get(rows);
      for (int column = 0; column < each_row.length; column++) {
        if (!each_row[column].equals(Block.EMPTY)) {
          g2d.fillRect(column * PIXELSIZE, (my_board.getHeight() - rows + (EXTRAROWS - 1)) *
                       PIXELSIZE - (EXTRAROWS - 1) * PIXELSIZE, PIXELSIZE, PIXELSIZE);
        }
      }
    }
  }

}
