package proto.tdg.game.Actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import proto.tdg.game.*;
import proto.tdg.game.Notification.MoveResult;
import proto.tdg.game.Notification.Notification;

/**
 * Created by Olva on 11/29/16.
 */
public class DisplayMoveAction extends Notification {
    private boolean success;
    private Vector2 result;
    private Vector2 displayLoc;
    private Color highlightColor;
    private String id;

    public DisplayMoveAction(Vector2 displayLoc, Color color) {
        super();
        this.id = "Tile[" + displayLoc.x + "][" + displayLoc.y + "]";
        this.displayLoc = displayLoc;
        this.highlightColor = color;
        addType(Enums.Notify.MOVE);
    }

    public String getId() { return id; }

    public void setHighlightColor(Color color) {
        this.highlightColor = color;
    }

    @Override
    public boolean act(float delta) {
        (TileUtility.GetFieldTile(displayLoc)).highlight = !success;
        (TileUtility.GetFieldTile(displayLoc)).highlightColor = highlightColor;

        return success;
    }

    @Override
    protected void moveNotification(MoveResult result) {
        this.result = result.tileXY;
        this.success = true; // don't care about success of move action

        NotifyUtility.ToRemove(this);
    }
}
