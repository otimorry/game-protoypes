package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Olva on 7/18/16.
 */
public class CCommandListener implements Component {
    public Set<Entity> moveListeners = new HashSet<>();
    public Set<Entity> attackListeners = new HashSet<>();
}
