package pong;

import arc.input.*;
import arc.math.*;
import arc.math.geom.*;

import static arc.Core.*;

class Logic extends Pong.PongListener implements InputProcessor {
    private int ballAngle = 45;
    private boolean paused = true;

    @Override
    public void init() {
        input.addProcessor(this);
    }

    @Override
    public boolean keyDown(KeyCode key) {
        if (key == KeyCode.enter) {
            paused = !paused;
        }

        return true;
    }

    @Override
    public void update() {
        handleInput();

        if (paused) return;

        // move ball
        objects.ball.x = objects.ball.x + Mathf.cosDeg(ballAngle) * 5;
        objects.ball.y = objects.ball.y + Mathf.sinDeg(ballAngle) * 5;

        // check for goals
        if (objects.ball.x <= objects.leftGoal.x + objects.leftGoal.width ||
                objects.ball.x >= objects.rightGoal.x - objects.rightGoal.width) {
            if (objects.ball.x <= objects.leftGoal.x + objects.leftGoal.width) {
                objects.score[1]++;
            } else {
                objects.score[0]++;
            }

            resetState();
        }

        final float k = 1.1f;

        // collide with borders
        if (objects.ball.x >= graphics.getWidth() - objects.ball.radius * k ||
                objects.ball.x <= 0 + objects.ball.radius * k ||
                objects.ball.y >= graphics.getHeight() - objects.ball.radius * k ||
                objects.ball.y <= 0 + objects.ball.radius * k) {
            ballAngle += 90;
        }

        // collide with left player
        if (objects.ball.x < objects.leftPlayer.x + objects.leftPlayer.width * 2
                && objects.ball.y < objects.leftPlayer.y + objects.leftPlayer.height / 2
                && objects.ball.y > objects.leftPlayer.y - objects.leftPlayer.height / 2) {
            ballAngle += 90;
        }

        // collide with right player
        if (objects.ball.x > objects.rightPlayer.x - objects.rightPlayer.width * 2
                && objects.ball.y < objects.rightPlayer.y + objects.rightPlayer.height / 2
                && objects.ball.y > objects.rightPlayer.y - objects.rightPlayer.height / 2) {
            ballAngle += 90;
        }
    }

    private void handleInput() {
        if (input.keyDown(KeyCode.w)) {
            movePlayer(objects.leftPlayer, 10);
        } else if (input.keyDown(KeyCode.s)) {
            movePlayer(objects.leftPlayer, -10);
        } else if (input.keyDown(KeyCode.up)) {
            movePlayer(objects.rightPlayer, 10);
        } else if (input.keyDown(KeyCode.down)) {
            movePlayer(objects.rightPlayer, -10);
        }

        if (input.keyDown(KeyCode.escape)) {
            paused = true;
        }
    }

    private void resetState() {
        paused = true;
        objects.ball.x = graphics.getWidth() / 2f;
        objects.ball.y = graphics.getHeight() / 2f;
    }

    private void movePlayer(Rect player, int amount) {
        player.y = Mathf.clamp(Mathf.lerpDelta(player.y, player.y + amount, 0.5f),
                0 + player.height / 2,
                graphics.getHeight() - player.height / 2);
    }
}
