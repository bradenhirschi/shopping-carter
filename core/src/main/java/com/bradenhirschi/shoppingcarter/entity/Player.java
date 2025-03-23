package com.bradenhirschi.shoppingcarter.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bradenhirschi.shoppingcarter.GameScreen;
import com.bradenhirschi.shoppingcarter.KeyHandler;
import com.bradenhirschi.shoppingcarter.Utils;

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
        y = 300; //gameScreen.tileSize * 10;
        speed = 5;
        direction = "right";
        hitbox = new Rectangle(8, 0, 32, 32);
    }

    public void getPlayerImage() {

        front1 = new Texture("entities/player/front1.png");
        front2 = new Texture("entities/player/front2.png");
        front3 = new Texture("entities/player/front2.png");

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
        // If no movement keys are pressed, stop walking and return
        if (!keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed && !keyHandler.rightPressed) {
            isWalking = false;
            return;
        } else {
            isWalking = true;
        }

        // Variables to store intended movement
        float nextX = x;
        float nextY = y;

        // Check movement without directly modifying x and y
        if (keyHandler.upPressed) {
            if (keyHandler.leftPressed) {
                nextX -= speed * Utils.DIAGONAL_FACTOR;
                nextY += speed * Utils.DIAGONAL_FACTOR;
                direction = "northwest";
            } else if (keyHandler.rightPressed) {
                nextX += speed * Utils.DIAGONAL_FACTOR;
                nextY += speed * Utils.DIAGONAL_FACTOR;
                direction = "northeast";
            } else {
                nextY += speed;
                direction = "north";
            }
        } else if (keyHandler.downPressed) {
            if (keyHandler.leftPressed) {
                nextX -= speed * Utils.DIAGONAL_FACTOR;
                nextY -= speed * Utils.DIAGONAL_FACTOR;
                direction = "southwest";
            } else if (keyHandler.rightPressed) {
                nextX += speed * Utils.DIAGONAL_FACTOR;
                nextY -= speed * Utils.DIAGONAL_FACTOR;
                direction = "southeast";
            } else {
                nextY -= speed;
                direction = "south";
            }
        } else if (keyHandler.leftPressed) {
            nextX -= speed;
            direction = "west";
        } else if (keyHandler.rightPressed) {
            nextX += speed;
            direction = "east";
        }

        // Separate collision checks for x and y to allow sliding along walls
        boolean xCollision = gameScreen.collisionChecker.checkTile(nextX, y);
        boolean yCollision = gameScreen.collisionChecker.checkTile(x, nextY);

        // Apply movement only if no collision occurs in that direction
        if (!xCollision) {
            x = nextX;
        }
        if (!yCollision) {
            y = nextY;
        }

        // Play crash sound only on a new collision event
        if ((xCollision || yCollision) && !collision) {
            gameScreen.soundManager.playCrash();
        }

        // Update collision flag
        collision = xCollision || yCollision;

        // Update walk animation
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

        switch (direction) {
            case "east":
                sprite = getWalkingSprite(right1, right2, right3);
                break;
            case "southeast":
                sprite = getWalkingSprite(frontRight1, frontRight2, frontRight3);
                break;
            case "south":
                sprite = getWalkingSprite(front1, front2, front3);
                break;
            case "southwest":
                sprite = getWalkingSprite(frontLeft1, frontLeft2, frontLeft3);
                break;
            case "west":
                sprite = getWalkingSprite(left1, left2, left3);
                break;
            case "northwest":
                sprite = getWalkingSprite(backLeft1, backLeft2, backLeft3);
                break;
            case "north":
                sprite = getWalkingSprite(back1, back2, back3);
                break;
            case "northeast":
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
