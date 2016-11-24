package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import firstgen.hopelesswar.events.EventArgs;
import firstgen.hopelesswar.events.MoveEvent;
import firstgen.hopelesswar.events.MoveEventArgs;
import firstgen.hopelesswar.util.Enums;
import firstgen.hopelesswar.util.StatusUtil;

import java.io.Serializable;

/**
 * Created by Olva on 7/18/16.
 */
public class CCommandHandler implements Component {
    public void handle(Entity owner, EventArgs args) {
        switch(args.type) {
            case MOVE:
                handleMoveEvent(owner, (MoveEventArgs)args);
                break;
            case ATTACK:
                handleAttackEvent(owner,args);
                break;
        }
    }

    private void handleMoveEvent(Entity owner, MoveEventArgs args) {
        new MoveEvent(owner, args.center).activate();
        StatusUtil.SetStatus(owner, Enums.Status.MOVING, true);
    }

    private void handleAttackEvent(Entity owner, EventArgs args) {

    }
}
