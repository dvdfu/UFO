package com.dvdfu.ufo;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameSprite {
	private TextureRegion sprite;
	private Image image;
	private int index;
	private boolean played;
	private boolean loops;
	
	public GameSprite(Image image) {
		this.image = image;
		sprite = image.get(0);
		index = 0;
		played = false;
		loops = true;
	}
	
	public void draw(SpriteBatch batch) {
//		batch.draw(sprite, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
	}
}
