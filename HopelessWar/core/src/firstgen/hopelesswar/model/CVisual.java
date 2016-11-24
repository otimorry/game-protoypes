package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Olva on 7/9/16.
 */
public class CVisual implements Component{
    public TextureRegion region;

    public CVisual( TextureRegion region ) {
        this.region = region;
    }
}
