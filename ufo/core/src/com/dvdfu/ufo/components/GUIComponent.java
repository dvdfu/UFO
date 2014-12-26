package com.dvdfu.ufo.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GUIComponent extends SpriteComponent {
	private OrthographicCamera cam;
	
	public GUIComponent(TextureRegion tex) {
		this(new ImageComponent(tex));
	}
	
	public GUIComponent(TextureRegion tex, int width) {
		this(new ImageComponent(tex, width));
		animates = true;
	}
	
	public GUIComponent(ImageComponent image) {
		super(image);
	}
	
	public void draw(SpriteBatch batch, float x, float y) {
		normalize(batch);
		super.draw(batch, x, y);
	}
	
	public void drawCentered(SpriteBatch batch, float x, float y) {
		normalize(batch);
		super.draw(batch, x - width / 2, y - height / 2);
	}
	
	public void drawOrigin(SpriteBatch batch, float x, float y) {
		normalize(batch);
		super.draw(batch, x - originX, y - originY);
	}
	
	private void normalize(SpriteBatch batch) {
		batch.setProjectionMatrix(cam.combined);
	}
	
	public void setCamera() {
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		cam.update();
	}
	
	public void setCamera(OrthographicCamera cam) {
		this.cam = cam;
	}
}
