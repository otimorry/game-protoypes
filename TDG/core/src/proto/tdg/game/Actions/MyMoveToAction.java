package proto.tdg.game.Actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import proto.tdg.game.FieldTile;
import proto.tdg.game.TileUtility;

/**
 * Created by Olva on 11/29/16.
 */
public class MyMoveToAction extends Action {
    private boolean primary;

    public MyMoveToAction(Actor target) {
        setTarget(target);
    }

    @Override
    public boolean act(float delta) {
        if(!(target instanceof FieldTile)) return true;
        else {
            FieldTile from = (FieldTile)actor;
            FieldTile to = (FieldTile)target;

            if(TileUtility.CanMoveObject(from, to, primary)) {
                TileUtility.MoveObject(from,to,primary);
            }
            else {
                System.out.println("Cannot move object due to obstruction!");
            }
        }
        return true;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
