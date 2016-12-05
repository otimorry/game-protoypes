package proto.tdg.game.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import proto.tdg.game.Actions.DisplayOptionUIAction;

/**
 * Created by Olva on 11/29/16.
 */
public interface UI {
    Actor getUI();
    void setParentContainer(DisplayOptionUIAction displayOptionUIAction);
    DisplayOptionUIAction getParentContainer();
}
