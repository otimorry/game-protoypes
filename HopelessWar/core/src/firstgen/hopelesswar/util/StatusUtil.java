package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Entity;
import firstgen.hopelesswar.model.CStatus;

/**
 * Created by Olva on 7/19/16.
 */
public class StatusUtil {

    public static boolean HasStatus(Entity entity, Enums.Status status) {
        return CM.STATUS.get(entity).statuses[status.ordinal()];
    }

    public static void SetStatus(Entity entity, Enums.Status status, boolean value) {
        CM.STATUS.get(entity).statuses[status.ordinal()] = value;
    }

    public static boolean IsInAlliance(Entity entity, Enums.Alliance alliance) {
        return CM.STATUS.get(entity).alliances[alliance.ordinal()];
    }

    public static void SetAlliance(Entity entity, Enums.Alliance alliance, boolean value) {
        CM.STATUS.get(entity).alliances[alliance.ordinal()] = value;
    }

    public static boolean IsAlly(Entity e1, Entity e2) {
        CStatus cs1 = CM.STATUS.get(e1);
        CStatus cs2 = CM.STATUS.get(e2);

        boolean ally = false;

        for( int i = 0; i < Enums.Alliance.values().length; i++ ) {
            ally |= cs1.alliances[i] & cs2.alliances[i];
        }

        return ally;
    }
}
