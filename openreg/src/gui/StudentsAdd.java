package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;

public class StudentsAdd extends Dialog {

	protected Object result;
	protected Shell shlAddStudent;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public StudentsAdd(Shell parent, int style) {
		super(parent, style);
		setText("Add a new student");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAddStudent.open();
		shlAddStudent.layout();
		Display display = getParent().getDisplay();
		while (!shlAddStudent.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlAddStudent = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.MAX | SWT.RESIZE | SWT.APPLICATION_MODAL);
		shlAddStudent.setSize(450, 300);
		shlAddStudent.setText("Add student");
		shlAddStudent.setLayout(new GridLayout(1, false));

	}
}
