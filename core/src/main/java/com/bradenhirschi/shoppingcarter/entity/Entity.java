package com.bradenhirschi.shoppingcarter.entity;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class Entity {
    public int x, y;
    public int speed;

    public Texture left1;
    public String direction;

    public Rectangle hitbox;
    public Boolean collision = false;
}
