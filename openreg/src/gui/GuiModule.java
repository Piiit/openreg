package gui;

import org.eclipse.swt.widgets.Composite;

public abstract class GuiModule {

	protected Composite container;

	public abstract void createContent(Composite parent);
	public abstract void reloadData();
	public abstract void reloadData(Object o);
	public abstract String getName();
	public abstract String getDescription();
	
}
