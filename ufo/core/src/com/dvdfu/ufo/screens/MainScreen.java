package com.dvdfu.ufo.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.MainGame;
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.objects.BodyMaker;
import com.dvdfu.ufo.objects.Floor;
import com.dvdfu.ufo.objects.Tree;
import com.dvdfu.ufo.objects.UFO;

public class MainScreen extends AbstractScreen {
	SpriteBatch batch;
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	BodyMaker maker;
	World world;
	Const consts = new Const();
	
	ImageComponent spr;
	UFO player;
	ArrayList<Tree> trees;
	Floor floor;
	
	public MainScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawJoints(true);
		
		world = new World(new Vector2(0, -9.8f), true);
		maker = new BodyMaker(world);
		player = new UFO(world);
		player.getBody().setTransform(0, 30, 0);
		floor = new Floor(maker.makeFloor(100, 0));
		spr = new ImageComponent(Const.atlas.findRegion("star"), 10);
		trees = new ArrayList<Tree>();
		
		for (int i = 0; i < 10; i++) {
			Tree t = new Tree(world, 0.5f + MathUtils.random(0.2f), 
					3 + MathUtils.random(-1f, 0), 
					1.5f + MathUtils.random(-0.25f, 0.25f));
			t.tree.setTransform((i - 4) * 5, 0, 0);
			t.attach(floor.getBody());
			trees.add(t);
		}
	}

	public void render(float delta) {
		camera.position.set((int) (player.getBody().getWorldCenter().x * 10), (int) ((player.getBody().getWorldCenter().y - 10) * 10), 0);
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		player.update();
		
		if (Gdx.input.isTouched()) {
			for (Tree b : trees) {
				b.update();
				Vector2 diff = player.getBody().getWorldCenter().cpy().sub(b.tree.getWorldCenter());
				float scale = 3 / diff.len();
				b.tree.applyLinearImpulse(diff.scl(scale), b.tree.getWorldCenter(), true);
			}
		}
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Tree b : trees) {
			b.draw(batch);
		}
		player.draw(batch);
		floor.draw(batch);
		batch.end();

		camera.combined.scale(10, 10, 0);
		debugRenderer.render(world, camera.combined);
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