package testMazeRunner;

import static org.junit.Assert.*;

import java.awt.Dimension;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import main.Game;
import levels.Level;
import levels.Maps;
import gfx.ImageLoader;

public class GameTestCases {

	Game g = new Game();
	
	private static ImageLoader loader = new ImageLoader();
	Level l = new Level(loader.load(Maps.lvl2));
	Level l1 = new Level(loader.load(Maps.lvl2));
	Level l2 = new Level(loader.load(Maps.finish));
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetLevel() {
		g.setPreferredSize(new Dimension(g.WIDTH * g.SCALE, g.HEIGHT * g.SCALE));
		g.run();
		Level lTest_1 = g.setLevel(2);
		Level lTest_2 = g.setLevel(2);
		Level lTest_3 = g.setLevel(6);
		// Checking set level method inside Game class!
		// We created our own equals by overriding the actual one in Level class
		assertTrue("Both Objects are equals!!" ,lTest_1.equals(lTest_2)); //Succes case
		assertFalse("Both Objects are equals!!" ,lTest_1.equals(lTest_3)); //Success case
		assertTrue("Both Objects are not equals!!" ,lTest_1.equals(lTest_3)); //Failing case
	}

}
