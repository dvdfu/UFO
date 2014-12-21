package com.dvdfu.ufo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Image {
	private TextureRegion[] frames;
	private int length;
	private int width;
	private int height;
	
	public Image(TextureRegion reg) {
		this(reg, reg.getRegionWidth());
	}

	public Image(TextureRegion reg, int width) {
		this.width = width;
		height = reg.getRegionHeight();
		length = reg.getRegionWidth() / width;
		frames = new TextureRegion[length];
		for (int i = 0; i < length; i++) {
			frames[i] = new TextureRegion(reg, i * width, 0, width, height);
		}
	}
	
	public TextureRegion get() {
		return get(0);
	}

	public TextureRegion get(int frame) {
		while (frame < 0) {
			frame += length;
		}
		return frames[frame % length];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getLength() {
		return length;
	}
}