package proto.tdg.game.Notification;

import com.badlogic.gdx.scenes.scene2d.Action;
import proto.tdg.game.Enums;
import proto.tdg.game.NotifyUtility;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 12/5/16.
 */
public abstract class Notification extends Action {
    public List<Enums.Notify> types = new ArrayList<>();

    public Notification() {
        types.add(Enums.Notify.CANCEL);
    }

    public void handle(Result result) {
        if(result instanceof MoveResult)
            moveNotification((MoveResult)result);
        else if(result instanceof SelectResult)
            selectNotification((SelectResult)result);
        else
            cancelNotification();
    }

    public void addType(Enums.Notify type) { this.types.add(type); }

    protected void moveNotification(MoveResult result) { }

    protected void selectNotification(SelectResult result) { }

    protected void cancelNotification() { NotifyUtility.ToRemove(this); }
}
