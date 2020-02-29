package com.inuwa.rockfall.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.inuwa.rockfall.HighScoreManager;
import com.inuwa.rockfall.RockFall;
import com.inuwa.rockfall.sprites.Button;

public class EndState extends State implements Input.TextInputListener {

    private Button restartBtn;
    private Button menuBtn;
    private Texture menuBtns;
    private TextureRegion restartBtnTex;
    private TextureRegion menuBtnTex;
    private Vector3 touchPos;

    private Music endMusic;
    private Sound buttonSound;

    private int playerScore;
    private String gameOverText, scoreLabel;
    private BitmapFont textFont;
    private float textWidth;
    private GlyphLayout glyphLayout;
    private HighScoreManager hsm;

    public EndState(StateManager stateManager, int score){
        super(stateManager);
        camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        menuBtns = new Texture("menuBtns.png");
        restartBtnTex = new TextureRegion(menuBtns, 0, 16, 20, 15);
        restartBtn = new Button(camera.position.x - Button.MENU_BUTTON_WIDTH/2, camera.position.y * 3/4,"menu", restartBtnTex, restartBtnTex);

        menuBtnTex = new TextureRegion(menuBtns, 21, 16, 20, 15);
        menuBtn = new Button(camera.position.x - Button.MENU_BUTTON_WIDTH/2, camera.position.y * 3/4 - Button.MENU_BUTTON_HEIGHT - 30,"menu", menuBtnTex, menuBtnTex);

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

        hsm = new HighScoreManager();
        Gdx.input.getTextInput(this, "Enter your name:", "", "Player");
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
        spriteBatch.draw(RockFall.background.getBackground(), 0, camera.position.y - camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);
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
        menuBtns.dispose();
        endMusic.dispose();
        buttonSound.dispose();
        textFont.dispose();
    }

    @Override
    public void input(String playerName) {
        StringBuilder str = new StringBuilder(playerName);
        String name;
        if (str.length() > 7) {
            str.setLength(7);
            name = str.toString();
            name = name + "...";
        } else
            name = playerName;
        if (name.isEmpty())
            name = "Player";
        hsm.updateHighScore(name, playerScore);
    }

    @Override
    public void canceled() {
        return;
    }
}
