package pong;

import arc.*;
import arc.freetype.*;
import arc.freetype.FreeTypeFontGenerator.*;
import arc.freetype.FreetypeFontLoader.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.struct.*;

public class UI extends Pong.PongListener {
    private final Scene scene;
    private final Fonts fonts;

    public UI() {
        scene = new Scene();
        fonts = new Fonts();
    }

    @Override
    public void init() {
        fonts.loadFonts();

        Core.scene = scene;
        Core.scene.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
        Core.scene.addStyle(Label.LabelStyle.class, new Label.LabelStyle(fonts.robotoRegular, Color.white));
        Core.input.addProcessor(scene);

        setupUI();
    }

    private void setupUI() {
        scene.table(t -> {
            t.name = "score";
            t.top();
            t.label(() -> "Score: " + objects.score[0] + ":" + objects.score[1]).padTop(30);
        });
    }

    private void debug(Element element) {
        if (element instanceof Group g) {
            g.getChildren().each(this::debug);
        }

        Draw.z(100);
        Draw.color(Color.green);
        Lines.rect(element.x, element.y, element.getWidth(), element.getHeight());
        Draw.color();
    }

    @Override
    public void update() {
        scene.act();
        scene.draw();
    }

    static class Fonts {
        private static final String fontsPrefix = "fonts/";
        private static final FreeTypeFontParameter defaultParameter = new FreeTypeFontParameter(){{
            incremental = true;
            size = 18;
        }};

        private final Seq<Font> all = new Seq<>();

        private Font robotoRegular;
        private Font robotoLight;

        private Fonts() {
            Core.assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(Core.files::internal));
            Core.assets.setLoader(Font.class, new FreetypeFontLoader(Core.files::internal));
        }

        private void loadFonts() {
            robotoRegular = loadFont("roboto/Roboto-Regular.ttf");
            robotoLight = loadFont("roboto/Roboto-Light.ttf");
        }

        private Font loadFont(String path) {
            path = fontsPrefix + path;

            Core.assets.load(path, Font.class, new FreeTypeFontLoaderParameter(path, defaultParameter));
            Core.assets.finishLoadingAsset(path);
            Font loaded = Core.assets.get(path);
            loaded.setUseIntegerPositions(true);
            all.add(loaded);

            return loaded;
        }
    }
}
