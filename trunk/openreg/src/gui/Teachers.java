package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class Teachers extends GuiModule {

	public Teachers(String name, GroupType groupType) throws Exception {
		super(name, groupType);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void show(Composite parent) {
		container = new Group(parent, SWT.NONE);
	}

	@Override
	public void update(Object... parameters) {
		// TODO Auto-generated method stub
		
	}

}
