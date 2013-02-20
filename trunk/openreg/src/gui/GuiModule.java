package gui;

import org.eclipse.swt.widgets.Composite;

public abstract class GuiModule {

	protected String name = "[Unknown]";
	protected String info = null;
	protected Composite container;
	
	public abstract void createContent(Composite parent);
	public abstract void update(Object... parameters);
	
	public GuiModule() {
	}
	
	public GuiModule(final String name) throws Exception {
		setName(name);
	}
	
	public void setName(String name) throws Exception {
		if(name.length() == 0) {
			throw new Exception("GuiModules must have a name!");
		}
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
