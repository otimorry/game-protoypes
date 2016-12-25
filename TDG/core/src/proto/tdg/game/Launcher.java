package proto.tdg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import proto.tdg.game.Actions.TimerAction;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.UI.GameTimer;
import proto.tdg.game.UI.OptionUI;
import proto.tdg.game.Utility.EventUtility;

import static proto.tdg.game.WorldState.*;

public class Launcher extends ApplicationAdapter {
    FieldTile bg;
    ShapeRenderer shapeRenderer;

	@Override
	public void create () {
        new WorldState(); // Don't reference -- just instantiate
        bg = new FieldTile("background", 0,0, viewportWidth, viewportHeight);
        bg.setFieldObject(new EntityState(bg, new TextureRegion(new Texture("bg.png"))),true);

        Group bgGroup = new Group();
        Group fgGroup = new Group();
        Group hudGroup = new Group();

        // init field tiles
        for(int row = 0; row < world.length; row++ ) {
            for(int col = 0; col < world[row].length; col++ ) {
                String id = "Field[" + row + "][" + col +"]";

                //player
                if(row == 2 && col == 2) {
                    world[row][col] = new FieldTile(id,row,col, TILESIZE);
                    EntityState player = new EntityState(world[row][col], new TextureRegion(new Texture("badlogic.jpg")));
                    player.time = 2;
                    world[row][col].setFieldObject(player,true);
                }
                // player 2
                else if(row == 4 && col == 2) {
                    world[row][col] = new FieldTile(id,row,col, TILESIZE);
                    EntityState player = new EntityState(world[row][col], new TextureRegion(new Texture("badlogic.jpg")));
                    player.time = 4;
                    world[row][col].setFieldObject(player,true);
                }
                //enemy
                else if(row == 4 && col == 4) {
                    world[row][col] = new FieldTile(id,row,col, TILESIZE);
                    EntityState enemy = new EntityState(world[row][col], new TextureRegion(new Texture("badlogic.jpg")));
                    enemy.time = 6;
                    world[row][col].setFieldObject(enemy,true);
                }

                // other tiles
                else {
                    world[row][col] = new FieldTile(id,row,col, TILESIZE);
                }

                world[row][col].setTouchable(Touchable.enabled);
                fgGroup.addActor(world[row][col]);
            }
        }

        Table rootTable = new Table();
        rootTable.setFillParent(true);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        bg.setTouchable(Touchable.disabled);
        bgGroup.addActor(bg);

        OptionUI = new OptionUI();
        hudGroup.addActor(OptionUI.getUI());

        rootTable.add(hudGroup).expand().pad(4f);
        Timer.addTo(rootTable);

        STAGE.addActor(bgGroup);
        STAGE.addActor(fgGroup);
        STAGE.addActor(rootTable);
        STAGE.getBatch().enableBlending();
        STAGE.setDebugAll(true);

        STAGE.addAction(Timer);
        Timer.setTimerState(GameTimer.TimerState.RUNNING);

        Gdx.input.setInputProcessor(STAGE);
    }


	@Override
	public void render () {
		Gdx.gl.glClearColor(173f/255f, 216f/255f, 230f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        EventUtility.RemoveOldEvents();

        STAGE.act(Gdx.graphics.getDeltaTime());
        STAGE.draw();

        //EventUtility.PrintTotalListeners();
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
