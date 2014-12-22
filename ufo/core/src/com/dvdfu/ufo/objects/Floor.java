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
		sprite = new SpriteComponent(Const.atlas.findRegion("ground"), 10);
		sprite.setSize(20, 20);
	}

	public Body getBody() {
		return body;
	}

	public void draw(SpriteBatch batch) {
		for (int i = -25; i < 25; i++) {
			for (int j = -5; j < 0; j++)
			sprite.draw(batch, i * 20, j * 20);
		}
	}

}
