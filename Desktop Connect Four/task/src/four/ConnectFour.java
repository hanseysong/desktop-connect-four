package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.*;

public class ConnectFour extends JFrame implements ActionListener {

    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final Color BASELINE_COLOR = GRAY;
    private static final Color WINNING_COLOR = YELLOW;

    private JButton[][] board;
    private JButton resetButton;

    private int currentPlayer;
    private boolean gameFinished;

    public ConnectFour() {

        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the game board
        JPanel gameBoard = new JPanel(new GridLayout(ROWS, COLS));
        board = new JButton[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JButton cell = new JButton();
                char colName = (char) ('A' + col);
                String rowName = Integer.toString(ROWS - row);
                cell.setName("Button" + Character.toString(colName) + rowName);
                cell.setText(" ");
                cell.setBackground(BASELINE_COLOR);
                cell.addActionListener(this);

                board[row][col] = cell;
                gameBoard.add(cell);
            }
        }

        // Create the reset button
        resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.setEnabled(true);
        resetButton.addActionListener(this);

        // Add the components to the frame
        add(gameBoard, BorderLayout.CENTER);
        add(resetButton, BorderLayout.SOUTH);

        // Initialize game state
        currentPlayer = 1;
        gameFinished = false;

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            resetGame();
            return;
        }

        if (gameFinished) {
            return;
        }

        // Find the column of the button that was clicked
        JButton clickedButton = (JButton) e.getSource();
        System.out.println(clickedButton.getName());
        int col = -1;
        for (int i = 0; i < COLS; i++) {
            for (int j = ROWS - 1; j >= 0; j--) {
                if (board[j][i] == clickedButton) {
                    col = i;
                    break;
                }
            }
            if (col != -1) {
                break;
            }
        }
        if (col == -1) {
            return;
        }

        // Find the row to place the current player's token
        int row;
        for (row = ROWS - 1; row >= 0; row--) {
            if (board[row][col].getText() == " ") {
                break;
            }
        }
        if (row < 0) {
            return;
        }

        // Set the color of the cell to the current player's color
        Color playerColor = (currentPlayer == 1) ? RED : Color.BLUE;
        String playerMark = (currentPlayer == 1) ? "X" : "O";
//        board[row][col].setBackground(playerColor);
        board[row][col].setText(playerMark);

        // Check for a winning combination
        if (checkWin(row, col)) {
            markWin(row, col);
            gameFinished = true;
//            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            return;
        }

        // Switch to the other player's turn
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    private void resetGame() {
        // Clear the board and reset game state
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col].setBackground(BASELINE_COLOR);
                board[row][col].setText(" ");
            }
        }
        currentPlayer = 1;
        gameFinished = false;
    }

    private boolean checkWin(int row, int col) {
        String playerMark = (currentPlayer == 1) ? "X" : "O";
        // Check for a winning combination in the row, column, and diagonals
        Color playerColor = (currentPlayer == 1) ? RED : Color.BLUE;

        // Check row
        int count = 0;
        for (int i = 0; i < COLS; i++) {
            if (board[row][i].getText() == playerMark) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check column
        count = 0;
        for (int i = 0; i < ROWS; i++) {
            if (board[i][col].getText() == playerMark) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check diagonal (bottom-left to top-right)
        count = 0;
        int i = row;
        int j = col;
        while (i > 0 && j < board[0].length - 1) {
            i--;
            j++;
        }
        while (i < board.length && j >= 0) {
            if (board[i][j].getText() == playerMark) {
                count++;
            } else {
                count = 0;
            }
            if (count == 4) {
                return true;
            }
            i++;
            j--;
        }

        // Check diagonal (top-left to bottom-right)
        count = 0;
        i = row;
        j = col;
        while (i > 0 && j > 0) {
            i--;
            j--;
        }
        while (i < board.length && j < board[0].length) {
            if (board[i][j].getText() == playerMark) {
                count++;
            } else {
                count = 0;
            }
            if (count == 4) {
                return true;
            }
            i++;
            j++;
        }

        return false;
    }

    private void markWin(int row, int col) {
        String playerMark = (currentPlayer == 1) ? "X" : "O";
        // Change the color of the four winning cells
        Color playerColor = (currentPlayer == 1) ? RED : Color.BLUE;

        // Check row

        int count = 0;
        for (int i = 0; i < COLS; i++) {
            if (board[row][i].getText() == playerMark) {
                count++;
                if (count == 4) {
                    for (int j = i - 3; j <= i; j++) {
                        board[row][j].setBackground(YELLOW);
                    }
                    return;
                }
            } else {
                count = 0;
            }
        }

        // Check column
        count = 0;
        for (int i = 0; i < ROWS; i++) {
            if (board[i][col].getText() == playerMark) {
                count++;
                if (count == 4) {
                    for (int j = i - 3; j <= i; j++) {
                        board[j][col].setBackground(YELLOW);
                    }
                    return;
                }
            } else {
                count = 0;
            }
        }

        // Check diagonal (bottom-left to top-right)
        int startRow = Math.max(row - 3, 0);
        int startCol = Math.max(col - 3, 0);
        int endRow = Math.min(row + 3, ROWS - 1);
        int endCol = Math.min(col + 3, COLS - 1);
        count = 0;
        for (int i = 0; i < endRow - startRow + 1; i++) {
            int r = startRow + i;
            int c = startCol + i;
            if (board[r][c].getText() == playerMark) {
                count++;
                if (count == 4) {
                    for (int j = 0; j < 4; j++) {
                        board[startRow + j][startCol + j].setBackground(YELLOW);
                    }
                    return;
                }
            } else {
                count = 0;
            }
        }

        // Check diagonal (top-left to bottom-right)
        startRow = Math.min(row + 3, ROWS - 1);
        startCol = Math.max(col - 3, 0);
        endRow = Math.max(row - 3, 0);
        endCol = Math.min(col + 3, COLS - 1);
        count = 0;
        for (int i = 0; i < startRow - endRow + 1; i++) {
            int r = startRow - i;
            int c = startCol + i;
            if (board[r][c].getText() == playerMark) {
                count++;
                if (count == 4) {
                    for (int j = 0; j < 4; j++) {
                        board[startRow - j][startCol + j].setBackground(YELLOW);
                    }
                    return;
                }
            } else {
                count = 0;
            }
        }
    }


}
