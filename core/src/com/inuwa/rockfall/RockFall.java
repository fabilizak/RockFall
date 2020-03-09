package com.inuwa.rockfall;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inuwa.rockfall.sprites.Background;
import com.inuwa.rockfall.states.StartState;
import com.inuwa.rockfall.states.StateManager;


public class RockFall extends ApplicationAdapter {

    public SpriteBatch spriteBatch;
    private StateManager stateManager;
    public static Background background;
    public static final int GAMESCREENWIDTH = 540;
    public static final int GAMESCREENHEIGHT = 1087;

    @Override
    public void create () {
        spriteBatch = new SpriteBatch();
        stateManager = new StateManager();
        Gdx.gl.glClearColor(0,0,1,1);
        stateManager.push(new StartState(stateManager));
        background = new Background();
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateManager.update(Gdx.graphics.getDeltaTime());
        stateManager.render(spriteBatch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
