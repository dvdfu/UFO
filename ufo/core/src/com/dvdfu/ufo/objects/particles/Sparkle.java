package com.dvdfu.ufo.objects.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ParticleComponent;

public class Sparkle extends ParticleComponent {
	private float vSpeed;
	
	public Sparkle() {
		super();
	}

	public void init() {
		sprite.setImage(Const.atlas.findRegion("sparkle"), 16);
		sprite.setFrameRate(4);
	}

	public void update() {
		super.update();
		position.y += vSpeed;
		position.x += 4 * MathUtils.cos(timer / 2f);
	}

	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, position.x, position.y);
	}

	public void reset() {
		super.reset();
		vSpeed = 2 + MathUtils.random(2f);
		timer = 30 + MathUtils.random(20);
//		sprite.setFrame(0);
	}

}
