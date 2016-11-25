package proto.tdg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

import static proto.tdg.game.WorldState.*;

public class Launcher extends ApplicationAdapter {
    SpriteBatch batch;
    FieldTile player, bg;
    ShapeRenderer shapeRenderer;

	@Override
	public void create () {
        new WorldState(); // Don't reference -- just instantiate


        bg = new FieldTile("background", 0,0,15,15, new Texture("bg.png"));

        STAGE.addActor(bg);

        // init field tiles
        for(int row = 0; row < world.length; row++ ) {
            for(int col = 0; col < world[row].length; col++ ) {
                String id = "Field[" + row + "][" + col +"]";

                //player
                if(row == 0 && col == 0) {
                    world[row][col] = new FieldTile("player", 0,0, WorldState.TILESIZE, WorldState.TILESIZE, new Texture("badlogic.jpg"));
                }
                // other tiles
                else {
                    world[row][col] = new FieldTile(id,row,col, WorldState.TILESIZE, WorldState.TILESIZE, null);
                }

                world[row][col].setTouchable(Touchable.enabled);
                STAGE.addActor(world[row][col]);
            }
        }

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        bg.setTouchable(Touchable.disabled);

        STAGE.setDebugAll(true);
        Gdx.input.setInputProcessor(STAGE);
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(173f/255f, 216f/255f, 230f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        STAGE.act(Gdx.graphics.getDeltaTime());
        STAGE.draw();

        shapeRenderer.begin();
        shapeRenderer.setProjectionMatrix(STAGE.getCamera().combined);
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
        STAGE.getViewport().update(width, height, false);
    }
}
