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
/**
 *
 * @author Nathan
 * Meant to replace the existing play class which was buggy beyond compare
 * 
 */
public class Game extends BasicGameState {
    public Game(int id)
    {
    
    }
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)throws SlickException
    {
    //initialize code 
        InitGUIRes();
        initOrgnaism();
        initFood();
        isPause = false;
        isGameOver = false;
        isInventory = false;
        foodIsPresent = false;
        currency = 25;
        foodTool_Active = false;
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException
    {
    //handle update
        if(isGameOver == false)
        {
            if(isPause == false)
            {
              //gameplay elements(GUI/ Char/ Etc)
                updateGui(gc, sbg, delta);
                handleSpawn(gc);
                updateOrganism(gc, delta);
                updateFood(gc, delta);
                handleBehaviour(gc, delta);    
                System.out.println(currentTool);
                if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON))
                {
                   currentTool = 0;
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
            //Render code for gameplay elements goes here
                renderGui(g);
                renderOrganism(g);
                renderFood(g);
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
        
        //foodTool = new Image("res/menuBtn_foodTool.png");
        //trashTool = new Image("res/menuBtn_trashTool.png");
        background = new Image("res/backDrop_RAW.png");
        currentTool = 0;
        
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
    }
    
    
    public void renderGui(Graphics g)
    {
        g.setDrawMode(Graphics.MODE_NORMAL);
        g.drawImage(background, 0, 0);
        g.drawImage(menuBar, 0, 0);
        g.drawImage(menuBtn, 0, 0);
        
    }
    
    public void initOrgnaism()
    {
        //setup polygon;
        gen = new ArrayList();
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
        for(int fl = 0; fl < food.size(); fl++)
        {
            g.setColor(Color.yellow);
            g.draw(food.get(fl).getPolygon());
        }
    }
 
    
    public void handleSpawn(GameContainer gc){
        Input input = gc.getInput();
        if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)&& currentTool == 0)
        {
            gen.add(new FishEntity(input.getMouseX(), input.getMouseY(), 64, 64));
        }
        if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && foodTool_Active == true)
        {
            food.add(new FoodEntity(input.getMouseX(), input.getMouseY(), 32, 32));
            System.out.println("Food Added");
        }
    }
    
    private int currentTool;
    private boolean foodTool_Active;
    private boolean isPause;
    private boolean isGameOver;
    private boolean isInventory;
    private boolean foodIsPresent;
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
    ArrayList<FishEntity> gen;
    ArrayList<FoodEntity> food;
    private int currency;
}
