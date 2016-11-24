package proto.tdg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends ApplicationAdapter {
	public static final int WORLD_WIDTH = 1000;
	public static final int WORLD_HEIGHT = 1000;
    public static final int TILESIZE = 32;
	public static OrthographicCamera CAM;
    public static boolean[] keys = new boolean[256];

    SpriteBatch batch;
    Texture bg;
    Player player;
    ShapeRenderer shapeRenderer;
    Label moveLabel;
    VerticalGroup table;

    // 1 -- player
    // 2 -- wall

    int[][] world = {
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }, //5
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 2,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,2,0,0,0,0,0,0,0,0,0,0,0,0,0 }, //10
            { 0,0,2,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,0,2,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,0,2,0,0,0,0,0,0,0,0,0,0,0 },
            { 0,0,2,0,0,0,0,0,0,0,0,0,0,0,0 }, //15
    };

	@Override
	public void create () {
		batch = new SpriteBatch();

        player = new Player(0,0, TILESIZE, TILESIZE,new Texture("badlogic.jpg"));

        shapeRenderer = new ShapeRenderer();
        bg = new Texture(Gdx.files.internal("bg.png"));

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        table = new VerticalGroup();
        table.setPosition(50, 75, Align.center);
        table.setSize(25,25);

        Label l1 = new Label("MOVE", skin);
        l1.setName("MOVE");

        Label l2 = new Label("ATTACK", skin);
        l1.setName("ATTACK");

        Label l3 = new Label("DEFEND", skin);
        l1.setName("DEFEND");

        table.addActor(l1);
        table.addActor(l2);
        table.addActor(l3);


        float width =  Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float camX = 640;
        float camY = 640 * (height / width);

        shapeRenderer.setAutoShapeType(true);

        CAM = new OrthographicCamera(camX, camY);
        CAM.position.set(camX / 2, camY / 2, 0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        CAM.update();
        batch.setProjectionMatrix(CAM.combined);
        shapeRenderer.setProjectionMatrix(CAM.combined);

        // Draw bg
		batch.begin();
        batch.draw(bg, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.end();

        shapeRenderer.begin();


        // Render game world
        for( int row = 0; row < world.length; row++ ) {
            for( int col = 0; col < world[row].length; col++ ) {

                //wall
                if( world[row][col] == 2) {
                    shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.ellipse(col * TILESIZE, (world.length -1 - row) * TILESIZE, TILESIZE, TILESIZE);
                }

                // grid
                shapeRenderer.set(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.rect(col * TILESIZE, (world.length -1 - row) * TILESIZE, TILESIZE, TILESIZE);
            }
        }

        batch.begin();
        batch.draw(player.img, player.x, player.y, player.width, player.height);
        table.draw(batch,1);
        batch.end();

        // Display Menu
        if(CombatDialogUtil.DisplayMenu) {

        }

        shapeRenderer.end();
	}
}
