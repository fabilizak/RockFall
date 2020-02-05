package com.inuwa.etd.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Joe {
    public static final int GRAVITY = -10;
    //public static final int JOE_WIDTH = 60;
    //public static final int JOE_HEIGHT = 100;
    private float joeWidth;
    private float joeHeight;
    private Vector3 position;
    private Vector3 velocity;
    private Texture joe;
    private boolean canJump = true, canMove = true, wouldCollide = false;
    public float speed = 500;
    private Ground ground;
    private static int posGroundTop;
    private Rectangle bounds;

    public Joe(OrthographicCamera camera){
        joeWidth = camera.viewportWidth/7;
        joeHeight = (joeWidth + joeWidth/4);
        position = new Vector3((camera.viewportWidth/2 - joeWidth/2), posGroundTop, 0);
        bounds = new Rectangle(position.x, position.y, joeWidth, joeHeight);
        velocity = new Vector3(0, 0, 0);
        joe = new Texture("joe.png");
        ground = new Ground();
        posGroundTop = 40 + 2 * Button.GAME_BUTTON_HEIGHT;
    }

    public void update(float deltaTime){
        if (canJump == false)
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
            bounds.setPosition(position.x, position.y);
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
        if (position.x < 0) {
            position.x = 0;
            bounds.x = position.x;
        }
    }

    private void checkRightBounds(){
        if(position.x > Gdx.graphics.getWidth()/2 - joeWidth) {
            position.x = Gdx.graphics.getWidth() / 2 - joeWidth;
            bounds.x = position.x;
        }
    }

    public static int getPosGroundTop() {
        return posGroundTop;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
        this.bounds.setPosition(position.x, position.y);
    }

    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
        this.bounds.x = x;
        this.bounds.y = y;
    }

    public void setYPosition(float y){
        this.position.y = y;
        this.bounds.y = y;
    }

    public void setXVelocity(float value) {
        this.velocity.x = value;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public float getJoeWidth() {
        return joeWidth;
    }

    public float getJoeHeight() {
        return joeHeight;
    }

    public void dispose(){
        joe.dispose();
    }
}
