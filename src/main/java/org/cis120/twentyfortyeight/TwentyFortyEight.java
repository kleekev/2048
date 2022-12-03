package org.cis120.twentyfortyeight;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * This class is a model for TicTacToe.
 *
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 *
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 *
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class TwentyFortyEight {

    private int[][] board;
    private int score;
    private int highest;
    private int[][] previous;
    private LinkedList<int[][]> boardList;
    private LinkedList<Integer> scoreList;

    /**
     * Constructor sets up game state.
     */
    public TwentyFortyEight() {
        reset();
    }

    /**
     * Moves all the tiles to the left
     */
    public void leftTurn() {
        boolean moved = false;
        // Checks through the array from top to bottom, left to right
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                boolean add = false;
                int curr = board[row][col];
                previous[row][col] = curr;
                int next = col + 1;
                int nonempty = 0;
                while (next < 4 && curr > 0) {
                    // if the current tile is equal to the next tile add them together
                    // to the first tile but make the next tile 0.
                    if (curr == board[row][next]) {
                        for (int i = col + 1; i < next; i++) {
                            if (board[row][i] != 0) {
                                nonempty++;
                            }
                        }
                        // Checks to make sure there is no tiles in between
                        if (nonempty == 0) {
                            board[row][col] = 2 * curr;
                            score += 2 * curr;
                            board[row][next] = 0;
                            add = true;
                            if (board[row][col] > highest) {
                                highest = board[row][col];
                            }
                        }
                    }
                    next++;
                    nonempty = 0;
                    if (add) {
                        break;
                    }
                }

            }
        }
        // Moves everything to the left
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 0) {
                    int next = col + 1;
                    int value = 0;
                    while (value == 0 && next < 4) {
                        value = board[row][next];
                        if (value != 0) {
                            board[row][col] = value;
                            board[row][next] = 0;
                        }
                        next++;
                    }
                }
            }
        }
        if (!sameMatrix(previous, board)) {
            moved = true;
        }
        // If the move did succeed, add a random tile
        if (moved) {
            addRandom();
        }
    }

    /**
     * Moves the tiles to the left
     */
    public void left() {
        reverseMatrix();
        reverseMatrix();
        leftTurn();
    }

    /**
     * Moves the tiles to the right by reversing the matrix using left then
     * reversing it again
     */

    public void right() {
        reverseMatrix();
        leftTurn();
        reverseMatrix();
    }

    /**
     * Moves the tiles down by rotating the matrix clockwise, using left then
     * using moving it clockwise 3 more times
     */
    public void down() {
        turnMatrixCW();
        leftTurn();
        turnMatrixCW();
        turnMatrixCW();
        turnMatrixCW();
    }

    /**
     * Moves the tiles up by rotating the matrix clockwise 3 times, then using
     * left and then rotating it one more time clockwise
     */
    public void up() {
        turnMatrixCW();
        turnMatrixCW();
        turnMatrixCW();
        leftTurn();
        turnMatrixCW();
    }

    /**
     * Reverse the order of the matrix
     */
    private void reverseMatrix() {
        int[][] newBoard = new int[4][4];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                newBoard[3 - row][3 - col] = board[row][col];
            }
        }
        board = newBoard;
    }

    /**
     * Rotates the matrix 90 degrees clockwise
     */

    private void turnMatrixCW() {
        int[][] newBoard = new int[4][4];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                newBoard[col][4 - row - 1] = board[row][col];
            }
        }
        board = newBoard;
    }

    /**
     * Checks the first and second matrix to see if they have the
     * same values in each cell
     * 
     * @param first  first matrix
     * @param second second matrix
     * @return boolean whether they have the same values
     */

    private boolean sameMatrix(int[][] first, int[][] second) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (first[row][col] != second[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds a random tile on the board where there is a 90% chance for it
     * to be a 2 and a 10% chance for a 4
     */

    public void addRandom() {
        int row = new Random().nextInt(4);
        int col = new Random().nextInt(4);
        while (board[row][col] != 0) {
            row = new Random().nextInt(4);
            col = new Random().nextInt(4);
        }
        int value = new Random().nextInt(100);
        if (value > 10) {
            board[row][col] = 2;
        } else {
            board[row][col] = 4;
        }
    }

    /**
     * Checks the borders and all the tiles to see if the neighbors are equal
     * if they are not equal it gets counted. If the total count is 16 then
     * the tiles have nowhere to move thus game over.
     * 
     * @return boolean
     */
    public boolean isGameOver() {
        int count = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int tile = board[row][col];
                if (tile > 0) {
                    if (row == 0 && col == 0) {
                        if (tile != board[row + 1][col] && tile != board[row][col + 1]) {
                            count++;
                        }
                    } else if (row == 0 && col == 3) {
                        if (tile != board[row + 1][col] && tile != board[row][col - 1]) {
                            count++;
                        }
                    } else if (row == 3 && col == 3) {
                        if (tile != board[row - 1][col] && tile != board[row][col - 1]) {
                            count++;
                        }
                    } else if (row == 3 && col == 0) {
                        if (tile != board[row - 1][col] && tile != board[row][col + 1]) {
                            count++;
                        }
                    } else if (row == 0 && (col == 1 || col == 2)) {
                        if (tile != board[row + 1][col] &&
                                tile != board[row][col - 1] &&
                                tile != board[row][col + 1]) {
                            count++;
                        }
                    } else if (row == 3 && (col == 1 || col == 2)) {
                        if (tile != board[row - 1][col] &&
                                tile != board[row][col - 1] &&
                                tile != board[row][col + 1]) {
                            count++;
                        }
                    } else if (col == 0 && (row == 1 || row == 2)) {
                        if (tile != board[row][col + 1] &&
                                tile != board[row - 1][col] &&
                                tile != board[row + 1][col]) {
                            count++;
                        }
                    } else if (col == 3 && (row == 1 || row == 2)) {
                        if (tile != board[row][col - 1] &&
                                tile != board[row - 1][col] &&
                                tile != board[row + 1][col]) {
                            count++;
                        }
                    } else {
                        if (tile != board[row - 1][col] &&
                                tile != board[row + 1][col] &&
                                tile != board[row][col - 1] &&
                                tile != board[row][col + 1]) {
                            count++;
                        }
                    }
                }
            }
        }
        return count == 16;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 3) {
                    System.out.print(" | ");
                }
            }
            if (i < 3) {
                System.out.println("\n---------");
            }
        }
    }

    /**
     * @return the current score
     */

    public int getScore() {
        return score;
    }

    /**
     * @return the current board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new int[4][4];
        score = 0;
        highest = 0;
        previous = new int[4][4];
        boardList = new LinkedList<>();
        scoreList = new LinkedList<>();
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the number the tile is
     */
    public int getCell(int c, int r) {
        return board[r][c];
    }

    /**
     * @return the previous board before the move
     */
    public int[][] previousBoard() {
        if (boardList.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return boardList.removeLast();
        }
    }

    /**
     * Adds the current score into the linked list of scores
     * 
     * @param number integer that is the current score
     */

    public void addScore(int number) {
        scoreList.add(number);
    }

    /**
     * get the previous score before the move
     * 
     * @return integer score
     */
    public int getPreviousScore() {
        return scoreList.removeLast();
    }

    /**
     * @return integer that denotes the highest tile
     */
    public int getHighest() {
        return highest;
    }

    /**
     * Replaces the current board with a new board
     * 
     * @param newBoard
     */

    public void setBoard(int[][] newBoard) {
        board = newBoard;
    }

    /**
     * Adds a board into the linked list
     * 
     * @param newBoard
     */
    public void addBoard(int[][] newBoard) {
        boardList.add(newBoard);
    }

    /**
     * Sets the current score
     * 
     * @param number
     */
    public void setScore(int number) {
        score = number;
    }

    /**
     * Puts an integer into board with row and column.
     * 
     * @param tile
     * @param row
     * @param col
     */

    public void put(int tile, int row, int col) {
        board[row][col] = tile;
    }

    /**
     * Saves the game variables into a file through a stream.
     * 
     * @param file
     * @param label
     */

    public void saveGame(File file, JLabel label) {
        try {
            FileOutputStream filestream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(filestream);
            objectStream.writeObject(board);
            objectStream.writeObject(highest);
            objectStream.writeObject(score);
            objectStream.writeObject(boardList);
            objectStream.writeObject(scoreList);

            objectStream.close();
            filestream.close();

        } catch (Exception e) {
            JLabel error = new JLabel("Cannot Save");
            label.add(error);
        }
    }

    /**
     * Loads the game and updates each variable game state into the one
     * from the file
     * 
     * @param file
     * @param label
     */
    public void load(File file, JLabel label) {
        try {
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            board = (int[][]) objectStream.readObject();
            highest = (int) objectStream.readObject();
            score = (int) objectStream.readObject();
            boardList = (LinkedList<int[][]>) objectStream.readObject();
            scoreList = (LinkedList<Integer>) objectStream.readObject();

            objectStream.close();
            fileStream.close();
        } catch (Exception e) {
            JLabel error = new JLabel("Cannot Load");
            label.add(error);
        }
    }

}
