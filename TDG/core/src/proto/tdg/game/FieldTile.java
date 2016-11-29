package proto.tdg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static proto.tdg.game.WorldState.*;

/**
 * Created by Olva on 11/24/16.
 */
public class FieldTile extends Actor
{
    public boolean highlight;
    public String fieldId;
    public float x,y,width,height;

    private ShapeRenderer shapeRenderer;

    private FieldObject primary;
    private FieldObject secondary;

    public FieldTile(String fieldId, float x, float y, float width, float height) {
        setBounds(x,y,width,height);
        addListener(ActorListener.GetFieldListener());

        shapeRenderer = new ShapeRenderer();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fieldId = fieldId;
    }

    public void setFieldObject(FieldObject fieldObject, boolean isPrimary) {
        if (isPrimary) primary = fieldObject;
        else secondary = fieldObject;
    }

    public FieldObject getFieldObject(boolean isPrimary) {
        return isPrimary ? primary : secondary;
    }

    @Override
    public void draw(Batch batch, float alpha){

        if(primary != null) {
            primary.draw(batch,alpha,x,y,width,height);
        }

        if(secondary != null) {
            secondary.draw(batch,alpha,x,y,width,height);
        }

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setColor(highlight ? new Color(1,0,0,0.4f) : Color.CLEAR);
        shapeRenderer.rect(x,y,width,height);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        InputUtil.handleAction();
    }
}
