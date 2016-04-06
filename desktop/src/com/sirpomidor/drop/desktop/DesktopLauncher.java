package com.sirpomidor.drop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sirpomidor.drop.Drop;
import com.sirpomidor.drop.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "GameScreen";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Drop(), config);
	}
}
