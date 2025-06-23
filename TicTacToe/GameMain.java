package TicTacToe;

import javax.swing.*;
import java.io.File;

import static TicTacToe.TicTacToe.TITLE;

public class GameMain {
    /** The entry "main" method */
    public static void main(String[] args) {
        // Run GUI construction codes in Event-Dispatching thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Membuat jendela utama
                JFrame frame = new JFrame(TITLE);

                // Set konten jendela dengan PageAwal (panel permainan)
                PageAwal pageAwal = new PageAwal(frame);
                frame.setContentPane(pageAwal);

                // Mengatur close operation untuk keluar ketika jendela ditutup
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Menentukan ukuran jendela setelah panel permainan dipasang
                frame.setSize(600, 600);

                // Menempatkan jendela di tengah layar
                frame.setLocationRelativeTo(null);

                // Menampilkan jendela
                frame.setVisible(true);

                // Play background music
                pageAwal.playBackgroundMusic("C:\\Users\\Alamsyah Mubarok\\IdeaProjects\\FP-ASD1\\src\\SoundtrackTictactoe.wav");
            }
        });
    }

}