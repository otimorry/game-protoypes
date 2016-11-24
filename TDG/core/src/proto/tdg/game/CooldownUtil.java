package proto.tdg.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olva on 7/25/16.
 */
public class CooldownUtil {
    private static Map<Integer, Cooldown> cooldowns = new HashMap<>();
    private static int counter = 0;

    // Special for handling double clicks
    public static Cooldown LeftClickDoubleTapped = new Cooldown(-1,0.175f);

    public static Cooldown CreateCooldown(float duration) {
        Cooldown cd = new Cooldown(counter, duration);
        cooldowns.put(counter, cd);

        counter++;
        return cd;
    }

    public static void update(float deltaTime) {
        for(Cooldown cooldown : cooldowns.values()) {
            cooldown.update(deltaTime);
        }
    }
}

