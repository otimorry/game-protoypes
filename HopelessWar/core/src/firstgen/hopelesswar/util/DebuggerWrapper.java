package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Entity;
import firstgen.hopelesswar.model.CState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 7/21/16.
 */
public class DebuggerWrapper implements Serializable {

    public List<CState> states;

    public DebuggerWrapper() {
        states = new ArrayList<>();
        for(Entity entity : DebugOrganizer.getEntityList()) {
            states.add(CM.STATE.get(entity));
        }
    }


}
