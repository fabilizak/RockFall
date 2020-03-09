package com.inuwa.rockfall.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.inuwa.rockfall.RockFall;

public class Button {
    private float x, y, width, height;
    private TextureRegion buttonTex;
    private Rectangle bounds;
    public static final float MENU_BUTTON_WIDTH = RockFall.GAMESCREENWIDTH/2.5f;
    public static final float MENU_BUTTON_HEIGHT = RockFall.GAMESCREENHEIGHT/7.5f;
    public static final float GAME_BUTTON_WIDTH = RockFall.GAMESCREENWIDTH/7.5f;
    public static final float GAME_BUTTON_HEIGHT = RockFall.GAMESCREENHEIGHT/12.5f;

    public Button(float x, float y, String btnType, TextureRegion buttonTex) {
        this.x = x;
        this.y = y;
        this.buttonTex = buttonTex;

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

    public boolean isPressed(float screenX, float screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(buttonTex, x, y, width, height);
    }
}
