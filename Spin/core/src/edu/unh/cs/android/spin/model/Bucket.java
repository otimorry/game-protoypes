package edu.unh.cs.android.spin.model;

/**
 * Created by Olva on 5/16/15.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ConcurrentModificationException;

import edu.unh.cs.android.spin.controller.ControllerManager;
import edu.unh.cs.android.spin.controller.IEntity;

public class Bucket implements IEntity {

    //region Fields
    private Rectangle bounds;
    private Color bucketColor;
    private Vector2 bucketLocation;
    private Label bucketLabel;
    private int ballCount;
    private boolean isActive;
    public static final int bucketSize = 100;
    //endregion Fields

    //region Constructor
    public Bucket( Ball.Colors ballColor, Vector2 bucketLocation, BitmapFont font ) {
        init( ballColor, bucketLocation, font );
    }
    //endregion Constructor

    //region Mutators
    /** - - - - - - - - Mutators - - - - - - - - **/

    /* Initialize a bucket object */
    public void init( Ball.Colors ballColor, Vector2 bucketLocation, BitmapFont font ) {
        this.bucketLocation = bucketLocation;
        bucketLabel = new Label( "Count: 0", new Label.LabelStyle(font, Color.WHITE));
        bucketLabel.setPosition( bucketLocation.x, bucketLocation.y );
        bounds = new Rectangle( bucketLocation.x, bucketLocation.y, bucketSize, bucketSize );
        bucketColor = getBucketColor(ballColor);
        ballCount = 0;
    }

    /* Increment the number of balls in the bucket by 1 */
    public void incrementBallCount( ) {
        ballCount++;
    }

    /* sets the state of the bucket
     * isActive is true when there is something in the bucket
     * false otherwise. */
    public void setBucketState( boolean isActive ) {
        this.isActive = isActive;
    }

    /* sets the label of the Bucket object */
    public void setBucketLabel( ) {
        bucketLabel.setText("Count: " + ballCount);
    }

    public void update( ) {

    }

    public void draw( Batch batch ) {

    }

    public void clean( ) {

    }
    //endregion Mutators

    //region Accessors
    /** - - - - - - - - Accessors - - - - - - - - **/

    /* returns the color of the ball */
    public Color getBucketColor( Ball.Colors ballColor ) {
        if( ballColor == Ball.Colors.BLUE ) {
            return Color.BLUE;
        } else if( ballColor == Ball.Colors.GREEN ) {
            return Color.GREEN;
        } else if( ballColor == Ball.Colors.RED ) {
            return Color.RED;
        } else if( ballColor == Ball.Colors.YELLOW ) {
            return Color.YELLOW;
        } else {
            Gdx.app.error( "Invalid Color", "A Color is not recognized " + bucketColor +
                    " Please update getImage()" );
            return null;
        }
    }

    /* returns the location of the bucket */
    public Vector2 getLocation( ) { return bucketLocation; }

    /* returns the color of the bucket */
    public Color getColor( ) { return bucketColor; }

    /* returns the bounds of the bucket */
    public Rectangle getBounds( ) { return bounds; }

    /* checks to see if there is a ball object in the bucket */
    public boolean getBucketState( ) { return isActive; }

    /* gets the bucket label */
    public Label getBucketLabel( ) { return bucketLabel; }
    //endregion Accessors
}
