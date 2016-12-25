package proto.tdg.game.Actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.Events.GameEventListener;
import proto.tdg.game.FieldObject;
import proto.tdg.game.UI.GameTimer;
import proto.tdg.game.Utility.EnumsUtility;
import proto.tdg.game.Utility.EventUtility;
import proto.tdg.game.WorldState;

/**
 * Created by Olva on 12/24/16.
 */
public class TimerAction extends Action {
    private boolean done;
    private FieldObject initator;
    private GameTimer.TimerState state;

    public TimerAction(GameTimer.TimerState state, FieldObject initiator) {
        this.initator = initiator;
        this.state = state;
    }

    @Override
    public boolean act(float delta) {
        GameEvent.FireTimerEvent(state, initator);
        return true;
    }
}
