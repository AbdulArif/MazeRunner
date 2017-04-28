package gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
  //holds the animation sequence
  private final BufferedImage[] images;
  //controls how often the animation is updated
  private final int interval;
  //controls which frame is displayed
  private int index;
  //tick control
  private long timer;
  private long now;
  private long lastTime;
  
  //constructor
  public Animation(BufferedImage[] images, int interval) {
    this.images = images;
    this.interval = interval;
    index = 0;
    timer = 0;
    now = 0;
    lastTime = System.currentTimeMillis();
  }

  //update timer for animation
  public void tick() {
    now = System.currentTimeMillis();
    timer += now - lastTime;
    lastTime = now;
    if (timer >= interval) {
      index++;
      timer = 0;
      if (index >= images.length) index = 0;
    }
  }

  //draws the animation
  public void render(Graphics g, int x, int y, int width, int height) {
    g.drawImage(images[index], x, y, width, height, null);
  }
}