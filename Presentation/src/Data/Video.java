package Data;

public class Video extends SlideItem {
	
	public Video(Defaults defaults) {
		super(defaults);
	}
	
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("");
	}
	
}
