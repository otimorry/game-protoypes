package firstgen.hopelesswar.events;

import com.badlogic.ashley.core.Entity;
import firstgen.hopelesswar.model.CTarget;
import firstgen.hopelesswar.util.EventUtil;
import firstgen.hopelesswar.util.Enums;

/**
 * Created by Olva on 7/18/16.
 */
public class FollowEvent implements EventBase {

    private Entity target;
    private Entity selected;

    public FollowEvent(Entity selected, Entity target) {
        this.target = target;
        this.selected = selected;
    }

    @Override
    public void activate() {
        selected.add(new CTarget(target));

        EventUtil.RegisterAtoB(selected, target, Enums.EventType.MOVE);
    }
}
