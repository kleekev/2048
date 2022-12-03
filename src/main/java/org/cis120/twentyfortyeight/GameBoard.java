package org.cis120.twentyfortyeight;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.NoSuchElementException;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private TwentyFortyEight ttt; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    // Color for each number
    final Color[] colorTable = {
        new Color(0xFFE4C3), new Color(0xfff4d3),
        new Color(0xffdac3), new Color(0xe7b08e),
        new Color(0xe7bf8e), new Color(0xffc4c3),
        new Color(0xE7948e), new Color(0xbe7e56),
        new Color(0xbe5e56), new Color(0x9c3931),
        new Color(0x701710) };

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ttt = new TwentyFortyEight(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                        ttt.addScore(ttt.getScore());
                        ttt.addBoard(ttt.getBoard());
                        ttt.right();
                        break;
                    case KeyEvent.VK_LEFT:
                        ttt.addScore(ttt.getScore());
                        ttt.addBoard(ttt.getBoard());
                        ttt.up();
                        break;
                    case KeyEvent.VK_RIGHT:
                        ttt.addScore(ttt.getScore());
                        ttt.addBoard(ttt.getBoard());
                        ttt.down();
                        break;
                    case KeyEvent.VK_UP:
                        ttt.addScore(ttt.getScore());
                        ttt.addBoard(ttt.getBoard());
                        ttt.left();
                        break;
                }
                updateStatus();
                repaint();
            }
        });

    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ttt.reset();
        ttt.addRandom();
        ttt.addRandom();
        ttt.addBoard(ttt.getBoard());
        repaint();
        status.setText("Score: 0");

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void undo() {
        try {
            int[][] previousBoard = ttt.previousBoard();
            ttt.setBoard(previousBoard);
            repaint();
            requestFocusInWindow();
            ttt.setScore(ttt.getPreviousScore());
            status.setText("Score:" + ttt.getScore());
        } catch (NoSuchElementException e) {
            requestFocusInWindow();
        }

    }

    public void save() {
        ttt.saveGame(new File("save.txt"), status);
        requestFocusInWindow();
    }

    public void load() {
        ttt.load(new File("save.txt"), status);
        requestFocusInWindow();
        repaint();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        int score = ttt.getScore();
        status.setText("Score:" + score);
    }

    public void drawTiles(Graphics g) {
        int[][] board = ttt.getBoard();
        g.drawLine(100, 0, 100, 400);
        g.drawLine(200, 0, 200, 400);
        g.drawLine(300, 0, 300, 400);
        g.drawLine(0, 100, 400, 100);
        g.drawLine(0, 200, 400, 200);
        g.drawLine(0, 300, 400, 300);
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int number = board[row][col];
                int colorNumber = 0;
                if (number != 0) {
                    while (number != 1) {
                        number /= 2;
                        colorNumber++;
                    }
                    g.setColor(colorTable[colorNumber - 1]);
                    g.fillRect(row * 100, col * 100, 100, 100);
                    String s = String.valueOf(board[row][col]);
                    g.setColor(Color.BLACK);
                    g.drawString(s, row * 100 + 50, col * 100 + 50);
                }
            }
        }
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        if (ttt.isGameOver()) {
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.drawString("Game Over! Reset To Play Again!", 50, 200);
        } else if (ttt.getHighest() == 2048) {
            g.drawString("You Win!", 150, 200);
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
