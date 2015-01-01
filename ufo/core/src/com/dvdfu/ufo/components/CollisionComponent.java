package com.dvdfu.ufo.components;

import java.util.LinkedHashSet;

import com.dvdfu.ufo.objects.GameObj;

public class CollisionComponent {
	private GameObj self;
	private LinkedHashSet<GameObj> collisions;
	
	public CollisionComponent(GameObj self) {
		this.self = self;
		collisions = new LinkedHashSet<GameObj>();
	}
	
	public boolean collidesWith(GameObj object) {
		if (collisions.size() == 0) {
			return false;
		}
		return collisions.contains(object);
	}
	
	public void addCollision(GameObj object) {
		collisions.add(object);
	}
	
	public void removeCollision(GameObj object) {
		collisions.remove(object);
	}
	
	public GameObj getSelf() {
		return self;
	}
}
