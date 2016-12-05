package proto.tdg.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import proto.tdg.game.Actions.DisplayMoveAction;
import proto.tdg.game.Actions.DisplayOptionUIAction;
import proto.tdg.game.Actions.MyMoveToAction;
import proto.tdg.game.Notification.SelectResult;
import proto.tdg.game.UI.UI;

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

                    System.out.println("I'm touched -- " + tile.fieldId + " at tileX: " + tile.tileX + " tileY: " + tile.tileY);

                    if(activeUI != null) {
                        if(activeUI.getSelected() == null) {
                            NotifyUtility.FireNotification(Enums.Notify.CANCEL,null);
                            activeUI = null;
                        }
                        else {
                            switch (activeUI.getSelected()) {
                                case MOVE:
                                    // MoveToAction given target
                                    MyMoveToAction moveToAction = new MyMoveToAction(activeUI.getTileXY(), tile.getTileXY());
                                    moveToAction.setPrimary(true);
                                    STAGE.addAction(moveToAction);
                                    activeUI = null;
                                    break;
                                case ATTACK: break;
                                case DEFEND: break;
                                default:break;
                            }
                        }
                    }
                    else {
                        if(tile.getFieldObject(true) != null) {
                            System.out.println("DisplayOptionUIAction");
                            DisplayOptionUIAction displayOptionUIAction = new DisplayOptionUIAction(OptionUI, tile.tileX, tile.tileY);
                            NotifyUtility.AddNotifyListener(displayOptionUIAction);
                            activeUI = displayOptionUIAction;
                            STAGE.addAction(displayOptionUIAction);
                        }
                    }
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    ((FieldTile)event.getTarget()).highlight = true;
                    ((FieldTile)event.getTarget()).highlightColor = new Color(1,0,0,0.4f);
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
                    UI ui = (UI)event.getTarget().getParent();
                    Vector2 xy = ui.getParentContainer().getTileXY();

                    FieldTile tile = world[(int)xy.x][(int)xy.y];


                    //TODO: Add Logic for moving secondary object too
                    FieldObject obj = tile.getFieldObject(true);

                    System.out.println("I'm touched -- " + "MOVE" + "at tX: " + tile.tileX + " posY: " + tile.tileY);

                    if(obj instanceof EntityState) {
                        //mv = ((EntityState)obj).mv;
                        mv = 5;
                    }

                    Vector2 origin = new Vector2(tile.tileX,tile.tileY);
                    List<Vector2> possibles = new ArrayList<>();
                    possibles.add(origin);
                    buildPossibleMoves(possibles, new ArrayList<>(), origin, 0);
                    possibles.remove(origin);

                    InputUtil.possibleMoves = possibles;

                    for(Vector2 v : possibles) {
                        DisplayMoveAction displayMoveAction = new DisplayMoveAction(v, new Color(1,0,0,0.4f));

                        if(TileUtility.GetFieldTile(v).getFieldObject(true) != null &&
                                !TileUtility.GetFieldTile(v).getFieldObject(true).equals(obj)  ) {
                            displayMoveAction.setHighlightColor(new Color(0,1,1,0.4f));
                        }

                        NotifyUtility.AddNotifyListener(displayMoveAction);
                        STAGE.addAction(displayMoveAction);
                    }

                    SelectResult result = new SelectResult(Enums.Act.MOVE);
                    NotifyUtility.FireNotification(Enums.Notify.SELECT, result);

                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    event.getTarget().setColor(Color.GREEN);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    event.getTarget().setColor(Color.WHITE);
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
                        if(v.y + 1 < world.length)
                            up = new Vector2( v.x, v.y + 1 );

                        if(v.y - 1 >= 0)
                            down = new Vector2( v.x, v.y - 1 );

                        if(v.x + 1 < world[0].length)
                            right = new Vector2( v.x + 1, v.y);

                        if(v.x - 1 >= 0)
                            left = new Vector2( v.x - 1, v.y);

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
