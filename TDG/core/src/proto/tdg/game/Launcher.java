package proto.tdg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Launcher extends ApplicationAdapter {
	public static final int WORLD_WIDTH = 100;
	public static final int WORLD_HEIGHT = 100;
    public static final int TILESIZE = 1;
	public static OrthographicCamera CAM;

    public static boolean[] keys = new boolean[256];
    public static int viewportWidth = 10;
    public static int viewportHeight = 10;

    SpriteBatch batch;
    FieldTile player, bg;
    ShapeRenderer shapeRenderer;
    Label moveLabel;
    VerticalGroup table;
    private Stage stage;

    // 1 -- player
    // 2 -- wall

    FieldTile[][] world = new FieldTile[15][15];

	@Override
	public void create () {

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getWidth();

        float camX = viewportWidth;
        float camY = viewportHeight * (height/width);

        CAM = new OrthographicCamera(camX, camY);
        stage = new Stage( new FitViewport(camX, camY, CAM), new SpriteBatch());

        bg = new FieldTile("background", 0,0,WORLD_WIDTH, WORLD_HEIGHT, new Texture("bg.png"));

        // init field tiles
        for(int row = 0; row < world.length; row++ ) {
            for(int col = 0; col < world[row].length; col++ ) {
                String id = "Field[" + row + "][" + col +"]";

                //player
                if(row == 0 && col == 0) {
                    world[row][col] = new FieldTile("player", 0,0,TILESIZE, TILESIZE, new Texture("badlogic.jpg"));
                }
                // other tiles
                else {
                    world[row][col] = new FieldTile(id,row,col,TILESIZE, TILESIZE, null);
                }

                world[row][col].setTouchable(Touchable.enabled);
                stage.addActor(world[row][col]);
            }
        }



        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        table = createTable(skin);
        table.setPosition(1.5f, 1.1f);

        bg.setTouchable(Touchable.disabled);
        table.setTouchable(Touchable.enabled);

        stage.addActor(bg);
        stage.addActor(world[0][0]);
        stage.addActor(table);
        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
	}

    private VerticalGroup createTable(Skin skin) {
        VerticalGroup t = new VerticalGroup();
        t.setScale(1.5f / WORLD_HEIGHT);

        Label l1 = new Label("MOVE", skin);
        l1.setName("MOVE");
        l1.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("I'm touched -- " + "MOVE" + "at x: " + x + " y: " + y);
                InputUtil.action = FieldTile.Act.MOVE;
                InputUtil.selectedActor = player;
                InputUtil.needAction = true;
                return true;
            }
        });

        Label l2 = new Label("ATTACK", skin);
        l2.setName("ATTACK");
        l2.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("I'm touched -- " + "ATTACK" + "at x: " + x + " y: " + y);
                InputUtil.action = FieldTile.Act.ATTACK;
                InputUtil.selectedActor = player;
                InputUtil.needAction = true;
                return true;
            }
        });

        Label l3 = new Label("DEFEND", skin);
        l3.setName("DEFEND");
        l3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("I'm touched -- " + "DEFEND" + "at x: " + x + " y: " + y);
                InputUtil.action = FieldTile.Act.DEFEND;
                InputUtil.selectedActor = player;
                InputUtil.needAction = true;
                return true;
            }
        });

        t.addActor(l1);
        t.addActor(l2);
        t.addActor(l3);

        return t;
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(173f/255f, 216f/255f, 230f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        shapeRenderer.begin();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);



//        // Render game world
//        for( int row = 0; row < world.length; row++ ) {
//            for( int col = 0; col < world[row].length; col++ ) {
//
//                //wall
//                if( world[row][col] == 2) {
//                    shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
//                    shapeRenderer.setColor(Color.BLACK);
//                    shapeRenderer.ellipse(col * TILESIZE, (world.length -1 - row) * TILESIZE, TILESIZE, TILESIZE);
//                }
//
//                // grid
//                shapeRenderer.set(ShapeRenderer.ShapeType.Line);
//                shapeRenderer.setColor(Color.BLACK);
//                shapeRenderer.rect(col * TILESIZE, (world.length -1 - row) * TILESIZE, TILESIZE, TILESIZE);
//            }
//        }

        shapeRenderer.end();
	}

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, false);
    }
}
