/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import imageHandler.ImageEffect;

import org.junit.BeforeClass;
import org.junit.Test;

import Data.Answer;
import Data.Audio;
import Data.Defaults;
import Data.DocumentInfo;
import Data.Image;
import Data.Question;
import Data.Slideshow;
import Data.Text;
import Data.Video;
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
		
		assertEquals(4, defaults.getxAspectRatio(), 0.001);
		assertEquals(3, defaults.getyAspectRatio(), 0.001);
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
		assertTrue(currentSlideshow.getSlide(0).getAll() != null);
	}

	@Test
	public void testSlideOneListCorrectSize() {
		assertTrue(currentSlideshow.getSlide(0).getAll().size() == 25);
	}

	@Test
	public void testSlideOneTextOne() {
		Text text = (Text) currentSlideshow.getSlide(0).get(0);
		assertTrue(text.getAlignment().equals(defaultAlignment));
		assertTrue(text.getBackgroundColor().equals(defaultBackgroundColor));
		assertTrue(text.getDuration() == 1.5);
		assertTrue(text.getSourceFile() == null);
		assertTrue(text.getStartTime() == defaultStartTime);
		assertTrue(text.getXStart() == 0.5);
		assertTrue(text.getYStart() == 0.5);
		assertTrue(text.getXEnd() == -1.0);
		assertTrue(text.getYEnd() == -1.0);
		assertTrue(text.getTextFragment(0).getFont().equals("Arial"));
		assertTrue(text.getTextFragment(0).getFontColor().equals("#00112233"));
		assertTrue(text.getTextFragment(0).getFontSize() == 26);
		assertTrue(text.getTextFragment(0).getHighlightColor().equals(defaultHighlightColor));
		assertFalse(text.getTextFragment(0).isBold());
		assertFalse(text.getTextFragment(0).isItalicised());
		assertFalse(text.getTextFragment(0).isUnderlined());
		assertFalse(text.getTextFragment(0).isStrikethrough());
		assertFalse(text.getTextFragment(0).isSuperscript());
		assertFalse(text.getTextFragment(0).isSubscript());
		assertTrue(text.getTextFragment(0).getText().equals("Some Text."));
	}

	@Test
	public void testSlideOneTextTwo() {
		Text text = (Text) currentSlideshow.getSlide(0).get(1);
		assertTrue(text.getAlignment().equals(defaultAlignment));
		assertTrue(text.getBackgroundColor().equals(defaultBackgroundColor));
		assertTrue(text.getDuration() == 1.5);

		// TODO sourcefile reading
		assertTrue(text.getSourceFile().equals("test.txt"));

		assertTrue(text.getStartTime() == defaultStartTime);
		assertTrue(text.getXStart() == 0.5);
		assertTrue(text.getYStart() == 0.5);
		assertTrue(text.getXEnd() == -1.0);
		assertTrue(text.getYEnd() == -1.0);

		/* Empty text fragment list for sourcefile? */
		assertTrue(text.getTextFragments().size() == 0);
	}

	@Test
	public void testSlideOneTextThree() {
		Text text = (Text) currentSlideshow.getSlide(0).get(2);

		assertTrue(text.getAlignment().equals("right"));
		assertTrue(text.getBackgroundColor().equals("#12345678"));
		assertTrue(text.getFontColor().equals("#88776655"));
		assertTrue(text.getDuration() == 1.5);
		assertTrue(text.getSourceFile() == null);
		assertTrue(text.getStartTime() == 2.0);
		assertTrue(text.getXStart() == 0.1f);
		assertTrue(text.getYStart() == 0.1f);
		assertTrue(text.getXEnd() == 0.9f);
		assertTrue(text.getYEnd() == 0.9f);

		assertTrue(text.getTextFragment(0).getFont().equals("Arial"));
		
		assertTrue(text.getTextFragment(0).getFontColor().equals("#11223344"));
		assertTrue(text.getTextFragment(0).getFontSize() == 26);
		
		assertTrue(text.getTextFragment(0).getHighlightColor().equals("#00000000"));
		assertTrue(text.getTextFragment(0).getText().equals("Example text!"));

		assertTrue(text.getTextFragment(1).getFont().equals("Arial"));
		assertTrue(text.getTextFragment(1).getFontColor().equals("#11223344"));
		assertTrue(text.getTextFragment(1).getFontSize() == 26);
		assertTrue(text.getTextFragment(1).getHighlightColor().equals("#87654321"));
		assertTrue(text.getTextFragment(1).getText().equals("Example "));

		
		assertTrue(text.getTextFragment(3).getFont().equals("Arial"));
		assertTrue(text.getTextFragment(3).getFontColor().equals("#88776655"));
		assertTrue(text.getTextFragment(3).getFontSize() == 24);
		assertTrue(text.getTextFragment(3).getHighlightColor().equals("#55446633"));
		assertTrue(text.getTextFragment(3).getText().equals("Example text!"));

	}

	@Test
	public void testSlideOneImageOne() {
		Image image = (Image) currentSlideshow.getSlide(0).get(3);

		assertTrue(image.getSourceFile().equals("cat.jpg"));
		assertTrue(image.getXStart() == 0.5);
		assertTrue(image.getYStart() == 0.5);
		assertTrue(image.getScaleX() == 1);
		assertTrue(image.getScaleY() == 1);
		assertTrue(image.getDuration() == 1.5);
		assertTrue(image.getStartTime() == 2.0);

		assertTrue(image.getCropX1() == defaultCropx1);
		assertTrue(image.getCropX2() == defaultCropx2);
		assertTrue(image.getCropY1() == defaultCropy1);
		assertTrue(image.getCropY2() == defaultCropy2);
		assertTrue(image.getFlipHorizontal() == false);
		assertTrue(image.getFlipVertical() == false);
		assertTrue(image.getRotation() == defaultRotation);

		assertTrue(image.getImageEffects().size() == 0);
	}

	@Test
	public void testSlideOneImageTwo() {
		Image image = (Image) currentSlideshow.getSlide(0).get(4);

		assertEquals("selfie.jpg", image.getSourceFile());
		assertEquals(0.5, image.getXStart(), 0.001);
		assertEquals(0.5, image.getYStart(), 0.001);

		/* TODO scale */
		assertEquals(image.getScaleX(), 0.75f, 0.001);
		assertEquals(image.getScaleY(), 0.5f, 0.001);

		assertEquals(1.5, image.getDuration(), 0.001);
		assertEquals(2.0, image.getStartTime(), 0.001);

		assertEquals(0.1, image.getCropX1(), 0.001);
		assertEquals(0.5, image.getCropX2(), 0.001);
		assertEquals(0.1, image.getCropY1(), 0.001);
		assertEquals(0.5, image.getCropY2(), 0.001);
		assertTrue(image.getFlipHorizontal());
		assertTrue(image.getFlipVertical());
		assertEquals(45, image.getRotation(), 0.001);

		/* TODO */
		assertEquals(6, image.getImageEffects().size());

		ArrayList<ImageEffect> imageEffects = image.getImageEffects();
		assertEquals(ImageEffect.SEPIA, imageEffects.get(0));
		assertEquals(ImageEffect.BLOOM, imageEffects.get(1));
		assertEquals(ImageEffect.BLUR, imageEffects.get(2));
		assertEquals(ImageEffect.GLOW, imageEffects.get(3));
		assertEquals(ImageEffect.GRAYSCALE, imageEffects.get(4));
		assertEquals(ImageEffect.REFLECTION, imageEffects.get(5));
	}

	@Test
	public void testSlideOneAudioOne() {
		Audio audio = (Audio) currentSlideshow.getSlide(0).get(5);

		assertEquals("gavel.wav", audio.getSourceFile());
		assertEquals(2.5, audio.getStartTime(), 0.001);

		assertFalse(audio.isVisibleControlsOnly());
	}

	@Test
	public void testSlideOneAudioTwo() {
		Audio audio = (Audio) currentSlideshow.getSlide(0).get(6);

		assertEquals("clap.wav", audio.getSourceFile());
		assertEquals(0.5, audio.getXStart(), 0.001);
		assertEquals(0.5, audio.getYStart(), 0.001);
		assertEquals(0.2, audio.getWidth(), 0.001);

		assertTrue(audio.isAutoplay());
		assertTrue(audio.isVisibleControlsOnly());
		assertFalse(audio.isPlayButtonOnly());
		assertEquals(1.5, audio.getDuration(), 0.001);
		assertEquals(2.5, audio.getStartTime(), 0.001);
	}

	@Test
	public void testSlideOneVideoOne() {
		Video video = (Video) currentSlideshow.getSlide(0).get(7);

		assertEquals("avengers.mkv", video.getSourceFile());
		assertEquals(0.5, video.getXStart(), 0.001);
		assertEquals(0.5, video.getYStart(), 0.001);
	}

	@Test
	public void testSlideOneVideoTwo() {
		Video video = (Video) currentSlideshow.getSlide(0).get(8);

		assertEquals("avengers.mkv", video.getSourceFile());
		assertEquals(0.5, video.getXStart(), 0.001);
		assertEquals(0.5, video.getYStart(), 0.001);
		assertEquals(0.2, video.getWidth(), 0.001);

		assertTrue(video.isAutoplay());
		assertTrue(video.isLoop());

		assertEquals(1.5, video.getDuration(), 0.001);
		assertEquals(2.5, video.getStartTime(), 0.001);

	}
	
	@Test
	public void testSlideOneHasQuestion() {
		assertTrue(currentSlideshow.getSlide(0).containsQuestion());
	}
	
	@Test
	public void testSlideOneQuestion() {
		Question question = currentSlideshow.getSlide(0).getQuestion();
		
		assertEquals("q1", question.getId());
		assertEquals("filename", question.getLogfile());
		assertEquals(1, question.getNumberOfAnswers());
		
		Answer answer = question.getAnswers().get(0);
		assertEquals("1", answer.getId());
		assertTrue(answer.getCorrect());
		
	}

}
