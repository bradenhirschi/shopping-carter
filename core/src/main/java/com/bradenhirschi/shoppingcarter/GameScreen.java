package com.bradenhirschi.shoppingcarter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bradenhirschi.shoppingcarter.entity.Player;
import com.bradenhirschi.shoppingcarter.tile.TileManager;

public class GameScreen implements Screen {

    // WORLD
    public final int maxWorldCol = 33;
    public final int maxWorldRow = 32;

    // Box2D Physics
    public final World world = new World(new Vector2(0, 0), true);
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    // SYSTEM
    private final Camera camera;
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
        camera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);  // Camera follows the player
        camera.update();

        // Set the projection matrix before rendering
        spriteBatch.setProjectionMatrix(camera.combined);  // This applies the camera settings to the sprite batch

        // Clear the screen
        ScreenUtils.clear(Color.valueOf("#9baa9f"));

        // Start drawing
        spriteBatch.begin();
        tileManager.draw(spriteBatch);
        player.draw(spriteBatch);
        spriteBatch.end();

        debugRenderer.render(world, camera.combined);
        world.step(1/60f, 6, 2);
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
        tileManager.dispose();
    }
}
