/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import imageHandler.ImageEffect;

import org.junit.BeforeClass;
import org.junit.Test;

import Data.Answer;
import Data.Audio;
import Data.CommonShapes.Oval;
import Data.CommonShapes.Rectangle;
import Data.Defaults;
import Data.DocumentInfo;
import Data.Image;
import Data.OtherShapes.Arc;
import Data.OtherShapes.Arrow;
import Data.OtherShapes.Chord;
import Data.OtherShapes.Line;
import Data.OtherShapes.Polygon;
import Data.OtherShapes.Triangle;
import Data.Question;
import Data.RadialShapes.Circle;
import Data.RadialShapes.EquilateralTriangle;
import Data.RadialShapes.RegularPolygon;
import Data.RadialShapes.Square;
import Data.RadialShapes.Star;
import Data.Slide;
import Data.Slideshow;
import Data.Text;
import Data.Video;
import XML.ImprovedXMLReader;

/**
 * @author dk666, vr561, ipf501
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
		
		assertEquals(5, defaults.getxAspectRatio(), 0.001);
		assertEquals(4, defaults.getyAspectRatio(), 0.001);
	}

	@Test
	public void testSlideshowSlidesListExists() {
		assertTrue(currentSlideshow.getSlides() != null);
	}

	@Test
	public void testSlideshowSlidesListCorrectSize() {
		assertTrue(currentSlideshow.getSlides().size() == 5);
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
		assertTrue(text.getXEnd() == 1.0);
		assertTrue(text.getYEnd() == 1.0);
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
		assertTrue(text.getXEnd() == 1.0);
		assertTrue(text.getYEnd() == 1.0);
		
		assertTrue(text.getFont().equals("Arial"));
		assertTrue(text.getFontColor().equals("#00112233"));
		assertTrue(text.getFontSize() == 26);

		/* Empty text fragment list for sourcefile? */
		assertTrue(text.getTextFragments().size() == 0);
	}

	@Test
	public void testSlideOneTextThree() {
		Text text = (Text) currentSlideshow.getSlide(0).get(2);

		assertTrue(text.getXStart() == 0.1f);
		assertTrue(text.getYStart() == 0.1f);
		assertTrue(text.getXEnd() == 0.9f);
		assertTrue(text.getYEnd() == 0.9f);
		assertTrue(text.getAlignment().equals("right"));
		assertTrue(text.getBackgroundColor().equals("#12345678"));
		assertTrue(text.getFontColor().equals("#88776655"));
		assertTrue(text.getHighlightColor().equals("#55446633"));
		assertTrue(text.getDuration() == 1.5);
		assertTrue(text.getStartTime() == 2.0);
		assertTrue(text.getSourceFile() == null);
		
		assertTrue(text.getTextFragment(0).getFont().equals("Arial"));
		assertTrue(text.getTextFragment(0).getFontSize() == 26);
		assertTrue(text.getTextFragment(0).getFontColor().equals("#11223344"));
		assertTrue(text.getTextFragment(0).getText().equals("Example text!"));
		assertTrue(text.getTextFragment(0).getHighlightColor().equals("#00000000"));
		assertTrue(text.getTextFragment(0).endsWithNewline());
		assertTrue(text.getTextFragment(0).isBold());
		assertTrue(text.getTextFragment(0).isItalicised());
		assertTrue(text.getTextFragment(0).isUnderlined());
		assertTrue(text.getTextFragment(0).isStrikethrough());
		assertTrue(text.getTextFragment(0).isSuperscript());
		assertTrue(text.getTextFragment(0).isSubscript());

		assertTrue(text.getTextFragment(1).getFont().equals("Arial"));
		assertTrue(text.getTextFragment(1).getFontColor().equals("#11223344"));
		assertTrue(text.getTextFragment(1).getFontSize() == 26);
		assertTrue(text.getTextFragment(1).getHighlightColor().equals("#87654321"));
		assertTrue(text.getTextFragment(1).getText().equals("Example "));
		assertTrue(text.getTextFragment(1).endsWithNewline());
		assertTrue(text.getTextFragment(1).isBold());
		assertTrue(text.getTextFragment(1).isItalicised());
		assertTrue(text.getTextFragment(1).isUnderlined());
		assertTrue(text.getTextFragment(1).isStrikethrough());
		assertTrue(text.getTextFragment(1).isSuperscript());
		assertTrue(text.getTextFragment(1).isSubscript());
		
		assertTrue(text.getTextFragment(2).getText().equals(" text!"));
		
		assertTrue(text.getTextFragment(3).endsWithNewline());
		assertTrue(text.getTextFragment(3).isBold());
		assertTrue(text.getTextFragment(3).isItalicised());
		assertTrue(text.getTextFragment(3).isUnderlined());
		assertTrue(text.getTextFragment(3).isStrikethrough());
		assertTrue(text.getTextFragment(3).isSuperscript());
		assertTrue(text.getTextFragment(3).isSubscript());
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
	public void testSlideOneShapeOne(){
		Oval oval = (Oval)currentSlideshow.getSlide(0).get(9);
	
		assertEquals("Oval", oval.getType());
		assertEquals(0.5, oval.getXStart(), 0.001);
		assertEquals(0.5, oval.getYStart(), 0.001);
		assertEquals(0.7, oval.getXEnd(), 0.001);
		assertEquals(0.7, oval.getYEnd(), 0.001);
	
		assertTrue(oval.isSolid());
		assertEquals(oval.getGraphicColor(),"#11223344");
		assertEquals(1.5, oval.getDuration(), 0.001);
		assertEquals(oval.getShadingType(),"cyclic");
		assertTrue(oval.getShadingList().get(0).equals("#88776655"));
	}
	
	@Test 
	public void testSlideOneShapeTwo(){
		Rectangle rectangle = (Rectangle)currentSlideshow.getSlide(0).get(10);	
	
		assertEquals("Rectangle", rectangle.getType());
		assertEquals(0.5, rectangle.getXStart(), 0.001);
		assertEquals(0.5, rectangle.getYStart(), 0.001);
		assertEquals(0.7, rectangle.getXEnd(), 0.001);
		assertEquals(0.7, rectangle.getYEnd(), 0.001);
		
		assertTrue(rectangle.isSolid());
		assertEquals(rectangle.getGraphicColor(),"#11223344");
		assertEquals(1.5, rectangle.getDuration(), 0.001);
	}
	
	@Test
	public void testSlideOneShapeThree(){
		Line line = (Line)currentSlideshow.getSlide(0).get(11);	
	
		assertEquals("Line", line.getType());
		assertEquals(0.5, line.getXStart(), 0.001);
		assertEquals(0.5, line.getYStart(), 0.001);
		assertEquals(0.7, line.getXEnd(), 0.001);
		assertEquals(0.7, line.getYEnd(), 0.001);
		
		assertTrue(line.isSolid());
		assertEquals(line.getGraphicColor(),"#11223344");
		assertEquals(1.5, line.getDuration(), 0.001);
	}
	
	@Test
	public void testSlideOneShapeFour(){
		Oval oval = (Oval)currentSlideshow.getSlide(0).get(12);
		
		assertEquals(oval.getGraphicColor(),"#11223344");
		assertEquals(1.5, oval.getDuration(), 0.001);
		assertEquals(2.0, oval.getStartTime(), 0.001);
		
		assertEquals(0.5, oval.getXStart(), 0.001);
		assertEquals(0.5, oval.getYStart(), 0.001);
		assertEquals(0.7, oval.getXEnd(), 0.001);
		assertEquals(0.7, oval.getYEnd(), 0.001);
		assertTrue(oval.isSolid());
		assertEquals(oval.getOutlineColor(),"#00000000");
		assertEquals(oval.getOutlineThickness(), 1.0, 0.001);
		assertEquals(oval.getRotation(), 45, 0.001);
		assertEquals(oval.getShadow(), "heavy");
		assertEquals(oval.getShadingType(),"horizontal");
		assertTrue(oval.getShadingList().get(0).equals("#88776655"));
		assertTrue(oval.getShadingList().get(1).equals("#00ddbbcc"));
		assertTrue(oval.getShadingList().get(2).equals("#00aabbcc"));
		assertEquals(oval.getStopValuesList().get(0), 0.5, 0.001);
	}
	
	@Test
	public void testSlideOneShapeFive(){
		Rectangle rectangle = (Rectangle)currentSlideshow.getSlide(0).get(13);
		
		assertEquals(rectangle.getGraphicColor(),"#11223344");
		assertEquals(1.5, rectangle.getDuration(), 0.001);
		assertEquals(2.0, rectangle.getStartTime(), 0.001);
		
		assertEquals(0.5, rectangle.getXStart(), 0.001);
		assertEquals(0.5, rectangle.getYStart(), 0.001);
		assertEquals(0.7, rectangle.getXEnd(), 0.001);
		assertEquals(0.7, rectangle.getYEnd(), 0.001);
		assertEquals(10, rectangle.getArcWidth(), 0.001);
		assertEquals(10, rectangle.getArcHeight(), 0.001);
		assertTrue(rectangle.isSolid());
		assertEquals(rectangle.getOutlineColor(),"#00000000");
		assertEquals(rectangle.getOutlineThickness(), 1.0, 0.001);
		assertEquals(rectangle.getRotation(), 45, 0.001);
		assertEquals(rectangle.getShadingType(),"vertical");
		assertTrue(rectangle.getShadingList().get(0).equals("#88776655"));
		assertTrue(rectangle.getShadingList().get(1).equals("#00aabbcc"));
		assertEquals(rectangle.getStopValuesList().get(1), 0.1, 0.001);
		assertTrue(rectangle.getShadingList().get(2).equals("#007777cc"));
		assertEquals(rectangle.getStopValuesList().get(2), 0.9, 0.001);
		
	}
	
	@Test
	public void testSlideOneShapeSix(){
		Line line = (Line)currentSlideshow.getSlide(0).get(14);	
		
		assertEquals(line.getGraphicColor(),"#11223344");
		assertEquals(1.5, line.getDuration(), 0.001);
		assertEquals(2.0, line.getStartTime(), 0.001);
		
		assertEquals(0.5, line.getXStart(), 0.001);
		assertEquals(0.5, line.getYStart(), 0.001);
		assertEquals(0.7, line.getXEnd(), 0.001);
		assertEquals(0.7, line.getYEnd(), 0.001);
		assertEquals(line.getThickness(), 1.0, 0.001);
		
	}
	
	@Test
	public void testSlideOneShapeSeven(){
		Circle circle = (Circle)currentSlideshow.getSlide(0).get(15);	
		
		assertEquals(circle.getGraphicColor(),"#11223344");
		assertEquals(1.5, circle.getDuration(), 0.001);
		assertEquals(2.0, circle.getStartTime(), 0.001);
		
		assertEquals(0.5, circle.getXStart(), 0.001);
		assertEquals(0.5, circle.getYStart(), 0.001);
		assertEquals(10, circle.getSize(), 0.001);
		assertTrue(circle.isSolid());
		assertEquals(circle.getOutlineColor(),"#00000000");
		assertEquals(circle.getOutlineThickness(), 1.0, 0.001);
		assertEquals(circle.getRotation(), 0, 0.001);
		
		assertEquals(circle.getShadow(), "heavy");
		assertEquals(circle.getShadingType(),"cyclic");
		assertTrue(circle.getShadingList().get(0).equals("#88776655"));
		assertTrue(circle.getShadingList().get(1).equals("#00ddbbcc"));
		assertTrue(circle.getShadingList().get(2).equals("#00aabbcc"));
		
	}
	
	@Test
	public void testSlideOneShapeEight(){
		Square square = (Square)currentSlideshow.getSlide(0).get(16);	
		
		assertEquals(square.getGraphicColor(),"#11223344");
		assertEquals(1.5, square.getDuration(), 0.001);
		assertEquals(2.0, square.getStartTime(), 0.001);
		
		assertEquals(0.5, square.getXStart(), 0.001);
		assertEquals(0.5, square.getYStart(), 0.001);
		assertEquals(10, square.getSize(), 0.001);
		assertTrue(square.isSolid());
		assertEquals(10, square.getArcWidth(), 0.001);
		assertEquals(10, square.getArcHeight(), 0.001);
		assertEquals(square.getOutlineColor(),"#00000000");
		assertEquals(square.getOutlineThickness(), 1.0, 0.001);
		assertEquals(square.getRotation(), 0, 0.001);
		
	}
	
	@Test
	public void testSlideOneShapeNine(){
		Arrow arrow = (Arrow)currentSlideshow.getSlide(0).get(17);	
		
		assertEquals(arrow.getGraphicColor(),"#11223344");
		assertEquals(1.5, arrow.getDuration(), 0.001);
		assertEquals(2.0, arrow.getStartTime(), 0.001);
		
		assertEquals(0.5, arrow.getXStart(), 0.001);
		assertEquals(0.5, arrow.getYStart(), 0.001);
		assertEquals(0.7, arrow.getXEnd(), 0.001);
		assertEquals(0.7, arrow.getYEnd(), 0.001);
		assertEquals(arrow.getThickness(), 1.0, 0.001);
		
	}
	
	@Test
	public void testSlideOneShapeTen(){
		EquilateralTriangle equitriangle = (EquilateralTriangle)currentSlideshow.getSlide(0).get(18);	
		
		assertEquals(equitriangle.getGraphicColor(),"#11223344");
		assertEquals(1.5, equitriangle.getDuration(), 0.001);
		assertEquals(2.0, equitriangle.getStartTime(), 0.001);
		
		assertEquals(0.5, equitriangle.getXStart(), 0.001);
		assertEquals(0.5, equitriangle.getYStart(), 0.001);
		assertEquals(10, equitriangle.getSize(), 0.001);
		assertTrue(equitriangle.isSolid());
		assertEquals(equitriangle.getOutlineColor(),"#00000000");
		assertEquals(equitriangle.getOutlineThickness(), 1.0, 0.001);
		assertEquals(equitriangle.getRotation(), 0, 0.001);
		
	}
	
	@Test
	public void testSlideOneShapeEleven(){
		Triangle triangle = (Triangle)currentSlideshow.getSlide(0).get(19);	
		
		assertEquals(triangle.getGraphicColor(),"#11223344");
		assertEquals(1.5, triangle.getDuration(), 0.001);
		assertEquals(2.0, triangle.getStartTime(), 0.001);
		
		assertEquals(0.1, triangle.getXStart(), 0.001);
		assertEquals(0.1, triangle.getYStart(), 0.001);
		assertTrue(triangle.isSolid());
		assertEquals(triangle.getOutlineColor(),"#00000000");
		assertEquals(triangle.getOutlineThickness(), 1.0, 0.001);
		assertEquals(triangle.getRotation(), 0, 0.001);
		
		assertEquals(triangle.getxPoints().get(0), 0.1, 0.001);
		assertEquals(triangle.getyPoints().get(0), 0.5, 0.001);
		assertEquals(triangle.getxPoints().get(1), 0.25, 0.001);
		assertEquals(triangle.getyPoints().get(1), 0.6, 0.001);
		assertEquals(triangle.getxPoints().get(2), 0.2, 0.001);
		assertEquals(triangle.getyPoints().get(2), 0.7, 0.001);
	}
	
	@Test
	public void testSlideOneShapeTwelve(){
		RegularPolygon regpolygon = (RegularPolygon)currentSlideshow.getSlide(0).get(20);	
		
		assertEquals(regpolygon.getGraphicColor(),"#11223344");
		assertEquals(1.5, regpolygon.getDuration(), 0.001);
		assertEquals(2.0, regpolygon.getStartTime(), 0.001);
		
		assertEquals(0.5,regpolygon.getXStart(), 0.001);
		assertEquals(0.5, regpolygon.getYStart(), 0.001);
		assertEquals(10, regpolygon.getSize(), 0.001);
		assertEquals(5, regpolygon.getNumberOfSides(), 0.001);
		assertTrue(regpolygon.isSolid());
		assertEquals(regpolygon.getOutlineColor(),"#00000000");
		assertEquals(regpolygon.getOutlineThickness(), 1.0, 0.001);
		assertEquals(regpolygon.getRotation(), 0, 0.001);
	
	}
	
	@Test
	public void testSlideOneShapeThirteen(){
		Polygon polygon = (Polygon)currentSlideshow.getSlide(0).get(21);	
		
		assertEquals(polygon.getGraphicColor(),"#11223344");
		assertEquals(1.5, polygon.getDuration(), 0.001);
		assertEquals(2.0, polygon.getStartTime(), 0.001);
		
		assertEquals(0.1,polygon.getXStart(), 0.001);
		assertEquals(0.1, polygon.getYStart(), 0.001);
		assertTrue(polygon.isSolid());
		assertEquals(polygon.getOutlineColor(),"#00000000");
		assertEquals(polygon.getOutlineThickness(), 1.0, 0.001);
		assertEquals(polygon.getRotation(), 0, 0.001);
		
		assertEquals(polygon.getxPoints().get(0), 0.1, 0.001);
		assertEquals(polygon.getyPoints().get(0), 0.5, 0.001);
		assertEquals(polygon.getxPoints().get(1), 0.25, 0.001);
		assertEquals(polygon.getyPoints().get(1), 0.6, 0.001);
		assertEquals(polygon.getxPoints().get(2), 0.2, 0.001);
		assertEquals(polygon.getyPoints().get(2), 0.7, 0.001);
	
	}
	
	@Test
	public void testSlideOneShapeFourteen(){
		Star star = (Star)currentSlideshow.getSlide(0).get(22);	
		
		assertEquals(star.getGraphicColor(),"#11223344");
		assertEquals(1.5, star.getDuration(), 0.001);
		assertEquals(2.0, star.getStartTime(), 0.001);
		
		assertEquals(0.5,star.getXStart(), 0.001);
		assertEquals(0.5, star.getYStart(), 0.001);
		assertEquals(10, star.getSize(), 0.001);
		assertEquals(5, star.getNumberOfPoints(), 0.001);
		assertTrue(star.isSolid());
		assertEquals(star.getOutlineColor(),"#00000000");
		assertEquals(star.getOutlineThickness(), 1.0, 0.001);
		assertEquals(star.getRotation(), 0, 0.001);
	
	}
	
	@Test
	public void testSlideOneShapeFifteen(){
		Chord chord = (Chord)currentSlideshow.getSlide(0).get(23);	
		
		assertEquals(chord.getGraphicColor(),"#11223344");
		assertEquals(1.5, chord.getDuration(), 0.001);
		assertEquals(2.0, chord.getStartTime(), 0.001);
		
		assertEquals(0.5,chord.getXStart(), 0.001);
		assertEquals(0.5, chord.getYStart(), 0.001);
		assertEquals(10, chord.getWidth(), 0.001);
		assertEquals(10, chord.getHeight(), 0.001);
		assertEquals(5, chord.getArcAngle(), 0.001);
		assertEquals(10, chord.getLength(), 0.001);
		assertTrue(chord.isSolid());
		assertEquals(chord.getOutlineColor(),"#00000000");
		assertEquals(chord.getOutlineThickness(), 1.0, 0.001);
		assertEquals(chord.getRotation(), 0, 0.001);
	
	}
	
	@Test
	public void testSlideOneShapeSixteen(){
		Arc arc = (Arc)currentSlideshow.getSlide(0).get(24);	
		
		assertEquals(arc.getGraphicColor(),"#11223344");
		assertEquals(1.5, arc.getDuration(), 0.001);
		assertEquals(2.0, arc.getStartTime(), 0.001);
		
		assertEquals(0.5,arc.getXStart(), 0.001);
		assertEquals(0.5, arc.getYStart(), 0.001);
		assertEquals(10, arc.getWidth(), 0.001);
		assertEquals(10, arc.getHeight(), 0.001);
		assertEquals(5, arc.getArcAngle(), 0.001);
		assertEquals(10, arc.getLength(), 0.001);
		assertTrue(arc.isSolid());
		assertEquals(arc.getOutlineColor(),"#00000000");
		assertEquals(arc.getOutlineThickness(), 1.0, 0.001);
		assertEquals(arc.getRotation(), 0, 0.001);
	
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
	
	@Test
	public void testSlideThreeContainsTangent() {
		assertEquals(currentSlideshow.getSlide(2).getTangentSlides().size(),1);
	}
	
	@Test
	public void testTangent() {
		Slide slide = currentSlideshow.getSlide(2);
		List<Slide> tangents = slide.getTangentSlides();
		assertTrue(((Text)tangents.get(0).get(0)).getTextFragment(0).getText().equals("I'm a tangent."));
		
	}
	
	@Test
	public void testTangentInsertion() {
		Slide slide = currentSlideshow.getSlide(2);
		List<Slide> tangents = slide.getTangentSlides();
		assertTrue(((Text)tangents.get(0).get(0)).getTextFragment(0).getText().equals("I'm a tangent."));
		assertTrue(((Text)slide.get(0)).getTextFragment(0).getText().equals("2."));
		slide = currentSlideshow.getSlide(3);
		assertTrue(((Text)slide.get(0)).getTextFragment(0).getText().equals("3."));
		slide = currentSlideshow.getSlide(2);
		assertTrue(((Text)slide.get(0)).getTextFragment(0).getText().equals("2."));
		currentSlideshow.importTangents();
		slide = currentSlideshow.getSlide(3);
		assertTrue(((Text)slide.get(0)).getTextFragment(0).getText().equals("I'm a tangent."));
		slide = currentSlideshow.getSlide(4);
		assertTrue(((Text)slide.get(0)).getTextFragment(0).getText().equals("3."));
	}

}
