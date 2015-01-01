package com.dvdfu.ufo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ShaderComponent;
import com.dvdfu.ufo.components.SpriteComponent;
import com.dvdfu.ufo.objects.particles.UFOTrail;

public class UFO extends GameObj {
	private SpriteComponent bodySpr;
	private SpriteComponent headSpr;
	private SpriteComponent raySprite;
	private ShaderComponent rayShader;
	private ShaderComponent defShader;
	private Pool<UFOTrail> particlePool;
	private final Array<UFOTrail> particles = new Array<UFOTrail>();
	private final float moveSpeed = 2000;
	private float groundHeight;

	public UFO(World world) {
		super(world);
		bodySpr = new SpriteComponent(Const.atlas.findRegion("ufobody"), 96);
		headSpr = new SpriteComponent(Const.atlas.findRegion("ufohead"), 36);
//		headSpr.setSize(40, 36);
		raySprite = new SpriteComponent(Const.atlas.findRegion("ray2"), 160);
		raySprite.setColor(0.5f, 0.75f, 1);
		rayShader = new ShaderComponent("shaders/ray.vsh", "shaders/ray.fsh");
		defShader = new ShaderComponent("shaders/passthrough.vsh", "shaders/passthrough.fsh");

		particlePool = new Pool<UFOTrail>() {
			protected UFOTrail newObject() {
				return new UFOTrail();
			}
		};
	}

	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			moveX(-moveSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			moveX(moveSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			moveY(-moveSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			moveY(moveSpeed);
		}

		moveX(-MathUtils.clamp(Gdx.input.getAccelerometerX(), -moveSpeed, moveSpeed));
		moveY(-MathUtils.clamp(Gdx.input.getAccelerometerY(), -moveSpeed, moveSpeed));
	}

	public void draw(SpriteBatch batch) {
		UFOTrail s = particlePool.obtain();
		// s.setPosition(body.getPosition().x * 10 + MathUtils.random(-30, 30),
		// body.getPosition().y * 10 + MathUtils.random(-6, 6));
		s.setPosition(body.getPosition().x * 10, body.getPosition().y * 10);
		particles.add(s);
		UFOTrail item;
		int len = particles.size;
		for (int i = len; --i >= 0;) {
			item = particles.get(i);
//			item.update();
//			item.draw(batch);
			if (item.getDead()) {
				particles.removeIndex(i);
				particlePool.free(item);
			}
		}

		if (isAbducting()) {
			raySprite.setSize(30, (body.getPosition().y - groundHeight) * 10);
			raySprite.setOrigin(15, (body.getPosition().y - groundHeight) * 10);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
			batch.setShader(rayShader);
			raySprite.drawOrigin(batch, body.getPosition().x * 10, body.getPosition().y * 10);
			batch.setShader(defShader);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		bodySpr.setOrigin(48, 9);
		bodySpr.setAngle(body.getAngle());
		headSpr.drawCentered(batch, body.getWorldCenter().x * 10, body.getWorldCenter().y * 10 + 6);
		bodySpr.drawCentered(batch, body.getWorldCenter().x * 10, body.getWorldCenter().y * 10 - 4);
	}

	public void buildBody() {
		BodyDef ufoDef = new BodyDef();
		ufoDef.type = BodyType.DynamicBody;
		ufoDef.linearDamping = 1;
		ufoDef.fixedRotation = true;
		ufoDef.gravityScale = 0;
		body = world.createBody(ufoDef);

		PolygonShape ufoShape = new PolygonShape();
		FixtureDef ufoFix = new FixtureDef();
		
		ufoShape.setAsBox(4.8f, 0.4f);
		ufoFix.shape = ufoShape;
		ufoFix.density = 3;
		ufoFix.friction = 0.9f;
		body.createFixture(ufoFix);
		
		ufoShape.setAsBox(3.8f, 0.7f);
		ufoFix.shape = ufoShape;
		body.createFixture(ufoFix);
		
		CircleShape domeShape = new CircleShape();
		domeShape.setRadius(1.8f);
		domeShape.setPosition(new Vector2(0, 0.9f));
		ufoFix.shape = domeShape;
		body.createFixture(ufoFix);
		
		ufoShape.setAsBox(1.5f, 0.2f, new Vector2(0, -1.2f), 0);
		ufoFix.shape = ufoShape;
		ufoFix.isSensor = true;
		body.createFixture(ufoFix).setUserData("ray");

		ufoShape.dispose();
		domeShape.dispose();
	}

	public void moveX(float speed) {
		body.applyForceToCenter(new Vector2(speed, 0), true);
	}

	public void moveY(float speed) {
		body.applyForceToCenter(new Vector2(0, speed), true);
	}

	public void setGroundHeight(float height) {
		groundHeight = height;
	}

	public boolean isAbducting() {
		return Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched();
	}
}
