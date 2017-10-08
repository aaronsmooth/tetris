/*
 * Aaron Nelson
 * 
 * TCSS 305 - Spring 2012
 * Tetris Part 4
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Piece;

/**
 * This class creates a panel for displaying a preview of the next tetris piece.
 * 
 * @author Aaron Nelson
 * @version 5/25/2012
 */

public class PiecePreviewPanel extends JPanel implements Observer {
  /**
   * The dimension for the preview panel.
   */
  private static final Dimension MYSIZE = new Dimension(200, 100);
  /**
   * The size the color pixels.
   */
  private static final int PIXELSIZE = 20;
  /**
   * The next piece for the board that is being previewed.
   */
  private Piece my_piece;
  /**
   * Constructs the panel to display the next piece as a preview.
   * 
   * @param the_piece The next piece to be used by the board.
   */
  public PiecePreviewPanel(final Piece the_piece) {
    super();
    my_piece = the_piece;
    setPreferredSize(MYSIZE);
    this.setBackground(Color.WHITE);
  }
  @Override
  public void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setColor(Color.BLUE);
    
    final int[][] block_locations = my_piece.getBlockLocations();
    for (int pair = 0; pair < block_locations.length; pair++) {
      g2d.fillRect(block_locations[pair][0] * PIXELSIZE + MYSIZE.width / 2 - PIXELSIZE, 
                   (MYSIZE.height - block_locations[pair][1] * PIXELSIZE / 2) - 
                   (block_locations[pair][1] * PIXELSIZE / 2), PIXELSIZE, PIXELSIZE);
    }
  }
  @Override
  public void update(final Observable the_source, final Object the_data) {
    if (the_data instanceof GameInformationHolder && ((GameInformationHolder) 
        the_data).isGameOver()) {
      my_piece = ((GameInformationHolder) the_data).getPiece();
      repaint();
    }
    
  }
}
