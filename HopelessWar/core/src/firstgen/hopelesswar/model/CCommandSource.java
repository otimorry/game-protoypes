package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Olva on 7/19/16.
 */
public class CCommandSource implements Component {
    public Set<Entity> moveSources = new HashSet<>();
    public Set<Entity> attackSources = new HashSet<>();
}
