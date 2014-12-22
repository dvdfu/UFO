package com.dvdfu.ufo.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.MainGame;
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.objects.BodyMaker;
import com.dvdfu.ufo.objects.Rope;
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
	ArrayList<RevoluteJoint> roots;
	Rope rope;
	
	public MainScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawJoints(true);
		
		world = new World(new Vector2(0, -9.8f), true);
		maker = new BodyMaker(world);
		player = new UFO(maker.makeUFO());
		player.getBody().setTransform(0, 20, 0);
		Body floor = maker.makeFloor(100, 0);
		spr = new ImageComponent(Const.atlas.findRegion("star"), 10);
		trees = new ArrayList<Tree>();
		roots = new ArrayList<RevoluteJoint>();
		rope = new Rope(world, 10, 1);
		
		for (int i = 0; i < 10; i++) {
			Tree t = new Tree(world, 0.25f + MathUtils.random(-0.1f, 0.1f), 
					3 + MathUtils.random(-1f, 1f), 
					1.5f + MathUtils.random(-0.25f, 0.25f));
			t.tree.setTransform((i - 4) * 5, 0, 0);
			trees.add(t);
		}

		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.localAnchorA.set(0, 0);
		jointDef.bodyB = floor;
		jointDef.collideConnected = true;
		jointDef.lowerAngle = -30 * MathUtils.degRad;
		jointDef.upperAngle = 30 * MathUtils.degRad;
		jointDef.enableLimit = true;
		
		for (int i = 0; i < 10; i++) {
			jointDef.bodyA = trees.get(i).tree;
			Vector2 worldp = new Vector2(trees.get(i).tree.getWorldPoint(new Vector2(0, 0)));
			jointDef.localAnchorB.set(floor.getLocalVector(worldp));
			roots.add((RevoluteJoint) world.createJoint(jointDef));
		}
	}

	public void render(float delta) {
		camera.position.set(player.getBody().getWorldCenter().scl(10), 0);
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		player.update();
		if (Gdx.input.isTouched()) {
			for (Tree b : trees) {
				Vector2 diff = player.getBody().getWorldCenter().cpy().sub(b.tree.getWorldCenter());
				float scale = 6 / diff.len() / diff.len();
				b.tree.applyLinearImpulse(diff.scl(scale), b.tree.getWorldCenter(), true);
			}
		}
		
		for (int i = 0; i < roots.size(); i++) {
			RevoluteJoint rj = roots.get(i);
			if (rj.getReactionForce(1 / delta).len() > 700) {
				world.destroyJoint(rj);
				roots.remove(rj);
				i--;
			}
		}
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Tree b : trees) {
			b.draw(batch);
		}
		rope.draw(batch);
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