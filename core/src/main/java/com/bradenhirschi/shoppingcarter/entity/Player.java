package com.bradenhirschi.shoppingcarter.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bradenhirschi.shoppingcarter.GameScreen;
import com.bradenhirschi.shoppingcarter.KeyHandler;

public class Player {

    GameScreen gameScreen;
    KeyHandler keyHandler;
    public Body body;
    private Texture texture;

    // Physics tuning
    private final float MAX_SPEED = 10f;
    private final float ACCELERATION = 12f;      // Lower acceleration to emphasize gradual movement
    private final float TURN_SPEED = 2.5f;       // Reduce turn speed to make it feel like a loose, unstable cart
    private final float LINEAR_DAMPING = 1.5f;     // Slightly higher to prevent infinite sliding but still allows drift
    private final float ANGULAR_DAMPING = 6.5f;  // Higher angular damping to reduce spinouts and keep handling predictable

    public Player(GameScreen gameScreen, KeyHandler keyHandler) {
        this.gameScreen = gameScreen;
        this.keyHandler = keyHandler;

        // Set up body for Box2d physics
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(1, 1);
        body = gameScreen.world.createBody(bodyDef);
        body.setUserData(this);
        body.setLinearDamping(LINEAR_DAMPING);
        body.setAngularDamping(ANGULAR_DAMPING);

        // Create polygon hitbox and attach it to body as a fixture
        PolygonShape polygon = new PolygonShape();
        float[] vertices = {0, 0, 0, 0.8f, 1.8f, 0.8f, 1.8f, 0};
        polygon.set(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.1f;
        body.createFixture(fixtureDef);
        polygon.dispose();

        // Set texture
        texture = new Texture("entities/player/topDown.png");
    }

    public void update(float delta) {
        boolean drifting = keyHandler.spacePressed;

        if (!keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed && !keyHandler.rightPressed) {
            return;
        }

        float angle = body.getAngle();
        Vector2 forward = new Vector2((float) Math.cos(angle), (float) Math.sin(angle));

        float currentAcceleration = drifting ? ACCELERATION * 0.6f : ACCELERATION;
        float currentTurnSpeed = drifting ? TURN_SPEED * 1.8f : TURN_SPEED;
        float currentDamping = drifting ? 1f : LINEAR_DAMPING; // Reduce damping for slide effect

        if (keyHandler.upPressed) {
            Vector2 force = forward.scl(currentAcceleration * delta);
            body.applyLinearImpulse(force, body.getWorldCenter(), true);
        }

        if (keyHandler.downPressed) {
            Vector2 force = forward.scl(-currentAcceleration * delta);
            body.applyLinearImpulse(force, body.getWorldCenter(), true);
        }

        if (keyHandler.leftPressed) {
            body.applyAngularImpulse(currentTurnSpeed * delta, true);
        }

        if (keyHandler.rightPressed) {
            body.applyAngularImpulse(-currentTurnSpeed * delta, true);
        }

        // Adjust friction dynamically
        for (Fixture fixture : body.getFixtureList()) {
            fixture.setFriction(drifting ? 0.1f : 0.8f);
        }

        // Reduce damping for drifting effect
        body.setLinearDamping(currentDamping);

        // Limit speed
        if (body.getLinearVelocity().len() > MAX_SPEED) {
            body.setLinearVelocity(body.getLinearVelocity().nor().scl(MAX_SPEED));
        }
    }


    public void draw(SpriteBatch batch) {
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

        float width = 2f;
        float height = 1f;

        batch.draw(region,
            body.getPosition().x, body.getPosition().y,  // Position (centered)
            0, 0,  // Origin (center for rotation)
            width, height,  // Size
            1f, 1f,  // Scale
            (float) Math.toDegrees(body.getAngle())  // Rotation
        );    }
}
