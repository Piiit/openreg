package gui;

import org.eclipse.swt.widgets.Composite;
import database.DatabaseView;

public abstract class GuiModule {

	protected GuiModule guiModule; 
	protected Composite container;
	protected DatabaseView view;
	
	public GuiModule() {
		guiModule = this;
	}
	
	public abstract void createContent(Composite parent);
	public abstract void createView();
	public abstract void reloadData();
	public abstract String getName();
	public abstract String getDescription();
	
}
