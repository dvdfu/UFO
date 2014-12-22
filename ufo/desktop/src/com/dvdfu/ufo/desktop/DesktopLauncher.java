package com.dvdfu.ufo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.dvdfu.ufo.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 480;
		TexturePacker.process("unpacked/", "/home/david/workspace/UFO/ufo/android/assets/img", "images");
		new LwjglApplication(new MainGame(), config);
	}
}
