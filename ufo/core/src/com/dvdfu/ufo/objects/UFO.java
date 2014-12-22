package com.dvdfu.ufo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class UFO {
	private Body body;
	public UFO (Body body) {
		this.body = body;
	}
	
	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			moveLeft(3);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			moveRight(3);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			moveDown(3);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			moveUp(3);
		}
	}
	
	public void moveLeft(float speed) {
		body.applyLinearImpulse(new Vector2(-speed, 0), body.getLocalCenter(), true);
	}
	
	public void moveRight(float speed) {
		body.applyLinearImpulse(new Vector2(speed, 0), body.getLocalCenter(), true);
	}
	
	public void moveDown(float speed) {
		body.applyLinearImpulse(new Vector2(0, -speed), body.getLocalCenter(), true);
	}
	
	public void moveUp(float speed) {
		body.applyLinearImpulse(new Vector2(0, speed), body.getLocalCenter(), true);
	}
	
	public Body getBody() {
		return body;
	}
}
