package com.dvdfu.ufo.objects.vehicles;

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
import com.dvdfu.ufo.objects.Abductable;

public class Truck extends Abductable {
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
		truckBody.setSize(bodyW * 10, bodyH * 10);
		truckBody.setOrigin(bodyW * 5, bodyH * 5);
		truckHead = new SpriteComponent(Const.atlas.findRegion("truckhead"), 20);
		truckHead.setSize(headW * 10, headH * 10);
		truckHead.setOrigin(headW * 5, headH * 5);
		// truckHead.setColor(1, 0.9f, 0.6f);
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
		wheelShape.setRadius(wheelR);

		body = world.createBody(truckDef);
		body.createFixture(trunkShape, 1f).setUserData(this);
		body.createFixture(cabinShape, 1f).setUserData(this);

		wheel1 = world.createBody(wheelDef);
		wheel1.createFixture(wheelShape, 0.5f);

		wheel2 = world.createBody(wheelDef);
		wheel2.createFixture(wheelShape, 0.5f);

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

	public void abduct() {
		world.destroyBody(body);
		world.destroyBody(wheel1);
		world.destroyBody(wheel2);
	}

	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		wheel1.setTransform(x, y, 0);
		wheel2.setTransform(x, y, 0);
	}
}
