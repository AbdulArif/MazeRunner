package gfx;

import java.awt.image.BufferedImage;
import main.Game;

public class SpriteSheet {

  private BufferedImage sheet;

  public SpriteSheet(BufferedImage sheet) {
    this.sheet = sheet;
  }
  
  //crops the sprite sheet for each image
  public BufferedImage crop(int col, int row, int w, int h) {
    return sheet.getSubimage(col * Game.TILESIZE, row * Game.TILESIZE, w, h);
  }
}