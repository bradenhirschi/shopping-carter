package com.bradenhirschi.shoppingcarter;

import com.bradenhirschi.shoppingcarter.entity.Entity;

public class CollisionChecker {

    GameScreen gameScreen;

    public CollisionChecker(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
    }

    public void checkTile(Entity entity) {

        int entityLeftX = entity.x + entity.hitbox.x;
        int entityRightX = entity.x + entity.hitbox.x + entity.hitbox.width;
        int entityTopY = entity.y + entity.hitbox.y + entity.hitbox.height;
        int entityBottomY = entity.y + entity.hitbox.y;

        int entityLeftCol = entityLeftX / gameScreen.tileSize;
        int entityRightCol = entityRightX / gameScreen.tileSize;
        int entityTopRow = entityTopY / gameScreen.tileSize;
        int entityBottomRow = entityBottomY / gameScreen.tileSize;

        int tile1Num;
        int tile2Num;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopY + entity.speed) / gameScreen.tileSize;
                tile1Num = gameScreen.tileManager.mapTileNumbers[entityLeftCol][entityTopRow];
                tile2Num = gameScreen.tileManager.mapTileNumbers[entityRightCol][entityTopRow];
                if (gameScreen.tileManager.tiles[tile1Num].collision || gameScreen.tileManager.tiles[tile2Num].collision) {
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY - entity.speed) / gameScreen.tileSize;
                tile1Num = gameScreen.tileManager.mapTileNumbers[entityLeftCol][entityBottomRow];
                tile2Num = gameScreen.tileManager.mapTileNumbers[entityRightCol][entityBottomRow];
                if (gameScreen.tileManager.tiles[tile1Num].collision || gameScreen.tileManager.tiles[tile2Num].collision) {
                    entity.collision = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.speed) / gameScreen.tileSize;
                tile1Num = gameScreen.tileManager.mapTileNumbers[entityLeftCol][entityTopRow];
                tile2Num = gameScreen.tileManager.mapTileNumbers[entityLeftCol][entityBottomRow];
                if (gameScreen.tileManager.tiles[tile1Num].collision || gameScreen.tileManager.tiles[tile2Num].collision) {
                    entity.collision = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightX + entity.speed) / gameScreen.tileSize;
                tile1Num = gameScreen.tileManager.mapTileNumbers[entityRightCol][entityTopRow];
                tile2Num = gameScreen.tileManager.mapTileNumbers[entityRightCol][entityBottomRow];
                if (gameScreen.tileManager.tiles[tile1Num].collision || gameScreen.tileManager.tiles[tile2Num].collision) {
                    entity.collision = true;
                }
                break;
        }
    }
}
