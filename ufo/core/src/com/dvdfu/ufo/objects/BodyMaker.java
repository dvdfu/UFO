package com.dvdfu.ufo.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyMaker {
	private World world;

	public BodyMaker(World world) {
		this.world = world;
		// BodyDef: properties of the entire body; fixed rotation, dynamic, damping
		// Shape
		// FixtureDef
		// Body
	}
	
	public Body makeUFO() {
		BodyDef ufoDef = new BodyDef();
		ufoDef.type = BodyType.DynamicBody;
		ufoDef.linearDamping = 0.1f;
		ufoDef.fixedRotation = true;
		ufoDef.gravityScale = 0;
		
		PolygonShape ufoShape = new PolygonShape();
		ufoShape.setAsBox(3, 0.6f);
		
		Body ufo = world.createBody(ufoDef);
		ufo.createFixture(ufoShape, 1);

		ufoShape.dispose();
		return ufo;
	}
	
	public Body makeFloor(float length, float y) {
		BodyDef floorDef = new BodyDef();
		floorDef.type = BodyType.StaticBody;
		
		PolygonShape floorShape = new PolygonShape();
		floorShape.setAsBox(length / 2, 5, new Vector2(0, y - 5), 0);
		
		FixtureDef floorFix = new FixtureDef();
		floorFix.shape = floorShape;
		floorFix.density = 3;
		floorFix.friction = 1;
		
		Body floor = world.createBody(floorDef);
		floor.createFixture(floorFix);

		floorShape.dispose();
		return floor;
	}

	public Body makeTree(float height, float width, float radius) {
		BodyDef treeDef = new BodyDef();
		treeDef.type = BodyType.DynamicBody;
		
		PolygonShape trunkShape = new PolygonShape();
		trunkShape.setAsBox(width / 2, height / 2, new Vector2(0, height / 2), 0);
		CircleShape leafShape = new CircleShape();
		leafShape.setRadius(radius);
		leafShape.setPosition(new Vector2(0, height));
		
		Body tree = world.createBody(treeDef);
		tree.createFixture(trunkShape, 1);
		tree.createFixture(leafShape, 0.1f);

		trunkShape.dispose();
		leafShape.dispose();
		return tree;
	}
}
