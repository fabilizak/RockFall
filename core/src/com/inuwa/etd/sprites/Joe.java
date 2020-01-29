package com.inuwa.etd.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Joe {
    public static final int GRAVITY = -10;
    public static final int JOE_WIDTH = 60;
    public static final int JOE_HEIGHT = 100;
    private Vector3 position;
    private Vector3 velocity;
    public static float playerOffset;

    private Texture joe;

    public Joe(float x, float y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        joe = new Texture("joe.png");
        playerOffset = 20 + 2 * 10 + 2 * Gdx.graphics.getHeight()/25;
    }

    public void update(float deltaTime){
        velocity.add(0, GRAVITY, 0);
        velocity.scl(deltaTime);
        position.add(0, velocity.y, 0);
            if (position.y < playerOffset)
                position.y = playerOffset;
        velocity.scl(1/deltaTime);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return joe;
    }

    public void move(String string){
        switch (string){
            case "left":
                moveLeft();
                break;
            case "right":
                moveRight();
                break;
            case "upLeft":
                velocity.y = 250;
                moveLeft();
                break;
            case "upRight":
                velocity.y = 250;
                moveRight();
                break;

        }
    }

    public void moveLeft(){
        position.x += -10;
        if (position.x < 0)
            position.x = 0;
    }

    public void moveRight(){
        position.x += 10;
        if(position.x > Gdx.graphics.getWidth()/2 - JOE_WIDTH)
            position.x = Gdx.graphics.getWidth()/2 - JOE_WIDTH;
    }
}
