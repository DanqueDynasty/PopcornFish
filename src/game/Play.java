/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Nathan
 */
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.geom.Polygon;

import java.util.ArrayList;
import java.util.Random;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

public class Play extends BasicGameState {
    public Play(int id)
    {
    
    }
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)throws SlickException
    {
      isPause = false;
      gameOver = false;
      inventoryScreen = false;
      
      menuBtn_Def = new Image("res/menuButton.png");
      menuBtn_Sel = new Image("res/menuBar_isSelect.png");
      
      menuBtn = menuBtn_Def;
      menuBar = new Image("res/menuBar.png");
      
      foodTool = new Image("res/menuBtn_foodTool.png");
      trashTool = new Image("res/menuBtn_trashTool.png");
      background = new Image("res/mockBG.png");
      currentTool = 0;
      
      tool_Food = false;
      food_isPresent = false;
      
      Food = new ArrayList<>();
      Fish = new ArrayList<>();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException
    {
        input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_ESCAPE))
        {
            isPause =! isPause;
        }
        if(!gameOver)
        {
            if(!isPause)
            {
                if(!inventoryScreen)
                {
                   if(Mouse.getX() <= 128 && Mouse.getX() >= 0 && Mouse.getY() <= 768 && Mouse.getY() >= 746)
                   {
                       menuBtn = menuBtn_Sel;
                       if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON))
                       {
                           sbg.enterState(0);//temporary
                       }
                   }else{
                       menuBtn = menuBtn_Def;
                   }
                   
                   System.out.println(Mouse.getX() + "      " + Mouse.getY());
                   
                   if(Mouse.getX() <= 992 && Mouse.getX() >= 960 && Mouse.getY() >= 746 && Mouse.getY() <= 768)
                   {
                       if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON))
                       {
                           tool_Food = true;
                           System.out.println("Food is selected");
                       }
                   }
                   
                   if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && tool_Food == true)
                   {
                       Food.add(new FoodEntity(input.getMouseX(), input.getMouseY(), 32, 32));
                       food_isPresent = true;
                       System.out.println("Food Spawned");
                   }
                   
                   for(int f = 0; f < Food.size(); f++)
                   {
                       Food.get(f).setupPolygon(input.getMouseX(), input.getMouseY(), 32, 32);
                       Food.get(f).setDir(3);
                       Food.get(f).setIsPresent(true);
                       System.out.println(Food.size());
                       if(Food.get(f).posY > gc.getHeight())
                       {
                           Food.remove(f);
                       }
                   }
                   //otherLoopCode here
                   
                   if(input.isKeyPressed(Input.KEY_ENTER))
                   {
                       Random rX = new Random();
                       Random rY = new Random();
                       Fish.add(new FishEntity((float)rX.nextInt(1024), (float)rY.nextInt(768), 64, 64));
                   }
                   
                   for(int fish = 0; fish < Fish.size(); fish++)
                   {
                       Fish.get(fish).setupPolygon(Fish.get(fish).getX(), Fish.get(fish).getY(), Fish.get(fish).getHOffset(), Fish.get(fish).getWOffset());
                       Random bX = new Random();
                       Random bY = new Random();
                       Fish.get(fish).setBeacon(bX.nextInt(512), bY.nextInt(768/2));
                       Fish.get(fish).roamBehaviour(Fish.get(fish).beaconX, Fish.get(fish).beaconY, gc);
                   }
                   
                   for(int z = 0; z < Food.size(); z++)
                   {
                       for(int a = 0; a < Fish.size(); a++)
                       {
                           if(food_isPresent)
                           {
                               Fish.get(a).searchForFood(Food.get(z));
                               if(Fish.get(a).getPolygon().intersects(Food.get(z).getPolygon()))
                               {
                                   Food.remove(z);
                               }
                           }else{
                               food_isPresent = false;
                           }
                       }  
                   }
                   
               }
               if(input.isKeyDown(Input.KEY_SPACE))
               {
                   inventoryScreen =! inventoryScreen;
               }
            }
            gc.setPaused(true);
        }
        
        
        
        System.out.println("In Play Level");
        //other input
        
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException
    {
        if(!isPause)
        {
            if(!inventoryScreen){
                g.drawImage(background, 0, 0);
                g.drawImage(menuBar, 0, 0);
                g.drawImage(menuBtn, 0, 0);
                g.drawImage(foodTool, 960, 0);
                g.drawImage(trashTool, 992, 0);
                for(int i = 0; i < Food.size(); i++)
                {
                    g.setDrawMode(Graphics.MODE_NORMAL);
                    g.setColor(Color.red);
                    g.draw(Food.get(i).poly);
                }
                
                for(int i = 0; i < Fish.size(); i++)
                {
                    g.draw(Fish.get(i).getPolygon());
                }
            }
        }
    }
    
    @Override
    public int getID()
    {
        return 1;
    }
    
    Input input;
    
    boolean isPause;
    boolean gameOver;
    boolean inventoryScreen;
    boolean food_isPresent;
    Image menuBar;
    Image menuBtn;
    Image menuBtn_Def;
    Image menuBtn_Sel;
    Image foodTool;
    Image trashTool;
    Image background;
    int currentTool;
    ArrayList<FoodEntity> Food;
    ArrayList<FishEntity> Fish;
    boolean tool_Food;
}
