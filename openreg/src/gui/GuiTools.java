package gui;

public class GuiTools {

	public static String nullIfEmptyTrimmed(String text) {
		if(text == null || text.trim().length() == 0) {
			return null;
		}
		return text.trim();
	}
	
	public static String nullIfEmpty(String text) {
		if(text == null || text.trim().length() == 0) {
			return null;
		}
		return text;
	}
	
}
