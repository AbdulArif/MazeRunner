package tiles;

import gfx.ImageManager;
import java.awt.Graphics;
import main.Game;

public class BypassTile extends Tile {

  public BypassTile(ImageManager im) {super(im);}

  @Override
  public void tick() {  }

  @Override
  public void render(Graphics g, int x, int y) {
    g.drawImage(im.bypassTile, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
  }
  
  
  @Override
  public boolean isSolid(int x, int y) {return false;}
}