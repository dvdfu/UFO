package com.dvdfu.ufo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ShaderComponent;
import com.dvdfu.ufo.components.SpriteComponent;
import com.dvdfu.ufo.objects.particles.Sparkle;

public class UFO extends GameObj {
	private SpriteComponent sprite;
	private SpriteComponent raySprite;
	private ShaderComponent rayShader;
	private ShaderComponent defShader;
	private Pool<Sparkle> particlePool;
	private final Array<Sparkle> particles = new Array<Sparkle>();

	public UFO(World world) {
		super(world);
		sprite = new SpriteComponent(Const.atlas.findRegion("default"), 4);
		raySprite = new SpriteComponent(Const.atlas.findRegion("ray"), 160);
		raySprite.setColor(0.5f, 0.75f, 1);
		rayShader = new ShaderComponent("shaders/ray.vsh", "shaders/ray.fsh");
		defShader = new ShaderComponent("shaders/passthrough.vsh", "shaders/passthrough.fsh");

		particlePool = new Pool<Sparkle>() {
			protected Sparkle newObject() {
				return new Sparkle();
			}
		};
	}

	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			moveX(-2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			moveX(2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			moveY(-2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			moveY(2);
		}

		moveX(-MathUtils.clamp(Gdx.input.getAccelerometerX(), -1, 1));
		moveY(-MathUtils.clamp(Gdx.input.getAccelerometerY(), -1, 1));
	}

	public void draw(SpriteBatch batch) {
		Sparkle s = particlePool.obtain();
		s.setPosition(body.getPosition().x * 10 + MathUtils.random(-30, 30), body.getPosition().y * 10 + MathUtils.random(-6, 6));
		particles.add(s);
		Sparkle item;
		int len = particles.size;
		for (int i = len; --i >= 0;) {
			item = particles.get(i);
			item.update();
//			item.draw(batch);
			if (item.getDead()) {
				particles.removeIndex(i);
				particlePool.free(item);
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			raySprite.setSize(30, body.getPosition().y * 10);
			raySprite.setOrigin(15, body.getPosition().y * 10);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
			batch.setShader(rayShader);
			raySprite.drawOrigin(batch, body.getPosition().x * 10, body.getPosition().y * 10);
			batch.setShader(defShader);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		sprite.setSize(60, 12);
		sprite.setOrigin(30, 6);
		sprite.drawCentered(batch, body.getWorldCenter().x * 10, body.getWorldCenter().y * 10);
	}

	public void buildBody() {
		BodyDef ufoDef = new BodyDef();
		ufoDef.type = BodyType.DynamicBody;
		ufoDef.linearDamping = 0.1f;
		ufoDef.fixedRotation = true;
		ufoDef.gravityScale = 0;

		PolygonShape ufoShape = new PolygonShape();
		ufoShape.setAsBox(3, 0.6f);

		body = world.createBody(ufoDef);
		FixtureDef ufoFix = new FixtureDef();
		ufoFix.shape = ufoShape;
		ufoFix.density = 1;
		ufoFix.friction = 0.9f;
		body.createFixture(ufoFix);

		ufoShape.dispose();
	}

	public void moveX(float speed) {
		body.applyLinearImpulse(new Vector2(speed, 0), body.getLocalCenter(), true);
	}

	public void moveY(float speed) {
		body.applyLinearImpulse(new Vector2(0, speed), body.getLocalCenter(), true);
	}
}
