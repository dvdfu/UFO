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

public class Tractor extends Abductable {
	private Body wheel1;
	private Body wheel2;
	private SpriteComponent wheel;
	private SpriteComponent truckBody;
	private SpriteComponent truckHead;
	private Vector2 p;
	private final float bodyW = 1.2f, bodyH = 1.6f, headW = 1.2f, headH = 0.9f, wheel1R = 0.5f, wheel1X = -0.9f, wheel2R = 0.4f, wheel2X = 0.9f;

	public Tractor(World world) {
		super(world);
		wheel = new SpriteComponent(Const.atlas.findRegion("wheel"), 12);
		truckBody = new SpriteComponent(Const.atlas.findRegion("tractorbody"), 16);
		truckBody.setSize(bodyW * 10, bodyH * 10);
		truckBody.setOrigin(bodyW * 5, bodyH * 5);
		truckHead = new SpriteComponent(Const.atlas.findRegion("tractorhead"), 16);
		truckHead.setSize(15, 15);
		truckHead.setOrigin(15f / 2, 15f / 2);
		// truckHead.setColor(1, 0.9f, 0.6f);
		p = new Vector2();
	}

	public void update() {}

	public void draw(SpriteBatch batch) {
		p.set(body.getWorldPoint(new Vector2(-bodyW / 2, bodyH / 2)));
		truckBody.setAngle(body.getAngle());
		truckBody.drawCentered(batch, p.x * 10, p.y * 10);
		p.set(body.getWorldPoint(new Vector2(headW / 2 + 0.1f, headH / 2 + 0.3f)));
		truckHead.setAngle(body.getAngle());
		truckHead.drawCentered(batch, p.x * 10, p.y * 10);
		p.set(body.getWorldPoint(new Vector2(wheel1X, wheel1R - wheel2R)));
		wheel.setSize(wheel1R * 20, wheel1R * 20);
		wheel.drawCentered(batch, p.x * 10, p.y * 10);
		p.set(body.getWorldPoint(new Vector2(wheel2X, 0)));
		wheel.setSize(wheel2R * 20, wheel2R * 20);
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
		wheelShape.setRadius(wheel1R);

		body = world.createBody(truckDef);
		body.createFixture(trunkShape, 1f).setUserData(this);
		body.createFixture(cabinShape, 1f).setUserData(this);

		wheel1 = world.createBody(wheelDef);
		wheel1.createFixture(wheelShape, 0.5f);

		wheelShape.setRadius(wheel2R);
		wheel2 = world.createBody(wheelDef);
		wheel2.createFixture(wheelShape, 0.5f);

		trunkShape.dispose();
		cabinShape.dispose();
		wheelShape.dispose();

		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = body;
		jointDef.localAnchorA.set(wheel1X, wheel1R - wheel2R);
		jointDef.bodyB = wheel1;
		jointDef.localAnchorB.set(0, 0);
		world.createJoint(jointDef);
		jointDef.localAnchorA.set(wheel2X, 0);
		jointDef.bodyB = wheel2;
		world.createJoint(jointDef);
	}

	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		wheel1.setTransform(x, y, 0);
		wheel2.setTransform(x, y, 0);
	}
}
