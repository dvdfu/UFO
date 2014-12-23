package com.dvdfu.ufo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.SpriteComponent;

public class UFO {
	private Body body;
	private SpriteComponent sprite;
	public Ray ray;

	public UFO(World world) {
		BodyMaker maker = new BodyMaker(world);
		body = maker.makeUFO();
		ray = new Ray(world);
		
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = body;
		jointDef.localAnchorA.set(0, -0.6f);
		jointDef.bodyB = ray.body;
		jointDef.localAnchorB.set(0, 0);
		
		world.createJoint(jointDef);
		
		sprite = new SpriteComponent(Const.atlas.findRegion("trunk"), 4);
	}

	public void update() {
		float height = Math.max(3, body.getPosition().y - 0.6f);
		ray.setSize(100 / height, height);
		ray.setPosition(body.getPosition().x, body.getPosition().y);
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			moveX(-2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			moveX(2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			moveY(-2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			moveY(2);
		}

		moveX(-MathUtils.clamp(Gdx.input.getAccelerometerX(), -1, 1));
		moveY(-MathUtils.clamp(Gdx.input.getAccelerometerY(), -1, 1));
	}

	public void moveX(float speed) {
		body.applyLinearImpulse(new Vector2(speed, 0), body.getLocalCenter(), true);
	}

	public void moveY(float speed) {
		body.applyLinearImpulse(new Vector2(0, speed), body.getLocalCenter(), true);
	}

	public Body getBody() {
		return body;
	}

	public void draw(SpriteBatch batch) {
		ray.draw(batch);
		sprite.setSize(60, 12);
		sprite.setOrigin(30, 6);
		sprite.drawCentered(batch, body.getWorldCenter().x * 10,
				body.getWorldCenter().y * 10);
	}
}
