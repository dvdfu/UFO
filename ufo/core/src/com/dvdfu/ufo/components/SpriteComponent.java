package com.dvdfu.ufo.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteComponent {
	protected ImageComponent image;
	protected int frame, frameRate, count;
	protected float originX, originY;
	protected float angle;
	protected float alpha;
	protected boolean animates;
	
	public SpriteComponent(ImageComponent image) {
		this.image = image;
		alpha = 1;
		frameRate = 15;
	}
	
	public void update() {
		if (animates) {
			count++;
			if (count >= frameRate) {
				count = 0;
				frame++;
				frame %= image.getLength();
			}
		}
	}
	
	public void draw(SpriteBatch batch, float x, float y) {
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, alpha);
		batch.draw(image.get(frame), x, y, originX, originY, image.getWidth(), image.getHeight(), 1, 1, angle);
		batch.setColor(c);
	}
	
	public void drawCentered(SpriteBatch batch, float x, float y) {
		draw(batch, x - image.getWidth() / 2, y - image.getHeight() / 2);
	}
	
	public void setOrigin(float originX, float originY) {
		this.originX = originX;
		this.originY = originY;
	}
	
	public void setImage(ImageComponent image) {
		this.image = image;
		frame = 0;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}
	
	public void setAnimates(boolean animates) {
		this.animates = animates;
	}
}
