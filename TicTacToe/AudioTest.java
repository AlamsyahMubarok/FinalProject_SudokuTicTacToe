package TicTacToe;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioTest {
    public static void main(String[] args) {
        try {
            File audioFile = new File("C:\\Users\\Alamsyah Mubarok\\IdeaProjects\\FP-ASD1\\src\\SoundtrackTictactoe.wav");
            if (!audioFile.exists()) {
                System.err.println("Audio file does not exist.");
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.println("Playing audio...");
            Thread.sleep(clip.getMicrosecondLength() / 1000); // Wait for the audio to finish
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}