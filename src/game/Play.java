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
      
      egg_default = new Image("res/egg_type0.png");
      egg_Type1 = egg_default;
      egg_Type2 = egg_default;
      egg_Type3 = egg_default;
      
      egg1_Unlocked = false;
      egg2_Unlocked = false;
      egg3_Unlocked = false;
      
      egg_TypeImg1 = new Image("res/egg_type1.png");
      egg_TypeImg2 = new Image("res/egg_type2.png");
      egg_TypeImg3 = new Image("res/egg_type3.png");
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
                       Random r1 = new Random();
                       int r = r1.nextInt(4);
                       Fish.get(fish).setType(r);
                   }
                   
                   for(int z = 0; z < Food.size(); z++)
                   {
                       for(int a = 0; a < Fish.size(); a++)
                       {
                           if(food_isPresent)
                           {
                            if(z<Food.size()){
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
                   
               }
               if(input.isKeyDown(Input.KEY_SPACE))
               {
                   inventoryScreen =! inventoryScreen;
               }
               if(inventoryScreen == true)
               {
                   gc.setPaused(true);
               }else{
                   gc.setPaused(false);
               }
               
               if(input.isKeyPressed(Input.KEY_1))
               {
                   egg1_Unlocked =! egg1_Unlocked;
               }
               
               if(input.isKeyPressed(Input.KEY_2))
               {
                   egg2_Unlocked =! egg2_Unlocked;
               }
               
               if(input.isKeyPressed(Input.KEY_3))
               {
                   egg3_Unlocked =! egg3_Unlocked;
               }
               
               //inventory code here
               if(egg1_Unlocked == true)
               {
                   egg_Type1 = egg_TypeImg1;
               }else{
                   egg_Type1 = egg_default;
               }
               
               if(egg2_Unlocked == true)
               {
                   egg_Type2 = egg_TypeImg2;
               }else{
                   egg_Type2 = egg_default;
               }
               
               if(egg3_Unlocked == true)
               {
                   egg_Type3 = egg_TypeImg3;
               }else{
                   egg_Type3 = egg_default;
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
                    g.setColor(Color.pink);
                    g.draw(Food.get(i).poly);
                }
                
                for(int i = 0; i < Fish.size(); i++)
                {
                    switch(Fish.get(i).getType()){
                        case 1:
                            g.setColor(Color.red);
                            g.draw(Fish.get(i).getPolygon());
                            break;
                        case 2:
                            g.setColor(Color.green);
                            g.draw(Fish.get(i).getPolygon());
                            break;
                        case 3:
                            g.setColor(Color.blue);
                            g.draw(Fish.get(i).getPolygon());
                            break;
                        default:
                            break; 
                    }
                    
                    g.draw(Fish.get(i).getPolygon());
                }
            }
            if(inventoryScreen == true){
            //render inventory code here
                g.drawImage(egg_Type1, 128, 384);
                g.drawImage(egg_Type2, 202, 384);
                g.drawImage(egg_Type3, 276, 384);
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
    boolean egg1_Unlocked;
    boolean egg2_Unlocked;
    boolean egg3_Unlocked;
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
    Image egg_Type1;
    Image egg_Type2;
    Image egg_Type3;
    Image egg_default;
    Image egg_TypeImg1;
    Image egg_TypeImg2;
    Image egg_TypeImg3;
}
