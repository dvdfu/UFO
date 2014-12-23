package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

public class Truck extends GameObj {
	private RopeJointDef jointDef;
	private Body body2;
	private Body wheel1;
	private Body wheel2;

	public Truck(World world) {
		super(world);
	}

	public void update() {}

	public void draw(SpriteBatch batch) {}

	public void buildBody() {
		BodyDef truckDef = new BodyDef();
		truckDef.type = BodyType.DynamicBody;

		BodyDef cabinDef = new BodyDef();
		cabinDef.type = BodyType.DynamicBody;
		
		BodyDef wheelDef = new BodyDef();
		wheelDef.type = BodyType.DynamicBody;

		PolygonShape trunkShape = new PolygonShape();
		trunkShape.setAsBox(3f, 1f, new Vector2(-3, 1), 0);

		PolygonShape cabinShape = new PolygonShape();
		cabinShape.setAsBox(1, 0.7f, new Vector2(1, 0.7f), 0);
		
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(0.5f);

		body = world.createBody(truckDef);
		body.createFixture(trunkShape, 1);

		body2 = world.createBody(cabinDef);
		body2.createFixture(cabinShape, 1);
		
		wheelShape.setPosition(new Vector2(-3, 0));
		wheel1 = world.createBody(wheelDef);
		wheel1.createFixture(wheelShape, 0.5f);

		wheelShape.setPosition(new Vector2(-1, 0));
		wheel2 = world.createBody(wheelDef);
		wheel2.createFixture(wheelShape, 0.5f);

		trunkShape.dispose();
		cabinShape.dispose();
		wheelShape.dispose();

		jointDef = new RopeJointDef();
		jointDef.bodyA = body;
		jointDef.localAnchorA.set(0, 0);
		jointDef.bodyB = body2;
		jointDef.localAnchorB.set(0, 0);
		jointDef.collideConnected = true;
		jointDef.maxLength = 0.1f;
		world.createJoint(jointDef);
	}

	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		body2.getTransform().setPosition(new Vector2(x + 6, y));
	}
}
