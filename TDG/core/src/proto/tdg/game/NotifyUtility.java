package proto.tdg.game;

import proto.tdg.game.Notification.Notification;
import proto.tdg.game.Notification.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olva on 12/5/16.
 */
public class NotifyUtility {

    private static Map<Enums.Notify, List<Notification>> notifyMap;
    private static List<Notification> toRemove;

    public NotifyUtility() {
        notifyMap = new HashMap<>();
        notifyMap.put(Enums.Notify.MOVE, new ArrayList<>());
        notifyMap.put(Enums.Notify.SELECT, new ArrayList<>());
        notifyMap.put(Enums.Notify.CANCEL, new ArrayList<>());

        toRemove = new ArrayList<>();
    }

    public static void AddNotifyListener(Notification listener) {
        if(!containsNotifyListener(listener)) {
            for(Enums.Notify type : listener.types) {
                GetNotifyListeners(type).add(listener);
            }
        }
    }

    public static List<Notification> GetNotifyListeners(Enums.Notify type) {
        return notifyMap.get(type);
    }

    public static void FireNotification(Enums.Notify type, Result result) {
        for( Notification n : GetNotifyListeners(type)) {
            n.handle(result);
        }
    }

    public static void PrintTotalListeners() {
        System.out.println("Move Listeners: " + GetNotifyListeners(Enums.Notify.MOVE).size());
        System.out.println("Select Listeners: " + GetNotifyListeners(Enums.Notify.SELECT).size());
        System.out.println("Cancel Listeners: " + GetNotifyListeners(Enums.Notify.CANCEL).size());
    }

    public static void ToRemove(Notification listener) {
        toRemove.add(listener);
    }

    public static void RemoveOldNotifications() {
        for(Notification l : toRemove) {
            remove(l);
        }
        toRemove.clear();
    }

    //----- private methods ----//

    private static void remove(Notification listener) {
        if(containsNotifyListener(listener)) {
            for(Enums.Notify type : listener.types) {
                GetNotifyListeners(type).remove(listener);
            }
        }
    }


    private static boolean containsNotifyListener(Notification listener) {
        boolean contains = false;

        for(Enums.Notify type : listener.types) {
            if(GetNotifyListeners(type).contains(listener)) {
                contains = true;
                break;
            }
        }

        return contains;
    }
}
