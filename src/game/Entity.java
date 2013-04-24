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
public interface Entity {
    public void setX(float x);
    public void setY(float y);
    public void setHOffset(float h);
    public void setWOffset(float w);
    public void setDamage(int d);
    public void setHealth(int hth);
    public void setDir(int dir);
    public void setBehaviour(int b);
    public void setIsPresent(boolean p);
    public void setupPolygon(float x, float y, float w, float h);
    public float getX();
    public float getY();
    public float getHOffset();
    public float getWOffset();
    public boolean isPresent();
    public int getHealth();
    public int getDamage();
    public int getBehaviour();
    public void setImage(Image img);
    public Image getImage();
    public Polygon getPolygon();
}
