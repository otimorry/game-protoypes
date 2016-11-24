package firstgen.hopelesswar.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.InputProcessor;

import firstgen.hopelesswar.util.*;


/**
 * Created by Olva on 7/9/16.
 */
public class InputHandlerSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private InputProcessor processor;

    public InputHandlerSystem( InputProcessor processor, int priority ) {
        super(priority);
        this.processor = processor;
    }

    //TODO: Handle multiple units
    @Override
    public void update(float deltaTime) {
        ViewManager.GetCurrentViewHandler(Enums.View.GAME).handle();
        CooldownUtil.LeftClickDoubleTapped.update(deltaTime);
    }
}
