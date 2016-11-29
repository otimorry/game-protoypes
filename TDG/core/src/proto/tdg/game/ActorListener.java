package proto.tdg.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import proto.tdg.game.Actions.DisplayMoveAction;

import java.util.ArrayList;
import java.util.List;

import static proto.tdg.game.WorldState.*;

/**
 * Created by Olva on 7/9/16.
 */
public class ActorListener {

    private static InputListener playerListener, fieldListener, moveListener;

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

                    System.out.println("I'm touched -- " + tile.fieldId + " at x: " + x + " y: " + y);

                    if(InputUtil.needAction) {
                        InputUtil.screenStartPt = new Vector2(tile.x,tile.y);
                        InputUtil.needAction = false;
                    }
                    else {
                        InputUtil.SetSelected(event.getTarget());

                        // for now to locate player
                        if(tile.getFieldObject(true) != null) {
                            table.setVisible(true);
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

    public static InputListener GetMoveListener() {
        if(moveListener == null) {
            moveListener = new InputListener() {

                private int mv;

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    FieldTile tile = (FieldTile)InputUtil.selectedActor;

                    //TODO: Logic for moving secondary object
                    FieldObject obj = tile.getFieldObject(true);

                    System.out.println("I'm touched -- " + "MOVE" + "at x: " + x + " y: " + y);
                    InputUtil.action = Enums.Act.MOVE;
                    InputUtil.needAction = true;

                    if(obj instanceof EntityState) {
                        //mv = ((EntityState)obj).mv;
                        mv = 3;
                    }

                    Vector2 origin = new Vector2(tile.x,tile.y);
                    List<Vector2> possibles = new ArrayList<>();
                    possibles.add(origin);
                    buildPossibleMoves(possibles, new ArrayList<Vector2>(), origin, 0);
                    possibles.remove(origin);

                    InputUtil.possibleMoves = possibles;

                    for(Vector2 v : possibles) {
                        world[(int)v.x][(int)v.y].addAction(new DisplayMoveAction());
                    }

                    return true;
                }

                private List<Vector2> buildPossibleMoves( List<Vector2> all, List<Vector2> ignore, Vector2 origin, int current ) {

                    if( current >= mv ) return all;
                    else{
                        List<Vector2> temp = fillEmptyAdjacent(all, new ArrayList<>(), ignore);

                        all.remove(origin);

                        for( Vector2 a : all ) {
                            if( !ignore.contains(a) ) ignore.add(a);
                        }

                        for( Vector2 t : temp ) all.add(t);

                        return buildPossibleMoves( all, ignore, origin, current + 1 );
                    }
                }

                private List<Vector2> fillEmptyAdjacent(List<Vector2> all,
                                                             List<Vector2> temp,
                                                             List<Vector2> ignore ) {

                    for( Vector2 v : all ) {
                        if( ignore.contains(v)) continue;

                        Vector2 up = null, left = null, right = null, down = null;
                        if(v.y + WorldState.TILESIZE < world.length)
                            up = new Vector2( v.x, v.y + WorldState.TILESIZE );

                        if(v.y - WorldState.TILESIZE >= 0)
                            down = new Vector2( v.x, v.y - WorldState.TILESIZE );

                        if(v.x + WorldState.TILESIZE < world[0].length)
                            right = new Vector2( v.x + WorldState.TILESIZE, v.y);

                        if(v.x - WorldState.TILESIZE >= 0)
                            left = new Vector2( v.x - WorldState.TILESIZE, v.y);

                        if( up != null && !all.contains(up) && !temp.contains(up)) temp.add(up);
                        if( down != null && !all.contains(down) && !temp.contains(down)) temp.add(down);
                        if( left != null && !all.contains(left) && !temp.contains(left)) temp.add(left);
                        if( right != null && !all.contains(right) && !temp.contains(right)) temp.add(right);
                    }

                    return temp;
                }
            };
        }
        return moveListener;
    }
}
