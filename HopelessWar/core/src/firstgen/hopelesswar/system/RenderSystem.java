package firstgen.hopelesswar.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import firstgen.hopelesswar.Game;
import firstgen.hopelesswar.model.*;
import firstgen.hopelesswar.util.*;

import java.util.ArrayList;
import java.util.List;

import static firstgen.hopelesswar.Game.CAM;

/**
 * Created by Olva on 7/9/16.
 */
public class RenderSystem extends EntitySystem{

    public float height = 0.0f;
    public float width = 0.0f;

//    private Sprite mapSprite;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public RenderSystem(float width, float height, int priority) {
        super(priority);

        this.width = width;
        this.height = height;

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.ORANGE);
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> main = getEngine().getEntitiesFor(Family.all(CState.class).exclude(CProjectile.class).get());
        ImmutableArray<Entity> projectiles = getEngine().getEntitiesFor(Family.all(CState.class, CProjectile.class).get());

        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        CState s;
        CVisual visual;
        CSelect select;
        COverlay overlay;
        CCombat combat;
        CStatus cs;
        CTarget target;

        CAM.update();
        batch.setProjectionMatrix(CAM.combined);
        shapeRenderer.setProjectionMatrix(CAM.combined);

        // DRAW UI
        batch.begin();
        BaseUI ui = ViewManager.GetCurrentViewUI(ViewManager.CurrentView);
        if(ui.followCamera) {
            ui.sprite.setPosition(CAM.position.x - CAM.viewportWidth / 2, CAM.position.y - CAM.viewportHeight / 2);
        }
        ui.sprite.draw(batch);
        batch.end();

        for (Entity entity : main) {
            s = CM.STATE.get(entity);
            visual = CM.VISUAL.get(entity);
            select = CM.SELECT.get(entity);
            overlay = CM.OVERLAY.get(entity);
            combat = CM.COMBAT.get(entity);
            cs = CM.STATUS.get(entity);
            target = CM.TARGET.get(entity);

            drawEntities(entity, s, visual);

            overlay.setShapeRenderer(shapeRenderer);
            overlay.drawOverlays();

            if(select != null ) {
                //select.drawOverlay(shapeRenderer);
                //combat.drawOverlay(shapeRenderer);

                updateCameraView(s);
            }

//            if(select != null) {
//                drawOverlays(entity, s,combat);
//                updateCameraView(s);
//            }
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GOLDENROD);
        for( Entity entity : projectiles ) {
            drawAttackAnimation(CM.STATE.get(entity));
        }
        shapeRenderer.end();

        if(InputUtil.isDrag) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Rectangle rect = InputUtil.DisplayDragBound;
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            shapeRenderer.end();
        }
    }

    private void drawEntities(Entity e, CState s, CVisual visual) {
        batch.begin();
        batch.draw(visual.region.getTexture(), s.posX, s.posY, s.width, s.height );
        batch.end();

        // Alliance Color -- Will not be needing this later
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if(StatusUtil.IsInAlliance(e, Enums.Alliance.A)) {
            shapeRenderer.setColor(new Color(0,255,0,0.5f));
        }
        else if(StatusUtil.IsInAlliance(e, Enums.Alliance.B)) {
            shapeRenderer.setColor(new Color(0,0,255,0.5f));
        }
        else if(StatusUtil.IsInAlliance(e, Enums.Alliance.C)) {
            shapeRenderer.setColor(new Color(255,0,0,0.5f));
        }
        shapeRenderer.rect(s.posX,s.posY,s.width,s.height);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawAttackAnimation(CState state) {
        shapeRenderer.rect(state.posX, state.posY, 0.5f, 0.5f);
    }

//    private void drawOverlays(Entity e, CState s, CCombat combat) {
//
//        s.drawOverlay(shapeRenderer);
//        combat.drawOverlay(shapeRenderer);
//    }

    private void updateCameraView(CState s) {
        Game.CAM.position.set(s.center, 0);
		//cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

		float effectiveViewportWidth = Game.CAM.viewportWidth * Game.CAM.zoom;
		float effectiveViewportHeight = Game.CAM.viewportHeight * Game.CAM.zoom;

		Game.CAM.position.x = MathUtils.clamp(Game.CAM.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
		Game.CAM.position.y = MathUtils.clamp(Game.CAM.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);


		if( s.posX > Game.CAM.position.x && s.posX <= Game.CAM.position.x + effectiveViewportWidth / 2f ) {
			Game.CAM.translate(s.velX,0);
		}

		if( s.posY > Game.CAM.position.y && s.posY <= Game.CAM.position.y + effectiveViewportHeight / 2f ) {
			Game.CAM.translate(0,s.velY);
		}
    }



}
