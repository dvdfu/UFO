package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.SpriteComponent;

public class Floor {
	private Body body;
	private SpriteComponent sprite;

	public Floor(Body body) {
		this.body = body;
		sprite = new SpriteComponent(Const.atlas.findRegion("trunk"), 4);
		sprite.setSize(1000, 100);
	}

	public Body getBody() {
		return body;
	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch, -500, -100);
	}

}
