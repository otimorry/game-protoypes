package proto.tdg.game.Events;

import com.badlogic.gdx.math.Vector2;
import proto.tdg.game.EntityState;
import proto.tdg.game.FieldObject;
import proto.tdg.game.UI.GameTimer;
import proto.tdg.game.Utility.EnumsUtility;
import proto.tdg.game.Utility.EventUtility;

/**
 * Created by Olva on 12/5/16.
 */
public class GameEvent extends Event {
    public boolean done;
    public Vector2 tileXY;
    public EnumsUtility.Act act;
    public Type type;
    public GameTimer.TimerState timerState;
    public FieldObject initiator;
    public FieldObject[] targets;

    private GameEvent (boolean done, Type type, Vector2 tileXY, EnumsUtility.Act act,
                       GameTimer.TimerState state, FieldObject initiator, FieldObject[] targets) {
        this.done = done;
        this.type = type;
        this.tileXY = tileXY;
        this.act = act;
        this.timerState = state;
        this.initiator = initiator;
        this.targets = targets;
    }

    public static void FireCancelEvent() {
        EventUtility.FireEvent(new GameEvent(true,Type.cancel,null,null,null,null,null));
    }

    public static void FireSelectEvent(EnumsUtility.Act act, FieldObject fieldObject) {
        EventUtility.FireEvent(new GameEvent(true,Type.select,null,act,null,fieldObject,null));
    }

    public static void FireAttackEvent(boolean done, FieldObject init, FieldObject... targets) {
        EventUtility.FireEvent(new GameEvent(done,Type.attack,null,null,null,init,targets));
    }

    public static void FireMoveEvent(boolean done, Vector2 tileXY) {
        EventUtility.FireEvent(new GameEvent(done,Type.move, tileXY, null,null,null,null));
    }

    public static void FireTimerEvent(GameTimer.TimerState state, FieldObject initiator) {
        EventUtility.FireEvent(new GameEvent(true,Type.timer,null,null,state,initiator,null));
    }

    /** Types of low-level input events supported by scene2d. */
    public enum Type {
        move,
        select,
        attack,
        cancel,
        timer
    }
}
