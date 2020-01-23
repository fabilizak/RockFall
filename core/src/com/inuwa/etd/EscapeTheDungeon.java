package com.inuwa.etd;

import com.inuwa.etd.Screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EscapeTheDungeon extends Game {
	public static final int V_WIDTH = 250;
	public static final int V_HEIGHT = 450;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
	    super.render();
	}
}
