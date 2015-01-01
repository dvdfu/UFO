package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.components.SpriteComponent;

public class Hydrant extends Abductable {
	private SpriteComponent sprite;
	private RevoluteJoint root;
	private RevoluteJointDef rootDef;
	private boolean detached;

	public Hydrant(World world) {
		super(world);
		sprite = new SpriteComponent(new ImageComponent(Const.atlas.findRegion("hydrant"), 6));
	}

	public void update() {
		if (!detached && root.getReactionForce(60).len() > 1000) {
			detached = true;
			world.destroyJoint(root);
		}
	}

	public void draw(SpriteBatch batch) {
		sprite.setSize(6, 10);
		sprite.setOrigin(3, 0);
		sprite.setAngle(body.getAngle());
		sprite.drawOrigin(batch, body.getPosition().x * 10, body.getPosition().y * 10);
	}

	public void buildBody() {
		BodyDef hydrantDef = new BodyDef();
		hydrantDef.type = BodyType.DynamicBody;
		PolygonShape hydrantShape = new PolygonShape();
		hydrantShape.setAsBox(0.3f, 0.5f, new Vector2(0, 0.5f), 0);
		body = world.createBody(hydrantDef);
		FixtureDef hydrantFix = new FixtureDef();
		hydrantFix.shape = hydrantShape;
		hydrantFix.density = 2.5f;
		hydrantFix.restitution = 0;
		body.createFixture(hydrantFix).setUserData(this);
		hydrantShape.dispose();

		rootDef = new RevoluteJointDef();
		rootDef.bodyA = body;
		rootDef.localAnchorA.set(0, 0);
		rootDef.collideConnected = true;
		rootDef.lowerAngle = -30 * MathUtils.degRad;
		rootDef.upperAngle = 30 * MathUtils.degRad;
		rootDef.enableLimit = true;
	}

	public void abduct() {
		world.destroyBody(body);
	}

	public void attach(Body floor) {
		rootDef.bodyB = floor;
		Vector2 worldp = new Vector2(body.getWorldPoint(new Vector2(0, 0)));
		rootDef.localAnchorB.set(floor.getLocalVector(worldp));
		root = (RevoluteJoint) world.createJoint(rootDef);
	}
}
