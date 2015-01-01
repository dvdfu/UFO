package com.dvdfu.ufo.objects.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ParticleComponent;

public class UFOTrail extends ParticleComponent {
	
	public UFOTrail() {
		super();
	}

	public void init() {
		sprite.setImage(Const.atlas.findRegion("trail"));
	}

	public void update() {
		super.update();
	}

	public void draw(SpriteBatch batch) {
		sprite.setAlpha(timer / 60f);
		sprite.drawCentered(batch, position.x, position.y);
	}

	public void reset() {
		super.reset();
		timer = 60;
	}

}
