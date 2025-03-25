package com.bradenhirschi.shoppingcarter.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.VertexArray;
import com.badlogic.gdx.math.Vector2;
import com.bradenhirschi.shoppingcarter.GameScreen;
import com.bradenhirschi.shoppingcarter.KeyHandler;

public class Player extends Entity {

    GameScreen gameScreen;
    KeyHandler keyHandler;

    private float walkTimer = 0f;
    private int currentFrame = 0;
    private final float WALK_SPEED = 0.07f; // Time between frames
    private boolean isWalking = false;

    public Player(GameScreen gameScreen, KeyHandler keyHandler) {

        this.gameScreen = gameScreen;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        x = 200; //gameScreen.tileSize * 2;
        y = 150; //gameScreen.tileSize * 10;
        speed = 300;
        direction = "left";

        float[] vertices = {
            0, 0,   // Bottom left corner
            0, gameScreen.tileSize,  // Top left corner
            gameScreen.tileSize * 2, gameScreen.tileSize, // Top right corner
            gameScreen.tileSize * 2, 0   // Bottom right corner
        };

        hitbox = new com.badlogic.gdx.math.Polygon(vertices);
        hitbox.setOrigin(gameScreen.tileSize, gameScreen.tileSize / 2);
        hitbox.setPosition(x, y);
    }

    public void getPlayerImage() {
        sprite = new Texture("entities/player/topDown.png");

        front1 = new Texture("entities/player/front1.png");
        front2 = new Texture("entities/player/front2.png");
        front3 = new Texture("entities/player/front3.png");

        frontLeft1 = new Texture("entities/player/frontLeft1.png");
        frontLeft2 = new Texture("entities/player/frontLeft2.png");
        frontLeft3 = new Texture("entities/player/frontLeft3.png");

        left1 = new Texture("entities/player/left1.png");
        left2 = new Texture("entities/player/left2.png");
        left3 = new Texture("entities/player/left3.png");

        backLeft1 = new Texture("entities/player/backLeft1.png");
        backLeft2 = new Texture("entities/player/backLeft2.png");
        backLeft3 = new Texture("entities/player/backLeft3.png");

        back1 = new Texture("entities/player/back1.png");
        back2 = new Texture("entities/player/back2.png");
        back3 = new Texture("entities/player/back3.png");

        backRight1 = new Texture("entities/player/backRight1.png");
        backRight2 = new Texture("entities/player/backRight2.png");
        backRight3 = new Texture("entities/player/backRight3.png");

        right1 = new Texture("entities/player/right1.png");
        right2 = new Texture("entities/player/right2.png");
        right3 = new Texture("entities/player/right3.png");

        frontRight1 = new Texture("entities/player/frontRight1.png");
        frontRight2 = new Texture("entities/player/frontRight2.png");
        frontRight3 = new Texture("entities/player/frontRight3.png");

    }

    private void updateHitbox() {
        hitbox.setPosition(x, y);
        hitbox.setRotation(-rotation);
    }

    public void update(float delta) {

        if (!keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed && !keyHandler.rightPressed) {
            isWalking = false;
            return;
        } else {
            isWalking = true;
        }

        // Rotate left or right
        if (keyHandler.leftPressed) {
            rotation -= rotationSpeed; // Turn left
        }
        if (keyHandler.rightPressed) {
            rotation += rotationSpeed; // Turn right
        }

        // Wrap rotation within 0-360 degrees
        if (rotation < 0) {
            rotation += 360;
        } else if (rotation >= 360) {
            rotation -= 360;
        }

        /// CHECK FOR COLLISIONS ///
        int xVelocity = 0;
        int yVelocity = 0;
        boolean xCollision = false;
        boolean yCollision = false;

        // Convert rotation to radians for movement calculation
        final float radians = (float) Math.toRadians(rotation);

        // Adjust velocity based on rotation and forward or backward pressed
        if (keyHandler.upPressed) {
            xVelocity += Math.cos(radians) * speed * delta;
            yVelocity -= Math.sin(radians) * speed * delta;
        }

        if (keyHandler.downPressed) {
            xVelocity -= Math.cos(radians) * speed * delta;
            yVelocity += Math.sin(radians) * speed * delta;
        }

        // For each vertex of the hitbox we check for collisions
        for (int i = 0; i < hitbox.getVertexCount(); i++) {
            Vector2 vertex = new Vector2();
            hitbox.getVertex(i, vertex);

            int futureX = x + xVelocity;
            int futureY = y + yVelocity;

            xCollision = gameScreen.collisionChecker.checkTile(futureX, y);
            yCollision = gameScreen.collisionChecker.checkTile(x, futureY);
        }

        /// Move if no collisions ///
        if (xCollision || yCollision) {
            gameScreen.soundManager.playCrash();
        }

        if (!xCollision) {
            x += xVelocity;
            hitbox.translate(xVelocity, 0);
        }

        if (!yCollision) {
            y += yVelocity;
            hitbox.translate(0, yVelocity);
        }

        updateHitbox();

        // Update walk timer for animation
        if (isWalking) {
            walkTimer += delta;
            if (walkTimer >= WALK_SPEED) {
                walkTimer = 0;
                currentFrame = (currentFrame + 1) % 3; // Cycle through 3 frames
            }
        }
    }


    public void draw(SpriteBatch batch) {

        // This isn't doing anything
        sprite = getWalkingSprite(right1, right2, right3);

        // This is bad, we're loading texture every frame
        Texture texture = new Texture("entities/player/topDown.png");
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

        batch.draw(region, (float) x, (float) y, gameScreen.tileSize, gameScreen.tileSize / 2, (float) gameScreen.tileSize * 2, (float) gameScreen.tileSize, 1f, 1f, rotation * -1);
    }

    // Helper method to select the correct walking sprite
    private Texture getWalkingSprite(Texture sprite1, Texture sprite2, Texture sprite3) {
        switch (currentFrame) {
            case 0:
                return sprite1;
            case 1:
                return sprite2;
            case 2:
                return sprite3;
            default:
                return sprite1;
        }
    }

}
