package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ShaderComponent;
import com.dvdfu.ufo.components.SpriteComponent;

public class Ray extends GameObj {
	public Body body;
	private FixtureDef rayFix;
	private PolygonShape rayShape;
	private Fixture fix;
	private Vector2[] vertices;
	private Vector2 size;
	private SpriteComponent sprite;
	private ShaderComponent rayShader;
	private ShaderComponent defShader;

	public Ray(World world) {
		super(world);
		size = new Vector2();

		sprite = new SpriteComponent(Const.atlas.findRegion("ray"), 160);
		sprite.setColor(0.70f, 0.85f, 1);
		rayShader = new ShaderComponent("shaders/ray.vsh", "shaders/ray.fsh");
		defShader = new ShaderComponent("shaders/passthrough.vsh", "shaders/passthrough.fsh");
	}

	public void update() {}

	public void draw(SpriteBatch batch) {
		sprite.setSize(size.x * 10, size.y * 10);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
		batch.setShader(rayShader);
		sprite.draw(batch, (body.getPosition().x - size.x / 2) * 10, (body.getPosition().y - size.y - 0.6f) * 10);
		batch.setShader(defShader);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void buildBody() {
		BodyDef rayDef = new BodyDef();
		rayDef.type = BodyType.DynamicBody;
		rayDef.fixedRotation = true;
		rayDef.gravityScale = 0;

		rayShape = new PolygonShape();
		vertices = new Vector2[3];
		vertices[0] = new Vector2(0, 0);
		vertices[1] = new Vector2(-1 / 2f, -2);
		vertices[2] = new Vector2(1 / 2f, -2);
		rayShape.set(vertices);

		body = world.createBody(rayDef);
		rayFix = new FixtureDef();
		rayFix.density = 0;
		rayFix.shape = rayShape;
		rayFix.isSensor = true;
		fix = body.createFixture(rayFix);
	}

	public void setSize(float width, float height) {
		size.set(width, height);
		vertices[1].set(-size.x / 2, -size.y);
		vertices[2].set(size.x / 2, -size.y);
		rayShape.set(vertices);
		rayFix.shape = rayShape;
		body.destroyFixture(fix);
		fix = body.createFixture(rayFix);
	}

	public float getHeight() {
		return size.y;
	}
}
