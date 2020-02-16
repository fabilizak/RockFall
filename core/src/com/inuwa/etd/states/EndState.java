package com.inuwa.etd.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.inuwa.etd.EscapeTheDungeon;
import com.inuwa.etd.sprites.Button;

public class EndState extends State {

    private Button restartBtn;
    private Texture restartBtnTex;
    private TextureRegion restartBtnTexReg;
    private Button menuBtn;
    private Texture menuBtnTex;
    private TextureRegion menuBtnTexReg;
    private int btnWidth = Gdx.graphics.getWidth()/5;
    private int btnHeight = Gdx.graphics.getHeight()/15;
    private Vector3 touchPos;

    private Music endMusic;
    private Sound buttonSound;

    private int playerScore;
    private String gameOverText, scoreLabel;
    private BitmapFont textFont;
    private float textWidth;
    private GlyphLayout glyphLayout;

    public EndState(StateManager stateManager, int score){
        super(stateManager);
        camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        restartBtnTex = new Texture("restartBtn.png");
        restartBtnTexReg = new TextureRegion(restartBtnTex, restartBtnTex.getWidth(), restartBtnTex.getHeight());
        restartBtn = new Button(camera.position.x - btnWidth/2, camera.position.y - btnHeight,"menu", restartBtnTexReg, restartBtnTexReg);

        menuBtnTex = new Texture("menuBtn.png");
        menuBtnTexReg = new TextureRegion(menuBtnTex, menuBtnTex.getWidth(), menuBtnTex.getHeight());
        menuBtn = new Button(camera.position.x - btnWidth/2, camera.position.y - 2 * btnHeight - 30,"menu", menuBtnTexReg, menuBtnTexReg);

        endMusic = Gdx.audio.newMusic(Gdx.files.internal("endMusic.mp3"));
        endMusic.setLooping(false);
        endMusic.setVolume(0.1f);
        endMusic.play();

        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button.wav"));

        playerScore = score;
        gameOverText = "GAME OVER";
        scoreLabel = "Your final score:";
        textFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        glyphLayout = new GlyphLayout();
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
            if (menuBtn.isPresssed(touchPos.x, touchPos.y)){
                endMusic.stop();
                buttonSound.play(0.5f);
                stateManager.set(new StartState(stateManager));
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
        menuBtn.draw(spriteBatch);
        textFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        textFont.getData().setScale(7, 12);
        glyphLayout.setText(textFont, gameOverText);
        textWidth = glyphLayout.width;
        textFont.draw(spriteBatch, gameOverText, camera.viewportWidth/2 - textWidth/2, camera.viewportHeight * 4/5);


        textFont.getData().setScale(5);
        glyphLayout.setText(textFont, scoreLabel);
        textWidth = glyphLayout.width;
        textFont.draw(spriteBatch, scoreLabel, camera.viewportWidth/2 - textWidth/2, camera.viewportHeight * 4/5 - 150);

        glyphLayout.setText(textFont, "" + playerScore);
        textWidth = glyphLayout.width;
        textFont.draw(spriteBatch, "" + playerScore, camera.viewportWidth/2 - textWidth/2, camera.viewportHeight * 4/5 - 220);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        restartBtnTex.dispose();
        endMusic.dispose();
        buttonSound.dispose();
        textFont.dispose();
    }
}
