package firstgen.hopelesswar.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Circle;
import firstgen.hopelesswar.events.FollowEvent;
import firstgen.hopelesswar.events.MoveEvent;
import firstgen.hopelesswar.model.*;
import firstgen.hopelesswar.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 7/12/16.
 */
public class CombatSystem extends EntitySystem {

    private static boolean DEBUG = true;

    public CombatSystem(int priority) { super(priority);}

    @Override
    public void update(float deltaTime) {
        List<Entity> entities = GetAttackingUnits(Family.all(CTarget.class, CCombat.class, CState.class).get());

        //System.out.println("deltaTime: " + deltaTime);

        CState otherState;
        CCombat initCombat, otherCombat;
        CTarget initTarget;

        for(Entity e : entities) {
            initTarget = CM.TARGET.get(e);
            initCombat = CM.COMBAT.get(e);

            otherState = CM.STATE.get(initTarget.target);
            otherCombat = CM.COMBAT.get(initTarget.target);

            // target is alive
            if(otherState.alive && !initCombat.attackCooldown.isOnCooldown()) {
                initCombat.targetInRange = true;
                initCombat.attackCooldown.startCooldown();

                StatusUtil.SetStatus(e, Enums.Status.ATTACKING, true);

                Entity bullet = EntityGenerator.CreateBullet(CM.STATE.get(e).center);

                getEngine().addEntity(bullet);
                new MoveEvent(bullet, otherState.center).activate();
                new FollowEvent(bullet,initTarget.target).activate();
            }

            if(DEBUG) {
                //DebugOrganizer.addDebugStatement(String.format("Attacker - HP: %d, DMG: %d", initCombat.health, initCombat.damage));
                //DebugOrganizer.addDebugStatement(String.format("Target - HP: %d, DMG: %d", otherCombat.health, otherCombat.damage));
                DebugOrganizer.addDebugStatement(String.format("Cooldown time: %f", initCombat.attackCooldown.getCooldownTime()));
            }
        }

        CooldownUtil.update(deltaTime);
    }

    private List<Entity> GetAttackingUnits(Family family) {
        List<Entity> attacking = new ArrayList<>();

        Entity target;

        for(Entity entity : getEngine().getEntitiesFor(family)) {
            target = CM.TARGET.get(entity).target;
            CState targState = CM.STATE.get(target);

            Circle range1 = CM.COMBAT.get(entity).getRange(CM.STATE.get(entity));
            Circle range2 = new Circle(targState.center.x, targState.center.y, 1);

            if(IsTargetInRange(range1, range2) && !StatusUtil.IsAlly(entity,target)) {
                attacking.add(entity);
            }
            else
            {
                CM.COMBAT.get(entity).targetInRange = false;
                StatusUtil.SetStatus(entity, Enums.Status.ATTACKING, false);
            }
        }

        return attacking;
    }

    private static boolean IsTargetInRange(Circle init, Circle targ) {
        return InputUtil.EntityInAreaTest(init,targ);
    }
}
