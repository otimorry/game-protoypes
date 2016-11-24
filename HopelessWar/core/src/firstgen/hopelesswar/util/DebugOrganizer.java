package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 5/25/16.
 */
public class DebugOrganizer {

    private static List<String> debugList = new ArrayList<>();
    private static List<Entity> debugEntityList = new ArrayList<>();

    public static List<String> getDebugList() { return debugList; }

    public static List<Entity> getEntityList() { return debugEntityList; }

    public static void addDebugStatement(String s) {
        debugList.add(s);
    }

    public static void addEntity(Entity entity) {
        debugEntityList.add(entity);
    }

    public static void clearDebugList() {
        debugList.clear();
    }
}
