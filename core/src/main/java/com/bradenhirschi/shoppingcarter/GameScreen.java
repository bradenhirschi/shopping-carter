package com.bradenhirschi.shoppingcarter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bradenhirschi.shoppingcarter.entity.Player;
import com.bradenhirschi.shoppingcarter.tile.TileManager;

public class GameScreen implements Screen {

    // SCREEN
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    // WORLD
    public final int maxWorldCol = 20;
    public final int maxWorldRow = 40;

    // SYSTEM
    private final Camera camera;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public KeyHandler keyHandler = new KeyHandler();
    public MusicManager musicManager = new MusicManager();
    public SoundManager soundManager = new SoundManager();
    private final SpriteBatch spriteBatch = new SpriteBatch();
    public TileManager tileManager = new TileManager(this);
    private final Viewport viewport;

    // ENTITY
    public Player player = new Player(this, this.keyHandler);

    public GameScreen(Camera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(keyHandler);
        musicManager.play();
    }

    @Override
    public void render(float delta) {
        // Update the player and camera
        player.update(delta);
        camera.position.set(player.x, player.y, 0);  // Camera follows the player
        camera.update();

        // Set the projection matrix before rendering
        spriteBatch.setProjectionMatrix(camera.combined);  // This applies the camera settings to the sprite batch

        // Clear the screen
        ScreenUtils.clear(Color.valueOf("#3B3B50"));

        // Start drawing
        spriteBatch.begin();
        tileManager.draw(spriteBatch);
        player.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);  // Adjust the viewport to the new window size.
        camera.update();  // Make sure the camera updates after resizing.
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
