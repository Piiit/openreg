package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class Teachers extends GuiModule {

	public Teachers() throws Exception {
		super("Teachers");
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createContent(Composite parent) {
		container = new Group(parent, SWT.NONE);
	}

	@Override
	public void update(Object... parameters) {
		// TODO Auto-generated method stub
		
	}

}
