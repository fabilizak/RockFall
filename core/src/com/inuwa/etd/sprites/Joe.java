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
    private boolean canJump = true, canMove = true;
    public float speed = 500;
    private Ground ground;
    private static int posGroundTop;

    public Joe(float x, float y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        joe = new Texture("joe.png");
        ground = new Ground();
        posGroundTop = 40 + 2 * Button.GAME_BUTTON_HEIGHT;
    }

    public void update(float deltaTime){
        velocity.add(0, GRAVITY, 0);
        velocity.scl(deltaTime);
        position.add(velocity.x, velocity.y, 0);
            if (position.y < posGroundTop) {
                position.y = posGroundTop;
                canJump = true;
                canMove = true;
            }
            if (canJump)
                velocity.x = 0;
            checkLeftBounds();
            checkRightBounds();
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
                jumpLeft();
                break;
            case "upRight":
                jumpRight();
                break;
            case "stop":
                velocity.x = 0;

        }
    }

    public void moveLeft(){
        if (canMove)
            velocity.x += -speed;
    }

    public void moveRight(){
        if (canMove)
            velocity.x += speed;
    }

    public void jumpLeft(){
        if (canJump){
            velocity.y = 350;
            velocity.x = -100;
            canJump = false;
            canMove = false;
        }
    }

    public void jumpRight(){
        if (canJump){
            velocity.y = 350;
            velocity.x = 100;
            canJump = false;
            canMove = false;
        }
    }

    private void checkLeftBounds(){
        if (position.x < 0)
            position.x = 0;
    }

    private void checkRightBounds(){
        if(position.x > Gdx.graphics.getWidth()/2 - JOE_WIDTH)
            position.x = Gdx.graphics.getWidth()/2 - JOE_WIDTH;
    }

    public static int getPosGroundTop() {
        return posGroundTop;
    }
}
