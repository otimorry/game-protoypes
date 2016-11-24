package proto.tdg.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Olva on 11/24/16.
 */
public class Player {
    public float x,y,width,height;
    public Texture img;

    public Player(float x, float y, float width, float height, Texture img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }
}
