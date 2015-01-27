package tjd511;

import java.util.List;

public class PresProgram {

	static List<VideoFile> videoList;
	
	public static void main(String[] args) {
		XMLReader reader = new XMLReader();
		videoList = reader.getList("videoList.xml");
		System.out.println("Got the list.");
		System.out.println(videoList);
		System.out.println(videoList.get(1).getTitle());
		
	}
	
}
