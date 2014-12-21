package com.dvdfu.ufo.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Tree {
	public Body tree;
	
	public Tree(World world, float trunkH, float trunkW, float leafR) {

		BodyDef treeDef = new BodyDef();
		treeDef.type = BodyType.DynamicBody;
//		segmentDef.linearDamping = 1;
		
		PolygonShape trunkShape = new PolygonShape();
		trunkShape.setAsBox(trunkW / 2, trunkH / 2);
		CircleShape leafShape = new CircleShape();
		leafShape.setRadius(leafR);
		leafShape.setPosition(new Vector2(0, trunkH / 2));
		
//		FixtureDef trunkFix = new FixtureDef();
//		trunkFix.shape = trunkShape;
//		trunkFix.density = 1f;
//		FixtureDef leafFix = new FixtureDef();
//		leafFix.shape = leafShape;
//		leafFix.density = 0.1f;
		
		tree = world.createBody(treeDef);
		tree.setTransform(new Vector2(0, 10), 0);
		tree.createFixture(trunkShape, 1);
//		tree.createFixture(leafFix, 1);
		tree.createFixture(leafShape, 0.1f);
		
		trunkShape.dispose();
		leafShape.dispose();
	}
}
