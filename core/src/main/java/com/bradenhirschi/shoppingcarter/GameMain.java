package com.bradenhirschi.shoppingcarter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameMain extends Game {
    private OrthographicCamera camera;
    private FitViewport viewport;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(24, 16, camera);
        viewport.apply();
        setScreen(new GameScreen(camera, viewport));

        // Center the camera in the world
        camera.position.set(12, 8, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }
}
