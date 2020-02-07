package com.inuwa.etd.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Joe {
    public enum joeState { STAND, JUMP, WALK };
    public joeState currentState;
    public joeState previousState;
    public static final int GRAVITY = -10;
    private float joeWidth;
    private float joeHeight;
    private Vector3 position;
    private Vector3 velocity;
    private Texture joe;
    private boolean canJump = true, canMove = true;
    public float speed = 300;
    private Ground ground;
    private static int posGroundTop;
    private Rectangle bounds;
    private Animation<TextureRegion> joeWalk;
    private TextureRegion joeStand;
    private TextureRegion joeJump;
    private boolean walkingLeft;
    private float joeStateTimer;
    private TextureRegion currentFrame;

    public Joe(OrthographicCamera camera){
        joeWidth = camera.viewportWidth/9;
        joeHeight = (joeWidth + joeWidth/4);
        position = new Vector3((camera.viewportWidth/2 - joeWidth/2), posGroundTop, 0);
        bounds = new Rectangle(position.x, position.y, joeWidth, joeHeight);
        velocity = new Vector3(0, 0, 0);
        joe = new Texture("joe.png");
        ground = new Ground();
        posGroundTop = 40 + 2 * Button.GAME_BUTTON_HEIGHT;

        currentState = joeState.STAND;
        previousState = joeState.STAND;
        joeStateTimer = 0;
        walkingLeft = true;
        Array<TextureRegion> frames = new Array<>();
        for (int i = 2; i < 12; i++)
            frames.add(new TextureRegion(joe, i * 13, 0, 13, joe.getHeight()));
        joeWalk = new Animation(0.1f, frames);
        frames.clear();
        joeStand = new TextureRegion(joe, 0, 0, 13, joe.getHeight());
        joeJump = new TextureRegion(joe, 13, 0, 13, joe.getHeight());
        currentFrame = joeStand;
    }

    public void update(float deltaTime){
        currentFrame = getFrame(deltaTime);
        velocity.add(0, GRAVITY, 0);
        velocity.scl(deltaTime);
        position.add(velocity.x, velocity.y, 0);
            if (position.y < posGroundTop) {
                position.y = posGroundTop;
                canJump = true;
                canMove = true;
                velocity.y = 0;
            }
            if (canJump)
                velocity.x = 0;
            checkLeftBounds();
            checkRightBounds();
            bounds.setPosition(position.x, position.y);
        velocity.scl(1/deltaTime);
    }

    public TextureRegion getFrame(float deltaTime){
        currentState = getState();

        TextureRegion textureRegion;
        switch (currentState){
            case JUMP:
                textureRegion = joeJump;
                break;
            case WALK:
                textureRegion = joeWalk.getKeyFrame(joeStateTimer, true);
                break;
            case STAND:
                default:
                    textureRegion = joeStand;
                    break;
        }

        if ((velocity.x < 0 || walkingLeft) && textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            walkingLeft = true;
        } else
            if ((velocity.x > 0 || !walkingLeft) && !textureRegion.isFlipX()){
                textureRegion.flip(true, false);
                walkingLeft = false;
            }
            joeStateTimer = currentState == previousState ? joeStateTimer + deltaTime : 0;
            previousState = currentState;
            return textureRegion;
    }

    public joeState getState(){
        if (velocity.y > 0 || (velocity.y < 0 && previousState == joeState.JUMP))
            return joeState.JUMP;
        else
            if (velocity.x != 0)
            return joeState.WALK;
            else
                return joeState.STAND;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return currentFrame;
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
            velocity.x = -40;
            canJump = false;
            canMove = false;
        }
    }

    public void jumpRight(){
        if (canJump){
            velocity.y = 350;
            velocity.x = 40;
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

    public void setYVelocity(float value){
        this.velocity.y = value;
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
