package com.bradenhirschi.shoppingcarter.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bradenhirschi.shoppingcarter.GameScreen;
import com.bradenhirschi.shoppingcarter.KeyHandler;

import java.awt.*;

public class Player extends Entity {

    GameScreen gameScreen;
    KeyHandler keyHandler;

    public Player(GameScreen gameScreen, KeyHandler keyHandler) {

        this.gameScreen = gameScreen;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        x = 300; //gameScreen.tileSize * 2;
        y = 300; //gameScreen.tileSize * 10;
        speed = 4;
        direction = "left";
        hitbox = new Rectangle(8, 0, 32, 32);
    }

    public void getPlayerImage() {

        left1 = new Texture("entities/playerLeft.png");
    }

    public void update() {

        if (!keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed && !keyHandler.rightPressed) {
            return;
        }

        if (keyHandler.upPressed) {
            direction = "up";
        }
        if (keyHandler.downPressed) {
            direction = "down";
        }
        if (keyHandler.leftPressed) {
            direction = "left";
        }
        if (keyHandler.rightPressed) {
            direction = "right";
        }

        // Check for collisions
        collision = false;
        gameScreen.collisionChecker.checkTile(this);

        // Stop if collision detected
        if (collision) return;

        // Move if no collision
        switch (direction) {
            case "up":
                y += speed;
                break;
            case "down":
                y -= speed;
                break;
            case "left":
                x -= speed;
                break;
            case "right":
                x += speed;
                break;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(left1, x, y, gameScreen.tileSize, gameScreen.tileSize);

        Texture shoppingCart = new Texture("entities/shoppingCart.png");
        batch.draw(shoppingCart, x - gameScreen.tileSize, y, gameScreen.tileSize, gameScreen.tileSize);
    }
}
