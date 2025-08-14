package com.bradenhirschi.shoppingcarter.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.bradenhirschi.shoppingcarter.GameScreen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    // Screen
    GameScreen gameScreen;
    public Tile[] tiles;
    public int mapTileNumbers[][];

    // Box2D
    BodyDef bodyDef;

    public TileManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        tiles = new Tile[10];
        mapTileNumbers = new int[gameScreen.maxWorldCol][gameScreen.maxWorldRow];

        // Standard Box2D bodydef for collidable tile
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        getTileImages();
        loadMap("/maps/level1.txt");
    }

    public void getTileImages() {

        tiles[0] = new Tile();
        tiles[0].image = new Texture("tiles/floor.png");

        tiles[1] = new Tile(true);
        tiles[1].image = new Texture("tiles/wall.png");

        tiles[2] = new Tile(true);
        tiles[2].image = new Texture("tiles/shelf.png");

        tiles[3] = new Tile(true);
        tiles[3].image = new Texture("tiles/register.png");
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0;
            int row = gameScreen.maxWorldRow - 1;

            while (col < gameScreen.maxWorldCol && row >= 0) {
                String line = bufferedReader.readLine();
                String numbers[] = line.split(" ");

                while (col < gameScreen.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumbers[col][row] = num;

                    // Only create Box2D bodies ONCE when the map loads
                    if (tiles[num].collision) {
                        BodyDef bodyDef = new BodyDef();
                        bodyDef.type = BodyDef.BodyType.StaticBody;
                        bodyDef.position.set(col, row);

                        Body body = gameScreen.world.createBody(bodyDef);
                        body.setUserData(this);

                        PolygonShape polygon = new PolygonShape();
                        float[] vertices = {0, 0, 0, 1, 1, 1, 1, 0};
                        polygon.set(vertices);

                        FixtureDef fixtureDef = new FixtureDef();
                        fixtureDef.shape = polygon;
                        body.createFixture(fixtureDef);

                        polygon.dispose();
                    }

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

            int x = col;
            int y = row;

            batch.draw(tiles[tileNum].image, (float) x, (float) y, 1, 1);

            col++;

            if (col == gameScreen.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void dispose() {
        for (Tile tile : tiles) {
            if (tile != null && tile.image != null) {
                tile.image.dispose();
            }
        }
    }

}
