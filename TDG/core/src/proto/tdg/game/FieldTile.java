package proto.tdg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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

    public FieldTile(String id, float x, float y, float width, float height, Texture img) {
        setBounds(x,y,width,height);
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("I'm touched -- " + id + " at x: " + x + " y: " + y);

                FieldTile tile = (FieldTile)event.getTarget();

                if(InputUtil.needAction) {
                    InputUtil.screenStartPt = new Vector2(tile.x,tile.y);
                    InputUtil.needAction = false;
                }
                else {
                    InputUtil.SetSelected(event.getTarget());

                    // for now to locate player
                    if(tile.img != null) {
                        STAGE.addActor(table);
                    }
                }

                return true;
            }
        });

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
