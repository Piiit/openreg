package log;
public enum LogType {

	INFORMATION(1), WARNING(2), ERROR(3);

	private final int index;

	LogType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}