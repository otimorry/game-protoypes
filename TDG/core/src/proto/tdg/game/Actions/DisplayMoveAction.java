package proto.tdg.game.Actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import proto.tdg.game.FieldTile;

/**
 * Created by Olva on 11/29/16.
 */
public class DisplayMoveAction extends Action {

    private MyMoveToAction move;

    public void setCallback(MyMoveToAction move) {
        this.move = move;
    }

    @Override
    public boolean act(float delta) {
        ((FieldTile)actor).highlight = true;
        boolean isDone = move != null && move.isDone();

        if(isDone) {
            ((FieldTile)actor).highlight = false;
        }

        return isDone;
    }
}
