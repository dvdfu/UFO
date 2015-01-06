package com.dvdfu.ufo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.GUIComponent;
import com.dvdfu.ufo.components.ShaderComponent;
import com.dvdfu.ufo.components.SpriteComponent;

public class UFO extends GameObj {
	private SpriteComponent bodySpr;
	private SpriteComponent headSpr;
	private SpriteComponent raySprite;
	private ShaderComponent rayShader;
	private ShaderComponent defShader;
	private final float moveSpeed = 3000;
	private float groundHeight;
	private TextureRegion tr;
	private GUIComponent rayIcon;
	private GUIComponent ufoIcon;
	private int rayMeter;

	public UFO(World world) {
		super(world);
		bodySpr = new SpriteComponent(Const.atlas.findRegion("ufobody"), 96);
		headSpr = new SpriteComponent(Const.atlas.findRegion("ufohead"), 36);
		// headSpr.setSize(40, 36);
		raySprite = new SpriteComponent(Const.atlas.findRegion("ray2"), 160);
		raySprite.setColor(0.5f, 0.75f, 1);
		rayShader = new ShaderComponent("shaders/ray.vsh", "shaders/ray.fsh");
		defShader = new ShaderComponent("shaders/passthrough.vsh", "shaders/passthrough.fsh");
		tr = new TextureRegion(new Texture(Gdx.files.internal("img/default.png")));
		rayIcon = new GUIComponent(Const.atlas.findRegion("ray2"));
		rayIcon.setColor(0.5f, 0.75f, 1);
		rayIcon.setCamera();
		ufoIcon = new GUIComponent(Const.atlas.findRegion("ufoicon"));
		ufoIcon.setCamera();
		rayMeter = 200;
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
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
			if (rayMeter > 0) {
				rayMeter--;
			} else {
				rayMeter = 0;
			}
		} else {
			if (rayMeter + 3 < 200) {
				rayMeter += 3;
			} else {
				rayMeter = 200;
			}
		}

		moveX(-MathUtils.clamp(Gdx.input.getAccelerometerX() * 300, -moveSpeed, moveSpeed));
		moveY(-MathUtils.clamp(Gdx.input.getAccelerometerY() * 300, -moveSpeed, moveSpeed));
	}

	public void draw(SpriteBatch batch) {
		if (isAbducting()) {
			raySprite.setSize(30, (body.getPosition().y - groundHeight) * 10);
			raySprite.setOrigin(15, (body.getPosition().y - groundHeight) * 10);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
			batch.setShader(rayShader);
			batch.setColor(0.5f, 0.75f, 1, 1);
			// raySprite.drawOrigin(batch, body.getPosition().x * 10,
			// body.getPosition().y * 10);
			batch.draw(tr, body.getPosition().x * 10 - 15, body.getPosition().y * 10 - (body.getPosition().y - groundHeight) * 10, 30,
					(body.getPosition().y - groundHeight) * 10);
			batch.setColor(1, 1, 1, 1);
			batch.setShader(defShader);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		bodySpr.setOrigin(48, 9);
		bodySpr.setAngle(body.getAngle());
		headSpr.drawCentered(batch, body.getWorldCenter().x * 10, body.getWorldCenter().y * 10 + 6);
		bodySpr.drawCentered(batch, body.getWorldCenter().x * 10, body.getWorldCenter().y * 10 - 4);
//		batch.flush();
		
		rayIcon.setSize(8, rayMeter / 2);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
		rayIcon.draw(batch, 20, 16);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		ufoIcon.drawCentered(batch, 24, rayMeter / 2 + 20);
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
		body.createFixture(ufoFix).setUserData(this);

		ufoShape.dispose();
		domeShape.dispose();
	}

	public void collide(GameObj object) {}

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
		return (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) && rayMeter > 0;
	}
}
