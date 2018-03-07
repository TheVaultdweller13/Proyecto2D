package com.scaperoom.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.scaperoom.game.game.Juego;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "La Fuga de Bernard";
		config.addIcon("icon.bmp", Files.FileType.Internal);
		new LwjglApplication(new Juego(), config);
	}
}
