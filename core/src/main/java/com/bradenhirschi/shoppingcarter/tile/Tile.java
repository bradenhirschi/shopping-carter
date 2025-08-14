package com.bradenhirschi.shoppingcarter.tile;

import com.badlogic.gdx.graphics.Texture;

public class Tile {

    public Texture image;
    public boolean collision = false;

    public Tile() {

    }

    public Tile(Boolean collision) {
        this.collision = collision;
    }
}
