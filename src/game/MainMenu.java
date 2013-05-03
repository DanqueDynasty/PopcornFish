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
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException
    {
        input = gc.getInput();
        if(input.isKeyDown(Input.KEY_ENTER))
        {
            sbg.enterState(1);
        }
    }
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException
    {
        
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
}
