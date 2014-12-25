package com.dvdfu.ufo.objects.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ParticleComponent;

public class Sparkle extends ParticleComponent {
	
	public Sparkle() {
		super();
	}

	public void init() {
		sprite.setImage(Const.atlas.findRegion("sparkle"), 16);
		sprite.setFrameRate(4);
	}

	public void update() {
		super.update();
//		position.y++;
	}

	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, position.x, position.y);
	}

	public void reset() {
		super.reset();
		timer = 20;
		sprite.setFrame(0);
	}

}
