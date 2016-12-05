package proto.tdg.game.Notification;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Olva on 12/5/16.
 */
public class MoveResult implements Result {
    public boolean success;
    public Vector2 tileXY;

    public MoveResult( boolean success, Vector2 tileXY) {
        this.success = success;
        this.tileXY = tileXY;
    }
}
