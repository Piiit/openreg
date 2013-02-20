package gui;

public enum GroupType {
	
	Administration(0), Reports(1);

	private final int index;

	GroupType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
