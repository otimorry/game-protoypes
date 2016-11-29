package proto.tdg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by olva on 11/24/16.
 */
public class WorldState {
    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;
    public static final int TILESIZE = 1;
    public static FieldTile[][] world = new FieldTile[15][15];
    public static OrthographicCamera CAM;
    public static boolean[] keys = new boolean[256];
    public static int viewportWidth = 10;
    public static int viewportHeight = 10;
    public static Stage STAGE;
    public static Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public static VerticalGroup table;


    public WorldState() {
        table = createTable(skin);
        table.setPosition(1.5f, 1.1f);
        table.setTouchable(Touchable.enabled);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getWidth();

        float camX = WorldState.viewportWidth;
        float camY = WorldState.viewportHeight * (height/width);

        CAM = new OrthographicCamera(camX, camY);
        STAGE = new Stage( new FitViewport(camX, camY, WorldState.CAM), new SpriteBatch());
    }

    private VerticalGroup createTable(Skin skin) {
        VerticalGroup t = new VerticalGroup();
        t.setScale(1.5f / WorldState.WORLD_HEIGHT);

        Label l1 = new Label("MOVE", skin);
        l1.setName("MOVE");
        l1.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("I'm touched -- " + "MOVE" + "at x: " + x + " y: " + y);
                InputUtil.action = Enums.Act.MOVE;
//                InputUtil.selectedActor = player;
                InputUtil.needAction = true;
                return true;
            }
        });

        Label l2 = new Label("ATTACK", skin);
        l2.setName("ATTACK");
        l2.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("I'm touched -- " + "ATTACK" + "at x: " + x + " y: " + y);
                InputUtil.action = Enums.Act.ATTACK;
//                InputUtil.selectedActor = player;
//                InputUtil.needAction = true;
                return true;
            }
        });

        Label l3 = new Label("DEFEND", skin);
        l3.setName("DEFEND");
        l3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("I'm touched -- " + "DEFEND" + "at x: " + x + " y: " + y);
                InputUtil.action = Enums.Act.DEFEND;
//                InputUtil.selectedActor = player;
//                InputUtil.needAction = true;
                return true;
            }
        });

        t.addActor(l1);
        t.addActor(l2);
        t.addActor(l3);

        return t;
    }

}
