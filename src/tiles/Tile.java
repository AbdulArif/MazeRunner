//Base Class for all tiles
package tiles;

import gfx.ImageManager;
import java.awt.Graphics;
import main.Game;

public abstract class Tile {

  protected ImageManager im;
  //available resources
  public static Tile floor = new FloorTile(Game.getImageManager());
  public static Tile wall = new WallTile(Game.getImageManager());
  public static Tile start = new StartTile(Game.getImageManager());
  public static Tile end = new EndTile(Game.getImageManager());
  public static Tile blank = new BlankTile(Game.getImageManager());
  public static Tile hazard = new HazardTile(Game.getImageManager());
  public static Tile bypass = new BypassTile(Game.getImageManager());
  public static Tile rock = new RockTile(Game.getImageManager());

  public Tile(ImageManager im) {this.im = im;}

  public abstract void tick();
  
  public abstract void render(Graphics g, int x, int y);

  public boolean isSolid(int x, int y) {return false;}
}