package com.inuwa.etd.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inuwa.etd.EscapeTheDungeon;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.inuwa.etd.Scenes.Hud;

public class GameScreen implements Screen {

    private EscapeTheDungeon game;
    Texture backgrounds;
    TextureRegion background;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;

    private World world;
    private Box2DDebugRenderer b2dr;

    public GameScreen(EscapeTheDungeon game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport( EscapeTheDungeon.V_WIDTH, EscapeTheDungeon.V_HEIGHT, camera);
        hud = new Hud(game.batch);
        backgrounds = new Texture("background.png");
        background = new TextureRegion(backgrounds, 3 * backgrounds.getWidth()/4, 0, backgrounds.getWidth()/4, backgrounds.getHeight());
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2,0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
    }

    @Override
    public void show() {

    }

    public void inputHandler(float dt){
        if (Gdx.input.isTouched())
            camera.position.y += 100 * dt;
    }

    public void update(float dt){
        inputHandler(dt);
        camera.update();
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()/2);
        game.batch.draw(background, 0, viewport.getWorldHeight()/2, viewport.getWorldWidth(), viewport.getWorldHeight()/2);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
