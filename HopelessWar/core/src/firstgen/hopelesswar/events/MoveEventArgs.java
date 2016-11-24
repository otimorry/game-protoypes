package firstgen.hopelesswar.events;

import com.badlogic.gdx.math.Vector2;
import firstgen.hopelesswar.util.Enums;


/**
 * Created by Olva on 7/18/16.
 */
public class MoveEventArgs extends EventArgs {
    public Vector2 center;

    public MoveEventArgs(Enums.EventType type, Vector2 center) {
        super(type);
        this.center = center;
    }
}
