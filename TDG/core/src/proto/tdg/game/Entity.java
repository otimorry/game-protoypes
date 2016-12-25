package proto.tdg.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import proto.tdg.game.Events.GameEventListener;
import proto.tdg.game.Utility.EnumsUtility;

/**
 * Created by Olva on 12/24/16.
 */
public abstract class Entity extends GameEventListener {
    protected int id;

    public Entity() {
        WorldState.AddObject(this);
    }

    public void setId(int id) { this.id = id; }

    abstract protected boolean selectEvent(boolean done, EnumsUtility.Act type);
}
