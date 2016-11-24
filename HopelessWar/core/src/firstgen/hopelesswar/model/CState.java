package firstgen.hopelesswar.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import firstgen.hopelesswar.util.Enums;
import firstgen.hopelesswar.util.StatusUtil;

import java.io.Serializable;

/**
 * Created by Olva on 7/9/16.
 */
public class CState implements Component, Serializable {

    public float posX, posY, velX, velY, angle, baseVelocity;
    public float startX, startY, endX, endY;
    public int width, height;
    public boolean alive;
    public Vector2 center;
    public Enums.UnitType unitType;

    public CState(float posX, float posY, float angle, int width, int height, Enums.UnitType unitType, float baseVelocity) {
        this.posX = this.startX = this.endX = posX;
        this.posY = this.startY = this.endY = posY;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.unitType = unitType;
        this.baseVelocity = baseVelocity;
        this.center = new Vector2(posX + (width) / 2, posY + (height) / 2);
        this.alive = true;
    }

    public CState(float posX, float posY, Enums.UnitType unitType) {
        this(posX,posY,0,2,2,unitType,0.5f);
    }

    public void setBaseVelocity(float vel) {
        baseVelocity = vel;
    }

    public Rectangle getBound() {
        return new Rectangle(posX,posY,width,height);
    }
}
