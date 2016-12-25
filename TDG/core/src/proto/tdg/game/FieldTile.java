package proto.tdg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import proto.tdg.game.Actions.ResetTileAction;
import proto.tdg.game.Actions.TimerAction;
import proto.tdg.game.UI.GameTimer;
import proto.tdg.game.Utility.EventUtility;
import proto.tdg.game.Utility.InputListenerUtility;

import static proto.tdg.game.WorldState.*;

/**
 * Created by Olva on 11/24/16.
 */
public class FieldTile extends Actor
{
    public boolean highlight;
    public Color highlightColor;
    public String fieldId;
    public float tileX, tileY, posX, posY,width, height;
    public boolean isVisited;
    public int distance;

    private ShapeRenderer shapeRenderer;

    private FieldObject primary;
    private FieldObject secondary;

//    private Object actionListenerMap;
//    private List<ActionListener> ToRemove;

    public FieldTile(String fieldId, float x, float y, float widthUnit, float heightUnit) {
        shapeRenderer = new ShapeRenderer();
//        ToRemove = new ArrayList<>();
        addListener(InputListenerUtility.GetFieldListener());

        this.fieldId = fieldId;
        this.tileX = x;
        this.tileY = y;
        this.posX = x * TILESIZE * SCALE;
        this.posY = y * TILESIZE * SCALE;
        this.width = widthUnit * SCALE;
        this.height = heightUnit * SCALE;
        this.highlightColor = Color.CLEAR;

        setBounds(this.posX, this.posY, this.width, this.height);
    }

    public FieldTile(String fieldId, float x, float y, float whUnit) {
        this(fieldId,x,y,whUnit,whUnit);
    }

    public FieldTile() {
        this("test",0,0,0,0);
    }


    public void setFieldObject(FieldObject fieldObject, boolean isPrimary) {
        if (isPrimary) primary = fieldObject;
        else secondary = fieldObject;
    }

    public Vector2 getTileXY() {
        return new Vector2(tileX,tileY);
    }

    public FieldObject getFieldObject(boolean isPrimary) {
        return isPrimary ? primary : secondary;
    }

    public void visit(int distance) {
        isVisited = true;
        this.distance = distance;
        STAGE.addAction(new ResetTileAction(new Vector2(tileX,tileY)));
    }

    public void reset() {
        isVisited = false;
        distance = 0;
    }

    @Override
    public void draw(Batch batch, float alpha){
        if(primary != null) {
            if( primary.time >= 0 && Timer.getTime() >= primary.time) {
                System.out.println(" Command Time: " +  primary.time);
                STAGE.addAction(new TimerAction(GameTimer.TimerState.PAUSED, primary));
            }
            primary.draw(batch,alpha, posX, posY,width,height,0,0,1);
        }

        if(secondary != null) {
            secondary.draw(batch,alpha, posX, posY,width,height,0,0,1);
        }

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setColor(highlight ? highlightColor : Color.CLEAR);
        shapeRenderer.rect(posX, posY, 0, 0, width, height, 1, 1, 0);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }
}
