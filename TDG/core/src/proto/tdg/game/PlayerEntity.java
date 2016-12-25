package proto.tdg.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import proto.tdg.game.Events.GameEventListener;
import proto.tdg.game.Utility.EnumsUtility;

/**
 * Created by Olva on 12/24/16.
 */
public class PlayerEntity extends Entity {
    protected TextureRegion img;
    public int hp, mp, str, def, agi, mv;
    public float time;

    public PlayerEntity() {
        super();

        this.hp = 10;
        this.mp = 5;
        this.str = 7;
        this.def = 4;
        this.agi = 9;
        this.mv = 5;
        time = -1;
    }

    @Override
    protected boolean selectEvent(boolean done, EnumsUtility.Act type) {
        return false;
    }

    @Override
    public boolean act(float delta) {
        return false;
    }
}
