package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import firstgen.hopelesswar.util.CM;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Olva on 7/10/16.
 */
public class CSelect implements Component {
    private static Map<Entity,CSelect> selected = new HashMap<>();

    public static CSelect get(Entity e)
    {
        CSelect c = null;
        if(selected.containsKey(e)) {
            c = selected.get(e);
        }
        return c;
    }

    public static CSelect create(Entity e)
    {
        CSelect c = new CSelect();
        selected.put(e,c);
        return c;
    }

    public static void remove(Entity e)
    {
        selected.remove(e);
    }

    public static boolean isSelected(Entity e) {
        return selected.containsKey(e);
    }

    public static void clear() {
        for( Entity e : selected.keySet() ) {
            CM.OVERLAY.get(e).disableOverlay(COverlay.Selection.SELECT);
            e.remove(selected.get(e).getClass());
        }
        selected.clear();
    }
}
