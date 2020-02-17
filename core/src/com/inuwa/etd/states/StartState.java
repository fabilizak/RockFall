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

public class StartState extends State {

    private Button playBtn;
    private Button exitBtn;
    private Texture menuBtns;
    private TextureRegion playBtnTex;
    private TextureRegion exitBtnTex;
    private Vector3 touchPos;

    private Music menuMusic;
    private Sound buttonSound;

    public StartState(StateManager stateManager) {
        super(stateManager);
        camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        menuBtns = new Texture("menuBtns.png");
        playBtnTex = new TextureRegion(menuBtns, 0, 0, 20, 15);
        playBtn = new Button(camera.position.x - Button.MENU_BUTTON_WIDTH/2, camera.position.y * 3/4,"menu", playBtnTex, playBtnTex);
        exitBtnTex = new TextureRegion(menuBtns, 21, 0, 20, 15);
        exitBtn = new Button(camera.position.x - Button.MENU_BUTTON_WIDTH/2, camera.position.y * 3/4 - Button.MENU_BUTTON_HEIGHT - 30,"menu", exitBtnTex, exitBtnTex);

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menuMusic.wav"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.1f);
        menuMusic.play();

        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button.wav"));
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (playBtn.isPresssed(touchPos.x, touchPos.y)) {
                menuMusic.stop();
                buttonSound.play(0.5f);
                stateManager.set(new GameState(stateManager));
            }

            if (exitBtn.isPresssed(touchPos.x, touchPos.y)){
                menuMusic.stop();
                buttonSound.play(0.5f);
                System.exit(0);
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
        playBtn.draw(spriteBatch);
        exitBtn.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        //playBtnTex.dispose();
        //exitBtnTex.dispose();
        menuBtns.dispose();
        menuMusic.dispose();
        buttonSound.dispose();
    }
}
