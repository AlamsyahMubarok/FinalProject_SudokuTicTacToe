package TicTacToe;

import java.awt.*;
import javax.swing.*;

public class Board {
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final int CANVAS_WIDTH = 600; // Adjusted for 600x600 canvas
    public static final int CANVAS_HEIGHT = 600; // Adjusted for 600x600 canvas
    public static final int CELL_SIZE = CANVAS_WIDTH / COLS; // Dynamically calculate cell size
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    public static final Color COLOR_GRID = Color.LIGHT_GRAY;
    public static final int Y_OFFSET = 1;

    Cell[][] cells;

    public Board() {
        initGame();
    }

    public void initGame() {
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col, CELL_SIZE); // Pass dynamic cell size
                cells[row][col].content = Seed.NO_SEED; // Initialize with no seed
            }
        }
    }

    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        if (cells[selectedRow][selectedCol].content != Seed.NO_SEED) {
            return State.PLAYING; // Invalid move
        }

        cells[selectedRow][selectedCol].content = player;

        if (hasWon(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (isDraw()) {
            return State.DRAW;
        } else {
            return State.PLAYING;
        }
    }

    private boolean hasWon(Seed player, int row, int col) {
        // Check row, column, and diagonals for a win
        return (cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player) ||
                (cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player) ||
                (cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player) ||
                (cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player);
    }

    private boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void paint(Graphics g) {
        g.setColor(COLOR_GRID);
        for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF, CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0 + Y_OFFSET, GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
        }

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}