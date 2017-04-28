package main;

import entities.Player;
import gfx.SpriteSheet;
import gfx.ImageManager;
import gfx.ImageLoader;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import levels.Level;
import levels.Maps;
import entities.Rock;



public class  Game extends Canvas implements Runnable {
  //game screen size
  public static final int WIDTH = 640;
  public static final int HEIGHT = 640;
  //Scale & size are adjustable
  public static final int SCALE = 1;
  public static final int TILESIZE = 32;
  //game running control
  public static boolean running = false;
  //sprite sheet & image manager
  private BufferedImage spriteSheet;
  private static ImageManager im;
  //our player
  private static Player player;
  public static Rock rock;
  //game level
  private static Level level;
  //game thread
  public Thread gameThread;
  //misc var
  private static ImageLoader loader = new ImageLoader();
  public static  Game game = new Game();
  // fixed final ----------------------
  
  public static void main(String[] args) {
    //Game game = new Game();
    game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
    //creates our game canvas
    JFrame frame = new JFrame("MAZE RUNNER");
    frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
    //closes game properly
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JMenuBar bar= new JMenuBar();
    frame.setLayout(null);//set layout
    JMenu file= new JMenu("Menu");
    file.setBounds(0,0,WIDTH,HEIGHT);
    
    JMenuItem newGame= new JMenuItem("New Game");
    newGame.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e ){
    
    	Game ng = new Game();
    	ng.setLevel(1);
    	ng.setPreferredSize(new Dimension(ng.WIDTH * ng.SCALE, ng.HEIGHT * ng.SCALE));
		ng.run();
		
		ng.main(args);
    	}
    });
    
    JMenuItem restartLvl = new JMenuItem("Restart Level");
    restartLvl.addActionListener(new ActionListener()// reset same lvl
    {
    	public void actionPerformed(ActionEvent e ){
    		
    		game = new Game();
    		game.setPreferredSize(new Dimension(game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE));
    		game.run();
    		game.main(args);
    	}
    });
    
    JMenuItem exit= new JMenuItem("Exit");
    exit.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e ){
    		//code for exit
    		System.out.println("Closing");
    		System.exit(0);
    	}
    });
  
  
    bar.setBounds(0, 0,WIDTH,25);//added
    bar.add(file);
   
    
    //centers game window on screen
    frame.setLocationRelativeTo(null);
    //cannot resize the screen
    frame.setResizable(false);
    game.setBounds(0,25,WIDTH, HEIGHT -25);//added
    //adds game to our game window
    frame.add(game);
    //allows us to see the game window
    bar.add(file);
    
    
    file.add(restartLvl);
    file.add(newGame);
    file.add(exit);
    frame.add(bar);
    frame.setVisible(true);
    //starts the game
    game.start();
  }
  
  //starts the game threat
  public synchronized void start() {
    if (running) return;
    running = true;
    gameThread = new Thread(this);
    gameThread.start();
  }
  
  
  
  @Override //Runs when the game runs
  public void run() {
    init();
    //variables to control updates per second
    long lastTime = System.nanoTime();
    final double amountOfTicks = 60D;
    double ns = 1000000000 / amountOfTicks;
    double delta = 0;
    //game is running
    while (running) {
      //limits updates to 60 per second (excpet renders)
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;
      if (delta >= 1) {
        tick();
        delta--;
      }
      //renders as quickly as possible
      render();
    }
    //stops thread when game ends
    stop();
  }
  

  
  //game initilization
  public void init() {
    //loads sprite sheet
    spriteSheet = loader.load("/spritesheet.png");
    SpriteSheet ss = new SpriteSheet(spriteSheet);
    im = new ImageManager(ss);
    //creates map level
    setLevel(Player.getRound());
    //centers the player on the screen
    player = new Player((WIDTH * SCALE / 2) - (TILESIZE * SCALE), (HEIGHT * SCALE / 2) - (TILESIZE * SCALE), im);
    rock = new Rock((WIDTH * SCALE / 2) - (TILESIZE * SCALE), (HEIGHT * SCALE / 2) - (TILESIZE * SCALE), im);
    //adds key controls
    this.addKeyListener(new KeyManager());
  }
  
  //closes the thread when game ends
  public synchronized void stop() {
    if (!running) return;
    running = false;
    try {
      gameThread.join();
    } catch (InterruptedException e) {}
  }
  
  //updates player
  public void tick() {player.tick();}

  //renders everything
  public void render() {
    BufferStrategy bs = this.getBufferStrategy();
    //keeps 3 frames in memory before displaying
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }
    //renders things
    Graphics g = bs.getDrawGraphics();
    if (Player.round == 1) g.setColor(Color.blue);
    else if (Player.round == 2) g.setColor(Color.green);
    else if (Player.round == 3) g.setColor(Color.yellow);
    else if (Player.round == 4) g.setColor(Color.red);
    else if (Player.round == 5) g.setColor(Color.pink);
    else g.setColor(Color.black);
    g.fillRect(0,0, WIDTH * SCALE, HEIGHT * SCALE);
    //render below here
    level.render(g);
    player.render(g);
    //only renders if player is carrying a rock
    if (Rock.getRocks() == 1)
        rock.render(g, rock.rx, rock.ry );
    //render above here
    g.dispose();
    bs.show();
  }
  
  //
  public static Level setLevel(int round) {
      int nx = (WIDTH * SCALE / 2) - (TILESIZE * SCALE);
      int ny = (WIDTH * SCALE / 2) - (TILESIZE * SCALE);

    switch (round) {
      case 1: return level = (new Level(loader.load(Maps.lvl1)));
      case 2:
        level.loadLevel(loader.load(Maps.lvl2));
        player = new Player(nx, ny, im);
        return level = new Level(loader.load(Maps.lvl2));
      case 3:
        level.loadLevel(loader.load(Maps.lvl3));
        player = new Player(nx, ny, im);
        return level = new Level(loader.load(Maps.lvl3));
      case 4:
        level.loadLevel(loader.load(Maps.lvl4));
        player = new Player(nx, ny, im);
        return level = new Level(loader.load(Maps.lvl4));
      case 5:
        level.loadLevel(loader.load(Maps.lvl5));
        player = new Player(nx, ny, im);
        return level = new Level(loader.load(Maps.lvl5));
      case 6:
        level.loadLevel(loader.load(Maps.finish));
        player = new Player(nx, ny, im);
        return level = new Level(loader.load(Maps.finish));
      case 7:
        level.loadLevel(loader.load(Maps.dead));
        player = new Player(nx, ny, im);
        return level = new Level(loader.load(Maps.dead));
      default:
        level.loadLevel(loader.load(Maps.finish));
        player = new Player(nx, ny, im);
        return level = new Level(loader.load(Maps.finish));//dummy code
    }
  }
  
  //
  public static Level getLevel() {return level;}
  public static Player getPlayer() {return player;}
  public static ImageManager getImageManager() {return im;}
  
  
}
