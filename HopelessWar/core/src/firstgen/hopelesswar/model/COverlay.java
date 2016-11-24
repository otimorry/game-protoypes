package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import firstgen.hopelesswar.util.CM;
import firstgen.hopelesswar.util.Enums;
import firstgen.hopelesswar.util.StatusUtil;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Olva on 7/27/16.
 */
public class COverlay implements Component {

    private Entity parent;
    private ShapeRenderer renderer;
    private Map<Selection, COverlayInfo> map = new EnumMap<>(Selection.class);

    public enum Selection {
        RANGE,
        SELECT
    }

    public COverlay(Entity parent) {
        this.parent = parent;
    }

    public class COverlayInfo {
        public Runnable runnable;
        public boolean active;

        public COverlayInfo(Runnable runnable, boolean active) {
            this.runnable = runnable;
            this.active = active;
        }
    }

    public void setShapeRenderer(ShapeRenderer renderer) {
        if( (this.renderer != null && !this.renderer.equals(renderer)) || this.renderer == null) {
            this.renderer = renderer;

            map.put(Selection.RANGE, new COverlayInfo(addAttackRange(),false));
            map.put(Selection.SELECT, new COverlayInfo(addSelect(),false));
        }
    }

    public void enableOverlay(Selection choice) {
        map.get(choice).active = true;
    }

    public void enableAllOverlays() {
        for(COverlayInfo info : map.values()) {
            info.active = true;
        }
    }

    public void disableOverlay(Selection choice) {
        map.get(choice).active = false;
    }

    public void disableAllOverlays() {
        for(COverlayInfo info : map.values()) {
            info.active = false;
        }
    }


    public void drawOverlays() {
        if(renderer == null) throw new IllegalStateException("renderer can't be null");

        renderer.begin(ShapeRenderer.ShapeType.Line);
        for(COverlayInfo info : map.values()) {
            if(info.active) {
                info.runnable.run();
            }
        }
        renderer.end();
    }


    private Runnable addAttackRange() {
        if(renderer == null) throw new IllegalStateException("renderer can't be null");

        return () -> {
            CCombat cCombat = CM.COMBAT.get(parent);
            CState cState = CM.STATE.get(parent);

            Circle c = cCombat.getRange(cState);

            if(cCombat.targetInRange) {
                renderer.setColor(Color.GREEN);
            }
            renderer.circle(c.x, c.y, c.radius);
        };
    }

    private Runnable addSelect() {
        if(renderer == null) throw new IllegalStateException("renderer can't be null");

        return () -> {
            CState s = CM.STATE.get(parent);

            renderer.setColor(Color.ORANGE);
            renderer.rect(s.posX,s.posY,s.width,s.height);

            if(StatusUtil.HasStatus(parent, Enums.Status.MOVING)) {
                renderer.line(s.startX, s.startY, s.endX, s.endY);
            }
        };
    }
}
