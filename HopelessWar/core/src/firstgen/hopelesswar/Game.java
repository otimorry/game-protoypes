package firstgen.hopelesswar;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import firstgen.hopelesswar.system.*;
import firstgen.hopelesswar.util.*;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {

	public static final int WORLD_WIDTH = 100;
	public static final int WORLD_HEIGHT = 100;
	public static final boolean DEBUG = true;
	public static final List<Entity> allEntities = new ArrayList<>();
	public static OrthographicCamera CAM;

	private Engine engine;

	@Override
	public void create () {
		engine = new Engine();

		engine.addEntityListener(new MyEntityListener());

		Entity entity = EntityGenerator.CreateGenericActor(5,2, Enums.UnitType.SOLDIER, Enums.Alliance.A);
		DebugOrganizer.addEntity(entity);

		engine.addEntity(entity);
		engine.addEntity(EntityGenerator.CreateGenericActor(9,2, Enums.UnitType.SOLDIER, Enums.Alliance.A));
		engine.addEntity(EntityGenerator.CreateGenericActor(5,9, Enums.UnitType.SOLDIER, Enums.Alliance.B));
		engine.addEntity(EntityGenerator.CreateGenericActor(9,9, Enums.UnitType.SOLDIER, Enums.Alliance.C));

		// Controller
		InputProcessor processor = new MainInputProcessor();
		Gdx.input.setInputProcessor(processor);

		float width =  Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		CAM = new OrthographicCamera(50, 50 * (height / width));
		CAM.position.set(20,15,0);

		BaseUI info1 = new BaseUI();
		info1.texture = new Texture(Gdx.files.internal("bg.png"));
		info1.sprite = new Sprite(info1.texture);
		info1.width = WORLD_WIDTH;
		info1.height = WORLD_HEIGHT;
		info1.sprite.setSize(info1.width, info1.height);
		info1.isActive = true;
		info1.followCamera = false;

		BaseUI info2 = new BaseUI();
		info2.texture = new Texture(Gdx.files.internal("CommandOverlay.png"));
		info2.sprite = new Sprite(info2.texture);
		info2.width = CAM.viewportWidth;
		info2.height = CAM.viewportHeight;
		info2.sprite.setSize(info2.width, info2.height);
		info2.isActive = false;
		info2.followCamera = true;

		ViewManager viewManager = new ViewManager(engine);
		viewManager.registerUI(Enums.View.GAME, info1);
		viewManager.registerUI(Enums.View.COMMAND, info2);

		engine.addSystem(new InputHandlerSystem(processor,1));
		engine.addSystem(new CombatSystem(2));
		engine.addSystem(new MovementSystem(3));
		engine.addSystem(new RenderSystem(width,height,4));

		viewManager.registerSystem(Enums.System.COMBAT, engine.getSystem(CombatSystem.class));
		viewManager.registerSystem(Enums.System.INPUTHANDLER, engine.getSystem(InputHandlerSystem.class));
		viewManager.registerSystem(Enums.System.MOVEMENT, engine.getSystem(MovementSystem.class));
		viewManager.registerSystem(Enums.System.RENDER, engine.getSystem(RenderSystem.class));

		if(DEBUG) {
			engine.addSystem(new DebugSystem(5));
			viewManager.registerSystem(Enums.System.DEBUG, engine.getSystem(DebugSystem.class));
		}
	}

	@Override
	public void render () {
		engine.update(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Handles callbacks for entities added/removed from the engine
	 */
	class MyEntityListener implements EntityListener {
		@Override
		public void entityAdded(Entity entity) {
			allEntities.add(entity);
		}

		@Override
		public void entityRemoved(Entity entity) {
			allEntities.remove(entity);
		}
	}
}
