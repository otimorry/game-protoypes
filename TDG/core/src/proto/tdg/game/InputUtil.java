package proto.tdg.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return Launcher.CAM.unproject(new Vector3(screenPt.x,screenPt.y,0));
    }

    public static Vector3 GetWorldPtFromScreenPt(Vector3 screenPt) {
        return Launcher.CAM.unproject(new Vector3(screenPt.x,screenPt.y, screenPt.z));
    }
}
