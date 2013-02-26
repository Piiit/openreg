package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

public abstract class GuiDialog extends Dialog {
	
	public abstract void loadData(Object data) throws Exception;
	public abstract void store() throws Exception;
	public abstract void update() throws Exception;
	public abstract void cancel();

	public GuiDialog(Shell parent) {
		super(parent, SWT.NONE);
		setText("SWT Dialog");
	}

}
