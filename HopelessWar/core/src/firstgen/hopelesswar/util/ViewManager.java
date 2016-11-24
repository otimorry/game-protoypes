package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Olva on 7/24/16.
 */
public class ViewManager {
    private static Map<Enums.View, BaseViewHandler> viewHandlers = new EnumMap<>(Enums.View.class);
    private static Map<Enums.View, BaseUI> UIs = new EnumMap<>(Enums.View.class);
    private static Map<Enums.System, EntitySystem> systems = new EnumMap<>(Enums.System.class);
    private static Engine engine;

    public static Enums.View CurrentView = Enums.View.GAME;

    public ViewManager(Engine e) {
        viewHandlers.put(Enums.View.GAME, new GameViewHandler(e));
        viewHandlers.put(Enums.View.COMMAND, new CommandViewHandler(e));

        engine = e;
    }

    public void registerUI(Enums.View view, BaseUI ui) {
        UIs.put(view,ui);
    }

    public void registerSystem(Enums.System key, EntitySystem value) {
        systems.put(key,value);
    }

    public static void DisableSystem(Enums.System system) {
        engine.removeSystem(systems.get(system));
    }

    public static void EnableSystem(Enums.System system) {
        engine.addSystem(systems.get(system));
    }

    public static BaseViewHandler GetCurrentViewHandler(Enums.View view) {
        return viewHandlers.get(view);
    }

    public static BaseUI GetCurrentViewUI(Enums.View view) {
        return UIs.get(view);
    }
}
