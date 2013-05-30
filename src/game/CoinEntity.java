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
public class CoinEntity implements Entity{
    public Image image;
    public float posX, posY, wOff, hOff;
    public Polygon poly;
    public CoinEntity(float x, float y, float w, float h){
        setX(x);
        setY(y);
        setWOffset(w);
        setHOffset(h);
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
        hOff = h;
    }

    @Override
    public void setWOffset(float w) {
        wOff = w;
    }

    @Override
    public void setDamage(int d) {
        
    }

    @Override
    public void setHealth(int hth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDir(int dir) {
        switch(dir)
        {
            case 1:
                posY++;
                break;
            default:
                
        }
    }

    @Override
    public void setBehaviour(int b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setIsPresent(boolean p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupPolygon(float x, float y, float w, float h) {
        poly = new Polygon(new float[]{ x, y,
                                        x, y + h,
                                        x + w, y + h,
                                        x + w, y});
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
        return hOff;
    }

    @Override
    public float getWOffset() {
        return wOff;
    }

    @Override
    public boolean isPresent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getHealth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDamage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getBehaviour() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setImage(Image img) {
        image = img;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Polygon getPolygon() {
        return poly;
    }
}
