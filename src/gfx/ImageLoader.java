package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

  public BufferedImage load(String path) {
    try {
      //loads the image sprite sheet
      return ImageIO.read(getClass().getResource(path));
    } catch (IOException e) {}
    return null;
  }
}