package com.inuwa.etd.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.inuwa.etd.EscapeTheDungeon;
import com.inuwa.etd.sprites.Button;
import com.inuwa.etd.sprites.Joe;


public class GameState extends State {

    private Joe joe;
    private Texture moveButtons;
    private TextureRegion leftBtnTex;
    private TextureRegion rightBtnTex;
    private TextureRegion leftJumpBtnTex;
    private TextureRegion rightJumpBtnTex;
    private int btnWidth = Gdx.graphics.getWidth()/15;
    private int btnHeight = Gdx.graphics.getHeight()/25;
    private Button leftButton;
    private Button rightButton;
    private Button leftJumpButton;
    private Button rightJumpButton;
    private Vector3 touchPos;
    private Texture ground;
    private TextureRegion groundRegion;

    protected GameState(StateManager stateManager) {
        super(stateManager);
        camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        joe = new Joe(camera.position.x - Joe.JOE_WIDTH/2, 500);
        moveButtons = new Texture("moveButtons.png");
        leftBtnTex = new TextureRegion(moveButtons, 0, 0, 32, 32);
        rightBtnTex = new TextureRegion(moveButtons, 33, 0, 32, 32);
        leftJumpBtnTex = new TextureRegion(moveButtons, 0, 33, 32, 32);
        rightJumpBtnTex = new TextureRegion(moveButtons, 33, 33, 32, 32);
        leftButton = new Button(10,10, btnWidth, btnHeight, leftBtnTex, leftBtnTex);
        rightButton = new Button((camera.viewportWidth - btnWidth) - 10,10, btnWidth, btnHeight, rightBtnTex, rightBtnTex);
        leftJumpButton = new Button( 10, 10 + btnHeight + 10, btnWidth, btnHeight, leftJumpBtnTex, leftJumpBtnTex);
        rightJumpButton = new Button((camera.viewportWidth - btnWidth) - 10, 10 + btnHeight + 10, btnWidth, btnHeight, rightJumpBtnTex, rightJumpBtnTex);
        ground = new Texture("ground.png");
        groundRegion = new TextureRegion(ground, 0, 0, 200, 100);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()){
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (leftButton.isClicked(touchPos.x, touchPos.y))
                joe.move("left");
            if (rightButton.isClicked(touchPos.x, touchPos.y))
                joe.move("right");
            if (leftJumpButton.isClicked(touchPos.x, touchPos.y))
                joe.move("upLeft");
            if (rightJumpButton.isClicked(touchPos.x, touchPos.y))
                joe.move("upRight");
        };
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        joe.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(EscapeTheDungeon.background.getBackground(), 0, camera.position.y - camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);
        //spriteBatch.draw(ground,0,0, camera.viewportWidth,camera.viewportHeight/5);
        spriteBatch.draw(groundRegion,0,0, camera.viewportWidth, Joe.playerOffset);
        spriteBatch.draw(joe.getTexture(), joe.getPosition().x, joe.getPosition().y, Joe.JOE_WIDTH, Joe.JOE_HEIGHT);
        leftButton.draw(spriteBatch);
        rightButton.draw(spriteBatch);
        leftJumpButton.draw(spriteBatch);
        rightJumpButton.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        moveButtons.dispose();
    }
}
