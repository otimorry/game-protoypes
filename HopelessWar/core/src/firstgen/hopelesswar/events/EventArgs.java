package firstgen.hopelesswar.events;

import com.badlogic.ashley.core.Entity;
import firstgen.hopelesswar.util.Enums;

/**
 * Created by Olva on 7/18/16.
 */
public abstract class EventArgs {
    public Enums.EventType type;

    public EventArgs(Enums.EventType type ) {
        this.type = type;
    }
}
