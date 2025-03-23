package com.bradenhirschi.shoppingcarter.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bradenhirschi.shoppingcarter.GameScreen;
import sun.jvm.hotspot.utilities.Assert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GameScreen gameScreen;
    public Tile[] tiles;
    public int mapTileNumbers[][];

    public TileManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        tiles = new Tile[11];
        mapTileNumbers = new int[gameScreen.maxWorldCol][gameScreen.maxWorldRow];

        getTileImages();
        loadMap("/maps/level1.txt");
    }

    public void getTileImages() {

        tiles[0] = new Tile();
        tiles[0].image = new Texture("tiles/floor.png");

        tiles[1] = new Tile();
        tiles[1].image = new Texture("tiles/wall.png");
        tiles[1].collision = true;

        tiles[2] = new Tile();
        tiles[2].image = new Texture("tiles/shelf.png");
        tiles[2].collision = true;

        tiles[3] = new Tile();
        tiles[3].image = new Texture("tiles/register.png");
        tiles[3].collision = true;

        tiles[4] = new Tile();
        tiles[4].image = new Texture("tiles/spill.png");

        tiles[5] = new Tile();
        tiles[5].image = new Texture("tiles/register.png");
        tiles[5].collision = true;

        tiles[6] = new Tile();
        tiles[6].image = new Texture("tiles/register.png");
        tiles[6].collision = true;

        tiles[7] = new Tile();
        tiles[7].image = new Texture("tiles/register.png");
        tiles[7].collision = true;

        tiles[8] = new Tile();
        tiles[8].image = new Texture("tiles/register.png");
        tiles[8].collision = true;

        tiles[9] = new Tile();
        tiles[9].image = new Texture("tiles/register.png");
        tiles[9].collision = true;

        tiles[10] = new Tile();
        tiles[10].image = new Texture("tiles/milk.png");
    }

    public void loadMap(String filePath) {

        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0;
            int row = gameScreen.maxWorldRow - 1;
            while (col < gameScreen.maxWorldCol && row >= 0) {
                String line = bufferedReader.readLine();

                while (col < gameScreen.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumbers[col][row] = num;

                    col++;
                }

                if (col == gameScreen.maxWorldCol) {
                    col = 0;
                    row--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(SpriteBatch batch) {

        int col = 0;
        int row = 0;

        while (col < gameScreen.maxWorldCol && row < gameScreen.maxWorldRow) {

            int tileNum = mapTileNumbers[col][row];

            int x = col * gameScreen.tileSize;
            int y = row * gameScreen.tileSize;

            batch.draw(tiles[tileNum].image, (float) x, (float) y, (float) gameScreen.tileSize, (float) gameScreen.tileSize);

            col++;

            if (col == gameScreen.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }
}
