package com.dvdfu.ufo.objects.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ParticleComponent;

public class Sparkle extends ParticleComponent {
	private Vector2 velocity;
	
	public Sparkle() {
		super();
		sprite.setFrameRate(6);
	}

	public void init() {
		sprite.setImage(Const.atlas.findRegion("sparkle"), 16);
		velocity = new Vector2(0, 0);
	}

	public void update() {
		super.update();
		position.add(velocity);
		velocity.scl(0.9f);
	}

	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, position.x, position.y);
	}

	public void reset() {
		super.reset();
		timer = 35;
		sprite.setFrame(0);
		float r = 3 + MathUtils.random(2);
		float a = MathUtils.random(MathUtils.PI2);
		velocity.set(MathUtils.cos(a) * r, MathUtils.sin(a) * r);
	}
}
