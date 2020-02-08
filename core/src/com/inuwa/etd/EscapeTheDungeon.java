package com.inuwa.etd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inuwa.etd.sprites.Background;
import com.inuwa.etd.states.StartState;
import com.inuwa.etd.states.StateManager;


public class EscapeTheDungeon extends ApplicationAdapter {

    public SpriteBatch spriteBatch;
    private StateManager stateManager;
    public static Background background;

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
