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
import org.newdawn.slick.SlickException;

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
            setLDirection();
            posX+= getSpeed() * delta;
            this.poly.setX(posX);
        }
        if(other.getX() <= this.getX())
        {
            setRDirection();
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
            setLDirection();
            setX(getX()+getSpeed()*delta);
        }
        if((float)bx <= posX)
        {
            setRDirection();
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
    public void setType(int t)
    {
        type = t;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void initRes(){
        try{
            specie_1_stage1 = new Image("res/fish_type1_stage1Sprite.png");
            specieImage = specie_1_stage1;
        }catch(SlickException e)
        {
            e.printStackTrace();
        }
    }
    
    public void handleType(int t, int delta)//will handle what type of species
    {
        t = this.getType();
        initSpecieSprite();
        switch(t)
        {
            case 1:
                //handle species type 1
                if(this.getHealth() <= 25)
                {
                    masterSprite = spec1_stage1;
                    initSpriteSheet(masterSprite, 0);
                    updateSpriteSheet(delta);
                   
                }else if(this.getHealth() <= 50)
                {
                    masterSprite = spec1_stage2;
                    initSpriteSheet(masterSprite, 0);
                    updateSpriteSheet(delta);
                }else if(this.getHealth() <= 75)
                {
                    //stage 3 obesity
                }else if(this.getHealth() == 100)
                {
                    //explosion 
                }
                if(this.getMutation() == 1)
                {
                    int timeB4Trans = 100;
                    for(int i = timeB4Trans; i > 0; i--)
                    {
                        masterSprite = spec1_stage1;
                        initSpriteSheet(masterSprite, 0);
                        updateSpriteSheet(delta);
                    }if(timeB4Trans == 0)
                    {
                        masterSprite = monster_type1;
                        initSpriteSheet(masterSprite, 0);
                        updateSpriteSheet(delta);
                    }
                }
                break;
            case 2:
                if(this.getHealth() <= 25)
                {
                    masterSprite = spec2_stage1;
                    initSpriteSheet(masterSprite, 0);
                    updateSpriteSheet(delta);
                }else if(this.getHealth() <= 50){
                    masterSprite = spec2_stage2;
                    initSpriteSheet(masterSprite, 0);
                    updateSpriteSheet(delta);
                }else if(this.getHealth() <= 75){
                    masterSprite = spec2_stage3;
                    initSpriteSheet(masterSprite, 0);
                    updateSpriteSheet(delta);
                }else if(this.getHealth() == 100)
                {
                    //xPlosion
                }
                if(this.getMutation() == 1)
                {
                    //change 
                }
                break;
            case 3:
                
            default: 
                break;
        }
    }
    
    public void initSpriteSheet(SpriteSheet sprite, int offset)
    {

            masterSprite = sprite;
            time = 0;
            timeOfLastFrameChange = 0;
            currentFrame = 0;
            masterImage = masterSprite.getSprite(currentFrame, 0);
            totalFrame = 4;
    }
    
    public void updateSpriteSheet(int delta)
    {
        time+=(float)delta/1000;
        if(time>timeOfLastFrameChange + 0.1f)
        {
            timeOfLastFrameChange = time;
            nextFrame();
        }
    }
    
    public void nextFrame()
    {
        currentFrame++;
        if(currentFrame>totalFrame-1)
        {
            currentFrame = 0;
        }
        masterImage = masterSprite.getSprite(currentFrame, 0);
    }
    
    public SpriteSheet getMasterSprite()
    {
        return masterSprite;
    }
    
    public Image getMasterImage()
    {
        return masterImage;
    }
    
    public void initSpecieSprite()
    {
        try{
            spec1_stage1 = new SpriteSheet("res/fish_type1_stage1Sprite.png",
                    128, 64);
            spec1_stage2 = new SpriteSheet("res/fish_type1_stage2Sprite.png",
                    128, 64);
            spec2_stage1 = new SpriteSheet("res/fish_type2_stage1Sprite.png",
                    128, 64);
            spec2_stage2 = new SpriteSheet("res/fish_type2_stage2Sprite.png",
                    128, 64);
            spec2_stage3 = new SpriteSheet("res/fish_type2_stage3Sprite.png",
                                            128, 64);
            spec3_stage1 = new SpriteSheet("res/fish_type3_stage3Sprite.png",
                                            128, 64);
            spec3_stage2 = new SpriteSheet("res/fish_type3_stage3Sprite.png",
                                            128, 64);
            spec3_stage3 = new SpriteSheet("res/fish_type3_stage3Sprite.png",
                                            128, 64);
            monster_type1 = new SpriteSheet("res/fish_typeM_M1.png", 128, 64);
            monster_type2 = new SpriteSheet("res/fish_typeM_M2.png", 128, 64);
        }catch(SlickException e)
        {
            e.printStackTrace();
        }
    }
    
    public void setMutation(int m)
    {
        mutation = m;
    }
    
    public int getMutation()
    {
        return mutation;
    }
    
    public void setLDirection()
    {
        if(dir_RIGHT == true)
        {
            dir_RIGHT = false;
        }
        dir_LEFT = true;
    }
    public void setRDirection()
    {
        if(dir_LEFT == true)
        {
            dir_LEFT = false;
        }
        dir_RIGHT = true;
    }
    
    public boolean getLDir()
    {
        return dir_LEFT;
    }
    
    public boolean getRDir()
    {
        return dir_RIGHT;
    }
    
    public Image img;
    public float posX;
    public float posY;
    public int beaconX, beaconY;
    public float time;
    public float timeOfLastFrameChange;
    public float hOffset;
    public float wOffset;
    public int damage;
    public int dir;
    public int health;
    public int behaviour;
    public int mutation;
    boolean isHere;
    public Polygon poly;
    public int lifeStage;
    public int type;
    public Animation anim;
    public Image specieImage;
    public Image specie_1_stage1;
    public Image specie_1_stage_2;
    public Image specie_1_stage_3;
    public Image specie_2;
    public Image specie_3;
    public boolean DirLeft;
    public boolean DirRight;
    private float speed;
    //sprites
    public SpriteSheet spec1_stage1;
    public SpriteSheet spec1_stage2;
    public SpriteSheet spec2_stage1;
    public SpriteSheet spec2_stage2;
    public SpriteSheet spec2_stage3;
    public SpriteSheet spec3_stage1;
    public SpriteSheet spec3_stage2;
    public SpriteSheet spec3_stage3;
    public SpriteSheet monster_type1;
    public SpriteSheet monster_type2;
    public int currentFrame;
    public int totalFrame;
    public boolean dir_LEFT;
    public boolean dir_RIGHT;
    public SpriteSheet masterSprite;
    public Image masterImage;
}