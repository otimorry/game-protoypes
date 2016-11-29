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
    enum Act { MOVE, ATTACK, DEFEND, INFO, NONE }

    public float x,y,width,height;
    public Texture img;
    public String id;
    public boolean highlight;

    private ShapeRenderer shapeRenderer;

    public FieldTile(String id, float x, float y, float width, float height, Texture img) {
        setBounds(x,y,width,height);
        addListener(ActorListener.GetFieldListener());

        shapeRenderer = new ShapeRenderer();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
        this.id = id;
    }

    @Override
    public void draw(Batch batch, float alpha){
        if(img != null) {
            batch.draw(img,x,y,width,height);
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
        InputUtil.handleAction();
    }

    @Override
    public void setPosition(float x, float y) {
        world[(int)x][(int)y].img = img;
        img = null;
    }

}
