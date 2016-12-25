package proto.tdg.game.Actions;

import com.badlogic.gdx.math.Vector2;
import proto.tdg.game.FieldObject;
import proto.tdg.game.Utility.EnumsUtility;
import proto.tdg.game.Utility.EventUtility;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.Events.GameEventListener;
import proto.tdg.game.UI.OptionUI;

/**
 * Created by Olva on 11/29/16.
 */
public class DisplayOptionUIAction extends GameEventListener {
    private String id;
    private boolean done;
    private OptionUI ui;
    private Vector2 tileXY;
    private EnumsUtility.Act select;
    private boolean hide;

    public DisplayOptionUIAction(OptionUI ui) {
        this(ui,0,0);
    }

    public DisplayOptionUIAction(OptionUI ui, float tileX, float tileY) {
        super();

        this.ui = ui;
        this.id = "Tile[" + tileX + "][" + tileY + "]";
        ui.setParentContainer(this);
        ui.refresh();
        setPosition(tileX,tileY);
        tileXY = new Vector2(tileX,tileY);

        listenTo(GameEvent.Type.move);
        listenTo(GameEvent.Type.select);
        listenTo(GameEvent.Type.attack);

        EventUtility.AddNotifyListener(this);
    }

    public void setPosition(float x, float y) {
        ui.getUI().setPosition(x,y);
    }

    @Override
    public boolean act(float delta) {
        ui.getUI().setVisible(!hide);

        return done;
    }

    @Override
    protected boolean moveEvent(boolean done, Vector2 tileXY) {
        this.done = done; // don't care about done
        this.tileXY = tileXY;

        // not done yet
        if(!this.done) {
            ui.disableActors("MOVE");
            hide(false);
        }
        else {
            EventUtility.ToRemove(this);
        }

        return true;
    }

    @Override
    protected boolean selectEvent(boolean success, EnumsUtility.Act select) {
        setSelected(select);
        return true;
    }

    @Override
    protected boolean attackEvent(boolean done, FieldObject initiator, FieldObject... targets) {
        this.done = hide = true;
        EventUtility.ToRemove(this);
        return true;
    }

    @Override
    protected boolean cancelEvent() {
        super.cancelEvent();
        this.done = hide = true;
        return true;
    }

    public EnumsUtility.Act getSelected() { return select; }

    public void setSelected(EnumsUtility.Act select) {
        this.select = select;
    }

    public Vector2 getTileXY() {
        return tileXY;
    }

    public void hide(boolean hide) {
        this.hide = hide;
    }
}
