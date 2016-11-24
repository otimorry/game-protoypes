package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import firstgen.hopelesswar.util.*;

/**
 * Created by Olva on 7/12/16.
 */
public class CCombat implements Component {
    public int health, damage;
    public boolean targetInRange;
    public int range = 5;
    public Cooldown attackCooldown = CooldownUtil.CreateCooldown(3.0f);

    // TODO: Add weapons with range
    public Circle getRange(CState s) {
        return new Circle(s.center.x, s.center.y, range);
    }
}
