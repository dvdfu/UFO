package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.SpriteComponent;

public class StreetLight extends GameObj {
	private RevoluteJoint root;
	private RevoluteJointDef rootDef;
	private SpriteComponent poleSpr;
	private boolean detached;

	public StreetLight(World world) {
		super(world);
		poleSpr = new SpriteComponent(Const.atlas.findRegion("pole"));
	}

	public void update() {
		if (!detached && root.getReactionForce(60).len() > 3500) {
			detached = true;
			world.destroyJoint(root);
		}
	}

	public void draw(SpriteBatch batch) {
//		Vector2 p = body.getWorldPoint(new Vector2(0, height / 2));
//		trunkSpr.setSize(width * 10, height * 10);
//		trunkSpr.setOrigin(width * 5, height * 5);
//		trunkSpr.setAngle(body.getAngle());
//
//		trunkSpr.drawCentered(batch, p.x * 10, p.y * 10);
//		p = body.getWorldPoint(new Vector2(0, height));
//		float r = body.getFixtureList().get(1).getShape().getRadius();
//		leafSpr.setSize(r * 20, r * 20);
//		leafSpr.setOrigin(r * 10, r * 10);
//		leafSpr.drawCentered(batch, p.x * 10, p.y * 10);
	}

	public void buildBody() {
		BodyDef poleDef = new BodyDef();
		poleDef.type = BodyType.DynamicBody;

		PolygonShape poleShape = new PolygonShape();
		poleShape.setAsBox(0.3f, 4.5f, new Vector2(0, 2.25f), 0);

		body = world.createBody(poleDef);
		body.createFixture(poleShape, 1);

		poleShape.dispose();

		rootDef = new RevoluteJointDef();
		rootDef.bodyA = body;
		rootDef.localAnchorA.set(0, 0);
		rootDef.collideConnected = true;
		rootDef.lowerAngle = -30 * MathUtils.degRad;
		rootDef.upperAngle = 30 * MathUtils.degRad;
		rootDef.enableLimit = true;
	}

	public void attach(Body floor) {
		rootDef.bodyB = floor;
		Vector2 worldp = new Vector2(body.getWorldPoint(new Vector2(0, 0)));
		rootDef.localAnchorB.set(floor.getLocalVector(worldp));
		root = (RevoluteJoint) world.createJoint(rootDef);
	}
}
