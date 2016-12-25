package proto.tdg.game.Actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.Events.GameEventListener;
import proto.tdg.game.FieldObject;
import proto.tdg.game.Utility.EventUtility;
import proto.tdg.game.Utility.TileUtility;

/**
 * Created by Olva on 11/29/16.
 */
public class DisplayMoveAction extends GameEventListener {
    private boolean done;
    private Vector2 tileXY;
    private Vector2 displayLoc;
    private Color highlightColor;
    private String id;

    public DisplayMoveAction(Vector2 displayLoc, Color color) {
        super();
        this.id = "Tile[" + displayLoc.x + "][" + displayLoc.y + "]";
        this.displayLoc = displayLoc;
        this.highlightColor = color;

        listenTo(GameEvent.Type.move);
        listenTo(GameEvent.Type.attack);

        EventUtility.AddNotifyListener(this);
    }

    public String getId() { return id; }

    public void setHighlightColor(Color color) {
        this.highlightColor = color;
    }

    @Override
    public boolean act(float delta) {
        (TileUtility.GetFieldTile(displayLoc)).highlight = !done;
        (TileUtility.GetFieldTile(displayLoc)).highlightColor = highlightColor;

        return done;
    }

    @Override
    protected boolean moveEvent(boolean done, Vector2 tileXY) {
        this.tileXY = tileXY;
        this.done = true;
        EventUtility.ToRemove(this);
        return true;
    }

    @Override
    protected boolean attackEvent(boolean done, FieldObject initiator, FieldObject... targets) {
        this.done = true;
        EventUtility.ToRemove(this);
        return true;
    }

    @Override
    protected boolean cancelEvent() {
        super.cancelEvent();
        this.done = true;
        return true;
    }
}
