package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Entity;
import firstgen.hopelesswar.events.EventArgs;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Olva on 7/18/16.
 */
public class EventUtil {

    public static void RegisterAtoB(Entity A, Entity B, Enums.EventType type) {
        GetListeners(B,type).add(A);
        GetSources(A,type).add(B);
    }

    public static void DeregisterAfromB(Entity A, Entity B, Enums.EventType type) {
        Set<Entity> listeners = GetListeners(B,type);
        Set<Entity> sources = GetSources(A,type);

        if(listeners.contains(A))
            listeners.remove(A);

        if(sources.contains(B))
            sources.remove(B);
    }

    public static void DeregisterAFromSource(Entity A, Enums.EventType type) {
        Set<Entity> entitySetCopy = new HashSet<>(GetSources(A,type));

        for( Entity B : entitySetCopy ) {
            DeregisterAfromB(A,B,type);
        }
    }

    public static void NotifyListenersOf(Entity source, EventArgs args ) {
        Set<Entity> entityList = GetListeners(source, args.type);
        for( Entity listener : entityList ) {
            CM.HANDLER.get(listener).handle(listener, args);
        }
    }

    private static Set<Entity> GetListeners(Entity entity, Enums.EventType type) {
        Set<Entity> e;

        switch(type) {
            case MOVE:
                e = CM.LISTENER.get(entity).moveListeners;
                break;
            case ATTACK:
                e = CM.LISTENER.get(entity).attackListeners;
                break;
            default:
                throw new NotImplementedException();
        }

        return e;
    }

    private static Set<Entity> GetSources(Entity entity, Enums.EventType type) {
        Set<Entity> e;

        switch(type) {
            case MOVE:
                e = CM.SOURCE.get(entity).moveSources;
                break;
            case ATTACK:
                e = CM.SOURCE.get(entity).attackSources;
                break;
            default:
                throw new NotImplementedException();
        }

        return e;
    }
}
