package com.inuwa.etd.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rock {

    private Texture rock1;
    private Texture rock2;
    private Texture rock3;
    private Vector3 rockPos;
    private Vector3 velocity;
    private Random randX;
    private Random randTex;
    private OrthographicCamera camera;
    private float rockWidth;
    private float rockHeight;
    private Texture myRockTexture;
    private boolean touchedGround;

    public Rock(OrthographicCamera camera){
        this.camera = camera;
        rock1 = new Texture("rock1.png");
        rock2 = new Texture("rock2.png");
        rock3 = new Texture("rock3.png");
        randX = new Random();
        randTex = new Random();
        rockWidth = camera.viewportWidth/7;
        rockHeight = rockWidth;
        velocity = new Vector3(0, 0, 0);

        rockPos = new Vector3(randX.nextInt(7) * rockWidth, camera.viewportHeight - rockHeight, 0 );
        generateTexture(randTex.nextInt(3));
        touchedGround = false;
    }

    public void update(float deltaTime, List rockList){
        velocity.add(0, Joe.GRAVITY, 0);
        velocity.scl(deltaTime);
        rockPos.add(0, velocity.y, 0);
        if (rockPos.y < Joe.getPosGroundTop())
            touchedGround = true;
        velocity.scl(1/deltaTime);
    }

    public void generateTexture(int number) {
        switch (number){
            case 0:
                myRockTexture = rock1;
                break;
            case 1:
                myRockTexture = rock2;
                break;
            case 2:
                myRockTexture = rock3;
                break;
        }
    }

    public Vector3 getRockPos() {
        return rockPos;
    }

    public float getRockWidth() {
        return rockWidth;
    }

    public float getRockHeight() {
        return rockHeight;
    }

    public Texture getRockTexture() {
        return myRockTexture;
    }

    public boolean isTouchedGround() {
        return touchedGround;
    }
}