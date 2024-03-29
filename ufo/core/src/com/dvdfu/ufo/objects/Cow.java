package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.components.SpriteComponent;

public class Cow extends Abductable {
	private SpriteComponent sprite;

	public Cow(World world) {
		super(world);
		sprite = new SpriteComponent(new ImageComponent(Const.atlas.findRegion("cow"), 14));
	}

	public void update() {}

	public void draw(SpriteBatch batch) {
		sprite.setSize(15, 10);
		sprite.setOrigin(7.5f, 5);
		sprite.setAngle(body.getAngle());
		sprite.drawCentered(batch, body.getPosition().x * 10, body.getPosition().y * 10);
	}

	public void buildBody() {
		BodyDef cowDef = new BodyDef();
		cowDef.type = BodyType.DynamicBody;
		PolygonShape cowShape = new PolygonShape();
		cowShape.setAsBox(0.75f, 0.5f, new Vector2(0, 0), 0);
		body = world.createBody(cowDef);
		FixtureDef cowFix = new FixtureDef();
		cowFix.shape = cowShape;
		cowFix.density = 1;
		cowFix.restitution = 0;
		body.createFixture(cowFix).setUserData(this);
		cowShape.dispose();
	}

	public void abduct() {
		world.destroyBody(body);
	}
}
