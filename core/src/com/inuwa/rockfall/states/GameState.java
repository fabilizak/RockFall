package com.inuwa.rockfall.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import java.util.Map;

public class GameState extends State {

    private Joe joe;
    private Texture moveButtons;
    private TextureRegion leftBtnTex;
    private TextureRegion rightBtnTex;
    private TextureRegion leftJumpBtnTex;
    private TextureRegion rightJumpBtnTex;
    private static int moveBtnWidth = Gdx.graphics.getWidth()/15;
    private static int moveBtnHeight = Gdx.graphics.getHeight()/25;
    private Button leftButton;
    private Button rightButton;
    private Button leftJumpButton;
    private Button rightJumpButton;
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

    public GameState(StateManager stateManager) {
        super(stateManager);
        camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        System.out.println(camera.viewportWidth + " " + camera.viewportHeight);

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.1f);
        gameMusic.play();

        joeDies = Gdx.audio.newSound(Gdx.files.internal("death.ogg"));

        joe = new Joe(camera);
        oldJoePos = new Vector3();
        oldJoePos.set(joe.getPosition());
        ground = new Ground();
        moveButtons = new Texture("moveButtons.png");
        leftBtnTex = new TextureRegion(moveButtons, 0, 0, 32, 32);
        rightBtnTex = new TextureRegion(moveButtons, 33, 0, 32, 32);
        leftJumpBtnTex = new TextureRegion(moveButtons, 0, 33, 32, 32);
        rightJumpBtnTex = new TextureRegion(moveButtons, 33, 33, 32, 32);
        leftButton = new Button(10,10,"game", leftBtnTex, leftBtnTex);
        rightButton = new Button((camera.viewportWidth - moveBtnWidth) - 10,10,"game", rightBtnTex, rightBtnTex);
        leftJumpButton = new Button( 10, 10 + moveBtnHeight + 10,"game", leftJumpBtnTex, leftJumpBtnTex);
        rightJumpButton = new Button((camera.viewportWidth - moveBtnWidth) - 10, 10 + moveBtnHeight + 10,"game", rightJumpBtnTex, rightJumpBtnTex);
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
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()){
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (leftButton.isPresssed(touchPos.x, touchPos.y))
                joe.move("left");
            if (rightButton.isPresssed(touchPos.x, touchPos.y))
                joe.move("right");
            if (leftJumpButton.isPresssed(touchPos.x, touchPos.y))
                joe.move("upLeft");
            if (rightJumpButton.isPresssed(touchPos.x, touchPos.y))
                joe.move("upRight");
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        oldJoePos.set(joe.getPosition());
        joe.update(deltaTime);
        joeCollides(joe);

        currentRock = rockList.get(rockList.size() - 1);
        currentRock.update(deltaTime);
        if (rockCollides(currentRock)) {
            generateRock();
        }
        if (currentRock.isTouchedGround())
            generateRock();
        checkNextLevel(joe);

        updateScore(joe);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(RockFall.background.getBackground(), 0, camera.position.y - camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.draw(ground.getTexture(),0,0, camera.viewportWidth, Joe.getPosGroundTop());
        spriteBatch.draw(joe.getTexture(), joe.getPosition().x, joe.getPosition().y, joe.getJoeWidth(), joe.getJoeHeight());
        leftButton.draw(spriteBatch);
        rightButton.draw(spriteBatch);
        leftJumpButton.draw(spriteBatch);
        rightJumpButton.draw(spriteBatch);
        for (Rock rock : rockList)
            spriteBatch.draw(rock.getRockTexture(), rock.getRockPos().x, rock.getRockPos().y, rock.getRockWidth(), rock.getRockHeight());
        textFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        textFont.getData().setScale(3);
        textFont.draw(spriteBatch, levelLabel, moveBtnWidth + 30, Joe.getPosGroundTop() - 30);
        textFont.draw(spriteBatch, scoreLabel, moveBtnWidth + 30, Joe.getPosGroundTop() - 70);
        textFont.draw(spriteBatch, highScoreLabel, moveBtnWidth + 30, Joe.getPosGroundTop() - 110);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        moveButtons.dispose();
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
                    joe.setYPosition(rockBounds.y + rockFromList.getRockHeight());
                    joe.setCanJump(true);
                    joe.setCanMove(true);
                    joe.setYVelocity(0);
                } else
                if (oldJoePos.x >= (rockBounds.x + rockFromList.getRockWidth()) && oldJoePos.y < (rockBounds.y + rockFromList.getRockHeight())) {
                    joe.setPosition((rockBounds.x + rockFromList.getRockWidth()), oldJoePos.y);
                    joe.setXVelocity(0);
                }
                else
                if ((oldJoePos.x + joe.getJoeWidth()) <= rockBounds.x && oldJoePos.y < (rockBounds.y + rockFromList.getRockHeight())){
                    joe.setPosition((rockBounds.x - joe.getJoeWidth()), oldJoePos.y);
                    joe.setXVelocity(0);
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
            levelBonus += 500;
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
