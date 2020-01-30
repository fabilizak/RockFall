package com.inuwa.etd.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.inuwa.etd.EscapeTheDungeon;
import com.inuwa.etd.sprites.Button;

public class StartState extends State {

    private Button playBtn;
    private int btnWidth = Gdx.graphics.getWidth()/5;
    private int btnHeight = Gdx.graphics.getHeight()/15;
    private Texture playBtnTex;
    private TextureRegion playBtnTexReg;
    private Vector3 touchPos;

    public StartState(StateManager stateManager) {
        super(stateManager);
        camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        //System.out.println(camera.viewportWidth + " " + camera.viewportHeight);
        playBtnTex = new Texture("playBtn.png");
        playBtnTexReg = new TextureRegion(playBtnTex, playBtnTex.getWidth(), playBtnTex.getHeight());
        playBtn = new Button(camera.position.x - btnWidth/2, camera.position.y,"menu", playBtnTexReg, playBtnTexReg);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (playBtn.isPresssed(touchPos.x, touchPos.y)) {
                stateManager.set(new GameState(stateManager));
                dispose();
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(EscapeTheDungeon.background.getBackground(), 0, camera.position.y - camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);
        //spriteBatch.draw(playBtn, Gdx.graphics.getWidth()/2 - btnWidth/2, Gdx.graphics.getHeight()/2, btnWidth, btnHeight);
        playBtn.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        playBtnTex.dispose();
    }
}
