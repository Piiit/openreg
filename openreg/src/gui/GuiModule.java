package gui;

import org.eclipse.swt.widgets.Composite;

public abstract class GuiModule {

	protected String info = null;
	protected Composite container;
	
	public abstract void createContent(Composite parent);
	public abstract void update();
	public abstract String getName();
	
}
