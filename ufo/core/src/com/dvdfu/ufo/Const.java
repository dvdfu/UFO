package com.dvdfu.ufo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Const {
	public static final int screenWidth = 720 * 3 / 4;
	public static final int screenHeight = 720 * 3 / 4;
	
	public AssetManager manager;
	public static TextureAtlas atlas;
	
	public static final float mtop = 10;

	public Const() {
		manager = new AssetManager();
		atlas = new TextureAtlas();
		
		manager.load("img/images.atlas", TextureAtlas.class);
		manager.finishLoading();
		
		atlas = manager.get("img/images.atlas", TextureAtlas.class);
	}
}
