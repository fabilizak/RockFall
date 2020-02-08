package com.inuwa.etd.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.inuwa.etd.EscapeTheDungeon;
import com.inuwa.etd.sprites.Button;

public class EndState extends State {

    private Button restartBtn;
    private Texture restartBtnTex;
    private TextureRegion restartBtnTexReg;
    private int btnWidth = Gdx.graphics.getWidth()/5;
    private int btnHeight = Gdx.graphics.getHeight()/15;
    private Vector3 touchPos;

    private Music endMusic;
    private Sound buttonSound;

    public EndState(StateManager stateManager){
        super(stateManager);
        camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        restartBtnTex = new Texture("restartBtn.png");
        restartBtnTexReg = new TextureRegion(restartBtnTex,restartBtnTex.getWidth(), restartBtnTex.getHeight());
        restartBtn = new Button(camera.position.x - btnWidth/2, camera.position.y,"menu", restartBtnTexReg, restartBtnTexReg);

        endMusic = Gdx.audio.newMusic(Gdx.files.internal("endMusic.mp3"));
        endMusic.setLooping(false);
        endMusic.setVolume(0.1f);
        endMusic.play();

        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button.wav"));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()){
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (restartBtn.isPresssed(touchPos.x, touchPos.y)) {
                endMusic.stop();
                buttonSound.play(0.5f);
                stateManager.set(new GameState(stateManager));
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
        restartBtn.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        restartBtnTex.dispose();
        endMusic.dispose();
        buttonSound.dispose();
    }
}
