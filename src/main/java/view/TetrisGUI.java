/*
 * Aaron Nelson
 * 
 * TCSS 305 - Spring 2012
 * Tetris Part 4
 */
package view;

import controller.Board;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import java.awt.Toolkit;

/**
 * This class creates the GUI for a Tetris game.
 * 
 * @author Aaron Nelson
 * @version 5/22/2012
 */
public class TetrisGUI extends JFrame implements Observer {
  /**
   * The initial timer delay time.
   */
  private static final int DELAY = 1000;
  /**
   * The timer delay change when leveling up.
   */
  private static final int DELAYCHANGE = 100;
  /**
   * The pixel size of the game being played.
   */
  private static final int PIXELSIZE = 20;
  /**
   * The number of extra rows there are at the top of the board.
   */
  private static final int EXTRAROWS = 4;
  /**
   * The string showing the controls of the game.
   */
  private static final String CONTROLS = "Move left = a | Move Right = s " +
      "| Rotate = w | Move Down = z | Drop Current Piece = v";
  /**
   * The name of the options that are available in the menu.
   */
  private static final String[] MENUNAMES = {"New Game", "Pause Game", "End Game"};
  /**
   * This field holds the current state of the tetris board.
   */
  private final Board my_board;
  /**
   * The timer for controlling events in the tetris game.
   */
  private final Timer my_timer;
  /**
   * This field holds the preview piece.
   */
  private final PiecePreviewPanel my_piece_preview;
  /**
   * This field holds the current tetris games graphical representation.
   */
  private final GamingPanel my_current_game;
  /**
   * This frame holds current score and level information to display in gui.
   */
  private final ScorePanel my_stats;
  /**
   * The menu at the top of the game.
   */
  private final JMenuBar my_menu;
  /**
   * The button for starting a new game.
   */
  private final JButton my_new_game = new JButton(MENUNAMES[0]);
  /**
   * The current game over status. False means the game is over.
   */
  private boolean my_game_over;
  /**
   * The current level of the game.
   */
  private int my_level;
  /**
   * This constructs the gui for a tetris game.
   */
  public TetrisGUI() {
    super();
    my_board = new Board();
    my_stats = new ScorePanel();
    my_timer = new Timer(DELAY, new TetrisPieceListener());
    my_piece_preview = new PiecePreviewPanel(my_board.getNextPiece());
    my_current_game = new GamingPanel(my_board);
    my_level = 1;
    addTheObservers();
    my_game_over = true;
    my_menu = new JMenuBar();
  }
  /**
   * Adds all the observers to the board.
   */
  private void addTheObservers() {
    my_board.addObserver(this);
    my_board.addObserver(my_piece_preview);
    my_board.addObserver(my_current_game);
    my_board.addObserver(my_stats);
  }
  @Override
  public void update(final Observable the_souce, final Object the_update) {
    if (the_update instanceof GameInformationHolder) {
      my_game_over = ((GameInformationHolder) the_update).isGameOver();
      if (((GameInformationHolder) the_update).getLevel() != my_level) {
        if (my_level < (DELAY / DELAYCHANGE) - 1) {
          my_level = ((GameInformationHolder) the_update).getLevel();
          my_timer.setDelay(my_timer.getDelay() - DELAYCHANGE);
        } else {
          my_level = DELAY / DELAYCHANGE;
          my_timer.setDelay(DELAY / DELAY);
        }
      }
    }
    if (the_update instanceof GameInformationHolder && 
        !((GameInformationHolder) the_update).isGameOver()) {
      gameOver();
    }
    
  }
  /**
   * This method initiates a timer and the background of the tetris board.
   */
  public void start() {
    compileMenu();
    final JLabel controls = new JLabel(CONTROLS);
    
    
    
    //setPreferredSize(new Dimension((my_piece_preview.getWidth() + my_stats.getWidth() + my_board.getWidth()) * PIXELSIZE,
	//(my_board.getHeight() * PIXELSIZE) + PIXELSIZE * EXTRAROWS + PIXELSIZE));
    
    //Center the board on the screen
    
    
    this.setFocusable(true);
    my_current_game.setVisible(true);
    add(my_menu, BorderLayout.NORTH);
    addKeyListener(new TetrisKeyListener());
    add(controls, BorderLayout.SOUTH);
    JPanel westPanel = new JPanel();
    westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
    westPanel.add(my_stats);
    westPanel.add(my_piece_preview);
    add(westPanel, BorderLayout.WEST);
    add(my_current_game, BorderLayout.CENTER);
    
  //Control the sizing of the JFrame
    setPreferredSize(new Dimension((my_board.getWidth() * PIXELSIZE * EXTRAROWS) + 
        PIXELSIZE, (my_board.getHeight() * PIXELSIZE) + PIXELSIZE * EXTRAROWS + PIXELSIZE));
    
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(dim.width/2-(EXTRAROWS * PIXELSIZE * my_board.getWidth()/2), 
    		dim.height/2-(PIXELSIZE * my_board.getHeight()/2));
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    
    setResizable(false);
    //Allow the board to become visible
    setVisible(true);
    JOptionPane.showMessageDialog(null, CONTROLS);
    my_timer.start();
  }
  /**
   * This method stops the timer when a game is over.
   */
  private void gameOver() {
    my_timer.stop();
    JOptionPane.showMessageDialog(null, "Game Over");
  }
  /**
   * Creates the menu of options for the tetris game.
   */
  private void compileMenu() {
    final JMenu mainmenu = new JMenu("Options");
    my_new_game.setName(MENUNAMES[0]);
    my_new_game.addActionListener(new GameAction());
    my_new_game.setEnabled(false);
    mainmenu.add(my_new_game);
    final JButton endgame = new JButton(MENUNAMES[2]);
    endgame.setName(MENUNAMES[2]);
    endgame.addActionListener(new GameAction());
    mainmenu.add(endgame);
    final JToggleButton pausegame = new JToggleButton(MENUNAMES[1]);
    pausegame.addActionListener(new PauseAction());
    mainmenu.add(pausegame);
    my_menu.add(mainmenu);
  }
  /**
   * This method hides the current game for pausing purposes.
   */
  private void hidegame() {
    if (my_current_game.isVisible()) {
      my_current_game.setVisible(false);
      
    } else {
      my_current_game.setVisible(true);
    }
  }
  /**
   * This class performs the action of stepping the pieces while a game is going.
   * 
   * @author Aaron Nelson
   * @version 5/25/2012
   */
  private class TetrisPieceListener implements ActionListener {

    @Override
    public void actionPerformed(final ActionEvent the_event) {
      if (my_game_over) {
        my_board.step();
      }
    }
  }
  /**
   * This class listens for key events for controlling the tetris piece currently
   * moving.
   * 
   * @author Aaron Nelson
   * @version 05/25/2012
   */
  private class TetrisKeyListener extends KeyAdapter {
    /**
     * This method controls what happens when certain keys are typed.
     * 
     * @param the_event the event triggered by a key being typed.
     */
    public void keyTyped(final KeyEvent the_event) {
      if (my_current_game.isVisible() && my_game_over) {
        if (the_event.getKeyChar() == 'a') {
          my_board.moveLeft();
        } else if (the_event.getKeyChar() == 's') {
          my_board.moveRight();
        } else if (the_event.getKeyChar() == 'w') {
          my_board.rotate();
        } else if (the_event.getKeyChar() == 'z') {
          my_board.moveDown();
        } else if (the_event.getKeyChar() == 'v') {
          my_board.drop();
        }
      }
    }
  }
  /**
   * This class is used for monitoring mouse clicks on the pause option in a game of tetris.
   * 
   * @author Aaron Nelson
   * @version 6/1/2012
   */
  private class PauseAction extends MouseAdapter implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent the_event) {
      if (the_event.getSource() instanceof JToggleButton) {
        if (((JToggleButton) the_event.getSource()).isSelected()) {
          my_timer.stop();
          hidegame();
        } else {
          my_timer.start();
          hidegame();
        }
      }
    }
  }
  /**
   * This class is used for monitoring mouse clicks on the new game and end game buttons in
   * the tetris gui.
   * 
   * @author Aaron Nelson
   * @version 6/1/2012
   */
  private class GameAction extends MouseAdapter implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent the_event) {
      if (the_event.getSource() instanceof JButton) {
        if (((JButton) the_event.getSource()).getName().equals(MENUNAMES[2])) {
          my_timer.stop();
          my_new_game.setEnabled(true);
          my_game_over = false;
          gameOver();
        } else if (((JButton) the_event.getSource()).getName().equals(MENUNAMES[0])) {
          my_new_game.setEnabled(false);
          my_board.newGame();
          my_timer.start();
        }
      }
    }  
  }
}
