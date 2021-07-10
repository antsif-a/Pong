package pong;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;

import static arc.Core.*;

class Renderer implements ApplicationListener {
    private final Pong.PongObjects objects;
    private final Camera camera;

    public Renderer(Pong.PongObjects objects) {
        this.objects = objects;
        this.camera = new Camera();
        this.camera.position.set(camera.project(0, 0));
    }

    @Override
    public void init() {
        Core.camera = camera;
    }

    @Override
    public void update() {
        camera.update();

        graphics.clear(Color.black);
        Draw.reset();

        shape(objects.leftPlayer);
        shape(objects.leftGoal, Color.red);
        shape(objects.rightPlayer);
        shape(objects.rightGoal, Color.blue);
        shape(objects.ball);

        Draw.flush();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
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
