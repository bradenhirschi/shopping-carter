package com.bradenhirschi.shoppingcarter.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;

import java.awt.*;

public class Entity {
    public int x, y;
    public int speed;

    public Texture right1, right2, right3;
    public Texture frontRight1, frontRight2, frontRight3;
    public Texture front1, front2, front3;
    public Texture frontLeft1, frontLeft2, frontLeft3;
    public Texture left1, left2, left3;
    public Texture backLeft1, backLeft2, backLeft3;
    public Texture back1, back2, back3;
    public Texture backRight1, backRight2, backRight3;

    public Rectangle hitbox;
    public String direction;
    public float rotation = 0; // Angle in degrees
    public float rotationSpeed = 3f; // Speed of turning

    public Boolean collision = false;
}
