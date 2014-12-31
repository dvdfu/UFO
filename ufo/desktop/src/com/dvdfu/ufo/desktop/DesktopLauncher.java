package com.dvdfu.ufo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Const.screenWidth;
		config.height = Const.screenHeight;
		TexturePacker.process("unpacked/", "/home/david/workspace/ufo/ufo/android/assets/img", "images");
//		TexturePacker.process("unpacked/", "/home/david/dev/eclipse/ufo/ufo/android/assets/img", "images");
		new LwjglApplication(new MainGame(), config);
	}
}
