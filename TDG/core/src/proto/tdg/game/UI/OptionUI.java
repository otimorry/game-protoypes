package proto.tdg.game.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import proto.tdg.game.Actions.DisplayOptionUIAction;
import proto.tdg.game.Utility.InputListenerUtility;


import static proto.tdg.game.WorldState.*;

/**
 * Created by Olva on 11/29/16.
 */
public class OptionUI extends Table implements UI {
    private DisplayOptionUIAction displayOptionUIAction;
    private Label l1,l2,l3;

    public OptionUI() {
        super(skin);

        l1 = new Label("MOVE", skin);
        l1.setName("MOVE");
        l1.addListener(InputListenerUtility.GetMoveListener());

        l2 = new Label("ATTACK", skin);
        l2.setName("ATTACK");
        l2.addListener(InputListenerUtility.GetAttackListener());

        l3 = new Label("DEFEND", skin);
        l3.setName("DEFEND");

        add(l1).pad(0.5f);
        row();
        add(l2).pad(0.5f);
        row();
        add(l3).pad(0.5f);

        setTouchable(Touchable.enabled);
        setVisible(false);
    }

    public void disableActors(String... names) {
        for( String name : names) {
            findActor(name).setVisible(false);
        }
    }

    public void refresh() {
        l1.setVisible(true);
        l2.setVisible(true);
        l3.setVisible(true);
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
