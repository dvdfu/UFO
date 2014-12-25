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
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.components.ShaderComponent;
import com.dvdfu.ufo.objects.Cow;
import com.dvdfu.ufo.objects.Floor;
import com.dvdfu.ufo.objects.GameObj;
import com.dvdfu.ufo.objects.Hydrant;
import com.dvdfu.ufo.objects.Tree;
import com.dvdfu.ufo.objects.Truck;
import com.dvdfu.ufo.objects.UFO;

public class MainScreen extends AbstractScreen {
	SpriteBatch batch;
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	World world;
	Const consts = new Const();
	ShaderComponent fbShader;

	ImageComponent spr;
	UFO player;
	ArrayList<GameObj> objects;
	Floor floor;

	public MainScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		debugRenderer = new Box2DDebugRenderer();
		// debugRenderer.setDrawVelocities(true);
		fbShader = new ShaderComponent("shaders/passthrough.vsh", "shaders/passthrough.fsh");
		batch.setShader(fbShader);
		batch.enableBlending();

		world = new World(new Vector2(0, -50f), true);
		player = new UFO(world);
		player.getBody().setTransform(0, 30, 0);
		floor = new Floor(world);
		spr = new ImageComponent(Const.atlas.findRegion("star"), 10);
		objects = new ArrayList<GameObj>();

		for (int i = 0; i < 7; i++) {
			Tree t = new Tree(world);
			t.getBody().setTransform((i + 2) * 5, 0, 0);
			t.attach(floor.getBody());
			objects.add(t);
		}

		Cow cow = new Cow(world);
		cow.getBody().setTransform(-1, 5, 0);
		objects.add(cow);

		Hydrant hydrant = new Hydrant(world);
		hydrant.getBody().setTransform(-30, 0, 0);
		hydrant.attach(floor.getBody());
		objects.add(hydrant);

		Truck truck = new Truck(world);
		truck.setPosition(-40, 0);
		objects.add(truck);
	}

	public void render(float delta) {
		camera.position.set((int) (player.getBody().getWorldCenter().x * 10), (int) ((player.getBody().getWorldCenter().y - 5) * 10), 0);
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		player.update();

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

			for (GameObj b : objects) {
				b.update();
				Vector2 diff = player.getBody().getWorldCenter().cpy().sub(b.getBody().getWorldCenter());
				diff.scl(150f / diff.len());
				if (Math.abs(diff.x) > 30) {
					b.getBody().applyForce(new Vector2(diff.x, 0), b.getBody().getWorldCenter(), true);
				} else {
					b.getBody().applyForce(new Vector2(0, diff.y), b.getBody().getWorldCenter(), true);
				}
			}
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (GameObj b : objects) {
			b.draw(batch);
		}
		floor.draw(batch);
		player.draw(batch);
		batch.end();

		camera.combined.scale(10, 10, 0);
//		debugRenderer.render(world, camera.combined);
	}

	public void resize(int width, int height) {}

	public void show() {}

	public void hide() {}

	public void pause() {}

	public void resume() {}

	public void dispose() {
		world.dispose();
	}

}