package proto.tdg.game.Actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import proto.tdg.game.*;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.Utility.EventUtility;
import proto.tdg.game.Utility.InputUtility;
import proto.tdg.game.Utility.TileUtility;


/**
 * Created by Olva on 11/29/16.
 */
public class MyMoveToAction extends Action {
    private boolean primary;
    private Vector2 vFrom, vTo;

    public MyMoveToAction(Vector2 from, Vector2 to) {
        this.vFrom = from;
        this.vTo = to;
    }

    @Override
    public boolean act(float delta) {
        boolean done = true;

        FieldTile from = TileUtility.GetFieldTile(vFrom);
        FieldTile to = TileUtility.GetFieldTile(vTo);

        if(TileUtility.CanMoveObject(from, to, primary)) {
            TileUtility.MoveObject(from,to,primary);
            done = false;
        }

        GameEvent.FireMoveEvent(done, new Vector2(to.tileX, to.tileY));

        InputUtility.reset();
        return true;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
