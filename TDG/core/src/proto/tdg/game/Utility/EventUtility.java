package proto.tdg.game.Utility;

import proto.tdg.game.Events.Event;
import proto.tdg.game.Events.GameEvent;
import proto.tdg.game.Events.GameEventListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olva on 12/5/16.
 */
public class EventUtility {

    private static Map<GameEvent.Type, List<GameEventListener>> notifyMap;
    private static List<GameEventListener> toRemove;

    public EventUtility() {
        notifyMap = new HashMap<>();
        notifyMap.put(GameEvent.Type.move, new ArrayList<>());
        notifyMap.put(GameEvent.Type.select, new ArrayList<>());
        notifyMap.put(GameEvent.Type.cancel, new ArrayList<>());
        notifyMap.put(GameEvent.Type.attack, new ArrayList<>());
        notifyMap.put(GameEvent.Type.timer, new ArrayList<>());

        toRemove = new ArrayList<>();
    }

    public static void AddNotifyListener(GameEventListener listener) {
        if(!containsNotifyListener(listener)) {
            for(GameEvent.Type type : listener.types) {
                GetNotifyListeners(type).add(listener);
            }
        }
    }

    public static List<GameEventListener> GetNotifyListeners(GameEvent.Type type) {
        if(notifyMap.get(type) == null) throw new NotImplementedException();
        return notifyMap.get(type);
    }

    public static void FireEvent(Event e) {
        GameEvent event = (GameEvent)e;

        for( GameEventListener n : GetNotifyListeners(event.type)) {
            n.handle(event);
        }
    }

    public static void PrintTotalListeners() {
        System.out.printf("MV: %d SL: %d Canc: %d\n", GetNotifyListeners(GameEvent.Type.move).size(),
                GetNotifyListeners(GameEvent.Type.select).size(),
                GetNotifyListeners(GameEvent.Type.cancel).size());
    }

    public static void ToRemove(GameEventListener listener) {
        toRemove.add(listener);
    }

    public static void RemoveOldEvents() {
        for(GameEventListener l : toRemove) {
            remove(l);
        }
        toRemove.clear();
    }

    //----- private methods ----//

    private static void remove(GameEventListener listener) {
        if(containsNotifyListener(listener)) {
            for(GameEvent.Type type : listener.types) {
                GetNotifyListeners(type).remove(listener);
            }
        }
    }


    private static boolean containsNotifyListener(GameEventListener listener) {
        boolean contains = false;

        for(GameEvent.Type type : listener.types) {
            if(GetNotifyListeners(type).contains(listener)) {
                contains = true;
                break;
            }
        }

        return contains;
    }
}
