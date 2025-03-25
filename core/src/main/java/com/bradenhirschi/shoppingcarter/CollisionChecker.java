package com.bradenhirschi.shoppingcarter;

import com.bradenhirschi.shoppingcarter.entity.Entity;

public class CollisionChecker {

    GameScreen gameScreen;

    public CollisionChecker(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
    }

    public boolean checkTile(float x, float y) {
        int col = (int) x / gameScreen.tileSize;
        int row = (int) y / gameScreen.tileSize;

        int tileNum = gameScreen.tileManager.mapTileNumbers[col][row];

        if (gameScreen.tileManager.tiles[tileNum].collision) {
            return true;
        }
        return false;
    }

    public void checkTile(Entity entity) {
//        // Calculate future position based on rotation
//        float radians = (float) Math.toRadians(entity.rotation);
//        int futureX = entity.x;
//        int futureY = entity.y;
//
//        if (gameScreen.keyHandler.upPressed) {
//            futureX += Math.cos(radians) * entity.speed;
//            futureY -= Math.sin(radians) * entity.speed;
//        }
//        if (gameScreen.keyHandler.downPressed) {
//            futureX -= Math.cos(radians) * entity.speed;
//            futureY += Math.sin(radians) * entity.speed;
//        }
//
//        // Check collision only at the forward position
//        int entityLeftX = futureX + entity.hitbox.x;
//        int entityRightX = futureX + entity.hitbox.x + entity.hitbox.width;
//        int entityTopY = futureY + entity.hitbox.y + entity.hitbox.height;
//        int entityBottomY = futureY + entity.hitbox.y;
//
//        int leftCol = entityLeftX / gameScreen.tileSize;
//        int rightCol = entityRightX / gameScreen.tileSize;
//        int topRow = entityTopY / gameScreen.tileSize;
//        int bottomRow = entityBottomY / gameScreen.tileSize;
//
//        // Get the tile numbers at the future movement location
//        int tile1Num = gameScreen.tileManager.mapTileNumbers[leftCol][topRow];
//        int tile2Num = gameScreen.tileManager.mapTileNumbers[rightCol][bottomRow];
//
//        // Check if the future position is colliding
//        if (gameScreen.tileManager.tiles[tile1Num].collision || gameScreen.tileManager.tiles[tile2Num].collision) {
//            entity.collision = true;
//        } else {
//            entity.collision = false;
//        }
    }

}
