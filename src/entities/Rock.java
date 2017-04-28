/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import gfx.ImageManager;
import main.Game;
import java.awt.Graphics;
import levels.Level;



public class Rock {
  public final int rx;
  public final int ry;
  private final ImageManager im;
  public static int numrocks;
 
  
  public Rock(int rx, int ry, ImageManager im) {
  this.rx = 0;
  this.ry = 0;
  this.im = im;
    }
  
  public void render(Graphics g, int x, int y) {
    g.drawImage(im.rock, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
  }
  
  public static void incRocks(int x, int y, int posrock) {
      numrocks = 1;
      //changes position of the rock so you can't pick it up again
      Level.arrayRocks [posrock][0] = 0;
      Level.arrayRocks [posrock][1] = 0;
  }
  public static void decRocks() {
      numrocks = 0; 
  }
  public static int getRocks() {return numrocks;}
}  
