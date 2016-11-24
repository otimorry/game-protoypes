package firstgen.hopelesswar.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

import firstgen.hopelesswar.util.DebugOrganizer;

/**
 * Created by Olva on 7/9/16.
 */
public class DebugSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private SpriteBatch debugBatch;
    private BitmapFont font;

    public DebugSystem(int priority) {
        super(priority);
        debugBatch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void update(float deltaTime) {
        List<String> debugList = DebugOrganizer.getDebugList();

        debugBatch.begin();

        for (int i = 0; i < debugList.size(); i++) {
            font.draw(debugBatch, debugList.get(i), 10, Gdx.graphics.getHeight() - 20 * i);
        }

        debugBatch.end();

        DebugOrganizer.clearDebugList();
    }
}
