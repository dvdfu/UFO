package com.dvdfu.ufo;

import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.dvdfu.ufo.screens.AbstractScreen;
import com.dvdfu.ufo.screens.GroundScreen;

public class MainGame extends Game {
	private Stack<AbstractScreen> screens;
	private FPSLogger framerate;

	public void create() {
		framerate = new FPSLogger();
		screens = new Stack<AbstractScreen>();
		enterScreen(new GroundScreen(this));
	}

	public void enterScreen(AbstractScreen screen) {
		if (!screens.isEmpty()) {
			screens.peek().pause();
		}
		screens.push(screen);
		setScreen(screens.peek());
	}

	public void changeScreen(AbstractScreen screen) {
		if (screens.isEmpty()) {
			return;
		}
		screens.pop();
		screens.push(screen);
		setScreen(screens.peek());
	}

	public void exitScreen() {
		if (screens.isEmpty()) {
			Gdx.app.exit();
		}
		screens.pop();
		screens.peek().resume();
		setScreen(screens.peek());
	}

	public void dispose() {}

	public void render() {
		framerate.log();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.7f, 0.9f, 1, 1);
//		Gdx.gl.glClearColor(0.3f, 0.5f, 0.7f, 1);
		if (getScreen() != null) {
			super.render();
		}
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void pause() {}

	public void resume() {}
}