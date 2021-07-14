package pong.desktop;

import arc.*;
import arc.backend.sdl.*;
import pong.*;

public class DesktopLauncher {
	public static void main(String[] arg) {
		SdlConfig config = new SdlConfig();
		config.title = "Pong";
		config.width = 1000;
		config.height = 700;
		config.setWindowIcon(Files.FileType.internal, "icons/icon.png");

		new SdlApplication(new Pong(), config);
	}
}
