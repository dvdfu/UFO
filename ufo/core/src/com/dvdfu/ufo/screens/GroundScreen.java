package com.dvdfu.ufo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.MainGame;
import com.dvdfu.ufo.components.SpriteComponent;

public class GroundScreen extends AbstractScreen {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private World world;
	
	private float[] heightmap;
	private SpriteComponent testSprite;
	private final int n = 10;
	private final int scale = 32;

	public GroundScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, 0), true);
		
		heightmap = new float[n];
		for (int i = 0; i < n; i++) {
			heightmap[i] = MathUtils.random(3.0f);
		}
		Const c = new Const();
		testSprite = new SpriteComponent(Const.atlas.findRegion("block1"));
	}

	public void render(float delta) {
		camera.position.set(0, 0, 0);
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < n; i++) {
			testSprite.drawCentered(batch, (i - 5) * scale, heightmap[i] * scale);
		}
		batch.end();

		camera.combined.scale(scale, scale, 0);
		debugRenderer.render(world, camera.combined);
		}

	public void resize(int width, int height) {}

	public void show() {}

	public void hide() {}

	public void pause() {}

	public void resume() {}

	public void dispose() {}

}
