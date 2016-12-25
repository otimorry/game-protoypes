package proto.tdg.game.Events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import proto.tdg.game.FieldObject;
import proto.tdg.game.UI.GameTimer;
import proto.tdg.game.Utility.EnumsUtility;
import proto.tdg.game.Utility.EventUtility;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 12/5/16.
 */
public abstract class GameEventListener extends Action implements EventListener {
    public List<GameEvent.Type> types = new ArrayList<>();

    public GameEventListener() {
        types.add(GameEvent.Type.cancel);
    }

    public boolean handle(Event e) {
        if (!(e instanceof GameEvent)) return false;
        GameEvent event = (GameEvent)e;

        switch(event.type) {
            case move:
                return moveEvent(event.done, event.tileXY);
            case select:
                return selectEvent(event.done, event.act);
            case attack:
                return attackEvent(event.done, event.initiator, event.targets);
            case timer:
                return timerEvent(event.timerState, event.initiator);
            case cancel:
                return cancelEvent();
            default: throw new NotImplementedException();
        }
    }

    public void listenTo(GameEvent.Type type) { this.types.add(type); }

    protected boolean moveEvent(boolean done, Vector2 tileXY) { return false; }

    protected boolean selectEvent(boolean done, EnumsUtility.Act type) { return false; }

    protected boolean attackEvent(boolean done, FieldObject initiator, FieldObject... targets) { return false; }

    protected boolean timerEvent(GameTimer.TimerState state, FieldObject initiator) { return false; }

    protected boolean cancelEvent() { EventUtility.ToRemove(this); return true; }
}
