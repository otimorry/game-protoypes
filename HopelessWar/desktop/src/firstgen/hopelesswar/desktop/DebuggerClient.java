package firstgen.hopelesswar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import firstgen.hopelesswar.Debugger;
import firstgen.hopelesswar.Game;
import firstgen.hopelesswar.model.CState;
import firstgen.hopelesswar.util.DebuggerWrapper;


/**
 * Created by Olva on 7/21/16.
 */
public class DebuggerClient {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        String hostName = "Olvas-MacBook-Pro.local";
        int port = 55555;

        config.title = "Debugger";
        config.width = 200;
        config.x = 790;
        new LwjglApplication(new Debugger(hostName,port), config);

    }
}
