/* Project: Match 3 tile game 
*  CS106 Data Structures and Algorithms
*  Mariya Mushtaq, Isbah Ameer, Virginia Do 
*
* Class GameBoard that extends JFrame and is used to represent the GUI for the match 3 game. It generates a gameboard grid 
* of dimension 5 x 5 that is a representation of randomly populated buttons/tiles (here by colors). It also implements a nested 
* ButtonUI  class that extends the BasicButtonUI class and customizes the appearance of graphical elements. 
 */



import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Random;

/*
 * helper class GameButton that extends JButton
 */
class GameButton extends JButton {
    private Tile tile;
    public GameButton(Tile tile) {
        super();
        this.tile = tile;
    }
    public Tile getTile() {
        return tile;
    }
}

/*
 * GameBoard class that extends JFrame and implements customizations for the game board
 */
public class GameBoard extends JFrame {
    // attributes
    private ScoreBoard scoreBoard;
    private static final int rows = 5;
    private static final int columns = 5;
    private JPanel gamePanel;
    private static final Color[] colors = { Color.RED, Color.BLUE, Color.WHITE, Color.YELLOW };
    private Match3Game game;
    private Tile firstTile;
    private Tile secondTile;
    // constructor
    public GameBoard() {
        game = new Match3Game(rows, columns);
        scoreBoard = new ScoreBoard(game);
        // set the title
        setTitle("Match 3 Game");
        // set its dimensions
        setSize(1000, 1000);
        // set operations 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // generate a new JPanel and add it central to the frame
        gamePanel = new JPanel(new GridLayout(rows, columns));
        add(gamePanel, BorderLayout.CENTER);

        // in order to randomly assign colors to the buttons
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GameButton button = new GameButton(game.getGrid()[i][j]);
                button.addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent e) {
                        if (firstTile == null) {
                            firstTile = button.getTile();
                        } else {
                            secondTile = button.getTile();
                        }
                        if (firstTile != null && secondTile != null) {
                            game.swapTiles(firstTile, secondTile);
                            firstTile = null;
                            secondTile = null;
                            //now we finish swapping
                        } 
                    }
                });
                // customizations for the game grid
                button.setPreferredSize(new Dimension(60, 60));
                button.setBackground(colors[random.nextInt(colors.length)]);
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setBorderPainted(true);
                button.setBorder(new LineBorder(Color.BLACK, 3));
                gamePanel.add(button);
            }
        }
        // set grid visibility to true 
        setVisible(true);
    }

    // nested class to customize the appearance of buttons on the grid
    private static class ButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        private final Color fill;

        // ButtonUI constructor to create a new ButoonUI object
        public ButtonUI(Color fill) {
            this.fill = fill;
        }

        // creates and displays a new GameBoard
    public static void main(String[] args) {
        new GameBoard();
    }
}
}
