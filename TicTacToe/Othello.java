package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Othello extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int CELL_SIZE = 60;
    private Seed[][] board;
    private Seed currentPlayer;
    private JLabel statusBar;
    private Image blackPieceImage;
    private Image whitePieceImage;

    enum Seed {
        EMPTY, BLACK, WHITE
    }

    public Othello() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE + 30));
        board = new Seed[ROWS][COLS];
        initGame();

        // Load images using ClassLoader with correct paths
        blackPieceImage = new ImageIcon(getClass().getResource("/TicTacToe/RAPIP.png")).getImage();
        whitePieceImage = new ImageIcon(getClass().getResource("/TicTacToe/WAHYU.png")).getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int colSelected = e.getX() / CELL_SIZE;
                int rowSelected = e.getY() / CELL_SIZE;
                if (isValidMove(currentPlayer, rowSelected, colSelected)) {
                    makeMove(currentPlayer, rowSelected, colSelected);
                    currentPlayer = (currentPlayer == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
                    repaint();
                    if (isGameOver()) {
                        declareWinner();
                    }
                }
            }
        });

        statusBar = new JLabel("Black's Turn");
        statusBar.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
        statusBar.setBackground(new Color(216, 216, 216));
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.PAGE_END);
    }

    private void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Seed.EMPTY;
            }
        }
        board[3][3] = Seed.WHITE;
        board[3][4] = Seed.BLACK;
        board[4][3] = Seed.BLACK;
        board[4][4] = Seed.WHITE;
        currentPlayer = Seed.BLACK;
    }

    private boolean isValidMove(Seed seed, int row, int col) {
        if (board[row][col] != Seed.EMPTY) return false;
        return canFlip(seed, row, col);
    }

    private boolean canFlip(Seed seed, int row, int col) {
        Seed opponentSeed = (seed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : directions) {
            int r = row + dir[0], c = col + dir[1];
            boolean hasOpponentSeed = false;
            while (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == opponentSeed) {
                r += dir[0];
                c += dir[1];
                hasOpponentSeed = true;
            }
            if (hasOpponentSeed && r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == seed) {
                return true;
            }
        }
        return false;
    }

    private void makeMove(Seed seed, int row, int col) {
        board[row][col] = seed;
        flipSeeds(seed, row, col);
    }

    private void flipSeeds(Seed seed, int row, int col) {
        Seed opponentSeed = (seed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : directions) {
            int r = row + dir[0], c = col + dir[1];
            boolean hasOpponentSeed = false;
            while (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == opponentSeed) {
                r += dir[0];
                c += dir[1];
                hasOpponentSeed = true;
            }
            if (hasOpponentSeed && r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == seed) {
                r = row + dir[0];
                c = col + dir[1];
                while (board[r][c] == opponentSeed) {
                    board[r][c] = seed;
                    r += dir[0];
                    c += dir[1];
                }
            }
        }
    }

    private boolean isGameOver() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Seed.EMPTY && (isValidMove(Seed.BLACK, row, col) || isValidMove(Seed.WHITE, row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void declareWinner() {
        int blackCount = 0, whiteCount = 0;
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Seed.BLACK) blackCount++;
                if (board[row][col] == Seed.WHITE) whiteCount++;
            }
        }
        String winner = (blackCount > whiteCount) ? "Black wins!" : (whiteCount > blackCount) ? "White wins!" : "It's a draw!";
        JOptionPane.showMessageDialog(this, winner);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                if (board[row][col] == Seed.BLACK) {
                    g.drawImage(blackPieceImage, x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10, this);
                } else if (board[row][col] == Seed.WHITE) {
                    g.drawImage(whitePieceImage, x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10, this);
                }
            }
        }
        statusBar.setText((currentPlayer == Seed.BLACK) ? "Black's Turn" : "White's Turn");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Othello");
        Othello gamePanel = new Othello();
        frame.setContentPane(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}