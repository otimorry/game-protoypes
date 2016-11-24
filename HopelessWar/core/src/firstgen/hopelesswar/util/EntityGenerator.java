package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import firstgen.hopelesswar.model.*;

/**
 * Created by Olva on 5/24/16.
 */
public class EntityGenerator {
    public static Entity CreateGenericActor(float posX, float posY, Enums.UnitType type, Enums.Alliance alliance)
    {
        Entity entity = CreateGeneric(posX, posY, type);

        // Modify existing components
        CState state = CM.STATE.get(entity);
        state.posX = posX;
        state.posY = posY;
        state.baseVelocity = 2.0f;


        CCombat combat = CM.COMBAT.get(entity);
        combat.damage = 5;
        combat.health = 50;
        combat.range = 10;

        CStatus status = CM.STATUS.get(entity);
        status.alliances[alliance.ordinal()] = true;

        // Add additional components
        entity.add(new CControllable());

        return entity;
    }

    public static Entity CreateBullet(Vector2 loc) {
        Entity entity = CreateGeneric(loc.x, loc.y, Enums.UnitType.PROJECTILE);

        CM.STATE.get(entity).baseVelocity = 5.0f;

        entity.add(new CProjectile());
        entity.remove(CVisual.class);
        entity.remove(CCombat.class);
        entity.remove(CSelectable.class);

        return entity;
    }

    private static Entity CreateGeneric(float posX, float posY, Enums.UnitType type) {
        Entity entity = new Entity();

        entity.add(new CCommandHandler());
        entity.add(new CCommandListener());
        entity.add(new CCommandSource());
        entity.add(new CCombat());
        entity.add(new COverlay(entity));
        entity.add(new CStatus());
        entity.add(new CState(posX, posY,type));
        entity.add(new CSelectable());
        entity.add(new CVisual(new TextureRegion(new Texture("Turret.png"))));

        return entity;
    }

}
