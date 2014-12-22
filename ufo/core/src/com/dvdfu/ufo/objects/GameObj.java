package com.dvdfu.ufo.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObj {
	protected World world;
	protected Body body;
	
	public abstract void update();
}
