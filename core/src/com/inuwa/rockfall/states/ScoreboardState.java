package com.inuwa.rockfall.states;

import com.badlogic.gdx.Gdx;
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

import java.util.Map;

public class ScoreboardState extends State {

    private Texture menuBtns;
    private TextureRegion returnBtnTex;
    private Button returnBtn;
    private Vector3 touchPos;
    private Sound buttonSound;
    private HighScoreManager hsm;
    private Map<String,Integer> highScores;
    private BitmapFont textFont;
    private float scoreY;
    private GlyphLayout glyphLayout;
    private float hsTextWidth, hsTextHeight;
    private String hsText;
    private int placement;

    public ScoreboardState(StateManager stateManager){
        super(stateManager);
        camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        menuBtns = new Texture("menuBtns.png");
        returnBtnTex = new TextureRegion(menuBtns, 42, 16, 20, 15);
        returnBtn = new Button(10, 10,"menu", returnBtnTex, returnBtnTex);

        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button.wav"));

        hsm = new HighScoreManager();
        highScores = hsm.getHighScores();
        textFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        glyphLayout = new GlyphLayout();

        hsText = "HIGHSCORES";
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (returnBtn.isPresssed(touchPos.x, touchPos.y)) {
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
        returnBtn.draw(spriteBatch);
        textFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        textFont.getData().setScale(7, 12);
        glyphLayout.setText(textFont, hsText);
        hsTextWidth = glyphLayout.width;
        hsTextHeight = glyphLayout.height;
        textFont.draw(spriteBatch, hsText, camera.viewportWidth/2 - hsTextWidth/2, camera.viewportHeight - 30);

        textFont.getData().setScale(5);
        scoreY = camera.viewportHeight - hsTextHeight - 70;
        placement = 1;
        for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
            textFont.draw(spriteBatch, "" + placement + ": "+ entry.getKey() + "     " + entry.getValue(), 10, scoreY);
            scoreY -= 70;
            placement++;
        }
        scoreY = camera.viewportHeight - hsTextHeight - 70;
        placement = 1;
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        menuBtns.dispose();
        buttonSound.dispose();
    }
}
