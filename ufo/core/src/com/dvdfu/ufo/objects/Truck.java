package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.SpriteComponent;

public class Truck extends GameObj {
	private Body wheel1;
	private Body wheel2;
	private SpriteComponent wheel;
	private SpriteComponent truckBody;
	private SpriteComponent truckHead;
	private Vector2 p;
	private final float bodyW = 5, bodyH = 2.5f, headW = 1.75f, headH = 2, wheelR = 0.5f, wheel1X = -3.5f, wheel2X = 0.5f;

	public Truck(World world) {
		super(world);
		wheel = new SpriteComponent(Const.atlas.findRegion("wheel"), 12);
		wheel.setSize(wheelR * 20, wheelR * 20);
		truckBody = new SpriteComponent(Const.atlas.findRegion("truckbody"), 60);
		truckBody.setOrigin(30, 15);
		truckBody.setSize(bodyW * 10, bodyH * 10);
		truckHead = new SpriteComponent(Const.atlas.findRegion("truckhead"), 20);
		truckHead.setOrigin(10, 11);
		truckHead.setSize(headW * 10, headH * 10);
		p = new Vector2();
	}

	public void update() {}

	public void draw(SpriteBatch batch) {
		p.set(body.getWorldPoint(new Vector2(-bodyW / 2, bodyH / 2)));
		truckBody.setAngle(body.getAngle());
		truckBody.drawCentered(batch, p.x * 10, p.y * 10);
		p.set(body.getWorldPoint(new Vector2(headW / 2, headH / 2)));
		truckHead.setAngle(body.getAngle());
		truckHead.drawCentered(batch, p.x * 10, p.y * 10);
		p.set(body.getWorldPoint(new Vector2(wheel1X, 0)));
		wheel.drawCentered(batch, p.x * 10, p.y * 10);
		p.set(body.getWorldPoint(new Vector2(wheel2X, 0)));
		wheel.drawCentered(batch, p.x * 10, p.y * 10);
	}

	public void buildBody() {
		BodyDef truckDef = new BodyDef();
		truckDef.type = BodyType.DynamicBody;

		BodyDef cabinDef = new BodyDef();
		cabinDef.type = BodyType.DynamicBody;
		
		BodyDef wheelDef = new BodyDef();
		wheelDef.type = BodyType.DynamicBody;

		PolygonShape trunkShape = new PolygonShape();
		trunkShape.setAsBox(bodyW / 2, bodyH / 2, new Vector2(-bodyW / 2, bodyH / 2), 0);

		PolygonShape cabinShape = new PolygonShape();
		cabinShape.setAsBox(headW / 2, headH / 2, new Vector2(headW / 2, headH / 2), 0);
		
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(0.5f);

		body = world.createBody(truckDef);
		body.createFixture(trunkShape, 1);
		body.createFixture(cabinShape, 1);
		
		wheel1 = world.createBody(wheelDef);
		wheel1.createFixture(wheelShape, wheelR);

		wheel2 = world.createBody(wheelDef);
		wheel2.createFixture(wheelShape, wheelR);

		trunkShape.dispose();
		cabinShape.dispose();
		wheelShape.dispose();

		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = body;
		jointDef.localAnchorA.set(wheel1X, 0);
		jointDef.bodyB = wheel1;
		jointDef.localAnchorB.set(0, 0);
		world.createJoint(jointDef);
		jointDef.localAnchorA.set(wheel2X, 0);
		jointDef.bodyB = wheel2;
		world.createJoint(jointDef);
	}

	public void setPosition(float x, float y) {
		super.setPosition(x, y);
	}
}
