package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.SpriteComponent;

public class Floor extends GameObj {
	private SpriteComponent sprite;

	public Floor(World world) {
		super(world);
		sprite = new SpriteComponent(Const.atlas.findRegion("ground"), 10);
		sprite.setSize(20, 20);
	}

	public Body getBody() {
		return body;
	}

	public void update() {}

	public void draw(SpriteBatch batch) {
		for (int i = -25; i < 25; i++) {
			for (int j = -10; j < 0; j++)
				sprite.draw(batch, i * 20, j * 20);
		}
	}

	public void buildBody() {
		BodyDef floorDef = new BodyDef();
		floorDef.type = BodyType.StaticBody;

		PolygonShape floorShape = new PolygonShape();
		floorShape.setAsBox(50, 10, new Vector2(0, -10), 0);

		FixtureDef floorFix = new FixtureDef();
		floorFix.shape = floorShape;
		floorFix.density = 3;
		floorFix.friction = 1;

		body = world.createBody(floorDef);
		body.createFixture(floorFix);

		floorShape.dispose();
	}
}
