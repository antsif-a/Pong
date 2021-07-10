package pong;

import arc.*;
import arc.assets.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;

import static arc.Core.*;

public class Pong extends ApplicationCore {
	private final PongObjects objects = new PongObjects();

	@Override
	public void setup() {
		batch = new SpriteBatch();
		assets = new AssetManager();

		atlas = new TextureAtlas("sprites/sprites.atlas");

		objects.init();

		add(new Renderer(objects));
		add(new Logic(objects));
	}

	static class PongObjects {
		Rect leftGoal;
		Rect leftPlayer;
		Rect rightGoal;
		Rect rightPlayer;
		Circle ball;

		private void init() {
			leftGoal = new Rect(5, 0, 10, graphics.getHeight() * 2);
			leftPlayer = new Rect(40 + leftGoal.width, graphics.getHeight() / 2f, 10, 100);
			rightGoal = new Rect(graphics.getWidth() - 5, 0, 10, graphics.getHeight() * 2);
			rightPlayer = new Rect(graphics.getWidth() - rightGoal.width - 40, graphics.getHeight() / 2f, 10, 100);

			ball = new Circle(graphics.getWidth() / 2f, graphics.getHeight() / 2f, 10);
		}
	}
}
