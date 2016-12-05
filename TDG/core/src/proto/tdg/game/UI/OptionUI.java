package proto.tdg.game.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import proto.tdg.game.Actions.DisplayOptionUIAction;
import proto.tdg.game.ActorListener;


import static proto.tdg.game.WorldState.*;

/**
 * Created by Olva on 11/29/16.
 */
public class OptionUI extends Table implements UI {
    private DisplayOptionUIAction displayOptionUIAction;

    public OptionUI() {
        super(skin);

        Label l1 = new Label("MOVE", skin);
        l1.setName("MOVE");
        l1.addListener(ActorListener.GetMoveListener());

        Label l2 = new Label("ATTACK", skin);
        l2.setName("ATTACK");

        Label l3 = new Label("DEFEND", skin);
        l3.setName("DEFEND");

        add(l1).colspan(2).pad(0.5f);
        row();
        add(l2).pad(0.5f);
        add(l3).pad(0.5f);

        setTouchable(Touchable.enabled);
        setVisible(false);
    }

    @Override
    public void setParentContainer(DisplayOptionUIAction displayOptionUIAction) {
        this.displayOptionUIAction = displayOptionUIAction;
    }

    @Override
    public DisplayOptionUIAction getParentContainer() {
        return displayOptionUIAction;
    }

    @Override
    public Actor getUI() {
        return this;
    }
}
