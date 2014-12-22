package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.components.SpriteComponent;

public class Cow {
	private SpriteComponent sprite;
	public Body body;
	
	public Cow(World world) {
		BodyDef cowDef = new BodyDef();
		cowDef.type = BodyType.DynamicBody;
		PolygonShape cowShape = new PolygonShape();
		cowShape.setAsBox(0.75f, 0.4f, new Vector2(0, 0.2f), 0);
		body = world.createBody(cowDef);
		body.createFixture(cowShape, 1);
		cowShape.dispose();
		sprite = new SpriteComponent(new ImageComponent(Const.atlas.findRegion("cow"), 14));
	}
	
	public void draw(SpriteBatch batch) {
		sprite.setSize(15, 10);
		sprite.setOrigin(7.5f, 5);
		sprite.setAngle(body.getAngle() * MathUtils.radDeg);
		sprite.drawCentered(batch, body.getPosition().x * 10, body.getPosition().y * 10);
	}
}
