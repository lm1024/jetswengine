/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Data.Defaults;
import Data.DocumentInfo;
import Data.Slideshow;
import Data.Text;
import XML.ImprovedXMLReader;

/**
 * @author dk666
 * 
 */
public class XMLTest {

	private static final String defaultBackgroundColor = "#ff00ff00";
	private static final String defaultFontName = "Times New Roman";
	private static final double defaultFontSize = 24;
	private static final String defaultFontColor = "#ffcccc00";
	private static final String defaultGraphicColor = "#ffaaaa00";
	private static final String defaultHighlightColor = "#ffaaaa00";
	private static final float defaultStartTime = 0;
	private static final float defaultDuration = Float.MAX_VALUE;
	private static final String defaultAlignment = "left";
	private static final float defaultScale = 1;
	private static final float defaultRotation = 0;
	private static final float defaultCropx1 = 0;
	private static final float defaultCropy1 = 0;
	private static final float defaultCropx2 = 1;
	private static final float defaultCropy2 = 1;
	
	
	private static Slideshow currentSlideshow;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		currentSlideshow = new ImprovedXMLReader("testingXMLParser.xml").getSlideshow();
	}

	@Test
	public void testSlideshowExists() {
		assertTrue(currentSlideshow != null);
	}
	
	@Test
	public void testSlideshowCorrectType() {
		assertTrue(currentSlideshow instanceof Slideshow);
	}
	

	@Test
	public void testSlideshowDocInfoExists() {
		assertTrue(currentSlideshow.getInfo() != null);
	}
	
	@Test
	public void testSlideshowDocInfoCorrectReturns() {
		DocumentInfo info = currentSlideshow.getInfo();
		assertTrue(info.getAuthor().equals("Tom Davidson"));
		assertTrue(info.getVersion().equals("1.0"));
		assertTrue(info.getComment().equals("this is a comment"));
		assertTrue(info.getGroupID().equals("2"));
	}
	
	@Test
	public void testSlideshowDefaultSettingsExists() {
		assertTrue(currentSlideshow.getDefaults() != null);
	}
	
	@Test
	public void testSlideshowDefaultSettingsCorrectReturns() {
		Defaults defaults = currentSlideshow.getDefaults();
		assertTrue(defaults.getBackgroundColor().equals(defaultBackgroundColor));
		assertFalse(defaults.getFont().equals("times new roman"));
		assertTrue(defaults.getFont().equals(defaultFontName));
		assertTrue(defaults.getFontSize() == defaultFontSize);
		assertTrue(defaults.getFontColor().equals(defaultFontColor));
		assertTrue(defaults.getGraphicColor().equals(defaultGraphicColor));
		assertTrue(defaults.getHighlightColor().equals(defaultHighlightColor));
		assertTrue(defaults.getStartTime() == defaultStartTime);
		assertTrue(defaults.getDuration() == defaultDuration);
		assertTrue(defaults.getAlignment().equals(defaultAlignment));
		assertTrue(defaults.getScale() == defaultScale);
		assertTrue(defaults.getRotation() == defaultRotation);
		assertTrue(defaults.getCropX1() == defaultCropx1);
		assertTrue(defaults.getCropY1() == defaultCropy1);
		assertTrue(defaults.getCropX2() == defaultCropx2);
		assertTrue(defaults.getCropY2() == defaultCropy2);
	}
	
	@Test
	public void testSlideshowSlidesListExists() {
		assertTrue(currentSlideshow.getSlides() != null);
	}
	
	@Test
	public void testSlideshowSlidesListCorrectSize() {
		assertTrue(currentSlideshow.getSlides().size() == 1);
	}
	
	@Test
	public void testSlideshowSlideOneContainsList() {
		assertTrue(currentSlideshow.getSlides().get(0).getAll() != null);
	}

	@Test
	public void testSlideOneListCorrectSize() {
		assertTrue(currentSlideshow.getSlide(0).getAll().size() == 24);
	}
	
	@Test
	public void testSlideOneTextOne() {
		Text text = (Text)currentSlideshow.getSlides().get(0).get(0);
		assertTrue(text.getAlignment().equals(defaultAlignment));
		assertTrue(text.getBackgroundColor().equals(defaultBackgroundColor));
		assertTrue(text.getDuration() == 1.5);
		assertTrue(text.getTextFragment(0).getFont().equals("Arial"));
		assertTrue(text.getTextFragment(0).getFontColor().equals("#00112233"));
		assertTrue(text.getTextFragment(0).getFontSize() == 26);
		assertTrue(text.getTextFragment(0).getHighlightColor().equals(defaultHighlightColor));
		assertTrue(text.getSourceFile() == null);
		assertTrue(text.getStartTime() == defaultStartTime);
		assertTrue(text.getXStart() == 0.5);
		assertTrue(text.getYStart() == 0.5);
		assertTrue(text.getTextFragment(0).getText().equals("Some Text."));
		/* Needs Finishing */
	}

}
