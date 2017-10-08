/*
 * Aaron Nelson
 * 
 * TCSS 305 - Spring 2012
 * Tetris Part 4
 */
package view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This class holds score and level information to display in a gui.
 * 
 * @author Aaron Nelson
 * @version 6/1/2012
 */
public class ScorePanel extends JPanel implements Observer {
  /**
   * The text that always will be displayed in the score label.
   */
  private static final String SCORE = "Current Score: ";
  /**
   * The text that always will be displayed in the level label.
   */
  private static final String LEVEL = "Current Level: ";
  /**
   * The text that always will be displayed for the number of rows that need
   * to be cleared before the next level.
   */
  private static final String NEXTLEVEL = "Level increase occurs after 10 lines are cleared." +
      " Lines cleared count: ";
  /**
   * The initial number of lines that must be cleared before a level increase.
   */
  private static final int INITIALCOUNT = 10;
  /**
   * The label that will display the current score.
   */
  private final JLabel my_score_label = new JLabel();
  /**
   * The label that will display the current level.
   */
  private final JLabel my_level_label = new JLabel();
  /**
   * The label that will display the amount of rows that need to be cleared until
   * the next level.
   */
  private final JLabel my_next_level = new JLabel();
  /**
   * Constructs the panel to display score and level information.
   */
  public ScorePanel() {
    super();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    my_score_label.setText(SCORE + "0");
    my_level_label.setText(LEVEL + "1");
    my_next_level.setText(NEXTLEVEL + INITIALCOUNT);
    addToPanel();
  }
  /**
   * Adds all buttons and text fields to the current panel.
   */
  private void addToPanel() {
    add(my_score_label);
    add(my_level_label);
    add(my_next_level);
  }
  @Override
  public void update(final Observable the_source, final Object the_update) {
    if (the_update instanceof GameInformationHolder) {
      my_score_label.setText(SCORE + ((GameInformationHolder) the_update).getScore());
      my_level_label.setText(LEVEL + ((GameInformationHolder) the_update).getLevel());
      my_next_level.setText(NEXTLEVEL + (INITIALCOUNT - ((GameInformationHolder) 
          the_update).getLineCount()));
      repaint();
    }
  }
}
