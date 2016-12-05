package proto.tdg.game.Actions;

import com.badlogic.gdx.math.Vector2;
import proto.tdg.game.Enums;
import proto.tdg.game.Notification.MoveResult;
import proto.tdg.game.Notification.Notification;
import proto.tdg.game.Notification.SelectResult;
import proto.tdg.game.NotifyUtility;
import proto.tdg.game.UI.OptionUI;

/**
 * Created by Olva on 11/29/16.
 */
public class DisplayOptionUIAction extends Notification {
    private String id;
    private boolean success;
    private OptionUI ui;
    private Vector2 tileXY;
    private Enums.Act select;

    public DisplayOptionUIAction(OptionUI ui) {
        this(ui,0,0);
    }

    public DisplayOptionUIAction(OptionUI ui, float tileX, float tileY) {
        super();

        this.ui = ui;
        this.id = "Tile[" + tileX + "][" + tileY + "]";
        ui.setParentContainer(this);
        setPosition(tileX,tileY);
        tileXY = new Vector2(tileX,tileY);

        addType(Enums.Notify.MOVE);
        addType(Enums.Notify.SELECT);
    }

    public void setPosition(float x, float y) {
        ui.getUI().setPosition(x,y);
    }

    @Override
    public boolean act(float delta) {
        ui.getUI().setVisible(!success);

        return success;
    }

    @Override
    protected void moveNotification(MoveResult result) {
        this.success = true; // don't care about success
        this.tileXY = result.tileXY;

        NotifyUtility.ToRemove(this);
    }

    @Override
    protected void selectNotification(SelectResult result) {
        this.select = result.select;
    }

    @Override
    protected void cancelNotification() {
        super.cancelNotification();
        this.success = true;
    }

    public Enums.Act getSelected() { return select; }

    public Vector2 getTileXY() {
        return tileXY;
    }
}
