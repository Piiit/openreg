package gui;

import org.eclipse.swt.widgets.Composite;

public abstract class GuiModule {

	protected Composite container;

	public abstract void createContent(Composite parent);
	public abstract void reloadData(Object filterId);
	public abstract String getName();
	public abstract String getDescription();
	
	public final void reloadData() {
		reloadData(null);
	}
	
}
