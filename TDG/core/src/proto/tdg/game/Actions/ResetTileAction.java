package proto.tdg.game.Actions;

import com.badlogic.gdx.math.Vector2;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.Events.GameEventListener;
import proto.tdg.game.FieldObject;
import proto.tdg.game.Utility.EventUtility;
import proto.tdg.game.WorldState;

/**
 * Created by Olva on 12/5/16.
 */
public class ResetTileAction extends GameEventListener {

    private Vector2 tileXY;
    private boolean done;

    public ResetTileAction(Vector2 tileXY) {
        super();
        this.tileXY = tileXY;
        listenTo(GameEvent.Type.move);
        listenTo(GameEvent.Type.attack);

        EventUtility.AddNotifyListener(this);
    }

    @Override
    public boolean act(float delta) {
        if(done) {
            WorldState.world[(int)tileXY.x][(int)tileXY.y].isVisited = false;
            EventUtility.ToRemove(this);
        }
        return done;
    }

    @Override
    protected boolean attackEvent(boolean done, FieldObject initiator, FieldObject... targets) {
        this.done = true;
        return true;
    }

    @Override
    protected boolean moveEvent(boolean done, Vector2 tileXY) {
        this.done = true;
        return true;
    }
}
