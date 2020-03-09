package com.inuwa.rockfall.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.inuwa.rockfall.RockFall;
import com.inuwa.rockfall.sprites.Button;

public class StartState extends State {

    private Button playBtn;
    private Button scoreBtn;
    private Button exitBtn;
    private Texture menuBtns;
    private TextureRegion playBtnTex;
    private TextureRegion scoreBtnTex;
    private TextureRegion exitBtnTex;
    private Vector3 touchPos;
    private Texture title;
    private float titleWidth, titleHeight;

    private Music menuMusic;
    private Sound buttonSound;

    public StartState(StateManager stateManager) {
        super(stateManager);
        camera.setToOrtho(false, RockFall.GAMESCREENWIDTH, RockFall.GAMESCREENHEIGHT);
        menuBtns = new Texture("menuBtns.png");
        title = new Texture("title.png");
        titleWidth = camera.viewportWidth * 8/10;
        titleHeight = camera.viewportHeight/3;
        playBtnTex = new TextureRegion(menuBtns, 0, 0, 20, 15);
        playBtn = new Button(camera.position.x - Button.MENU_BUTTON_WIDTH/2, camera.position.y * 3/4,"menu", playBtnTex);
        scoreBtnTex = new TextureRegion(menuBtns, 42, 0, 20, 15);
        scoreBtn = new Button(camera.position.x - Button.MENU_BUTTON_WIDTH/2, camera.position.y * 3/4 - Button.MENU_BUTTON_HEIGHT - 30,"menu", scoreBtnTex);
        exitBtnTex = new TextureRegion(menuBtns, 21, 0, 20, 15);
        exitBtn = new Button(camera.position.x - Button.MENU_BUTTON_WIDTH/2, camera.position.y * 3/4 - 2 * Button.MENU_BUTTON_HEIGHT - 60,"menu", exitBtnTex);

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
            if (playBtn.isPressed(touchPos.x, touchPos.y)) {
                menuMusic.stop();
                buttonSound.play(0.5f);
                stateManager.set(new GameState(stateManager));
            }

            if (scoreBtn.isPressed(touchPos.x, touchPos.y)){
                buttonSound.play(0.5f);
                stateManager.set(new ScoreboardState(stateManager));
            }

            if (exitBtn.isPressed(touchPos.x, touchPos.y)){
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
        spriteBatch.draw(RockFall.background.getBackground(), 0, camera.position.y - camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.draw(title, camera.viewportWidth/2 - titleWidth/2, camera.viewportHeight * 3/4 - titleHeight/2, titleWidth, titleHeight);
        playBtn.draw(spriteBatch);
        scoreBtn.draw(spriteBatch);
        exitBtn.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        menuBtns.dispose();
        menuMusic.dispose();
        buttonSound.dispose();
    }
}
