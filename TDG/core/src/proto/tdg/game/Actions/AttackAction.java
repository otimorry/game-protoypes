package proto.tdg.game.Actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.FieldObject;
import proto.tdg.game.FieldTile;
import proto.tdg.game.Utility.EventUtility;
import proto.tdg.game.Utility.InputUtility;
import proto.tdg.game.Utility.TileUtility;

/**
 * Created by Olva on 12/5/16.
 */
public class AttackAction extends Action {
    private FieldObject init, target;

    public AttackAction(FieldObject init, FieldObject target) {
        this.init = init;
        this.target = target;
    }

    @Override
    public boolean act(float delta) {

        //Todo: Multiple targets
        if(target != null) {
            target.hp -= init.str;
            init.hp -= target.str;
            System.out.println("Attack successful");
        }

        GameEvent.FireAttackEvent(true, init, target);

        return true;
    }
}
