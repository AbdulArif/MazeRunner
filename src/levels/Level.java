package levels;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import tiles.Tile;


public final class Level {
  //used to set two different 2D arrays
  private final static int MAXROCKS = 100;
  private final static int MAXHAZARDS = 1000;
  //holds map information of all tiles
  private static int[][] tiles;
  //holds map information of all rocks
  public static int [][] arrayRocks = new int [MAXROCKS][2];
  //holds map information of all hazards
  public static int [][] arrayHazards = new int [MAXHAZARDS][2];
  //holds level dimensions
  private final int w;
  private final int h;
  //holds start location
  private static int startX;
  private static int startY;
  //holds finish location
  private static int finishX;
  private static int finishY;
  //counts the number of rocks on the map *** used for indexing in the rock array
  public static int countRock;
  //counts the number of hazards on the map *** used for indexing in the hazard array
  public static int countHazard;
  
@Override
public boolean equals(Object obj){
	try{
		if(obj == null) return true;
		
		final Level l = (Level) obj;
		  if(MAXROCKS == l.MAXROCKS && MAXHAZARDS == l.MAXHAZARDS && startX == l.startX && startY == l.startY &&
				   finishX == l.finishX && finishY == l.finishY && countRock == l.countRock && countHazard == l.countHazard && h == l.h
				   && tiles.equals(l.tiles) && arrayRocks.equals(l.arrayRocks) && arrayHazards.equals(l.arrayHazards) && w == l.w )
		  {
			  return true;
		  }
		  else{
			  return false;
		  }
	  }catch(Exception e)
	  {
		  throw e;
	  }
}
  
//Loads the level
  public Level(BufferedImage levelImage) {
    w = levelImage.getWidth();
    h = levelImage.getHeight();
    loadLevel(levelImage);
  }
  
  //looks at level map and stores the info in a multiD-array
  public void loadLevel(BufferedImage levelImage) {
    //resets the count for rocks, hazards, and tiles for each level
    countRock = 0;
    countHazard = 0;
    tiles = new int[levelImage.getWidth()][levelImage.getHeight()];
    //reads map top to bottom (row)
    for (int y = 0; y < levelImage.getHeight(); y++) {
      //reads map left to right (col)
      for (int x = 0; x < levelImage.getWidth(); x++) {
        //determines the color of the pixel located at index x & y
        Color c = new Color(levelImage.getRGB(x, y));
        //converts color to hex string
        String h = String.format("%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
        //determines color to ID tile at that location
        switch(h) {
        case "362f2d": tiles[x][y] = 1; break; //Wall
        case "00ff00": tiles[x][y] = 2; break; //Floor
        case "ff0000": tiles[x][y] = 3; setStart(x, y); break; //Start
        case "ffff00": tiles[x][y] = 4; setFinish(x, y); break; //End
        case "ffaa00": tiles[x][y] = 5; //hazard
        //used to index hazard tiles
        countHazard += 1;
        setHazard (x , y , countHazard);
        break; //Hazard - end
        case "c7a20d": tiles[x][y] = 7; //Rockfloor
        //used to index rocktiles
        countRock += 1;
        setRock (x, y, countRock); 
        break; //Rockfloor - end
        case "000000": tiles[x][y] = 6;  break; //Bypass
            
        default: tiles[x][y] = 1000; //prevents map errors
        }
      }
    }
  }
  
  //draws the tile
  public void render(Graphics g) {
    //get's the offsets
    int xo = Game.getPlayer().getXO();
    int yo = Game.getPlayer().getYO();
    //determines the amount of TILES (not pixels) to render based on game window
    int x0 = Math.max(xo / (Game.TILESIZE * Game.SCALE), 0);  //x axis max
    int y0 = Math.max(yo / (Game.TILESIZE * Game.SCALE), 0);  //y axis max
    int x1 = Math.min((xo + Game.WIDTH * Game.SCALE) / (Game.TILESIZE * Game.SCALE) + 1, w);  //x axis min
    int y1 = Math.min((yo + Game.HEIGHT * Game.SCALE) / (Game.TILESIZE * Game.SCALE) + 1, h); //y axis min
    //renders only those tiles in the game window
    for (int y = y0; y < y1; y++) { //renders rows
      for (int x = x0; x < x1; x++) { //renders col in each row
        getTile(x, y).render(g, x * Game.TILESIZE * Game.SCALE - xo, y * Game.TILESIZE * Game.SCALE - yo);
      }
    }
  }
  
  //returns the tile stored in the multiD-array
  public Tile getTile(int x, int y) {
    if (x < 0 || y < 0 || x >= w || y >= h) return Tile.blank;
    switch(tiles[x][y]) {
      case 1: return Tile.wall;
      case 2: return Tile.floor;
      case 3: return Tile.start;
      case 4: return Tile.end;
      case 5: return Tile.hazard;
      case 6: return Tile.bypass;
      case 7: return Tile.rock;
      default: return Tile.blank;
    }
  }
  
  //sets & gets start location
  public static void setStart(int x, int y) {startX = x; startY = y;}
  public static int getStartX() {return startX;}
  public static int getStartY() {return startY;}
  
  //sets & gets finish lcoation
  public static void setFinish(int x, int y) {finishX = x; finishY = y;}
  public static int getFinishX() {return finishX;}
  public static int getFinishY() {return finishY;}
  
  //the followint two [][] 2D arrays hold loctaions of rocktiles and hazardtiles respectively
  public static void setRock  (int x, int y, int countRock) {
      arrayRocks [countRock - 1][0] =  x;
      arrayRocks [countRock - 1][1] =  y;
  }
  public static void setHazard  (int x, int y, int countHazard) {
      arrayHazards [countHazard - 1][0] =  x;
      arrayHazards [countHazard - 1][1] =  y;
  }
  //after picking it up, changes the rocktile into a floortile
  public static void changeFloorTile (int x, int y){
      tiles[x][y] = 2;    
  }
  //after putting it down, changes the hazardtile into a stepping stone
  public static void changeRockTile (int x, int y){
      tiles[x][y] = 6;
  } 
  
}
