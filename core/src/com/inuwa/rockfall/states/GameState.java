package com.inuwa.rockfall.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.inuwa.rockfall.HighScoreManager;
import com.inuwa.rockfall.RockFall;
import com.inuwa.rockfall.sprites.Button;
import com.inuwa.rockfall.sprites.Ground;
import com.inuwa.rockfall.sprites.Joe;
import com.inuwa.rockfall.sprites.Rock;

import java.util.ArrayList;
import java.util.List;

public class GameState extends State {

    private Joe joe;
    private Texture gameButtons;
    private TextureRegion leftBtnTex;
    private TextureRegion rightBtnTex;
    private TextureRegion leftJumpBtnTex;
    private TextureRegion rightJumpBtnTex;
    private TextureRegion pauseBtnTex;
    private TextureRegion blackPixelTex;
    private Sprite blackPixel;
    private static float moveBtnWidth = RockFall.GAMESCREENWIDTH/7.5f;
    private static float moveBtnHeight = RockFall.GAMESCREENHEIGHT/12.4f;
    private Button leftButton;
    private Button rightButton;
    private Button leftJumpButton;
    private Button rightJumpButton;
    private Button pauseButton;
    private Vector3 touchPos;
    private Ground ground;
    private List<Rock> rockList;
    private Rock currentRock;
    private Vector3 oldJoePos;

    private Music gameMusic;
    private Sound joeDies;

    private int level, levelBonus, score, highScore;
    private String scoreLabel, levelLabel, highScoreLabel;
    private BitmapFont textFont;
    private HighScoreManager hsm;
    private boolean isPaused;
    private GlyphLayout glyphLayout;

    public GameState(StateManager stateManager) {
        super(stateManager);
        camera.setToOrtho(false, RockFall.GAMESCREENWIDTH, RockFall.GAMESCREENHEIGHT);
        isPaused = false;

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.1f);
        gameMusic.play();

        joeDies = Gdx.audio.newSound(Gdx.files.internal("death.ogg"));

        joe = new Joe(camera);
        oldJoePos = new Vector3();
        oldJoePos.set(joe.getPosition());
        ground = new Ground();
        gameButtons = new Texture("gameButtons.png");
        leftBtnTex = new TextureRegion(gameButtons, 0, 0, 32, 32);
        rightBtnTex = new TextureRegion(gameButtons, 33, 0, 32, 32);
        leftJumpBtnTex = new TextureRegion(gameButtons, 0, 33, 32, 32);
        rightJumpBtnTex = new TextureRegion(gameButtons, 33, 33, 32, 32);
        pauseBtnTex = new TextureRegion(gameButtons, 0, 66, 32, 32);
        blackPixelTex = new TextureRegion(gameButtons, 0, 0, 1, 1);
        blackPixel = new Sprite(blackPixelTex);
        blackPixel.setAlpha(0.5f);
        blackPixel.setScale(camera.viewportWidth * 2, camera.viewportHeight * 2);
        leftButton = new Button(10,10,"game", leftBtnTex);
        rightButton = new Button((camera.viewportWidth - moveBtnWidth) - 10,10,"game", rightBtnTex);
        leftJumpButton = new Button( 10, 10 + moveBtnHeight + 10,"game", leftJumpBtnTex);
        rightJumpButton = new Button((camera.viewportWidth - moveBtnWidth) - 10, 10 + moveBtnHeight + 10,"game", rightJumpBtnTex);
        pauseButton = new Button((camera.viewportWidth - moveBtnWidth) - 10, camera.viewportHeight - moveBtnHeight - 10, "game", pauseBtnTex);
        rockList = new ArrayList<>();
        generateRock();

        score = 0;
        level = 1;
        levelBonus = 0;
        scoreLabel = "SCORE: 0";
        levelLabel = "LEVEL: 1";
        textFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        hsm = new HighScoreManager();
        if (hsm.getHighScores() == null)
            highScore = 0;
        else
            highScore = (int)hsm.getHighScores().values().toArray()[0];
        highScoreLabel = "HIGH: " + highScore;
        glyphLayout = new GlyphLayout();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()){
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (isPaused == true)
                isPaused = false;
            if (leftButton.isPressed(touchPos.x, touchPos.y))
                joe.move("left");
            if (rightButton.isPressed(touchPos.x, touchPos.y))
                joe.move("right");
            if (leftJumpButton.isPressed(touchPos.x, touchPos.y))
                joe.move("upLeft");
            if (rightJumpButton.isPressed(touchPos.x, touchPos.y))
                joe.move("upRight");
            if (pauseButton.isPressed(touchPos.x, touchPos.y))
                isPaused = true;
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        if (isPaused == false) {
            oldJoePos.set(joe.getPosition());
            joe.update(deltaTime);
            joeCollides(joe);

            currentRock = rockList.get(rockList.size() - 1);
            currentRock.update(deltaTime);
            if (rockCollides(currentRock)) {
                generateRock();
            }
            if (currentRock.touchedGround())
                generateRock();
            checkNextLevel(joe);

            updateScore(joe);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        spriteBatch.setProjectionMatrix(camera.combined);
            spriteBatch.begin();
            spriteBatch.draw(RockFall.background.getBackground(), 0, camera.position.y - camera.viewportHeight / 2, camera.viewportWidth, camera.viewportHeight);
            spriteBatch.draw(ground.getTexture(), 0, 0, camera.viewportWidth, Joe.getPosGroundTop());
            spriteBatch.draw(joe.getTexture(), joe.getPosition().x, joe.getPosition().y, joe.getJoeWidth(), joe.getJoeHeight());
            for (Rock rock : rockList)
                spriteBatch.draw(rock.getRockTexture(), rock.getRockPos().x, rock.getRockPos().y, rock.getRockWidth(), rock.getRockHeight());
            leftButton.draw(spriteBatch);
            rightButton.draw(spriteBatch);
            leftJumpButton.draw(spriteBatch);
            rightJumpButton.draw(spriteBatch);
            pauseButton.draw(spriteBatch);
            textFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            textFont.getData().setScale(3);
            textFont.draw(spriteBatch, levelLabel, moveBtnWidth + 30, Joe.getPosGroundTop() - 30);
            textFont.draw(spriteBatch, scoreLabel, moveBtnWidth + 30, Joe.getPosGroundTop() - 70);
            textFont.draw(spriteBatch, highScoreLabel, moveBtnWidth + 30, Joe.getPosGroundTop() - 110);
            if (isPaused == true) {
                blackPixel.draw(spriteBatch);
                textFont.getData().setScale(5);
                glyphLayout.setText(textFont, "PAUSED");
                float textWidth = glyphLayout.width;
                textFont.draw(spriteBatch, "PAUSED", camera.viewportWidth/2 - textWidth/2, camera.viewportHeight/2);
                //textFont.draw(spriteBatch, Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight(), 10, 50);
                //textFont.draw(spriteBatch, camera.viewportWidth + " " + camera.viewportHeight, 10, 100);
            }
            spriteBatch.end();
    }

    @Override
    public void dispose() {
        gameButtons.dispose();
        joe.dispose();
        for(Rock rock : rockList){
            rock.dispose();
        }
        ground.dispose();
        gameMusic.dispose();
        joeDies.dispose();
        textFont.dispose();
    }

    public void generateRock(){
        rockList.add(new Rock(camera));
    }

    public boolean rockCollides(Rock currentRock){
        for(int i = 0; i < rockList.size()-1; i++){
            Rock rockFromList = rockList.get(i);
            if (currentRock.getBounds().overlaps(rockFromList.getBounds())) {
                currentRock.setRockPos(rockFromList.getRockPos().y + rockFromList.getRockHeight());
                return true;
            }
        }
        return false;
    }

    public void joeCollides(Joe joe){
        Rectangle joeBounds = joe.getBounds();
        for (Rock rockFromList : rockList){
            Rectangle rockBounds = rockFromList.getBounds();
            if (joeBounds.overlaps(rockBounds)){
                if (oldJoePos.y >= (rockBounds.y + rockFromList.getRockHeight())) {
                    joe.setYVelocity(0);
                    joe.setYPosition(rockBounds.y + rockFromList.getRockHeight());
                    joe.setCanJump(true);
                    joe.setCanMove(true);
                } else
                if (oldJoePos.x >= (rockBounds.x + rockFromList.getRockWidth()) && oldJoePos.y < (rockBounds.y + rockFromList.getRockHeight())) {
                    joe.setXVelocity(0);
                    joe.setPosition((rockBounds.x + rockFromList.getRockWidth()), oldJoePos.y);
                }
                else
                if ((oldJoePos.x + joe.getJoeWidth()) <= rockBounds.x && oldJoePos.y < (rockBounds.y + rockFromList.getRockHeight())){
                    joe.setXVelocity(0);
                    joe.setPosition((rockBounds.x - joe.getJoeWidth()), oldJoePos.y);
                } else {
                    gameMusic.stop();
                    joeDies.play(0.5f);
                    stateManager.set(new EndState(stateManager, score));
                }
            }
        }
    }

    private void checkNextLevel(Joe joe){
        if (joe.getBounds().y + joe.getJoeHeight() >= camera.viewportHeight){
            level++;
            levelLabel = "LEVEL: " + level;
            joe.setYPosition(Joe.getPosGroundTop());
            levelBonus += 1000;
            for(Rock rock : rockList)
                rock.dispose();
            rockList.clear();
            generateRock();
        }
    }

    private void updateScore(Joe joe){
        if (score < joe.getPosition().y - Joe.getPosGroundTop() + levelBonus) {
            score =(int)(joe.getPosition().y - Joe.getPosGroundTop() + levelBonus);
            scoreLabel = "SCORE: " + score;
            if (score > highScore)
                highScoreLabel = "HIGH: " + score;
        }
    }
}
