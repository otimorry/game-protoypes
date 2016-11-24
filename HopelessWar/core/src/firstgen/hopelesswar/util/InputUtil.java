package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import firstgen.hopelesswar.Game;
import firstgen.hopelesswar.model.CSelect;
import firstgen.hopelesswar.model.CState;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Olva on 7/10/16.
 */
public class InputUtil {
    @SuppressWarnings("FieldCanBeLocal")
    private static boolean DEBUG = false;

    public static boolean isDrag;
    public static Vector2 screenStartPt, screenEndPt, screenDragPt;
    public static Vector3 worldStartPt, worldEndPt, worldDragPt;
    public static boolean isLeftClick;
    public static boolean isRightClick;
    public static boolean needAction;
    public static boolean isLeftDoubleClick;

    public static Rectangle NormalizedDragBound, DisplayDragBound;

    public static Entity lastSelected;

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

        if(DEBUG) {
            DebugOrganizer.addDebugStatement(String.format("StartCoord - x: %f y: %f", worldStartPt.x, worldStartPt.y));
            DebugOrganizer.addDebugStatement(String.format("DragCoord - x: %f y: %f", worldDragPt.x, worldDragPt.y));
            DebugOrganizer.addDebugStatement(String.format("DisplayDragBound - x: %f y: %f width: %f, height: %f",
                    DisplayDragBound.x, DisplayDragBound.y, DisplayDragBound.width, DisplayDragBound.height));
            DebugOrganizer.addDebugStatement(String.format("NormalizedDragBound - x: %f y: %f width: %f, height: %f",
                    NormalizedDragBound.x, NormalizedDragBound.y, NormalizedDragBound.width, NormalizedDragBound.height));
        }
    }


    public static void toggleSelection(Entity e) {
        if(CSelect.get(e) == null) {
            e.add(CSelect.create(e));
            InputUtil.lastSelected = e;
        }
        else {
            resetSelection(e);
        }
    }

    public static void resetSelection(Entity e) {
        e.remove(CSelect.get(e).getClass());
        CSelect.remove(e);
        InputUtil.lastSelected = null;
    }

    public static Entity GetSelectableEntityAtPoint(Vector3 v3) {
        return GetAllEntities(null).
                filter(e -> PointHitTest(e,v3) && CM.SELECTABLE.get(e) != null).
                findFirst().
                orElse(null);
    }

    public static List<Entity> GetEntitiesOfSameUnitType(Enums.UnitType unitType)
    {
        return GetAllEntities(unitType).
                filter(e -> CM.SELECTABLE.get(e) != null).
                collect(Collectors.toList());
    }

    public static List<Entity> GetEntitiesAtPoint(Vector3 v3)
    {
        return GetAllEntities(null).
                filter(e -> PointHitTest(e,v3) && CM.SELECTABLE.get(e) != null ).
                collect(Collectors.toList());
    }

    public static List<Entity> GetOverlappedEntities(Rectangle r)
    {
        return GetAllEntities(null).
                filter(e -> EntityOverlapTest(CM.STATE.get(e),r) && CM.SELECTABLE.get(e) != null).
                collect(Collectors.toList());
    }

    private static Stream<Entity> GetAllEntities(Enums.UnitType unitType) {
        Stream<Entity> entityStream;

        if(unitType == null) {
            entityStream = Game.allEntities.stream();
        }
        else {
            entityStream = Game.allEntities.stream().filter(e -> CM.STATE.get(e).unitType == unitType);
        }

        return entityStream;
    }

//    public static List<Entity> GetEntitiesInBound(Circle c) {
//        return Game.allEntities.stream().
//                filter(e -> EntityInAreaTest(CM.STATE.get(e),c)).
//                collect(Collectors.toList());
//    }

    public static boolean EntityOverlapTest(CState state, Rectangle bound) {
        return bound.overlaps(state.getBound());
    }

    public static boolean EntityInAreaTest(Circle init, Circle targ ) {
        return init.overlaps(targ);
    }

    public static boolean PointHitTest(Entity entity, Vector3 v) {
        return CM.STATE.get(entity).getBound().contains(v.x,v.y);
    }

    public static Vector3 GetWorldPtFromScreenPt(Vector2 screenPt) {
        return Game.CAM.unproject(new Vector3(screenPt.x,screenPt.y,0));
    }

    public static Vector3 GetWorldPtFromScreenPt(Vector3 screenPt) {
        return Game.CAM.unproject(new Vector3(screenPt.x,screenPt.y, screenPt.z));
    }
}
