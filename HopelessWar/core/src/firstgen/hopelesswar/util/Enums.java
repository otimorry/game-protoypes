package firstgen.hopelesswar.util;

/**
 * Created by Olva on 7/12/16.
 */
public class Enums {

    public enum Target {
        SINGLE, MULTIPLE, AREA
    }

    public enum EventType {
        MOVE, ATTACK,
    }

    public enum Alliance {
        A,
        B,
        C
    }

    public enum View {
        GAME,
        COMMAND,
    }

    public enum System {
        COMBAT,
        DEBUG,
        INPUTHANDLER,
        MOVEMENT,
        RENDER,
    }

    public enum UnitType {
        SOLDIER,
        PROJECTILE,
    }

    public enum Status {
        NONE,
        MOVING,
        ATTACKING,
    }
}
