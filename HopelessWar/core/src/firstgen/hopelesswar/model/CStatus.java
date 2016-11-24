package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import firstgen.hopelesswar.util.Enums;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Olva on 7/18/16.
 */
public class CStatus implements Component {
    public boolean[] statuses = new boolean[Enums.Status.values().length];
    public boolean[] alliances = new boolean[Enums.Alliance.values().length];
}
