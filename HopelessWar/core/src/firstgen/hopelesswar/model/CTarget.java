package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 * Created by Olva on 7/12/16.
 */
public class CTarget implements Component {
    public Entity target;

    public CTarget(Entity target) {
        this.target = target;
    }
}
