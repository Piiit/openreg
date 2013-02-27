package gui;

public enum GuiModuleType {
	
	Administration(0), Reports(1);

	private final int index;

	GuiModuleType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
