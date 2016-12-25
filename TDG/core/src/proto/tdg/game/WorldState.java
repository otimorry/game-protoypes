package proto.tdg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import proto.tdg.game.Actions.DisplayOptionUIAction;
import proto.tdg.game.UI.GameTimer;
import proto.tdg.game.UI.OptionUI;
import proto.tdg.game.Utility.EventUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by olva on 11/24/16.
 */
public class WorldState {
    public static final float WORLD_WIDTH = 640;
    public static final float WORLD_HEIGHT = 480;
    public static FieldTile[][] world;
    public static OrthographicCamera CAM;
    public static int viewportWidth;
    public static int viewportHeight;
    public static float TILESIZE;
    public static float SCALE;
    public static Stage STAGE;
    public static Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public static OptionUI OptionUI;
    public static GameTimer Timer;

    public static Map<Integer, Entity> allGameObjects = new HashMap<>();
    private static int count;

    public static DisplayOptionUIAction activeUI;

    public WorldState() {

        new EventUtility(); // no need to reference

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        viewportWidth = (int)(WORLD_WIDTH / 4);
        viewportHeight = (int)(WORLD_HEIGHT / 4);

        SCALE = WORLD_HEIGHT / viewportHeight;

        int gridWidth = (int) (viewportWidth / SCALE);
        int gridHeight = (int) (viewportHeight / SCALE);

        TILESIZE = gridWidth / SCALE; // what is my tilesize ??

        world = new FieldTile[gridHeight][gridWidth];

        float camX = viewportWidth;
        float camY = viewportHeight * (height/width);

        CAM = new OrthographicCamera(camX, camY);
        Timer = new GameTimer(0,10,skin);

        STAGE = new Stage( new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, CAM), new SpriteBatch());
    }

    public static void AddObject(Entity entity) {
        allGameObjects.put(count, entity);
        entity.setId(count);
        count++;
    }
}
