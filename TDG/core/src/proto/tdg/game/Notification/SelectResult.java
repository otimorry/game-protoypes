package proto.tdg.game.Notification;

import proto.tdg.game.Enums;

/**
 * Created by Olva on 12/5/16.
 */
public class SelectResult implements Result{
    public Enums.Act select;

    public SelectResult(Enums.Act select) {
        this.select = select;
    }
}
