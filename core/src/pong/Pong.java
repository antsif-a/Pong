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
		state = new PongState();
		objects = new PongObjects(state);
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

	public static class PongObjects {
		private static final int ballRadius = 10;
		private static final int goalWidth = 20;
		private static final int playerWidth = 10;
		private static final int playerHeight = 100;

		private final PongState state;

		public final Rect leftGoal = new Rect();
		public final Rect leftPlayer = new Rect();
		public final Rect rightGoal = new Rect();
		public final Rect rightPlayer = new Rect();
		public final Circle ball = new Circle();

		public PongObjects(PongState state) {
			this.state = state;
		}

		public void init() {
			resize(graphics.getWidth(), graphics.getHeight());
		}

		public void resize(int width, int height) {
			leftGoal.setPosition(0, 0).setSize(goalWidth, height * 2);
			rightGoal.setPosition(width, 0).setSize(goalWidth, height * 2);
			leftPlayer.setPosition(new Vec2(leftGoal.x, height / 2f).add(30, 0)).setSize(playerWidth, playerHeight);
			rightPlayer.setPosition(new Vec2(rightGoal.x, height / 2f).sub(30, 0)).setSize(playerWidth, playerHeight);

			if (state.paused) {
				ball.setPosition(graphics.getWidth() / 2f, graphics.getHeight() / 2f).setRadius(ballRadius);
			}
		}
	}

	public static class PongState {
		public final long[] score = new long[]{0, 0};
		public boolean paused = true;
	}

	public static abstract class PongListener implements ApplicationListener {
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
