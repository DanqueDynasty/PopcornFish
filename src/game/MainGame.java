/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Nathan
 */

//imports
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {

public static final String GAME_NAME = "PopcornFish";
public static final int MENU_LEVEL = 0;
public static final int PLAY_LEVEL = 1;
public MainGame(String name)
{
    super(name);
    this.addState(new MainMenu(MENU_LEVEL));
    this.addState(new Play(PLAY_LEVEL));
}

  @Override
  public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(MENU_LEVEL).init(gc, this);
        this.getState(PLAY_LEVEL).init(gc, this);
        this.enterState(MENU_LEVEL);
  }
  //main function
  public static void main(String [] args)
  {
      try
      {
          AppGameContainer app = new AppGameContainer(new MainGame(GAME_NAME));
          app.setDisplayMode(1024, 768, false);
          app.setVSync(true);
          app.setTargetFrameRate(60);
          app.setShowFPS(false);
          app.start();
      }catch(SlickException e)
      {
          e.printStackTrace();
      }
  } 
}
