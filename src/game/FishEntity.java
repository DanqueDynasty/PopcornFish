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
import java.util.Timer;

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
        setHealth(25);
        hasMutated = false;
        initSpecieSprite();
        time = 0;
        timeOfLastFrameChange = 0;
        currentFrame = 0;
        ctime = 0;
        timeSinceLastChange = 0;
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
            if(hasMutated == false)
            {
                setDir(1);
            }else if(hasMutated == true)
            {
                setDir(3);
            }
            posX+= getSpeed() * delta;
            this.poly.setX(posX);
        }
        if(other.getX() <= this.getX())
        {
            //setDir(0);
            if(hasMutated == false)
            {
                setDir(0);
            }else if(hasMutated == true)
            {
                setDir(4);
            }
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
            //setDir(1);
            if(hasMutated == false)
            {
                setDir(1);
            }else if(hasMutated == true)
            {
                setDir(3);
            }
            setX(getX()+getSpeed()*delta);
        }
        if((float)bx <= posX)
        {
            if(hasMutated == false)
            {
                setDir(0);
            }else if(hasMutated == true)
            {
                setDir(4);
            }
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
        if(Mutated = true){
            ctime += (float)(delta)/1000;
            if(ctime > timeSinceLastChange + 5)
            {
                hasMutated = true;
            }
        }
        //System.out.println(getHealth());
        switch(t)
        {
            case 1:
                //handle species type 1
                if(this.getHealth() <= 25)
                {   
                    masterSprite = spec1_stage1;
                    initSpriteSheet(masterSprite);
                }else if(this.getHealth() <= 50)
                {
                    masterSprite = spec1_stage2;
                    initSpriteSheet(masterSprite);
                }else if(this.getHealth() <= 75)
                {
                    //stage 3 obesity
                }else if(this.getHealth() == 100)
                {
                    //explosion 
                }
                if(mutation == 1)
                {
                    
                }
                break;
            case 2:
                if(this.getHealth() <= 25)
                {
                    masterSprite = spec2_stage1;
                    initSpriteSheet(masterSprite);
                }else if(this.getHealth() <= 50){
                    masterSprite = spec2_stage2;
                    initSpriteSheet(masterSprite);
                }else if(this.getHealth() <= 75){
                    masterSprite = spec2_stage3;
                    initSpriteSheet(masterSprite);
                }else if(this.getHealth() == 100)
                {
                    //xPlosion
                }
                if(this.getMutation() == 1)
                {
                    masterSprite = spec1_stage1;
                    initSpriteSheet(masterSprite);
                }
                break;
            case 3:
                if(this.getHealth() <= 25)
                {
                    masterSprite = spec3_stage1;
                    initSpriteSheet(masterSprite);
                }else if(this.getHealth() <= 50)
                {
                    masterSprite = spec3_stage2;
                    initSpriteSheet(masterSprite);
                }else if(this.getHealth() <= 75)
                {
                    masterSprite = spec3_stage3;
                    initSpriteSheet(masterSprite);
                }else if(this.getHealth() == 100)
                {
                    //explosion
                }
                break;
            case 4:
                    masterSprite = spec1_stage1;
                    initSpriteSheet(masterSprite);
                    //ChangeSprite change = new ChangeSprite(monster_type1);
                    //timer.schedule(change, 6000, 6000);
                    
                break;
            case 5:
                break;
            default: 
                break;
        }

        updateSpriteSheet(delta);
    }
    
    public int getDir()
    {
        return dir;
    }
    
    public void initSpriteSheet(SpriteSheet sprite)
    {
            int d = getDir();
            masterSprite = sprite;
            masterImage = masterSprite.getSprite(currentFrame, d);
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
        int d = getDir();
        masterImage = masterSprite.getSprite(currentFrame, d);
    }  
    
    public SpriteSheet getMasterSprite()
    {
        return masterSprite;
    }
    
    public Image getMasterImage()
    {
        return masterImage;
    }
    
    public void setOffset(int o)
    {
        Offset = o;
    }
    
    public int getOffset()
    {
        return Offset;
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
    
    public void setMutation(boolean m)
    {
        Mutated = m;
    }
    
    public boolean getMutated()
    {
        return Mutated;
    }
    
    
    //methods to handle le timer
    
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
    boolean hasMutated;
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
    public int Offset;
    public boolean Mutated;
    private float ctime;
    private float timeSinceLastChange;
}