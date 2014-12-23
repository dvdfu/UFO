package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObj {
	protected World world;
	protected Body body;

	public GameObj(World world) {
		this.world = world;
		buildBody();
	}
	
	public abstract void update();

	public abstract void draw(SpriteBatch batch);

	public abstract void buildBody();
	
	public void setPosition(Vector2 position) {
		body.getTransform().setPosition(position);
	}
	
	public void setPosition(float x, float y) {
		setPosition(new Vector2(x, y));
	}
	
	public Body getBody() {
		return body;
	}
}
