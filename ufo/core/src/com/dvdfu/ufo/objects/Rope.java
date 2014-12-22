package com.dvdfu.ufo.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.components.SpriteComponent;

public class Rope {
	private int segNum;
	private float segLen;
	private float maxAngle;
	private ArrayList<Body> segments;
	private SpriteComponent sprite;
	
	public Rope(World world, int segNum, float segLen) {
		this.segNum = segNum;
		this.segLen = segLen;
		maxAngle = 10;
		segments = new ArrayList<Body>();
		
		// BODYDEF
		BodyDef segmentDef = new BodyDef();
		segmentDef.type = BodyType.DynamicBody;
		segmentDef.linearDamping = 1;
		
		// SHAPE
		PolygonShape segmentShape = new PolygonShape();
		segmentShape.setAsBox(this.segLen, 0.1f);
		
		// FIXTUREDEF
		FixtureDef segmentFix = new FixtureDef();
		segmentFix.shape = segmentShape;
		segmentFix.density = 1f;
		// segmentFix.isSensor = true;
		
		// JOINT
		RevoluteJointDef stringJoint = new RevoluteJointDef();
		stringJoint.upperAngle = maxAngle * MathUtils.degRad;
		stringJoint.lowerAngle = -maxAngle * MathUtils.degRad;
		stringJoint.enableLimit = true;
		
		RopeJointDef bridgeJoint = new RopeJointDef();
		bridgeJoint.maxLength = 2 * segLen;
		
		for (int i = 0; i < segNum; i++) {
			segmentDef.position.set(i * segLen * 2 - segLen * segNum, 0);
			Body segment = world.createBody(segmentDef);
			segment.createFixture(segmentFix);
			segments.add(segment);
		}
		
		for (int i = 0; i < segNum + 1; i++) {
			if (i > 0 && i < segNum) {
				Body prev = segments.get(i - 1);
				Body next = segments.get(i);
				stringJoint.bodyA = prev;
				stringJoint.localAnchorA.set(segLen, 0);
				stringJoint.bodyB = next;
				stringJoint.localAnchorB.set(-segLen, 0);
				world.createJoint(stringJoint);
				
				bridgeJoint.bodyA = prev;
				bridgeJoint.localAnchorA.set(0, 0);
				bridgeJoint.bodyB = next;
				bridgeJoint.localAnchorB.set(0, 0);
				world.createJoint(bridgeJoint);
			}		
		}
		
		sprite = new SpriteComponent(new ImageComponent(Const.atlas.findRegion("star"), 10));
		sprite.setAnimates(true);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.update();
		for (Body b : segments)
		sprite.drawCentered(batch, b.getPosition().x * 10, b.getPosition().y * 10);
	}
	
	public Body getHead() {
		return segments.get(0);
	}
	
	public Body getTail() {
		return segments.get(segNum - 1);
	}
	
	public ArrayList<Body> getBody() {
		return segments;
	}
}