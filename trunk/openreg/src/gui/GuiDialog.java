package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class GuiDialog extends Dialog {
	
	protected GuiModule parentModule;
	
	public abstract void loadData(Object data);
	public abstract void store();
	public abstract void update();

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GuiDialog(Shell parent, GuiModule parentModule) {
		super(parent, SWT.NONE);
		setText("SWT Dialog");
		this.parentModule = parentModule;
	}

}
