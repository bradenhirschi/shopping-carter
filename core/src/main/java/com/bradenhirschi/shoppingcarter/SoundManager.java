package com.bradenhirschi.shoppingcarter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {

    Clip clip;
    URL soundUrls[] = new URL[30];

    public SoundManager() {

        soundUrls[0] = getClass().getResource("/sound/gameMusic.wav");
        soundUrls[1] = getClass().getResource("/sound/gotCoin.wav");
    }

    public void setFile(int soundNumber) {

        try {

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrls[soundNumber]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {

        clip.start();
    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {

        clip.stop();
    }
}
