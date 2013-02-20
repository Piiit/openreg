package gui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public abstract class GuiModule {

	protected String name;
	protected String info = null;
	protected Group container;
	
	public GuiModule(final String name) throws Exception {
		if(name.length() == 0) {
			throw new Exception("GuiModules must have a name!");
		}
		this.name = name;
	}
	
	public abstract void createContent(Composite parent);
	public abstract void update(Object... parameters);
	
	public void setVisible(boolean visible) {
		if(this.container == null || !(this.container instanceof Group)) {
			throw new NullPointerException("The module " + name + " has no valid container!");
		}
		this.container.setVisible(visible);
	}

	public String getName() {
		return name;
	}

	public Group getContainer() {
		return container;
	}

}
