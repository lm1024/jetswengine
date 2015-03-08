/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Data.Slideshow;
import Data.Text;
import XML.ImprovedXMLReader;

/**
 * @author dk666
 * 
 */
public class XMLTest {

	private Slideshow currentSlideshow;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.currentSlideshow = new ImprovedXMLReader("pws.xml").getSlideshow();
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
		assertTrue(currentSlideshow.getInfo().getAuthor().equals("Tom Davidson"));
		assertTrue(currentSlideshow.getInfo().getVersion().equals("1.0"));
		assertTrue(currentSlideshow.getInfo().getComment().equals("this is a comment"));
		assertTrue(currentSlideshow.getInfo().getGroupID().equals("2"));
	}
	
	@Test
	public void testSlideshowDefaultSettingsExists() {
		assertTrue(currentSlideshow.getDefaults() != null);
	}
	
	@Test
	public void testSlideshowDefaultSettingsCorrectReturns() {
		assertTrue(currentSlideshow.getDefaults().getBackgroundColor().equals("#ff00ff00"));
		assertFalse(currentSlideshow.getDefaults().getFont().equals("times new roman"));
		assertTrue(currentSlideshow.getDefaults().getFont().equals("Times New Roman"));
		assertTrue(currentSlideshow.getDefaults().getFontSize() == 24);
		assertTrue(currentSlideshow.getDefaults().getFontColor().equals("#ffcccc00"));
		assertTrue(currentSlideshow.getDefaults().getGraphicColor().equals("#ffaaaa00"));
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
		assertTrue(currentSlideshow.getSlides().get(0).getAll().size() == 13);
	}
	
	@Test
	public void testSlideOneTextOne() {
		Text text = (Text)currentSlideshow.getSlides().get(0).get(0);
		assertTrue(text.getAlignment().equals("left"));
		/* Needs Finishing */
	}

}
