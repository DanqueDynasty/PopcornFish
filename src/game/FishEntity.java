/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.newdawn.slick.Image;
import java.util.Random;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Animation;

/**
 *
 * @author Nathan
 */
public class FishEntity implements Entity{
    public FishEntity(float x, float y, float h, float w)
    {
    	setupPolygon(x,y,h,w);
        setX(x);
        setY(y);
        setHOffset(h);
        setWOffset(w); 
        setSpeed(.1f);
        setBeacon();
    }

    @Override
    public void setX(float x) {
        posX = x;
        poly.setX(posX);
    }

    @Override
    public void setY(float y) {
        posY = y;
        poly.setY(posY);
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
        this.dir = dir;
        if(dir == 0)
        {
            //TODO go left
        }else if(dir == 1)
        {
            //TODO go Right
        }else if(dir == 2)
        {
            
        }
    }

    @Override
    public void setBehaviour(int b) {
       behaviour = b;
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
    public int getHealth() {
        return health;
    }

    public void setHealthAfterFood(FoodEntity other)
    {
        int v = other.getValue();
        int newHealth = this.getHealth() + v;
        this.setHealth(newHealth);
    }
    
    @Override
    public int getDamage() {
       return damage;
    }

    @Override
    public int getBehaviour() {
        return behaviour;
    }

    @Override
    public void setImage(Image img) {
        this.img = img;
    }

    @Override
    public Image getImage() {
       return img;
    }
    
    public boolean isFoodPresent(FoodEntity other)
    {
        if(other.isPresent() == true)
        {
            return true;
        }
        return false;
    }
    
    public void searchForFood(FoodEntity other, int delta)
    {
        if(other.getX() >= this.getX())
        {
            posX+= getSpeed() * delta;
            this.poly.setX(posX);
        }
        if(other.getX() <= this.getX())
        {
            posX-=  getSpeed() * delta;
            this.poly.setX(posX);
        }
        if(other.getY() >= this.getY())
        {
            posY+=  getSpeed() * delta;
            this.poly.setY(posY);
        }
        if(other.getY() <= this.getY())
        {
            posY-=  getSpeed() * delta;
            this.poly.setY(posY);
        }
    }
    
    public void setSubImage(int d)
    {
        d = this.dir;
        if(d == 0)
        {

        }else if(d == 1)
        {
            
        }
    }
   

    @Override
    public void setIsPresent(boolean p) {
        isHere = p;
    }

    @Override
    public boolean isPresent() {
        return isHere;
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
    
    public void setBeacon()
    {
        Random r = new Random();
        beaconX = r.nextInt(768);
        Random r2 = new Random();
        beaconY = r2.nextInt(768);
    }
    
    public void setBeacon(int x, int y)
    {
        beaconX = x;
        beaconY = y;
    }
    
    public int getBeaconX()
    {
        return beaconX;
    }
    
    public int getBeaconY()
    {
        return beaconY;
    }
    public void setSpeed(float f){
    	this.speed = f;
    }
    public float getSpeed(){
    	return speed;
    }
    public void roamBehaviour(GameContainer gc, int delta)
    {
    	int bx = beaconX;
    	int by = beaconY;
        if((float)bx >= posX)
        {
            setX(getX()+getSpeed()*delta);
        }
        if((float)bx <= posX)
        {
            setX(getX()-getSpeed()*delta);
        }
        if((float)by >= posY)
        {
            setY(getY()+getSpeed()*delta);
        }
        if((float)by <= posY)
        {
            setY(getY()-getSpeed()*delta);
        }
        if(bx>posX&&bx<posX+10){
        	if(by>posY&&by<posY+10){
        		setBeacon();
        	}else if(by<posY&&by>posY-10){
        		setBeacon();
        	}
        }else if(bx<posX&&bx>posX-10){
        	if(by>posY&&by<posY+10){
        		setBeacon();
        	}else if(by<posY&&by>posY-10){
        		setBeacon();
        	}
        }
        
    }
    
    public void setLifeStage(int stage)
    {
        lifeStage = stage;
    }
    
    public int getLifeStage()
    {
        return lifeStage;
    }
    
    public void handStage(int st)
    {
        st = this.getLifeStage();
        switch(st)
        {
            
            default:
                break;
        }
    }
    
    public void setType(int t)
    {
        type = t;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void handleType(int t)//will handle what type of species
    {
        t = this.getType();
        switch(t)
        {
            case 1:
                //handle species type 1
                if(this.getHealth() <= 25)
                {
                    spritesheet = sprite_Type1;
                }else if(this.getHealth() <= 50)
                {
                    //stage 2 obesity
                }else if(this.getHealth() <= 75)
                {
                    //stage 3 obesity
                }else if(this.getHealth() == 100)
                {
                    //explosion 
                }
                break;
            case 2:
                spritesheet = sprite_Type2;
                break;
            case 3:
                spritesheet = sprite_Type3;
            default: 
                break;
        }
    }
    
    public void initSpriteSheet()
    {
        
    }
    
    public void initAnimation()
    {
        anim = new Animation();
    }
    
    
    public Image img;
    public float posX;
    public float posY;
    public int beaconX, beaconY;
    public float hOffset;
    public float wOffset;
    public int damage;
    public int dir;
    public int health;
    public int behaviour;
    boolean isHere;
    public Polygon poly;
    public int lifeStage;
    public int type;
    public Animation anim;
    public SpriteSheet spritesheet;
    public SpriteSheet sprite_Type1;
    public SpriteSheet sprite_Type2;
    public SpriteSheet sprite_Type3;
    private float speed;
}