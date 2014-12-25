package com.dvdfu.ufo.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dvdfu.ufo.Const;

public abstract class ParticleComponent implements Poolable {
	protected SpriteComponent sprite;
	protected Vector2 position;
	protected int timer;
	protected boolean dead;
	
	public ParticleComponent() {
		sprite = new SpriteComponent(Const.atlas.findRegion("default"));
		position = new Vector2(0, 0);
		init();
		reset();
	}
	
	public abstract void init();
	
	public void update() {
		sprite.update();
		if (timer > 0) {
			timer--;
		} else {
			dead = true;
		}
	}
	
	public abstract void draw(SpriteBatch batch);

	public void reset() {
		dead = false;
	}
	
	public void setDead() {
		dead = true;
	}
	
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	
	public boolean getDead() {
		return dead;
	}
	
	public int getTimer() {
		return timer;
	}
}
