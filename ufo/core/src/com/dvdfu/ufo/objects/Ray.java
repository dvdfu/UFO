package com.dvdfu.ufo.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ray {
	public Body body;
	private FixtureDef rayFix;
	private PolygonShape rayShape;
	private Fixture fix;
	private Vector2[] vertices;
	private Vector2 position;
	private Vector2 size;

	public Ray(World world) {
		position = new Vector2();
		size = new Vector2();
		
		BodyDef rayDef = new BodyDef();
		rayDef.type = BodyType.DynamicBody;
		rayDef.fixedRotation = true;
		rayDef.gravityScale = 0;

		rayShape = new PolygonShape();
		vertices = new Vector2[3];
		vertices[0] = new Vector2(0, 0);
		vertices[1] = new Vector2(-1/2f, -2);
		vertices[2] = new Vector2(1/2f, -2);
		rayShape.set(vertices);

		body = world.createBody(rayDef);
		rayFix = new FixtureDef();
		rayFix.density = 0;
		rayFix.shape = rayShape;
		rayFix.isSensor = true;
		fix = body.createFixture(rayFix);
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	
	public void setSize(float width, float height) {
		size.set(width, height);
		vertices[1].set(- size.x / 2, -size.y);
		vertices[2].set(size.x / 2, -size.y);
		rayShape.set(vertices);
		body.destroyFixture(fix);
		rayFix.shape = rayShape;
		fix = body.createFixture(rayFix);
		setPosition(position.x, position.y);
	}
	
	public float getHeight() {
		return size.y;
	}
}
