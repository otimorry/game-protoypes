package proto.tdg.game.Utility;

import proto.tdg.game.Cooldown;

import java.util.*;

/**
 * Created by Olva on 7/25/16.
 */
public class CooldownUtility {
    private static Map<Integer, Cooldown> cooldowns = new HashMap<>();
    private static int counter = 0;

    public static Cooldown CreateCooldown(float duration) {
        Cooldown cd = new Cooldown(counter, duration);
        cooldowns.put(counter, cd);

        counter++;
        return cd;
    }

    public static void update(float deltaTime) {
        Iterator it = cooldowns.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<Integer,Cooldown> item = (Map.Entry<Integer,Cooldown>)it.next();
            item.getValue().update(deltaTime);
            if(!item.getValue().isOnCooldown()) {
                it.remove();
            }
        }
    }
}

