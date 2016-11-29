package proto.tdg.game;

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

    public static boolean CanMoveObject(FieldTile from, FieldTile to, boolean isPrimary) {
        assert from != null;
        assert to != null;

        // Destination FieldTile should be empty
        return from.getFieldObject(isPrimary) != null && to.getFieldObject(isPrimary) == null;
    }
}
