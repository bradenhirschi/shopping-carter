package com.bradenhirschi.shoppingcarter;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class KeyHandler implements InputProcessor {
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                upPressed = true;
                break;
            case Input.Keys.A:
                leftPressed = true;
                break;
            case Input.Keys.S:
                downPressed = true;
                break;
            case Input.Keys.D:
                rightPressed = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                upPressed = false;
                break;
            case Input.Keys.A:
                leftPressed = false;
                break;
            case Input.Keys.S:
                downPressed = false;
                break;
            case Input.Keys.D:
                rightPressed = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
