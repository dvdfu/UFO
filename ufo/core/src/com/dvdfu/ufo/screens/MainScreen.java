package com.dvdfu.ufo.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.Image;
import com.dvdfu.ufo.MainGame;
import com.dvdfu.ufo.objects.BodyMaker;
import com.dvdfu.ufo.objects.UFO;

public class MainScreen extends AbstractScreen {
	SpriteBatch batch;
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	BodyMaker maker;
	World world;
	Const consts = new Const();
	
	Image spr;
	UFO player;
	ArrayList<Body> abductable;
	
	public MainScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, 0), true);
		maker = new BodyMaker(world);
		player = new UFO(maker.makeUFO());
		spr = new Image(consts.atlas.findRegion("star"), 10);
		abductable = new ArrayList<Body>();
		abductable.add(maker.makeTree(3, 0.25f, 1.5f));
		abductable.get(0).setTransform(10, 10, 10);
	}

	public void render(float delta) {
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		player.update();
		if (Gdx.input.isTouched()) {
			for (Body b : abductable) {
				b.applyLinearImpulse(player.getBody().getWorldCenter().cpy().sub(b.getWorldCenter()).scl(0.1f), b.getWorldCenter(), true);
			}
		}
		
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