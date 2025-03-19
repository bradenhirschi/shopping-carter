package com.bradenhirschi.shoppingcarter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"));

    public void play() {

        music.setLooping(true);
        music.play();
    }
}
