package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Abductable extends GameObj {
	protected boolean ufoCollide;

	public Abductable(World world) {
		super(world);
	}

	public void update() {}

	public void draw(SpriteBatch batch) {}

	public void buildBody() {}

	public void collide(GameObj object) {
		if (object instanceof UFO) {
			ufoCollide = true;
		}
	}
	
	public void uncollide(GameObj object) {
		if (object instanceof UFO) {
			ufoCollide = false;
		}
	}
	
	public boolean getUFOCollide() {
		return ufoCollide;
	}
}
