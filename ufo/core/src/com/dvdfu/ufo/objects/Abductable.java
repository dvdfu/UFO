package com.dvdfu.ufo.objects;

import com.badlogic.gdx.physics.box2d.World;

public abstract class Abductable extends GameObj {
	protected boolean ufoCollide;

	public Abductable(World world) {
		super(world);
	}

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
	
	public abstract void abduct();
}
