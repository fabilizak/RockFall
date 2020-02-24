package com.inuwa.rockfall.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class Background {

    private Texture backgrounds;
    private TextureRegion background;
    private int bgSelector;
    private Random random;

    public Background(){
        random = new Random();
        bgSelector = random.nextInt(4);
        backgrounds = new Texture("background.png");
        background = new TextureRegion(backgrounds, bgSelector * backgrounds.getWidth()/4, 0, backgrounds.getWidth()/4, backgrounds.getHeight());
    }

    public TextureRegion getBackground(){
        return background;
    }
}
