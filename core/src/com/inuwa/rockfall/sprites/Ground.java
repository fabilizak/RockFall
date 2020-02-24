package com.inuwa.rockfall.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ground {

    private Texture groundTex;
    private TextureRegion groundTexReg;
    private static final int GROUND_WIDTH = 200;
    private static final int GROUND_HEIGHT = 100;

    public Ground(){
        groundTex = new Texture("ground.png");
        groundTexReg = new TextureRegion(groundTex, 0, 0, GROUND_WIDTH, GROUND_HEIGHT);
    }

    public TextureRegion getTexture() {
        return groundTexReg;
    }

    public void dispose(){
        groundTex.dispose();
    }
}
