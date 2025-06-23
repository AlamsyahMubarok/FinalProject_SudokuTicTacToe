package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80);
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private AIPlayer aiPlayer;
    private boolean vsCpu;

    public TicTacToe(boolean vsCpu) {
        this.vsCpu = vsCpu;
        initGame();
        newGame();

        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                        if (vsCpu && currentPlayer == aiPlayer.mySeed && currentState == State.PLAYING) {
                            int[] move = aiPlayer.move();
                            currentState = board.stepGame(aiPlayer.mySeed, move[0], move[1]);
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        }
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(600, 30)); // Adjusted width to match frame
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END);
        super.setPreferredSize(new Dimension(600, 570)); // Adjusted to fit the frame size
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));
    }

    public void initGame() {
        board = new Board();
        if (vsCpu) {
            aiPlayer = new AIPlayerSimple(board);
            aiPlayer.setSeed(Seed.NOUGHT); // AI plays as NOUGHT
        }
    }

    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        board.paint(g);

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
            showWinDialog((currentState == State.CROSS_WON) ? "'Miya' Won!" : "'Zilong' Won!");
        }
    }

    private void showWinDialog(String message) {
        // Create a custom JDialog
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Game Over", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        // Create a panel with a blue background
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180)); // Blue background
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add message label
        JLabel messageLabel = new JLabel(message + " Would you like to start a new game or quit?");
        messageLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        messageLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        // Create buttons with custom styling
        JButton newGameButton = createStyledButton("New Game");
        newGameButton.addActionListener(e -> {
            newGame();
            dialog.dispose();
        });

        JButton quitButton = createStyledButton("Quit");
        quitButton.addActionListener(e -> {
                    dialog.dispose();
                    ;//dispose sebelum di exit
                    System.exit(0);
                });

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(newGameButton, gbc);

        gbc.gridx = 1;
        panel.add(quitButton, gbc);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 50)); // Adjust size as needed
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); // Comic Sans MS font
        button.setBackground(new Color(255, 165, 0)); // Orange background
        button.setForeground(Color.BLACK); // Black text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
        return button;
    }
}