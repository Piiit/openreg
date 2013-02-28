package gui.modules;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;

public class TeacherClassCourseModule extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TeacherClassCourseModule(Composite parent, int style) {
		super(parent, style);
		
		Group group = new Group(this, SWT.NONE);
		group.setBounds(0, 0, 450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
