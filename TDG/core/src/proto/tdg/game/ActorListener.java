package proto.tdg.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static proto.tdg.game.WorldState.STAGE;
import static proto.tdg.game.WorldState.table;

/**
 * Created by Olva on 7/9/16.
 */
public class ActorListener {

    private static InputListener playerListener, fieldListener;

    public static InputListener GetPlayerListener() {
        if(playerListener == null) {
            playerListener = new InputListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    super.touchDragged(event, x, y, pointer);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                }

                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                    return super.keyDown(event, keycode);
                }

                @Override
                public boolean keyUp(InputEvent event, int keycode) {
                    return super.keyUp(event, keycode);
                }
            };
        }
        return playerListener;
    }

    public static InputListener GetFieldListener() {
        if(fieldListener == null) {
            fieldListener = new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    FieldTile tile = (FieldTile)event.getTarget();

                    System.out.println("I'm touched -- " + tile.id + " at x: " + x + " y: " + y);

                    if(InputUtil.needAction) {
                        InputUtil.screenStartPt = new Vector2(tile.x,tile.y);
                        InputUtil.needAction = false;
                    }
                    else {
                        InputUtil.SetSelected(event.getTarget());

                        // for now to locate player
                        if(tile.img != null) {
                            STAGE.addActor(table);
                        }
                    }
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    ((FieldTile)event.getTarget()).highlight = true;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    ((FieldTile)event.getTarget()).highlight = false;
                }
            };
        }
        return fieldListener;
    }
}
