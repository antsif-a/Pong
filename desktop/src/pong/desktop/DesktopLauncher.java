package pong.desktop;

import arc.backend.sdl.*;
import pong.*;

public class DesktopLauncher {
	public static void main(String[] arg) {
		SdlConfig config = new SdlConfig();
		config.title = "Pong";
		config.width = 1000;
		config.height = 700;
		config.resizable = false;

		new SdlApplication(new Pong(), config);
	}
}
