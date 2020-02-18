package com.inuwa.etd.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera camera;
    protected Vector3 pointer;
    protected StateManager stateManager;

    protected State(StateManager stateManager){
        this.stateManager = stateManager;
        camera = new OrthographicCamera();
        pointer = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void dispose();
}
