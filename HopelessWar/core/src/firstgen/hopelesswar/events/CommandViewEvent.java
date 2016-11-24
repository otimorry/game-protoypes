package firstgen.hopelesswar.events;

import com.badlogic.ashley.core.Entity;
import firstgen.hopelesswar.Game;
import firstgen.hopelesswar.util.CM;
import firstgen.hopelesswar.util.ViewManager;
import firstgen.hopelesswar.util.Enums;

/**
 * Created by Olva on 7/26/16.
 */
public class CommandViewEvent implements EventBase {
    @Override
    public void activate() {
        System.out.println("Command view");
        ViewManager.CurrentView = Enums.View.COMMAND;
        ViewManager.DisableSystem(Enums.System.MOVEMENT);
        ViewManager.DisableSystem(Enums.System.COMBAT);

        for(Entity e : Game.allEntities) {
            CM.OVERLAY.get(e).enableAllOverlays();
        }
    }
}
