/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Nathan
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

public class MainMenu extends BasicGameState{
    public MainMenu(int id)
    {
    
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)throws SlickException
    {
        titleCard = new Image("res/titleCard.png");
        playBtn = new Image("res/playBtn.png");
        helpBtn = new Image("res/helpBtn.png");
        creditBtn = new Image("res/creditBtn.png");
        quitButton = new Image("res/quitBtn.png");
        background = new Image("res/backDrop_RAW.png");
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException
    {
        input = gc.getInput();
        if(input.isKeyDown(Input.KEY_ENTER))
        {
            sbg.enterState(1);
        }
        //handle other input
        
        if(input.getMouseX() >= 128 && input.getMouseX() <= 256 && input.getMouseY() >= 202 && input.getMouseY()<= 266)
        {
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
                sbg.enterState(1);
            }
        }
        
        if(input.getMouseX() >= 128 && input.getMouseX() <= 256 && input.getMouseY() >= 424 && input.getMouseY() <= 488)
        {
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
                System.exit(0);
            }
        }
    }
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException
    {
        g.drawImage(background, 0, 0);
        g.drawImage(titleCard, 128, 0);
        g.drawImage(playBtn, 128, 202);
        g.drawImage(helpBtn, 128, 276);
        g.drawImage(creditBtn, 128, 350);
        g.drawImage(quitButton, 128, 424);
        g.setDrawMode(Graphics.MODE_NORMAL);
        g.setColor(Color.white);
        g.drawString("(c)SwingInnovations for 1GAM", 512, 724);
    }
    
    @Override
    public int getID()
    {
        return 0;
    }
    Input input;
    Image titleCard;
    Image playBtn;
    Image helpBtn;
    Image creditBtn;
    Image quitButton;
    Image background;
}