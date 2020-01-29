/*package com.inuwa.etd.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inuwa.etd.EscapeTheDungeon;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private int highScore;
    private int speedCount;
    private int score;

    Label highScoreLabel;
    Label scoreLabel;
    Label highScoreText;
    Label speedLabel;
    Label speedText;
    Label scoreText;

    public Hud(SpriteBatch spriteBatch){
        highScore = 0;
        speedCount = 1;
        score = 0;

        viewport = new FitViewport(EscapeTheDungeon.V_WIDTH, EscapeTheDungeon.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        highScoreLabel = new Label(String.format("%05d", highScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        highScoreText = new Label("HIGH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        speedLabel = new Label(String.format("%02d", speedCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        speedText = new Label("SPEED", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreText = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(scoreText).expandX().padTop(10);
        table.add(speedText).expandX().padTop(10);
        table.add(highScoreText).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(speedLabel).expandX();
        table.add(highScoreLabel).expandX();

        stage.addActor(table);

    }
}
*/