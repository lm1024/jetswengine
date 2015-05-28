package utils;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLogger {

	private static final Logger logger = Logger.getLogger(Logger.class.getName());
	private static final int LOG_SIZE = 1000000;
	private static final int LOG_ROTATION_COUNT = 1;
	
	public static void init(){
		
		Handler handler = null;
		try {
			handler = new FileHandler("test.xml", LOG_SIZE, LOG_ROTATION_COUNT);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		Logger.getLogger("").addHandler(handler);
		
	}
	
	public static void log(boolean b, String msg){
		
		Level actualLevel = b ? Level.SEVERE : Level.INFO;
		
		logger.log(actualLevel, msg);
		
	}


}
