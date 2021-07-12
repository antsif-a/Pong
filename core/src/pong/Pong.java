package pong;

import arc.*;
import arc.assets.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;

import static arc.Core.*;

public class Pong extends ApplicationCore {
	private final PongObjects objects;
	private final PongState state;

	public Pong() {
		objects = new PongObjects();
		state = new PongState();
	}

	@Override
	public void setup() {
		batch = new SpriteBatch();
		assets = new AssetManager();

		atlas = new TextureAtlas("sprites/sprites.aatls");

		objects.init();

		add(new Renderer());
		add(new UI());
		add(new Logic());
	}

	public void add(PongListener listener) {
		listener.setObjects(objects);
		listener.setState(state);

		super.add(listener);
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

	static class PongState {
		final long[] score = new long[]{0, 0};
		boolean paused = true;
	}

	static abstract class PongListener implements ApplicationListener {
		protected PongObjects objects;
		protected PongState state;

		private void setObjects(PongObjects objects) {
			this.objects = objects;
		}

		private void setState(PongState state) {
			this.state = state;
		}
	}
}
