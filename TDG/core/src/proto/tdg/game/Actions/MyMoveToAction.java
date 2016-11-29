package proto.tdg.game.Actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import proto.tdg.game.FieldTile;
import proto.tdg.game.InputUtil;
import proto.tdg.game.TileUtility;

import static proto.tdg.game.WorldState.table;

/**
 * Created by Olva on 11/29/16.
 */
public class MyMoveToAction extends Action {
    private boolean primary;
    private boolean isDone;

    public MyMoveToAction(Actor target) {
        setTarget(target);
    }

    @Override
    public boolean act(float delta) {
        isDone = false;

        if(!(target instanceof FieldTile)) return isDone = true;
        else {
            FieldTile from = (FieldTile)actor;
            FieldTile to = (FieldTile)target;

            if(TileUtility.CanMoveObject(from, to, primary)) {
                TileUtility.MoveObject(from,to,primary);
                table.setPosition(InputUtil.screenStartPt.x + 1.5f, InputUtil.screenStartPt.y + 1.1f);
            }
            else {
                System.out.println("Cannot move object due to obstruction!");
            }
        }

        table.setVisible(false);
        InputUtil.reset();
        return isDone = true;
    }

    public boolean isDone() { return isDone; }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
