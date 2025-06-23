package TicTacToe;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Clip;

public class VolumeManager {
    private static VolumeManager instance;
    private float volume = 1.0f;
    private Clip currentClip;

    private VolumeManager() {}

    public static VolumeManager getInstance() {
        if (instance == null) {
            instance = new VolumeManager();
        }
        return instance;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateVolume();
    }

    public void setCurrentClip(Clip clip) {
        this.currentClip = clip;
        updateVolume();
    }

    private void updateVolume() {
        if (currentClip != null) {
            FloatControl gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}