/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

//imports
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import java.util.Random;
import org.newdawn.slick.Color;
import org.omg.CORBA.MARSHAL;
/**
 *
 * @author Nathan
 * Meant to replace the existing play class which was buggy beyond compare
 * 
 */
public class Play extends BasicGameState {
    public Play(int id)
    {
    
    }
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)throws SlickException
    {
    //initialize code 
        InitGUIRes();
        initOrgnaism();
        initFood();
        initInventoryScreen();
        isPause = false;
        isGameOver = false;
        isInventory = false;
        foodIsPresent = false;
        currency = 25;
        isSpecies1Get = true;
        isSpecies2Get = false;
        isSpecies3Get = false;
        currency = 25;
        for(int i = 0; i < specie1.size(); i++)
        {
            specie1.get(i).initRes();
        }
        for(int i = 0; i < specie2.size(); i++)
        {
            specie2.get(i).initRes();
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException
    {
    //handle update
        if(isGameOver == false)
        {
            if(isPause == false)
            {
                if(isInventory == false)
                {
                    //gameplay elements(GUI/ Char/ Etc)
                    updateGui(gc, sbg, delta);
                    handleSpawn(gc);
                    updateOrganism(gc, delta);
                    updateFood(gc, delta);
                    handleBehaviour(gc, delta); 
                    Spec1Behaviour(gc, delta);
                    Spec2Behaviour(gc, delta);
                    Spec3Behaviour(gc, delta);
                    updateStatus();
                    if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON))
                    {
                        currentTool = 0;
                    }
               //     System.out.println("Current Tool = " + currentTool);
                    
                    if(food.size() == 0)
                    {
                        foodIsPresent = false;
                    }
                }
            }
        }
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException
    {
        if(isGameOver == false)
        {
            if(isPause == false)
            {
                if(isInventory == false)
                {
                    //Render code for gameplay elements goes here
                    renderGui(g);
                    renderOrganism(g);
                    renderFood(g);
                    renderSpec1(g);
                    renderSpec2(g);
                    renderSpec3(g);
                }else if(isInventory == true)
                {
                    renderInventoryScreen(g);
                }
            }
        }
    }
    
    @Override
    public int getID()
    {
        return 1;
    }
    
    public void InitGUIRes()
    {
        try{
        menuBtn_def = new Image("res/menuButton.png");
        menuBtn_sel = new Image("res/menuBar_isSelect.png");
        
        menuBar = new Image("res/menuBar.png");
        
        menuBtn = menuBtn_def;
        
        foodTool = new Image("res/menuBtn_foodTool.png");
        trashTool = new Image("res/menuBtn_trashTool.png");
        background = new Image("res/backDrop_RAW.png");
        currentTool = 0;
        
        egg_def = new Image("res/egg_type0.png");
        egg_Type1_Img = new Image("res/egg_type1.png");
        egg_Type2_Img = new Image("res/egg_type2.png");
        egg_Type3_Img = new Image("res/egg_type3.png");
        
        egg_Type1 = egg_def;
        egg_Type2 = egg_def;
        egg_Type3 = egg_def;
        
        }catch(SlickException e)
        {
            e.printStackTrace();
        }
    }
    
    public void updateGui(GameContainer gc, StateBasedGame sbg, int delta)
    {

        Input input = gc.getInput();
            
            if(Mouse.getX() <= 128 && Mouse.getX() >= 0 && Mouse.getY() <= 768 && Mouse.getY() >= 746)
            {
                menuBtn = menuBtn_sel;
                if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
                {
                    sbg.enterState(0);
                }
            }else{
                menuBtn = menuBtn_def;
            }
            
            if(input.isKeyPressed(Input.KEY_0) || input.isKeyPressed(Input.KEY_NUMPAD0))
            {
                currentTool = 0;
            }
            
            if(input.isKeyPressed(Input.KEY_1) || input.isKeyPressed(Input.KEY_NUMPAD1))
            {
                currentTool = 1;
            }
            
            if(input.isKeyPressed(Input.KEY_3) || input.isKeyPressed(Input.KEY_NUMPAD3))
            {
                currentTool = 3;
            }
            
            if(input.isKeyPressed(Input.KEY_4) || input.isKeyPressed(Input.KEY_NUMPAD4))
            {
                currentTool = 4;
            }
            if(input.isKeyPressed(Input.KEY_5) || input.isKeyDown(Input.KEY_NUMPAD5))
            {
                currentTool = 5;
            }
            
            //GUI speaking
            try{
            if(currentTool == 1)
            {
                foodTool = new Image("res/menuBtn_foodTool_sel.png");
            }else{
                foodTool = new Image("res/menuBtn_foodTool.png");
            }
                }catch(SlickException e)
            {
                e.printStackTrace();
            }
            
    }
    
    public void renderGui(Graphics g)
    {
        g.drawImage(background, 0, 0);
        g.drawImage(menuBar, 0, 0);
        g.drawImage(menuBtn, 0, 0);
        g.drawImage(foodTool, 960, 0);
        g.drawImage(trashTool, 960, 64);
        if(isSpecies1Get == true)
        {
            egg_Type1 = egg_Type1_Img;
        }else{
            egg_Type1 = egg_def;
        }
        if(isSpecies2Get == true)
        {
            egg_Type2 = egg_Type2_Img;
        }else{
            egg_Type2 = egg_def;
        }
        if(isSpecies3Get == true)
        {
            egg_Type3 = egg_Type3_Img;
        }else{
            egg_Type3 = egg_def;
        }
        g.drawImage(egg_Type1, 960, 128);
        g.drawImage(egg_Type2, 960, 192);
        g.drawImage(egg_Type3, 960, 256);
        
        g.setColor(Color.green);
        g.drawString("ToolBar:", 0, 730);
        g.drawString(status, 128, 730);
    }
    
    public void initOrgnaism()
    {
        //setup polygon;
        gen = new ArrayList();
        specie1 = new ArrayList();
        specie2 = new ArrayList();
        specie3 = new ArrayList();
    }
    
    public void updateOrganism(GameContainer gc, int delta)
    {
        //adding dem
        Input input = gc.getInput();

        for(int f = 0; f < gen.size(); f++)
        {
            gen.get(f).setupPolygon(gen.get(f).getX(),
                                    gen.get(f).getY(),
                                    gen.get(f).getWOffset(), 
                                    gen.get(f).getHOffset());
        }     
    }
    
    public void handleBehaviour(GameContainer gc, int delta){
        for(int f = 0; f < gen.size(); f++)
        {
            if(foodIsPresent == false)
            {
                //roam code
                
                gen.get(f).roamBehaviour(gc, delta);
            }else if(foodIsPresent == true)
            {
                for(int fl = 0; fl < food.size(); fl++)
                {
                    if(fl < gen.size())
                    {
                        gen.get(f).searchForFood(food.get(fl), delta);
                        if(gen.get(f).poly.intersects(food.get(fl).poly))
                        {
                            food.remove(fl);
                            gen.get(fl).setHealth(5);
                        }
                    }
                }
            }
        }
    }
    
    public void Spec1Behaviour(GameContainer gc, int delta)
    {
        for(int i = 0; i < specie1.size(); i++)
        {
            specie1.get(i).setType(1);
            specie1.get(i).setupPolygon(specie1.get(i).getX(), 
                                        specie1.get(i).getY(), 
                                        specie1.get(i).getWOffset(),
                                        specie1.get(i).getHOffset());
     //       specie1.get(i).setHealth(25);
            for(int j = 0; j < specie1.size(); j+= 3)
            {
                specie1.get(j).setType(4);
                //System.out.println("Mustation for " + j + "Is true" );
            }
            specie1.get(i).handleType(1, delta);
            if(foodIsPresent == false)
            {
                specie1.get(i).roamBehaviour(gc, delta);
            }else if(foodIsPresent == true)
            {
                for(int f = 0; f < food.size(); f++)
                {
                    if(f < specie1.size()){
                        specie1.get(i).searchForFood(food.get(f), delta);
                        if(specie1.get(i).getPolygon().intersects(food.get(f).poly))
                        {
                            food.remove(f);
                         //   int h = specie1.get(i).getHealth();
                            specie1.get(i).setHealth(specie1.get(i).getHealth() + 10);
                        }
                    }
                }
            }
        }
    }
    
    public void Spec2Behaviour(GameContainer gc, int delta)
    {
        for(int i = 0; i < specie2.size(); i++)
        {
            specie2.get(i).setType(2);
            specie2.get(i).setupPolygon(specie2.get(i).getX(),
                                        specie2.get(i).getY(),
                                        specie2.get(i).getWOffset(),
                                        specie2.get(i).getHOffset());
            specie2.get(i).handleType(2, delta);
            if(foodIsPresent == false)
            {
                specie2.get(i).roamBehaviour(gc, delta);
            }else if(foodIsPresent == true)
            {
                for(int f = 0; f < food.size(); f++)
                {
                    if(f < specie2.size())
                    {
                        specie2.get(i).searchForFood(food.get(f), delta);
                        if(specie2.get(i).getPolygon().intersects(food.get(f).getPolygon()))
                        {
                            food.remove(f);
                            specie2.get(i).setHealth(specie2.get(i).getHealth() + 10);
                        }
                    }
                }
            }
        }
    }
    
    public void Spec3Behaviour(GameContainer gc, int delta)
    {
        for(int i = 0; i < specie3.size(); i++)
        {
            specie3.get(i).setType(3);
            specie3.get(i).setupPolygon(specie3.get(i).getX(),
                                        specie3.get(i).getY(),
                                        specie3.get(i).getWOffset(),
                                        specie3.get(i).getHOffset());
            specie3.get(i).handleType(3, delta);
            if(foodIsPresent == false)
            {
                specie3.get(i).roamBehaviour(gc, delta);
            }else if(foodIsPresent == true)
            {
                for(int f = 0; f < food.size(); f++)
                {
                    if(f < specie3.size())
                    {
                        specie3.get(i).searchForFood(food.get(f), delta);
                        if(specie3.get(i).getPolygon().intersects(food.get(f).getPolygon()))
                        {
                            food.remove(f);
                            specie3.get(i).setHealth(specie3.get(i).getHealth() + 10);
                        }
                    }
                }
            }
        }
    }
    
    public void renderSpec1(Graphics g)
    {
        for(int i = 0; i < specie1.size(); i++)
        {
            /*
            g.setColor(Color.red);
            g.draw(specie1.get(i).getPolygon());
            if(specie1.get(i).getMutation() == 1)
            {
                g.setColor(Color.cyan);
                g.draw(specie1.get(i).getPolygon());
            }
            */
            g.drawImage(specie1.get(i).getMasterImage(), 
                        specie1.get(i).getX(), 
                        specie1.get(i).getY());
        }
    }
   
    public void renderSpec2(Graphics g)
    {
        for(int i = 0; i < specie2.size(); i++)
        {
            g.drawImage(specie2.get(i).getMasterImage(), 
                        specie2.get(i).getX(), 
                        specie2.get(i).getY());
        }
    }
    
    public void renderSpec3(Graphics g)
    {
        for(int i = 0; i < specie3.size(); i++)
        {
            g.drawImage(specie3.get(i).getMasterImage(),
                        specie3.get(i).getX(), 
                        specie3.get(i).getY());
        }
    }
    
    public void renderOrganism(Graphics g)
    {
        for(int f = 0; f < gen.size(); f++)
        {
            g.setColor(Color.white);
            g.draw(gen.get(f).getPolygon());
        }
    }
    
    public void initFood()
    {
        food = new ArrayList();
        try{
            foodImg = new Image("res/food.png");
        }catch(SlickException e)
        {
            e.printStackTrace();
        }
    }
    
    public void updateFood(GameContainer gc, int delta)
    {
        for(int fl = 0; fl < food.size(); fl++)
        {
            food.get(fl).setupPolygon(food.get(fl).getX(), 
                                       food.get(fl).getY(), 
                                       food.get(fl).getWOffset(), 
                                       food.get(fl).getHOffset());
            
            food.get(fl).setDir(3);
            if(food.get(fl).posY >= gc.getHeight())
            {
                food.remove(fl);
            }
        }
    }
    
    public void renderFood(Graphics g)
    {
        /*
        for(int fl = 0; fl < food.size(); fl++)
        {
            g.setColor(Color.yellow);
            g.draw(food.get(fl).getPolygon());
        }
        */
        for(int f = 0; f < food.size(); f++)
        {
            food.get(f).setImage(foodImg);
            g.drawImage(food.get(f).getImage(), 
                    food.get(f).getX(), 
                    food.get(f).getY());
        }
    }
    
    public void initInventoryScreen()
    {
        try{
            egg_def = new Image("res/egg_type0.png");
            egg_Type1 = egg_def;
            egg_Type2 = egg_def;
            egg_Type3 = egg_def;
            egg_Type1_Img = new Image("res/egg_type1.png");
            egg_Type2_Img = new Image("res/egg_type2.png");
            egg_Type3_Img = new Image("res/egg_type3.png");
            fishType1_invImg = new Image("res/fish1_preview.png");
        }catch(SlickException e)
        {
            e.printStackTrace();
        }
    }
    
    public void updateStatus()
    {
        if(currentTool == 0)
        {
            status = "cursorTool";
        }else if(currentTool == 1)
        {
            status = "Food Tool";
        }else if(currentTool == 2)
        {
            status = "Gun Tool";
        }else if(currentTool == 3)
        {
            status = "Spawn Platy ----- 25 Shillings";
        }else if(currentTool == 4)
        {
            status = "Spawn Gaptso ----- 50 Shillings";
        }else if(currentTool == 5)
        {
            status = "Spawn Glapple ----- 75 Shillings";
        }
    }
    
    
    public void updateInventoryScreen()
    {
        //handle update code
    }
    
    public void renderInventoryScreen(Graphics g)
    {
        g.drawImage(egg_Type1, 256, 128);
        g.drawImage(egg_Type2, 330, 128);
        g.drawImage(egg_Type3, 404, 128);
    }
    
    public void handleSpawn(GameContainer gc){
        
        Input input = gc.getInput();
        /*
        if(currentTool == 0)
        {
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
                for(int i = 0; i < 1; i++)
                {
                    gen.add(new FishEntity(input.getMouseX(), input.getMouseY(), 64, 64));
                }
            }
        }  
        */

        if(currentTool == 1)
        {
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
                food.add(new FoodEntity(input.getMouseX(), input.getMouseY(), 32, 32));
                foodIsPresent = true;
            }
        }

        if(currentTool == 3)
        {
            if(isSpecies1Get == true)
            {
                if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
                {
                    for(int i = 0; i < 1; i++)
                    {
                        specie1.add(new FishEntity(input.getMouseX(), input.getMouseY(), 64, 128));
                    }
                }
            }
        }
        if(currentTool == 4)
        {
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
                for(int i = 0; i < 1; i++)
                {
                    specie2.add(new FishEntity(input.getMouseX(), input.getMouseY(), 64, 128));
                }
            }
        }
        
        if(currentTool == 5)
        {
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
                specie3.add(new FishEntity(input.getMouseX(), input.getMouseY(), 64, 128));
            }
        }
    }
    
    private int currentTool;
    private boolean isPause;
    private boolean isGameOver;
    private boolean isInventory;
    private boolean foodIsPresent;
    private boolean isSpecies1Get;
    private boolean isSpecies2Get;
    private boolean isSpecies3Get;
    String errMon;
    String status;
    Image egg_def;
    Image egg_Type1;
    Image egg_Type2;
    Image egg_Type3;
    Image egg_Type1_Img;
    Image egg_Type2_Img;
    Image egg_Type3_Img;
    Image menuBtn_def;
    Image menuBtn_sel;
    Image menuBtn;
    Image menuBar;
    Image foodTool;
    Image trashTool;
    Image background;
    Image fishType1_invImg;
    Image foodImg;
    ArrayList<FishEntity> gen;
    ArrayList<FoodEntity> food;
    ArrayList<FishEntity> specie1;
    ArrayList<FishEntity> specie2;
    ArrayList<FishEntity> specie3;
    private int currency;
}