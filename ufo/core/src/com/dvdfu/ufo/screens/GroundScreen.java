package com.dvdfu.ufo.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.MainGame;
import com.dvdfu.ufo.Terrain;
import com.dvdfu.ufo.objects.Cow;
import com.dvdfu.ufo.objects.GameObj;
import com.dvdfu.ufo.objects.Tree;
import com.dvdfu.ufo.objects.UFO;
import com.dvdfu.ufo.objects.vehicles.Tractor;
import com.dvdfu.ufo.objects.vehicles.Truck;

public class GroundScreen extends AbstractScreen {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private World world;
	private Terrain floor;
	private ArrayList<GameObj> objects;
	private UFO player;

	public GroundScreen(MainGame game) {
		super(game);
		Const c = new Const();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawJoints(false);
		world = new World(new Vector2(0, -50), true);
		floor = new Terrain(world);
		objects = new ArrayList<GameObj>();
		player = new UFO(world);
		player.setPosition(30, 30);
		
		for (int i = 0; i < 30; i++) {
			Tree t = new Tree(world);
			t.setPosition((i + 2) * 10, floor.getHeight((i + 2) * 10));
			t.attach(floor.getBody());
			objects.add(t);
//			Truck truck = new Truck(world);
//			truck.setPosition((i + 2) * 10, floor.getHeight((i + 2) * 10) + 3);
//			objects.add(truck);
			Tractor tractor = new Tractor(world);
			tractor.setPosition((i + 2) * 10, floor.getHeight((i + 2) * 10) + 6);
			objects.add(tractor);
			Cow cow = new Cow(world);
			cow.setPosition((i + 2) * 10, floor.getHeight((i + 2) * 10) + 9);
			objects.add(cow);
		}
	}

	public void render(float delta) {
		camera.position.set((int) (player.getBody().getWorldCenter().x * 10),
				(int) ((player.getBody().getWorldCenter().y - 15) * 10), 0);
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		player.update();
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
			player.setGroundHeight(floor.getHeight(player.getBody().getPosition().x));
			for (GameObj b : objects) {
				b.update();
				Vector2 diff = player.getBody().getWorldCenter().cpy().sub(b.getBody().getWorldCenter());
				diff.scl(150f / diff.len());
				b.getBody().applyForce(diff, b.getBody().getWorldCenter(), true);
			}
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		floor.draw(batch);
		for (GameObj b : objects) {
			b.draw(batch);
		}
		player.draw(batch);
		batch.end();
//		camera.combined.scale(10, 10, 0);
//		debugRenderer.render(world, camera.combined);
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
	}

}
