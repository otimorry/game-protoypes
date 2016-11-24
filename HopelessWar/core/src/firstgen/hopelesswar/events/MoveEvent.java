package firstgen.hopelesswar.events;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import firstgen.hopelesswar.model.CState;
import firstgen.hopelesswar.util.CM;
import firstgen.hopelesswar.util.Enums;
import firstgen.hopelesswar.util.StatusUtil;

/**
 * Created by Olva on 7/18/16.
 */
public class MoveEvent implements EventBase {

    private Entity selected;
    private Vector2 loc;

    public MoveEvent(Entity selected, Vector2 loc) {
        this.selected = selected;
        this.loc = loc;
    }

    @Override
    public void activate() {
        CState s = CM.STATE.get(selected);

        s.startX = s.center.x;
        s.startY = s.center.y;
        s.endX = loc.x;
        s.endY = loc.y;

        float diffX = (s.endX - s.center.x);
        float diffY = (s.endY - s.center.y);

        s.angle = MathUtils.atan2(diffY,diffX);

        s.velX = s.baseVelocity * MathUtils.cos(s.angle);
        s.velY = s.baseVelocity * MathUtils.sin(s.angle);

        StatusUtil.SetStatus(selected, Enums.Status.MOVING, true);
    }

}
