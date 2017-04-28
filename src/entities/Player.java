package entities;

import gfx.Animation;
import gfx.ImageManager;
import main.Game;
import java.awt.Graphics;
import levels.Level;
 
public class Player {
  //determines how player is moving
  public boolean up = false;
  public boolean dn = false;
  public boolean lt = false;
  public boolean rt = false;
  //player's starting location
  public final int x;
  public final int y;
  //map offsets
  private int xo;
  private int yo;
  //speed of movement
  private int xs;
  private int ys;
  //finish tile offsets
  private static int xf;
  private static int yf;
  //rock tile offsets
  public static int rx;
  public static int ry;
  //hazard tile offsets
  public static int hx;
  public static int hy;
  //
  private static int tx;        
  private static int ty;
  //needed for changing hazard tiles
  public static boolean placeRock = false;
  public static boolean dead = false;
  //round counter
  public static int round = 1;
  //speed of player
  private final int SPEED = 2;
  //image of player
  private final ImageManager im;
  //animation images
  private final Animation upAnimation;
  private final Animation dnAnimation;
  private final Animation ltAnimation;
  private final Animation rtAnimation;
  private final Animation stdAnimation;
  public static Animation deadAnimation;
  
  //constructor
  public Player(int x, int y, ImageManager im) {
    this.x = x;
    this.y = y;
    xo = Level.getStartX() * Game.SCALE * Game.TILESIZE - (9 * Game.TILESIZE + Game.SCALE);
    yo = Level.getStartY() * Game.SCALE * Game.TILESIZE - (9 * Game.TILESIZE * Game.SCALE);
    xs = 0;
    ys = 0;
    xf = Level.getFinishX() * Game.SCALE * Game.TILESIZE - (9 * Game.TILESIZE + Game.SCALE);
    yf = Level.getFinishY() * Game.SCALE * Game.TILESIZE - (9 * Game.TILESIZE * Game.SCALE);
    tx = (xo + xs + x) / (Game.TILESIZE * Game.SCALE);
    ty = (yo + ys + y) / (Game.TILESIZE * Game.SCALE);
    this.im = im;
    upAnimation = new Animation(im.playerUp, 500);
    dnAnimation = new Animation(im.playerDown, 500);
    ltAnimation = new Animation(im.playerLeft, 500);
    rtAnimation = new Animation(im.playerRight, 500);
    stdAnimation = new Animation(im.playerStand, 500);
    deadAnimation = new Animation(im.playerDead, 500);
  }
  
  //updates player
  public void tick() {
    //resets player's speed
    xs = 0;
    ys = 0;
    
    //player's up/down speed
    if (up) {ys -= SPEED;}
    else if (dn) {ys += SPEED;}
    
    //player's left/right speed
    if (lt) {xs -= SPEED;}
    else if (rt) {xs += SPEED;}
    
    //moves the player by speed
    move(xs, ys);
    
    //updates player's animation
    upAnimation.tick();
    dnAnimation.tick();
    ltAnimation.tick();
    rtAnimation.tick();
    stdAnimation.tick();
    deadAnimation.tick();
  }
  
  //moves map under player & checks for finish tile
  public void move(int xs, int ys) {
    if (round == 6 || round == 7) {
      if ((xs > 0 || xs < 0) || (ys > 0 || ys < 0))
        Game.running = false;
    }
    if (dead == true) {
        Game.setLevel(7);
        try{
        Thread.sleep(1000);
        }
        catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
    
    if (!collision(xs, 0)) xo += xs;
    if (!collision(0, ys)) yo += ys;
    //if player on finish tile
    if ((xo <= xf + Game.TILESIZE / 2 && xo >= xf - Game.TILESIZE / 2) && (yo <= yf + Game.TILESIZE / 2 && yo >= yf - Game.TILESIZE / 2)) {
      round += 1;
      Game.setLevel(round);
    }
    //if player runs over a rocktile (PS. Player can only carry 1 rock at a time)
    if (Rock.getRocks() == 0){
        for (int i = 0; i < Level.countRock; i++){
            //checks the position of all rocktiles on the map
            rx = (Level.arrayRocks [i][0]) * Game.SCALE * Game.TILESIZE - (9 * Game.TILESIZE + Game.SCALE);
            ry = (Level.arrayRocks [i][1]) * Game.SCALE * Game.TILESIZE - (9 * Game.TILESIZE + Game.SCALE);
            //when player is within half a tile from the rocktile's origin (before scaling), picks up the rock
            if ((xo <= rx + Game.TILESIZE / 2 && xo >= rx - Game.TILESIZE / 2) && (yo <= ry + Game.TILESIZE / 2 && yo >= ry - Game.TILESIZE / 2)) {
                //displays the rock in the upper left corner
                Game.rock = new Rock(0, 0, im);
                //changes floor tile of the rock that was picked up
                Level.changeFloorTile(Level.arrayRocks[i][0], Level.arrayRocks[i][1]);
                //ensures that the same rock cannot be picked up twice
                Rock.incRocks(Level.arrayRocks[i][0], Level.arrayRocks[i][1], i); 
            }//end if
        }//end for
    }//end if-getRocks
    //collission with a hazard tile? if you have a rock, placeRock = true
    if (placeRock == true){
        for (int i = 0; i < Level.countHazard; i++){
            //checks the position of all rocktiles on the map
              hx = (Level.arrayHazards [i][0]) * Game.SCALE * Game.TILESIZE - (9 * Game.TILESIZE + Game.SCALE);
              hy = (Level.arrayHazards [i][1]) * Game.SCALE * Game.TILESIZE - (9 * Game.TILESIZE + Game.SCALE);
                //when player is within 1.1 * tile from the hazardtile's origin (after scaling), puts down the rock to save his bacon
                if ((xo <= hx + Game.TILESIZE * 1.1 && xo >= hx - Game.TILESIZE * 1.1) && (yo <= hy + Game.TILESIZE * 1.1 && yo >= hy - Game.TILESIZE * 1.1)) {
                    //puts down the stepping stone
                   Level.changeRockTile(Level.arrayHazards[i][0], Level.arrayHazards[i][1]);
                   //deletes rock from topleft corner
                   Rock.decRocks();
                   //Player now has 0 rocks
                   placeRock = false;
                }//end if
            }//end for
        //placeRock = false;
    }//end if-placeRock
    
    
   
  }
  
  //checks for collisoin
  private boolean collision(int xs, int ys) {
    //checks top left corner
    if (Game.getLevel().getTile((xo + xs + x) / (Game.TILESIZE * Game.SCALE), (yo + ys + y) / (Game.TILESIZE * Game.SCALE)).isSolid(tx, ty)) return true;
    //checks bottom left corner
    if (Game.getLevel().getTile((xo + xs + x + Game.TILESIZE * Game.SCALE - SPEED) / (Game.TILESIZE * Game.SCALE), (yo + ys + y) / (Game.TILESIZE * Game.SCALE)).isSolid(tx, ty)) return true;
    //checks top right corner
    if (Game.getLevel().getTile((xo + xs + x) / (Game.TILESIZE * Game.SCALE), (yo + ys + y + Game.TILESIZE * Game.SCALE - SPEED) / (Game.TILESIZE * Game.SCALE)).isSolid(tx, ty)) return true;
    //checks bottom right corner
    if (Game.getLevel().getTile((xo + xs + x + Game.TILESIZE * Game.SCALE - SPEED) / (Game.TILESIZE * Game.SCALE), (yo + ys + y + Game.TILESIZE * Game.SCALE - SPEED) / (Game.TILESIZE * Game.SCALE)).isSolid(tx, ty)) return true;
    return false;
  }
  
  //draws the player's animation
  public void render(Graphics g) {
    if (dead == true) deadAnimation.render(g, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE);
    else if (up) upAnimation.render(g, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE);
    else if (dn) dnAnimation.render(g, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE);
    else if (lt) ltAnimation.render(g, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE);
    else if (rt) rtAnimation.render(g, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE);
    else stdAnimation.render(g, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE);
  }
  
  //returns the offsets of X and Y
  public int getXO() {return xo;}
  public int getYO() {return yo;}
  
  //round management
  public static int getRound() {return round;}
  
}