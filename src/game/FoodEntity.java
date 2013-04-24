/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author Nathan
 */
public class FoodEntity implements Entity{

    public float posX, posY, hOffset, wOffset;
    public int damage, health, behaviour, direction, val;
    public Image sprite;
    public boolean isHere;
    public Polygon poly;
    
    public FoodEntity(float x, float y, float h, float w)
    {
        setX(x);
        setY(y);
        setHOffset(h);
        setWOffset(w);
    }
    
    @Override
    public void setX(float x) {
        posX = x;
    }

    @Override
    public void setY(float y) {
        posY = y;
    }

    @Override
    public void setHOffset(float h) {
        hOffset = h;
    }

    @Override
    public void setWOffset(float w) {
        wOffset = w;
    }

    @Override
    public void setDamage(int d) {
        damage = d;
    }

    @Override
    public void setHealth(int hth) {
        health = hth;
    }

    @Override
    public void setDir(int dir) {
        switch(dir)
        {
            case 1:
                posX++;
                poly.setX(posX);   
                break;
            case 2:
                posX--;
                poly.setX(posX);
                break;
            case 3:
                posY++;
                poly.setY(posY);
                break;
            case 4:
                posY--;
                poly.setY(posY);
                break;
            default:
                break;
        }
    }

    @Override
    public void setBehaviour(int b) {
        behaviour = b;
    }
    
    public void setValue(int v)
    {
        val = v;
    }

    @Override
    public void setIsPresent(boolean p) {
        isHere = p;
    }

    @Override
    public float getX() {
        return posX;
    }

    @Override
    public float getY() {
        return posY;
    }

    @Override
    public float getHOffset() {
        return hOffset;
    }

    @Override
    public float getWOffset() {
        return wOffset;
    }

    @Override
    public boolean isPresent() {
        return isHere;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getDamage() {
        return damage;
    }
    
    public int getValue()
    {
        return val;
    }

    @Override
    public int getBehaviour() {
        return behaviour;
    }

    @Override
    public void setImage(Image img) {
        sprite = img;
    }

    @Override
    public Image getImage() {
       return sprite;
    }

    @Override
    public void setupPolygon(float x, float y, float w, float h) {
        x = this.getX();
        y = this.getY();
        w = this.getWOffset();
        h = this.getHOffset();
        
        poly = new Polygon(new float[]{x, y,
                                        x, y + h,
                                        x + w, y + h,
                                        x + w, y});
    }

    @Override
    public Polygon getPolygon() {
        return poly;
    }
    
}
