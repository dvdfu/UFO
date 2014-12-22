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
	ArrayList<RevoluteJoint> roots;
	
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
		spr = new Image(consts.atlas.findRegion("star"), 10);
		abductable = new ArrayList<Body>();
		roots = new ArrayList<RevoluteJoint>();
		
		for (int i = 0; i < 10; i++) {
			abductable.add(maker.makeTree(3 + MathUtils.random(-1f, 1f), 
					0.25f + MathUtils.random(-0.1f, 0.1f), 
					1.5f + MathUtils.random(-0.25f, 0.25f)));
			abductable.get(i).setTransform((i - 4) * 5, 0, 0);
		}
		
		for (int i = 0; i < 10; i++) {
			RevoluteJointDef jointDef = new RevoluteJointDef();
			jointDef.bodyA = abductable.get(i);
			jointDef.localAnchorA.set(0, 0);
			jointDef.bodyB = floor;
			jointDef.collideConnected = true;
			jointDef.lowerAngle = -30 * MathUtils.degRad;
			jointDef.upperAngle = 30 * MathUtils.degRad;
			jointDef.enableLimit = true;
			Vector2 worldp = new Vector2(abductable.get(i).getWorldPoint(new Vector2(0, 0)));
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
			for (Body b : abductable) {
				Vector2 diff = player.getBody().getWorldCenter().cpy().sub(b.getWorldCenter());
				float scale = 6 / diff.len() / diff.len();
				b.applyLinearImpulse(diff.scl(scale), b.getWorldCenter(), true);
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
		
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		for (Body b : abductable) {
//			batch.draw(spr.get(), b.getWorldPoint(new Vector2(0, 0)).x * 10 - 5, 
//					b.getWorldPoint(new Vector2(0, 0)).y * 10 - 5);
//		}
//		batch.end();

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