package proto.tdg.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;


/**
 * Created by Olva on 11/28/16.
 */
public class TileUtility {

    public static void MoveObject(FieldTile from, FieldTile to, boolean isPrimary) {
        assert from != null;
        assert to != null;

        FieldObject frObj = from.getFieldObject(isPrimary);

        // Set tile destination
        frObj.setFieldTile(to);

        // Replace existing destination object with origin
        to.setFieldObject(frObj, isPrimary);

        // Reset tile origin object
        from.setFieldObject(null, isPrimary);
    }

    public static FieldTile GetFieldTile(Vector2 xy) {
        return WorldState.world[(int)xy.x][(int)xy.y];
    }

    public static boolean CanMoveObject(FieldTile from, FieldTile to, boolean isPrimary) {
        assert from != null;
        assert to != null;

        boolean canMove = false;

        for(Vector2 v : InputUtil.possibleMoves) {
            if(v.x == to.tileX && v.y == to.tileY) {
                canMove = true;
            }
        }

        // Destination FieldTile should be empty
        return from.getFieldObject(isPrimary) != null && to.getFieldObject(isPrimary) == null && canMove;
    }
}
