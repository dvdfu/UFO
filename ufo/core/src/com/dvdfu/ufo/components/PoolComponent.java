package com.dvdfu.ufo.components;

import java.util.Stack;

import com.badlogic.gdx.utils.Pool;

@SuppressWarnings("hiding")
public class PoolComponent<ParticleComponent> {
	private Pool<ParticleComponent> particles;
	private Stack<ParticleComponent> activeParticles;

	public PoolComponent() {
		activeParticles = new Stack<ParticleComponent>();
//		particles = new Pool<ParticleComponent>() {
//			protected ParticleComponent newObject() {
//				return Sparkle.newInstance();
//			}
//		};
	}
	
	public void update() {

//		ParticleComponent part;
//		int len = activeParticles.size();
//		for (int i = len; --i >= 0;) {
//			part = activeParticles.get(i);
//			part.update();
//			if (part.getDead()) {
//				activeParticles.removeIndex(i);
//				particlePool.free(part);
//			}
//		}
	}
	
	public void addParticle() {
		ParticleComponent part = particles.obtain();
		activeParticles.add(part);
	}

}
