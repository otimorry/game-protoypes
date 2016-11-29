package proto.tdg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Olva on 11/28/16.
 */
public abstract class FieldObject extends Actor {
    protected FieldTile tile;
    protected Texture img;

    public FieldObject(FieldTile tile, Texture img) {
        setFieldTile(tile);
        this.img = img;
    }

    public void setFieldTile(FieldTile tile) {
        assert tile != null;

        this.tile = tile;
    }

    public abstract void draw(Batch batch, float alpha, float x, float y, float width, float height);
}
