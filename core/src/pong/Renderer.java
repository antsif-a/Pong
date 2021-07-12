package pong;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;

class Renderer extends Pong.PongListener {
    private final Camera camera;

    public Renderer() {
        camera = new Camera();
        camera.position.set(camera.project(0, 0));
    }

    @Override
    public void init() {
        Core.camera = camera;
        Core.camera.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
    }

    @Override
    public void update() {
        camera.update();

        Core.graphics.clear(Color.black);

        Draw.reset();
        Draw.proj(camera);

        shape(objects.leftPlayer);
        shape(objects.leftGoal, Color.red);
        shape(objects.rightPlayer);
        shape(objects.rightGoal, Color.blue);
        shape(objects.ball);

        Draw.flush();
    }

    private <T extends Shape2D> void shape(T obj) {
        shape(obj, Color.white);
    }

    private <T extends Shape2D> void shape(T obj, Color color) {
        Draw.color(color);
        if (obj instanceof Rect r) {
            Fill.rect(r.x, r.y, r.width, r.height);
        } else if (obj instanceof Circle c) {
            Fill.circle(c.x, c.y, c.radius);
        }
        Draw.color();
    }
}
