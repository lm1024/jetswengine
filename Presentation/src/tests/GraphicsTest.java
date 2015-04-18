/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.junit.Before;
import org.junit.Test;

import GUI.GraphicsHandler;
import GUI.Shading;
import GUI.Shadow;

/**
 * @author tjd511
 * 
 */
public class GraphicsTest {

	public static GraphicsHandler testGraphicsHandler;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testGraphicsHandler = new GraphicsHandler(new Group());
	}

	@Test
	public void colorShapeTest() {
		final Color testColor = Color.BISQUE;

		Shape testShape = new Rectangle();
		testShape.setFill(Color.BLACK);
		Method method = null;
		try {
			method = testGraphicsHandler.getClass().getDeclaredMethod("colorShape", Shape.class, boolean.class,
					Color.class, Color.class, double.class, Shadow.class, Shading.class, Color.class);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		method.setAccessible(true);
		/*
		try {
			method.invoke(testGraphicsHandler, testShape, true, Color.BLACK, Color.BLACK, 0, Shadow.NONE, Shading.NONE,
					Color.BLACK);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
		assertTrue(testShape.getFill() == testColor);

	}
}
