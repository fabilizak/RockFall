package com.inuwa.etd.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ground {

    private Texture groundTex;
    private TextureRegion groundTexReg;

    public Ground(){
        groundTex = new Texture("ground.png");
        groundTexReg = new TextureRegion(groundTex, 0, 0, 200, 100);
    }

    public TextureRegion getTexture() {
        return groundTexReg;
    }
}
