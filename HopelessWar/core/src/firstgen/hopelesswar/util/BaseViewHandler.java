package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import firstgen.hopelesswar.events.FollowEvent;
import firstgen.hopelesswar.events.MoveEvent;
import firstgen.hopelesswar.model.COverlay;
import firstgen.hopelesswar.model.CSelect;
import firstgen.hopelesswar.model.CTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 7/24/16.
 */
public abstract class BaseViewHandler {

    protected Engine engine;

    public BaseViewHandler(Engine engine) {
        this.engine = engine;
    }

    public abstract void handle();

    protected void handleSelect(List<Entity> eList, Enums.Target target) {
        CSelect.clear();
        if(eList.isEmpty()) return;

        if(target == Enums.Target.SINGLE) {
            Entity e = eList.get(0);
            InputUtil.toggleSelection(e);
            CM.OVERLAY.get(e).enableOverlay(COverlay.Selection.SELECT);
        }
        else
        {
            for(Entity e : eList ) {
                InputUtil.toggleSelection(e);
                CM.OVERLAY.get(e).enableOverlay(COverlay.Selection.SELECT);
            }
        }
    }

    protected void handleAction() {
        List<Entity> targets = InputUtil.GetEntitiesAtPoint(InputUtil.worldEndPt);
        if(!targets.isEmpty()) {
            Entity target = targets.get(0);
            for (Entity selected : getAllSelected()) {
                new MoveEvent(selected, new Vector2(InputUtil.worldEndPt.x, InputUtil.worldEndPt.y)).activate();
                new FollowEvent(selected, target).activate();
            }
        }
        // Handle move
        else {
            for (Entity selected : getAllSelected()) {
                selected.remove(CTarget.class);
                CM.COMBAT.get(selected).targetInRange = false;
                StatusUtil.SetStatus(selected, Enums.Status.ATTACKING, false);

                EventUtil.DeregisterAFromSource(selected, Enums.EventType.MOVE);
                new MoveEvent(selected, new Vector2(InputUtil.worldEndPt.x, InputUtil.worldEndPt.y)).activate();
            }
        }
    }

    private ImmutableArray<Entity> getAllSelected() { return engine.getEntitiesFor(Family.all(CSelect.class).get());}
}
