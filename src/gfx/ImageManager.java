package gfx;

import java.awt.image.BufferedImage;
import main.Game;

public class ImageManager {
  //aniamtion images
  public BufferedImage[] playerUp;
  public BufferedImage[] playerDown;
  public BufferedImage[] playerLeft;
  public BufferedImage[] playerRight;
  public BufferedImage[] playerStand;
  //static rock
  public BufferedImage rock;
  //maze textures
  public BufferedImage blankTile;
  public BufferedImage endTile;
  public BufferedImage floorTile;
  public BufferedImage hazardTile;
  public BufferedImage bypassTile;
  public BufferedImage startTile;
  public BufferedImage wallTile;
  public BufferedImage rockTile;
  //dead images
  public BufferedImage[] playerDead;
  
  //constructor
  public ImageManager(SpriteSheet ss) {
    //UP animation
    playerUp = new BufferedImage[4];
      playerUp[0] = ss.crop(0, 3, Game.TILESIZE, Game.TILESIZE);
      playerUp[1] = ss.crop(1, 3, Game.TILESIZE, Game.TILESIZE);
      playerUp[2] = ss.crop(2, 3, Game.TILESIZE, Game.TILESIZE);
      playerUp[3] = ss.crop(1, 3, Game.TILESIZE, Game.TILESIZE);
    //DOWN animaiton
    playerDown = new BufferedImage[4];
      playerDown[0] = ss.crop(0, 0, Game.TILESIZE, Game.TILESIZE);
      playerDown[1] = ss.crop(1, 0, Game.TILESIZE, Game.TILESIZE);
      playerDown[2] = ss.crop(2, 0, Game.TILESIZE, Game.TILESIZE);
      playerDown[3] = ss.crop(1, 0, Game.TILESIZE, Game.TILESIZE);
    //LEFT animation
    playerLeft = new BufferedImage[4];
      playerLeft[0] = ss.crop(0, 1, Game.TILESIZE, Game.TILESIZE);
      playerLeft[1] = ss.crop(1, 1, Game.TILESIZE, Game.TILESIZE);
      playerLeft[2] = ss.crop(2, 1, Game.TILESIZE, Game.TILESIZE);
      playerLeft[3] = ss.crop(1, 1, Game.TILESIZE, Game.TILESIZE);
    //RIGHT animation
    playerRight = new BufferedImage[4];
      playerRight[0] = ss.crop(0, 2, Game.TILESIZE, Game.TILESIZE);
      playerRight[1] = ss.crop(1, 2, Game.TILESIZE, Game.TILESIZE);
      playerRight[2] = ss.crop(2, 2, Game.TILESIZE, Game.TILESIZE);
      playerRight[3] = ss.crop(1, 2, Game.TILESIZE, Game.TILESIZE);
    //STANDING STILL animation
    playerStand = new BufferedImage[1];
      playerStand[0] = ss.crop(1, 0, Game.TILESIZE, Game.TILESIZE);
    
    //ROCK
    rock = ss.crop(4, 1, Game.TILESIZE, Game.TILESIZE);
    
    //DEATH
    playerDead = new BufferedImage[1];
      playerDead[0] = ss.crop(3, 1, Game.TILESIZE, Game.TILESIZE);
      
    //Tiles
    rockTile = ss.crop(6, 1, Game.TILESIZE, Game.TILESIZE);
    blankTile = ss.crop(3, 0, Game.TILESIZE, Game.TILESIZE);
    endTile = ss.crop(5, 0, Game.TILESIZE, Game.TILESIZE);
    floorTile = ss.crop(7, 0, Game.TILESIZE, Game.TILESIZE);
    hazardTile = ss.crop(7, 1, Game.TILESIZE, Game.TILESIZE);
    bypassTile = ss.crop(5, 1, Game.TILESIZE, Game.TILESIZE);
    startTile = ss.crop(4, 0, Game.TILESIZE, Game.TILESIZE);
    wallTile = ss.crop(6, 0, Game.TILESIZE, Game.TILESIZE);
  }
}