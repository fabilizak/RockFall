package com.inuwa.etd.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Button {
    private float x, y, width, height;
    private TextureRegion buttonUp;
    private TextureRegion buttonDown;
    private Rectangle bounds;
    private boolean isPressed = false;
    public static final int MENU_BUTTON_WIDTH = Gdx.graphics.getWidth()/5;
    public static final int MENU_BUTTON_HEIGHT = Gdx.graphics.getHeight()/15;
    public static final int GAME_BUTTON_WIDTH = Gdx.graphics.getWidth()/15;
    public static final int GAME_BUTTON_HEIGHT = Gdx.graphics.getHeight()/25;

    public Button(float x, float y, String btnType,
                  TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        //this.width = width;
        //this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;

        switch (btnType){
            case "menu":
                this.width = MENU_BUTTON_WIDTH;
                this.height = MENU_BUTTON_HEIGHT;
                break;
            case "game":
                this.width = GAME_BUTTON_WIDTH;
                this.height = GAME_BUTTON_HEIGHT;
                break;
        }

        bounds = new Rectangle(x, y, width, height);

    }

    public boolean isPresssed(float screenX, float screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch spriteBatch) {
        if (isPressed) {
            spriteBatch.draw(buttonDown, x, y, width, height);
        } else {
            spriteBatch.draw(buttonUp, x, y, width, height);
        }
    }

    public boolean isTouchDown(float screenX, float screenY) {

        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }

        return false;
    }

    public boolean isTouchUp(float screenX, float screenY) {

        // It only counts as a touchUp if the button is in a pressed state.
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }

        // Whenever a finger is released, we will cancel any presses.
        isPressed = false;
        return false;
    }
}
