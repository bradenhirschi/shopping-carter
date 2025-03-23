package com.bradenhirschi.shoppingcarter;

import com.bradenhirschi.shoppingcarter.entity.Entity;

public class CollisionChecker {

    GameScreen gameScreen;

    public CollisionChecker(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
    }

    public boolean checkTile(float x, float y) {
        int leftCol = (int) Math.floor(x / gameScreen.tileSize);
        int rightCol = (int) Math.floor(x / gameScreen.tileSize);
        int topRow = (int) Math.floor(y / gameScreen.tileSize);
        int bottomRow = (int) Math.floor(y / gameScreen.tileSize);

        // Get the tile numbers at the future movement location
        int tile1Num = gameScreen.tileManager.mapTileNumbers[leftCol][topRow];
        int tile2Num = gameScreen.tileManager.mapTileNumbers[rightCol][bottomRow];

        // Check if the future position is colliding
        if (gameScreen.tileManager.tiles[tile1Num].collision || gameScreen.tileManager.tiles[tile2Num].collision) {
            return true;
        } else {
            return false;
        }
    }

}
