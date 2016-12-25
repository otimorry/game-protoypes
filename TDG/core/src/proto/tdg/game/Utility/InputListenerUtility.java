package proto.tdg.game.Utility;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import proto.tdg.game.Actions.AttackAction;
import proto.tdg.game.Actions.DisplayMoveAction;
import proto.tdg.game.Actions.DisplayOptionUIAction;
import proto.tdg.game.Actions.MyMoveToAction;
import proto.tdg.game.EntityState;
import proto.tdg.game.FieldObject;
import proto.tdg.game.FieldTile;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.UI.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static proto.tdg.game.WorldState.*;

/**
 * Created by Olva on 7/9/16.
 */
public class InputListenerUtility {

    private static InputListener playerListener, fieldListener, moveListener, attackListener;

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
                            System.out.println("Nothing selected");
                            GameEvent.FireCancelEvent();
                            activeUI = null;
                        }
                        else {
                            System.out.println("Selected: " + activeUI.getSelected());
                            FieldTile origin = TileUtility.GetFieldTile(activeUI.getTileXY());

                            switch (activeUI.getSelected()) {
                                case MOVE:
                                    // MoveToAction given target
                                    MyMoveToAction moveToAction = new MyMoveToAction(activeUI.getTileXY(), tile.getTileXY());
                                    moveToAction.setPrimary(true);
                                    STAGE.addAction(moveToAction);
                                    break;
                                case ATTACK:
                                    // Attack target
                                    AttackAction attackAction = new AttackAction(origin.getFieldObject(true), tile.getFieldObject(true));
                                    STAGE.addAction(attackAction);
                                    activeUI = null;
                                    break;
                                case DEFEND: break;
                                default:break;
                            }
                            // clear selection
                            if(activeUI != null) {
                                activeUI.setSelected(null);
                            }
                        }
                    }
                    else {
                        System.out.println("fresh yo");
                        if(tile.getFieldObject(true) != null) {
                            System.out.println("DisplayOptionUIAction");
                            DisplayOptionUIAction displayOptionUIAction = new DisplayOptionUIAction(OptionUI, tile.tileX, tile.tileY);
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

                    ui.getParentContainer().hide(true);

                    FieldTile tile = world[(int)xy.x][(int)xy.y];


                    //TODO: Add Logic for moving secondary object too
                    FieldObject obj = tile.getFieldObject(true);

                    System.out.println("I'm touched -- " + "MOVE" + "at tX: " + tile.tileX + " posY: " + tile.tileY);

                    if(obj instanceof EntityState) {
                        //mv = ((EntityState)obj).mv;
                        mv = 5;
                    }

                    Vector2 origin = new Vector2(tile.tileX,tile.tileY);
                    List<Vector2> possibleMoves = findPossibleMoves(new ArrayList<>(), origin, mv);

                    InputUtility.possibleMoves = possibleMoves;

                    for(Vector2 v : possibleMoves) {
                        DisplayMoveAction displayMoveAction = new DisplayMoveAction(v, new Color(0,0,0.2f,0.4f));

//                        if(TileUtility.GetFieldTile(v).getFieldObject(true) != null &&
//                                !TileUtility.GetFieldTile(v).getFieldObject(true).equals(obj)  ) {
//                            displayMoveAction.setHighlightColor(new Color(0,1,1,0.4f));
//                        }

                        STAGE.addAction(displayMoveAction);
                    }

                    GameEvent.FireSelectEvent(EnumsUtility.Act.MOVE, null);

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
            };
        }
        return moveListener;
    }

    public static InputListener GetAttackListener() {
        if(attackListener == null) {
            attackListener = new InputListener() {
                private int mv;

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    UI ui = (UI)event.getTarget().getParent();
                    Vector2 xy = ui.getParentContainer().getTileXY();

                    FieldTile tile = world[(int)xy.x][(int)xy.y];

                    ui.getParentContainer().hide(true);


                    //TODO: Add Logic for moving secondary object too
                    FieldObject obj = tile.getFieldObject(true);

                    System.out.println("I'm touched -- " + "ATTACK" + "at tX: " + tile.tileX + " posY: " + tile.tileY);

                    if(obj instanceof EntityState) {
                        //mv = ((EntityState)obj).mv;
                        mv = 1;
                    }

                    Vector2 origin = new Vector2(tile.tileX,tile.tileY);
                    List<Vector2> possibleMoves = findPossibleMoves(new ArrayList<>(), origin, mv);
                    InputUtility.possibleMoves = possibleMoves;

                    for(Vector2 v : possibleMoves) {
                        DisplayMoveAction displayMoveAction = new DisplayMoveAction(v, new Color(0,0,1,0.4f));
                        STAGE.addAction(displayMoveAction);
                    }

                    GameEvent.FireSelectEvent(EnumsUtility.Act.ATTACK, null);

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
            };
        }
        return attackListener;
    }

    private static List<Vector2> findNeighbors(FieldTile from, Vector2 v) {
        List<Vector2> neighbors = new ArrayList<>();

        Vector2 v2 = null;
        FieldTile to = null;

        if(v.y + 1 < world.length ) {
            v2 = new Vector2( v.x, v.y + 1 );
            to = TileUtility.GetFieldTile(v2);
            if(!to.isVisited || from.distance + 1 < to.distance) {
                neighbors.add(v2);
            }
        }

        if(v.y - 1 >= 0) {
            v2 = new Vector2( v.x, v.y - 1 );
            to = TileUtility.GetFieldTile(v2);
            if(!to.isVisited || from.distance + 1 < to.distance) {
                neighbors.add(v2);
            }
        }

        if(v.x + 1 < world[0].length) {
            v2 = new Vector2(v.x + 1, v.y);
            to = TileUtility.GetFieldTile(v2);
            if(!to.isVisited || from.distance + 1 < to.distance) {
                neighbors.add(v2);
            }
        }

        if(v.x - 1 >= 0) {
            v2 = new Vector2(v.x - 1, v.y);
            to = TileUtility.GetFieldTile(v2);
            if(!to.isVisited || from.distance + 1 < to.distance) {
                neighbors.add(v2);
            }
        }

        return neighbors;
    }

    private static List<Vector2> findPossibleMoves(List<Vector2> solution, Vector2 start, int maxDist) {
        Stack<Vector2> s = new Stack<>();
        s.push(start);

        TileUtility.GetFieldTile(start).visit(0);

        while( !s.empty()) {
            Vector2 v = s.pop();

            if(v.x == 5 && v.y == 4) {
                System.out.println("here");
            }

            FieldTile from = TileUtility.GetFieldTile(v);

            for( Vector2 w : findNeighbors(from,v)) {
                int nextDist = from.distance + 1;

                if(world[(int)w.x][(int)w.y].getFieldObject(true) == null && nextDist <= maxDist) {
                    solution.add(w);
                    s.push(w);
                    TileUtility.GetFieldTile(w).visit(nextDist);
                }
            }
        }

        return solution;
    }
}
