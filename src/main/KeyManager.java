//Controls for the game.  Can use WSAD or arrow keys to move
package main;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

  @Override
  public void keyTyped(KeyEvent e) {  }

  @Override
  public void keyPressed(KeyEvent e) {
  if (e.getKeyCode() == VK_UP || e.getKeyCode() == VK_W) {Game.getPlayer().up = true;}
  if (e.getKeyCode() == VK_DOWN || e.getKeyCode() == VK_S) {Game.getPlayer().dn = true;}
  if (e.getKeyCode() == VK_LEFT || e.getKeyCode() == VK_A) {Game.getPlayer().lt = true;}
  if (e.getKeyCode() == VK_RIGHT || e.getKeyCode() == VK_D) {Game.getPlayer().rt = true;}
  }

  @Override
  public void keyReleased(KeyEvent e) {
  if (e.getKeyCode() == VK_UP || e.getKeyCode() == VK_W) {Game.getPlayer().up = false;}
  if (e.getKeyCode() == VK_DOWN || e.getKeyCode() == VK_S) {Game.getPlayer().dn = false;}
  if (e.getKeyCode() == VK_LEFT || e.getKeyCode() == VK_A) {Game.getPlayer().lt = false;}
  if (e.getKeyCode() == VK_RIGHT || e.getKeyCode() == VK_D) {Game.getPlayer().rt = false;}
  }
}