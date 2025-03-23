package com.bradenhirschi.shoppingcarter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import static com.badlogic.gdx.math.MathUtils.random;

public class SoundManager {

    public void playCrash() {
        Sound sound;

        int i = random.nextInt(2) + 1;

        switch(i) {
            case 1:
                sound = Gdx.audio.newSound(Gdx.files.internal("sound/crash1.mp3"));
                break;
            case 2:
                sound = Gdx.audio.newSound(Gdx.files.internal("sound/crash2.mp3"));
                break;
            default:
                sound = Gdx.audio.newSound(Gdx.files.internal("sound/crash1.mp3"));
        }

        long soundId = sound.play(1.0f);
        sound.setPitch(soundId, 2.2f);
//        sound.dispose();
    }
}
