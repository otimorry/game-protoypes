package proto.tdg.game.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import proto.tdg.game.EntityState;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.Events.GameEventListener;
import proto.tdg.game.FieldObject;
import proto.tdg.game.Utility.EventUtility;

import static proto.tdg.game.WorldState.*;


/**
 * Created by Olva on 12/24/16.
 */
public class GameTimer extends GameEventListener {

    private Label label;
    private String textLabel;
    private int turn = 1;

    private ProgressBar bar;
    private TimerState timerState = TimerState.STOPPED;

    private Table bgLayer;
    private VerticalGroup fgLayer;

    private Stack stack;

    TextureRegion testOverlay = new TextureRegion(new Texture("badlogic.jpg"));
    private Actor actor;

    //private float timer = 1f;
    private float min;
    private float max;

    private boolean done;

    public GameTimer(float min, float max, Skin skin) {
        super();
        bar = new ProgressBar(min,max,0.03f,true, skin);

        this.min = min;
        this.max = max;

        textLabel = "Turn 1";
        label = new Label(textLabel,skin);

        stack = new Stack();
        bgLayer = new Table();
        fgLayer = new VerticalGroup();

        actor = new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                // batch.draw(img, x, y, originX, originY, width, height, scaleXY, scaleXY, 0);
                batch.draw(testOverlay, 32, 2 * 40, 0, 0, TILESIZE * SCALE, TILESIZE * SCALE, 0.8f, 0.8f, 0);
                batch.draw(testOverlay, 32, 4 * 40, 0, 0, TILESIZE * SCALE, TILESIZE * SCALE, 0.8f, 0.8f, 0);
            }
        };

        bar.setAnimateInterpolation(Interpolation.linear);
        bar.setVisualInterpolation(Interpolation.linear);
        bar.setAnimateDuration(0.03f);

        listenTo(GameEvent.Type.timer);
        EventUtility.AddNotifyListener(this);
    }

    public boolean act(float delta) {
        if( timerState == TimerState.STOPPED || timerState == TimerState.PAUSED) return false;

        //timer -= delta;
        System.out.println("Timer time: " + bar.getValue() + " Visual time: " + bar.getVisualValue());


        bar.setValue(bar.getValue() + delta);

//        if(timer <= 0 && bar.getValue() < max) {
//            timer = 1f;
//            bar.setValue(bar.getValue() + 1f);
//        }



        if(bar.getVisualValue() >= max) {
//            bar.setAnimateDuration(0);
            bar.setValue(min);
//            bar.setAnimateDuration(1);
            turn++;
            updateTextLabel();

            System.out.println("Turn " + turn);
        }

        return done;
    }

    public void addTo(Table rootTable) {
        bgLayer.add(label).pad(4f).row();
        bgLayer.add(bar).expandY().fill();

        fgLayer.addActor(actor);

        stack.add(bgLayer);
        stack.add(fgLayer);

        rootTable.add(stack).expandY().fill().pad(4f);
    }

    public float getTime() { return bar.getValue(); }


    private void updateTextLabel() {
        textLabel = "Turn " + turn;
        label.setText(textLabel);
    }

    public void setTimerState(TimerState state) {
        this.timerState = state;
    }

    @Override
    protected boolean timerEvent(TimerState state, FieldObject initiator) {
        timerState = state;

        if(initiator != null) {
            GameEvent.FireSelectEvent(null, initiator);
        }

        return true;
    }

    public enum TimerState { STOPPED, RUNNING, PAUSED }
}
