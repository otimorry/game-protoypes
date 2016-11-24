package firstgen.hopelesswar.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import firstgen.hopelesswar.events.MoveEventArgs;
import firstgen.hopelesswar.model.*;
import firstgen.hopelesswar.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 7/9/16.
 */
public class MovementSystem extends EntitySystem {

    private static final boolean DEBUG = false;

    public MovementSystem(int priority) { super(priority);}

    @Override
    public void update(float deltaTime) {
//        ImmutableArray<Entity> ai = getEngine().getEntitiesFor(Family.all(CState.class).exclude(CControllable.class).get());
        List<Entity> movingUnits = GetMovingUnits(Family.all(CState.class).exclude(CProjectile.class).get());
        List<Entity> projectiles = GetMovingUnits(Family.all(CProjectile.class).get());

        CState s;
        CStatus cs;

        // update my units
        for( Entity entity : movingUnits) {
            s = CM.STATE.get(entity);
            cs = CM.STATUS.get(entity);

            if(!StatusUtil.HasStatus(entity, Enums.Status.ATTACKING)) {
                boolean stop = updateMovement(s,deltaTime);
                updateMain(entity, s, cs, stop);
            }

            // Debug position
            if(DEBUG) {
                DebugOrganizer.addDebugStatement(String.format("Player - x: %f, y: %f, rad: %f Target: %s", s.posX, s.posY, s.angle,
                        CM.TARGET.get(entity) != null ? CM.TARGET.get(entity).target : "none" ));
//                DebugOrganizer.addDebugStatement(String.format("End Target - x: %f, y: %f", s.endX, s.endY ));
                DebugOrganizer.addDebugStatement(String.format("Move Listeners: %d", CM.LISTENER.get(entity).moveListeners.size()));
                DebugOrganizer.addDebugStatement(String.format("Move Sources: %d", CM.SOURCE.get(entity).moveSources.size()));
            }
        }

        for( Entity entity : projectiles ) {
            s = CM.STATE.get(entity);
            boolean stop = updateMovement(s,deltaTime);
            updateProjectiles(entity, stop);
        }
    }


    private void updateMain(Entity entity, CState s, CStatus cs, boolean stop){
        EventUtil.NotifyListenersOf(entity, new MoveEventArgs(Enums.EventType.MOVE, new Vector2(s.center)));
        if(stop) {
            StatusUtil.SetStatus(entity, Enums.Status.MOVING, false);
        }
    }

    private void updateProjectiles(Entity entity, boolean stop ) {
        if(stop) {
            EventUtil.DeregisterAFromSource(entity, Enums.EventType.MOVE);
            getEngine().removeEntity(entity);
        }
    }

    private boolean updateMovement(CState s, float deltaTime) {
        float updateX = s.velX * deltaTime;
        float updateY = s.velY * deltaTime;

        // naive approach.
        List<Entity> collisionList = InputUtil.GetOverlappedEntities(s.getBound());

        if(collisionList.size() > 1) {
            updateX = -updateX;
            updateY = -updateY;
        }

        s.posX += updateX;
        s.posY += updateY;
        s.center.add(updateX, updateY);

        boolean stop = false;

        // First Quadrant
        if(s.angle >= 0 && s.angle < MathUtils.PI / 2)
        {
            stop = s.center.x >= s.endX && s.center.y >= s.endY;
        }
        // Second Quadrant
        else if(s.angle >= MathUtils.PI / 2 && s.angle < MathUtils.PI )
        {
            stop = s.center.x <= s.endX && s.center.y >= s.endY;
        }
        // Third Quadrant
        else if(s.angle >= -MathUtils.PI && s.angle < -(MathUtils.PI/2)) {
            stop = s.center.x <= s.endX && s.center.y <= s.endY;
        }
        // Fourth Quadrant
        else if(s.angle >= -(MathUtils.PI/2) && s.angle < 0 ) {
            stop = s.center.x >= s.endX && s.center.y <= s.endY;
        }

        return stop;
    }

    private List<Entity> GetMovingUnits(Family family) {
        List<Entity> moving = new ArrayList<>();

        for(Entity entity : getEngine().getEntitiesFor(family)) {
            if(StatusUtil.HasStatus(entity, Enums.Status.MOVING)) {
                moving.add(entity);
            }
        }

        return moving;
    }
}
