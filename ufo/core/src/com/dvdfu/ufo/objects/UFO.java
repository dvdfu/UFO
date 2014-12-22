package com.dvdfu.ufo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.SpriteComponent;

public class UFO {
	private Body body;
	private SpriteComponent sprite;

	public UFO(Body body) {
		this.body = body;
		sprite = new SpriteComponent(Const.atlas.findRegion("trunk"), 4);
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

	public void moveX(float speed) {
		body.applyLinearImpulse(new Vector2(speed, 0), body.getLocalCenter(), true);
	}

	public void moveY(float speed) {
		body.applyLinearImpulse(new Vector2(0, speed), body.getLocalCenter(), true);
	}

	public Body getBody() {
		return body;
	}

	public void draw(SpriteBatch batch) {
		sprite.setSize(60, 12);
		sprite.setOrigin(30, 6);
		sprite.drawCentered(batch, body.getWorldCenter().x * 10,
				body.getWorldCenter().y * 10);
	}
}
