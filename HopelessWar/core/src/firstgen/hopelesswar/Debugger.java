package firstgen.hopelesswar;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import firstgen.hopelesswar.model.CState;
import firstgen.hopelesswar.util.DebugOrganizer;
import firstgen.hopelesswar.util.DebuggerWrapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 7/21/16.
 */
public class Debugger extends ApplicationAdapter {

    private PrintWriter out;
    private ObjectInputStream in;
    private SpriteBatch debugBatch;
    private BitmapFont font;
    private String hostName;
    private int port;

    public Debugger(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    @Override
    public void create() {
        debugBatch = new SpriteBatch();
        font = new BitmapFont();

        try {
            // Debugger only needs to observe.
            Socket socket = new Socket(hostName, port);
            out = new PrintWriter(socket.getOutputStream(),true);
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        out.println("UPDATE");
        DebuggerWrapper wrapper = null;

        try {
            wrapper = (DebuggerWrapper)in.readObject();
            List<String> debugList = new ArrayList<>();

            for( CState state : wrapper.states) {
                debugList.add("posX: " + state.posX);
                debugList.add("posY: " + state.posY);
            }

            debugBatch.begin();
            for (int i = 0; i < debugList.size(); i++) {
                font.draw(debugBatch, debugList.get(i), 10, Gdx.graphics.getHeight() - 20 * i);
            }
            debugBatch.end();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
