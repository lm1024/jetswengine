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
		assertTrue(currentSlideshow.getSlides().size() == 2);
	}
	
	@Test
	public void testSlideshowSlideOneContainsAllLists() {
		assertTrue(currentSlideshow.getSlides().get(0).getAudiosList() != null);
		assertTrue(currentSlideshow.getSlides().get(0).getVideosList() != null);
		assertTrue(currentSlideshow.getSlides().get(0).getImagesList() != null);
		assertTrue(currentSlideshow.getSlides().get(0).getGraphicsList() != null);
		assertTrue(currentSlideshow.getSlides().get(0).getTextList() != null);
	}
	
	@Test
	public void testSlideshowSlideTwoContainsAllLists() {
		assertTrue(currentSlideshow.getSlides().get(1).getAudiosList() != null);
		assertTrue(currentSlideshow.getSlides().get(1).getVideosList() != null);
		assertTrue(currentSlideshow.getSlides().get(1).getImagesList() != null);
		assertTrue(currentSlideshow.getSlides().get(1).getGraphicsList() != null);
		assertTrue(currentSlideshow.getSlides().get(1).getTextList() != null);
	}
	
	@Test
	public void testSlideOneTextListCorrectSize() {
		assertTrue(currentSlideshow.getSlides().get(1).getTextList().size() == 3);
	}
	
	@Test
	public void testSlideOneGraphicsListCorrectSize() {
		assertTrue(currentSlideshow.getSlides().get(1).getGraphicsList().size() == 6);
	}
	
	@Test
	public void testSlideOneImagesListCorrectSize() {
		assertTrue(currentSlideshow.getSlides().get(1).getImagesList().size() == 2);
	}
	
	@Test
	public void testSlideOneAudiosListCorrectSize() {
		assertTrue(currentSlideshow.getSlides().get(1).getAudiosList().size() == 1);
	}
	
	@Test
	public void testSlideOneVideosListCorrectSize() {
		assertTrue(currentSlideshow.getSlides().get(1).getVideosList().size() == 1);
	}
	
	@Test
	public void testSlideOneTextOne() {
		Text text = currentSlideshow.getSlides().get(1).getTextList().get(0);
		System.out.println(" HAHAHSHSA " + currentSlideshow.getSlide(1).getImage(1).getSourceFile());
		assertTrue(text.getAlignment().equals("none"));
		/* Needs Finishing */
		assertTrue(currentSlideshow.getSlide(1).getImage(1).getSourceFile() != null);
	}

}
