package com.bradenhirschi.shoppingcarter.entity;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bradenhirschi.shoppingcarter.GameScreen;
import com.bradenhirschi.shoppingcarter.KeyHandler;

import java.awt.*;

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
        speed = 5;
        direction = "left";
        hitbox = new Rectangle(8, 0, 32, 32);
    }

    public void getPlayerImage() {

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

        // Check for collisions
        collision = false;
        gameScreen.collisionChecker.checkTile(this);

        if (collision) {
            gameScreen.soundManager.playCrash();
            return;
        }

        // Convert rotation to radians for movement calculation
        float radians = (float) Math.toRadians(rotation);

        // Move forward or backward based on rotation
        if (keyHandler.upPressed) {
            x += Math.cos(radians) * speed;
            y -= Math.sin(radians) * speed;
        }
        if (keyHandler.downPressed) {
            x -= Math.cos(radians) * speed;
            y += Math.sin(radians) * speed;
        }

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

        Texture sprite = null;

        // Normalize rotation to the nearest 45 degrees
        int normalizedRotation = Math.round(rotation / 45) * 45;

        switch (normalizedRotation) {
            case 0:
            case 360:
                sprite = getWalkingSprite(right1, right2, right3);
                break;
            case 45:
                sprite = getWalkingSprite(frontRight1, frontRight2, frontRight3);
                break;
            case 90:
                sprite = getWalkingSprite(front1, front2, front3);
                break;
            case 135:
                sprite = getWalkingSprite(frontLeft1, frontLeft2, frontLeft3);
                break;
            case 180:
                sprite = getWalkingSprite(left1, left2, left3);
                break;
            case 225:
                sprite = getWalkingSprite(backLeft1, backLeft2, backLeft3);
                break;
            case 270:
                sprite = getWalkingSprite(back1, back2, back3);
                break;
            case 315:
                sprite = getWalkingSprite(backRight1, backRight2, backRight3);
                break;
            default:
                sprite = front1;
                break;
        }

        batch.draw(sprite, x, y, gameScreen.tileSize, gameScreen.tileSize);
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
