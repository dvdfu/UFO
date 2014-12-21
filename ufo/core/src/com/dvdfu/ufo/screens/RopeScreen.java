package com.dvdfu.ufo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.MainGame;
import com.dvdfu.ufo.objects.Rope;

public class RopeScreen extends AbstractScreen {
	SpriteBatch batch;
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	World world;
	Rope rope;
	Const consts = new Const();

	public RopeScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, 0), true);
		rope = new Rope(world, 60, 0.5f);
	}

	public void render(float delta) {
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) rope.getHead().applyForceToCenter(-400, 0, true);
		if (Gdx.input.isKeyPressed(Input.Keys.D)) rope.getHead().applyForceToCenter(400, 0, true);
		if (Gdx.input.isKeyPressed(Input.Keys.S)) rope.getHead().applyForceToCenter(0, -400, true);
		if (Gdx.input.isKeyPressed(Input.Keys.W)) rope.getHead().applyForceToCenter(0, 400, true);
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) rope.getTail().applyForceToCenter(-400, 0, true);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rope.getTail().applyForceToCenter(400, 0, true);
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) rope.getTail().applyForceToCenter(0, -400, true);
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) rope.getTail().applyForceToCenter(0, 400, true);
		
		camera.zoom = 1 / 10f;
		camera.update();
		debugRenderer.render(world, camera.combined);
		camera.zoom = 1;
	}

	public void resize(int width, int height) {
	}

	public void show() {
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		world.dispose();
	}

}