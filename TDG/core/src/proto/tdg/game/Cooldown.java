package proto.tdg.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olva on 5/2/16.
 */
public class Cooldown implements Serializable
{
    private float cooldown; // This cooldown's time (in seconds)
    private float startCooldown = -1;
    public int id;

    public static List<Cooldown> allCooldowns = new ArrayList<>();

    public Cooldown(int id, float cooldown) {
        this.id = id;
        this.cooldown = cooldown;
    }

    public void setCooldownPeriod( float cooldownPeriod ) {
        this.cooldown = cooldownPeriod;
    }

    // Start cooldown
    public void startCooldown() {
        startCooldown = cooldown;
        //allCooldowns.add(this);
    }

    public boolean isOnCooldown() {
        return startCooldown > 0;
    }

    public float getCooldownTime() { return startCooldown; }

    // Update state
    public void update(float deltaTime) {
        if(isOnCooldown()) {
            startCooldown -= deltaTime;
            if(!isOnCooldown()) {
                startCooldown = -1;
            }
        }
    }
}
