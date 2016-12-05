package proto.tdg.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import proto.tdg.game.Actions.DisplayMoveAction;
import proto.tdg.game.Actions.MyMoveToAction;

import java.util.List;

import static proto.tdg.game.WorldState.*;

/**
 * Created by Olva on 7/10/16.
 */
public class InputUtil {
    public static boolean isDrag;
    public static Vector2 screenStartPt, screenEndPt, screenDragPt;
    public static Vector3 worldStartPt, worldEndPt, worldDragPt;
    public static boolean isLeftClick;
    public static boolean isRightClick;
    public static boolean needAction;
    public static boolean isLeftDoubleClick;
    public static Enums.Act action = Enums.Act.NONE;
    public static Actor selectedActor = null;

    public static List<Vector2> possibleMoves;

    public static Rectangle NormalizedDragBound, DisplayDragBound;

    public static void reset() {
        isDrag = false;
        screenStartPt = null;
        screenEndPt = null;
        screenDragPt = null;
        isLeftClick = false;
        isRightClick = false;
        needAction = false;
        DisplayDragBound = null;
        NormalizedDragBound = null;
        isLeftDoubleClick = false;
        action = Enums.Act.NONE;
        selectedActor = null;
        possibleMoves = null;
    }

    public static boolean IsSelect() { return screenStartPt.x == screenEndPt.x && screenStartPt.y == screenEndPt.y; }

    public static void calcDragBound() {

        float width = (worldDragPt.x - worldStartPt.x);
        float height = (worldDragPt.y - worldStartPt.y);

        // Display Bound on Screen
        DisplayDragBound = new Rectangle(worldStartPt.x, worldStartPt.y, width, height);

        // Actual Bound where Bottom Left corner is the start point
        NormalizedDragBound = new Rectangle(Math.min(worldStartPt.x, worldDragPt.x),
                Math.min(worldStartPt.y, worldDragPt.y),
                Math.abs(width),
                Math.abs(height));
    }



    public static Vector3 GetWorldPtFromScreenPt(Vector2 screenPt) {
        return WorldState.CAM.unproject(new Vector3(screenPt.x,screenPt.y,0));
    }

    public static Vector3 GetWorldPtFromScreenPt(Vector3 screenPt) {
        return WorldState.CAM.unproject(new Vector3(screenPt.x,screenPt.y, screenPt.z));
    }

//    public static void handleAction() {
//        if(InputUtil.action == Enums.Act.MOVE ) {
//            if( !InputUtil.needAction ) {
//                System.out.println("Alright lets go to posX: " + InputUtil.screenStartPt.x + " posY: " + InputUtil.screenStartPt.y);
//
//                // MoveToAction given target
//                MyMoveToAction moveToAction = new MyMoveToAction(world[(int)InputUtil.screenStartPt.x][(int)InputUtil.screenStartPt.y]);
//                moveToAction.setPrimary(true);
//                selectedActor.addAction(moveToAction);
//            }
//        }
//    }

    public static void SetSelected(Actor actor) {
        if(actor.equals(selectedActor)) {
            selectedActor = null;
        }
        else {
            selectedActor = actor;
        }

        System.out.println( selectedActor != null ? "selected" : "deselected");
    }
}
