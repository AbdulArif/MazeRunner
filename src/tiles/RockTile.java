package tiles;

import gfx.ImageManager;
import java.awt.Graphics;
import main.Game;

public class RockTile extends Tile {

  public RockTile(ImageManager im) {super(im);}

  @Override
  public void tick() {  }

  @Override
  public void render(Graphics g, int x, int y) {
    g.drawImage(im.rockTile, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
  }
}