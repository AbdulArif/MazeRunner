package tiles;

import gfx.ImageManager;
import main.Game;
import entities.Player;
import java.awt.Graphics;


public class HazardTile extends Tile {
    Tile HazardTile;
    
  public HazardTile(ImageManager im) {super(im);}

  @Override
  public void tick() {  }

  @Override
  public void render(Graphics g, int x, int y) {
    g.drawImage(im.hazardTile, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
  }

  @Override
  public boolean isSolid(int x, int y){
      //if you have a rock, you can place it here!!!
      if (entities.Rock.getRocks() == 1) 
          Player.placeRock = true;
      else{
          Player.dead = true;
      }
      return true;
  }
}