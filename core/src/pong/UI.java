package pong;

import arc.*;
import arc.assets.*;
import arc.freetype.*;
import arc.freetype.FreeTypeFontGenerator.*;
import arc.freetype.FreetypeFontLoader.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.struct.*;
import arc.util.*;
import arc.util.async.*;

public class UI extends Pong.PongListener {
    private final Scene scene;
    private final Fonts fonts;
    private final Styles styles;

    public UI() {
        scene = new Scene();
        fonts = new Fonts();
        styles = new Styles();
    }

    @Override
    public void init() {
        Core.scene = scene;
        Core.scene.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
        Core.input.addProcessor(scene);

        // todo fix this strange problem with deps
        Core.assets.load(fonts);
        Core.assets.finishLoading();
        Core.assets.load(styles);
        Core.assets.finishLoading();

        setupUI();
    }

    private void setupUI() {
        scene.registerStyles(styles);
        scene.table(t -> {
            t.name = "score";
            t.top();
            t.label(() -> "Score: " + state.score[0] + ":" + state.score[1]).padTop(30);
            t.row();
            t.label(() -> "Press Enter to start").padTop(5).visible(() -> state.paused);
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

    @Override
    public void resize(int width, int height) {
        scene.resize(width, height);
    }

    public static class Fonts implements Loadable {
        private static final String fontsPrefix = "fonts/";
        private static final FreeTypeFontParameter defaultParameter = new FreeTypeFontParameter(){{
            incremental = true;
            size = 18;
        }};

        private final Seq<Font> all = new Seq<>();

        private Font robotoRegular;

        private Fonts() {
            Core.assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(Core.files::internal));
            Core.assets.setLoader(Font.class, new FreetypeFontLoader(Core.files::internal));
        }

        @Override
        public void loadSync() {
            loadFont("default", "roboto/Roboto-Regular.ttf", f -> robotoRegular = f);
        }

        private void loadFont(String name, String path, Cons<Font> loaded) {
            AssetDescriptor<Font> desc = Core.assets.load(name, Font.class,
                    new FreeTypeFontLoaderParameter(fontsPrefix + path, defaultParameter));

            desc.loaded = (f) -> {
                all.add(f);
                loaded.get(f);
            };
        }
    }

    public static class Styles implements Loadable {
        public Label.LabelStyle defaultLabel;

        @Override
        public void loadAsync() {
            Fonts fonts = Core.assets.get("Fonts");

            defaultLabel = new Label.LabelStyle(fonts.robotoRegular, Color.white);
        }

        @Override
        public Seq<AssetDescriptor> getDependencies() {
            return Seq.with(new AssetDescriptor<>(Fonts.class));
        }
    }
}
