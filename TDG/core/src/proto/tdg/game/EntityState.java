package proto.tdg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Olva on 11/28/16.
 */
public class EntityState extends FieldObject {
    public int hp, mp, str, def, agi, mv;

    public EntityState(FieldTile tile, Texture img) {
        super(tile,img);
    }

    @Override
    public void draw(Batch batch, float alpha, float x, float y, float width, float height) {
        assert img != null;

        batch.draw(img,x,y,width,height);
    }
}