package com.inuwa.etd.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Rock {

    private Texture rock1;
    private Texture rock2;
    private Texture rock3;
    private Vector3 posRock;
    private Random random;

    public Rock(){
        rock1 = new Texture("rock1.png");
        rock2 = new Texture("rock2.png");
        rock3 = new Texture("rock3.png");
        random = new Random();
    }


}
