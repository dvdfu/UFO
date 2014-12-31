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
import com.dvdfu.ufo.components.GUIComponent;
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
	private GUIComponent sprite;

	public GroundScreen(MainGame game) {
		super(game);
		Const c = new Const();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawJoints(false);
		debugRenderer.setDrawVelocities(true);
		world = new World(new Vector2(0, -50), true);
		floor = new Terrain(world);
		player = new UFO(world);
		objects = new ArrayList<GameObj>();
		player.setPosition(30, floor.getHeight(30) + 10);
		sprite = new GUIComponent(Const.atlas.findRegion("default"));
		sprite.setCamera();

		for (int i = 0; i < 30; i++) {
			Tree t = new Tree(world);
			t.setPosition((i + 2) * 10, floor.getHeight((i + 2) * 10));
			t.attach(floor.getBody());
			objects.add(t);
			Tractor tractor = new Tractor(world);
			tractor.setPosition((i + 2) * 10 + 3, floor.getHeight((i + 2) * 10 + 3));
			objects.add(tractor);
			Cow cow = new Cow(world);
			cow.setPosition((i + 2) * 10, floor.getHeight((i + 2) * 10) + 9);
			objects.add(cow);
		}
		Truck truck = new Truck(world);
		truck.setPosition(10 * 10, floor.getHeight(10 * 10) + 3);
		objects.add(truck);
	}

	public void render(float delta) {
		camera.position.set(player.getBody().getWorldCenter().x * 10, (player.getBody().getWorldCenter().y - 5) * 10, 0);
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		player.update();
		player.setGroundHeight(floor.getHeight(player.getBody().getPosition().x));
		for (GameObj b : objects) {
			b.update();
		}
		if (player.isAbducting()) {
			for (GameObj b : objects) {
				Vector2 diff = player.getBody().getWorldCenter().cpy().sub(b.getBody().getWorldCenter());
				if (diff.y > 0) {
					float distance = Math.abs(diff.x);
					diff.scl(400f / diff.len());
					if (distance < 3) { // inside abduction ray
						b.getBody().setLinearVelocity(b.getBody().getLinearVelocity().scl(0.5f));
						// b.getBody().setLinearVelocity(0, 0);
						b.getBody().applyForce(new Vector2(diff.x * 40, diff.y), b.getBody().getWorldCenter(), true);
					} else if (distance < 20) {
						b.getBody().applyForce(new Vector2(diff.x / 5, 0), b.getBody().getWorldCenter(), true);
					}
				}
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.zoom /= 1.05f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.zoom *= 1.05f;
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		floor.draw(batch);
		for (GameObj b : objects) {
			b.draw(batch);
		}
		player.draw(batch);
		// batch.flush();
		// sprite.draw(batch, 0, 0);
		batch.end();
		// camera.combined.scale(10, 10, 0);
		// debugRenderer.render(world, camera.combined);
	}

	public void resize(int width, int height) {}

	public void show() {}

	public void hide() {}

	public void pause() {}

	public void resume() {}

	public void dispose() {}

}
