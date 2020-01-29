package com.inuwa.etd.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inuwa.etd.EscapeTheDungeon;

public class StartState extends State {

    private Texture playBtn;
    private int btnWidth = Gdx.graphics.getWidth()/5;
    private int btnHeight = Gdx.graphics.getHeight()/15;

    public StartState(StateManager stateManager) {
        super(stateManager);
        //camera.setToOrtho(false, EscapeTheDungeon.WIDTH, EscapeTheDungeon.HEIGHT);
        playBtn = new Texture("playBtn.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            stateManager.set(new GameState(stateManager));
            dispose();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        //spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(EscapeTheDungeon.background.getBackground(), 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.draw(playBtn, Gdx.graphics.getWidth()/2 - btnWidth/2, Gdx.graphics.getHeight()/2, btnWidth, btnHeight);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        playBtn.dispose();
    }
}
