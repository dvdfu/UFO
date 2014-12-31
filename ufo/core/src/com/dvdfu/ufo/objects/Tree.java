package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.components.SpriteComponent;

public class Tree extends GameObj {
	private final float height = 2.5f, width = 0.5f, radius = 1.5f;
	private RevoluteJoint root;
	private RevoluteJointDef rootDef;
	private SpriteComponent leafSpr;
	private SpriteComponent trunkSpr;
	private boolean detached;

	public Tree(World world) {
		super(world);
		leafSpr = new SpriteComponent(new ImageComponent(Const.atlas.findRegion("leaves"), 32));
		trunkSpr = new SpriteComponent(new ImageComponent(Const.atlas.findRegion("trunk"), 4));
	}

	public void update() {
		if (!detached && root.getReactionForce(60).len() > 3500) {
			detached = true;
			world.destroyJoint(root);
		}
	}

	public void draw(SpriteBatch batch) {
		Vector2 p = body.getWorldPoint(new Vector2(0, height / 2));
		trunkSpr.setSize(width * 10, height * 10);
		trunkSpr.setOrigin(width * 5, height * 5);
		trunkSpr.setAngle(body.getAngle());
		trunkSpr.drawCentered(batch, p.x * 10, p.y * 10);

		p = body.getWorldPoint(new Vector2(0, height));
		float r = body.getFixtureList().get(1).getShape().getRadius();
		leafSpr.setSize(r * 20, r * 20);
		leafSpr.setOrigin(r * 10, r * 10);
		leafSpr.drawCentered(batch, p.x * 10, p.y * 10);
	}

	public void buildBody() {
		BodyDef treeDef = new BodyDef();
		treeDef.type = BodyType.DynamicBody;

		PolygonShape trunkShape = new PolygonShape();
		trunkShape.setAsBox(width / 2, height / 2, new Vector2(0, height / 2), 0);
		CircleShape leafShape = new CircleShape();
		leafShape.setRadius(radius);
		leafShape.setPosition(new Vector2(0, height));

		body = world.createBody(treeDef);
		body.createFixture(trunkShape, 1);
		body.createFixture(leafShape, 0.1f);

		trunkShape.dispose();
		leafShape.dispose();

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
