package gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import database.Row;
import database.query.MarkQuery;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class MarkDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	protected Row loadedData;
	private Text txtNewMark;
	
	public MarkDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlDialog.open();
		shlDialog.layout();
		Display display = getParent().getDisplay();
		while (!shlDialog.isDisposed()) {
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
		shlDialog = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlDialog.setSize(346, 400);
		shlDialog.setText("Add a new mark");
		shlDialog.setLayout(new FormLayout());
		
		Label label = new Label(shlDialog, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 10);
		fd_label.right = new FormAttachment(100, -10);
		label.setLayoutData(fd_label);
		
		Button btnSave = new Button(shlDialog, SWT.NONE);
		fd_label.bottom = new FormAttachment(100, -44);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(label, 6);
		fd_btnSave.right = new FormAttachment(100, -14);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlDialog, SWT.NONE);
		fd_btnSave.left = new FormAttachment(0, 258);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.top = new FormAttachment(btnSave, 0, SWT.TOP);
		fd_btnCancel.right = new FormAttachment(btnSave, -6);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Label lblMandatoryFields = new Label(shlDialog, SWT.NONE);
		FormData fd_lblMandatoryFields = new FormData();
		fd_lblMandatoryFields.top = new FormAttachment(label, 6);
		fd_lblMandatoryFields.left = new FormAttachment(label, 0, SWT.LEFT);
		lblMandatoryFields.setLayoutData(fd_lblMandatoryFields);
		lblMandatoryFields.setText("* Mandatory Fields");
		
		Label lblMarkTypeName = new Label(shlDialog, SWT.NONE);
		FormData fd_lblMarkTypeName = new FormData();
		fd_lblMarkTypeName.top = new FormAttachment(0, 10);
		fd_lblMarkTypeName.left = new FormAttachment(label, 0, SWT.LEFT);
		lblMarkTypeName.setLayoutData(fd_lblMarkTypeName);
		lblMarkTypeName.setText("Mark Type Name:");
		
		Combo combo = new Combo(shlDialog, SWT.NONE);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(0, 10);
		fd_combo.left = new FormAttachment(lblMarkTypeName, 6);
		combo.setLayoutData(fd_combo);
		
		Label lblMinimum = new Label(shlDialog, SWT.NONE);
		FormData fd_lblMinimum = new FormData();
		fd_lblMinimum.top = new FormAttachment(lblMarkTypeName, 14);
		fd_lblMinimum.right = new FormAttachment(lblMarkTypeName, 0, SWT.RIGHT);
		lblMinimum.setLayoutData(fd_lblMinimum);
		lblMinimum.setText("Minimum:");
		
		Label lblMaximum = new Label(shlDialog, SWT.NONE);
		FormData fd_lblMaximum = new FormData();
		fd_lblMaximum.top = new FormAttachment(lblMinimum, 7);
		fd_lblMaximum.right = new FormAttachment(lblMarkTypeName, 0, SWT.RIGHT);
		lblMaximum.setLayoutData(fd_lblMaximum);
		lblMaximum.setText("Maximum:");
		
		Group grpOrderOfMarks = new Group(shlDialog, SWT.NONE);
		fd_combo.right = new FormAttachment(grpOrderOfMarks, 0, SWT.RIGHT);
		fd_label.top = new FormAttachment(grpOrderOfMarks, 6);
		grpOrderOfMarks.setText("Order of marks...");
		grpOrderOfMarks.setLayout(new GridLayout(5, false));
		FormData fd_grpOrderOfMarks = new FormData();
		fd_grpOrderOfMarks.right = new FormAttachment(100, -18);
		fd_grpOrderOfMarks.left = new FormAttachment(0, 10);
		fd_grpOrderOfMarks.bottom = new FormAttachment(100, -63);
		grpOrderOfMarks.setLayoutData(fd_grpOrderOfMarks);
		
		Label label_1 = new Label(grpOrderOfMarks, SWT.NONE);
		label_1.setText("(1)");
		
		txtNewMark = new Text(grpOrderOfMarks, SWT.BORDER);
		GridData gd_txtNewMark = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtNewMark.widthHint = 141;
		txtNewMark.setLayoutData(gd_txtNewMark);
		txtNewMark.setText("New mark");
		
		Spinner spinner = new Spinner(grpOrderOfMarks, SWT.BORDER);
		spinner.setSelection(50);
		
		Link link_1 = new Link(grpOrderOfMarks, SWT.NONE);
		link_1.setText("<a>Up</a>");
		
		Link link_2 = new Link(grpOrderOfMarks, SWT.NONE);
		link_2.setText("<a>Down</a>");
		
		Spinner spinner_1 = new Spinner(shlDialog, SWT.BORDER);
		FormData fd_spinner_1 = new FormData();
		fd_spinner_1.top = new FormAttachment(combo, 3);
		fd_spinner_1.left = new FormAttachment(lblMinimum, 6);
		spinner_1.setLayoutData(fd_spinner_1);
		
		Spinner spinner_2 = new Spinner(shlDialog, SWT.BORDER);
		fd_grpOrderOfMarks.top = new FormAttachment(spinner_2, 1);
		FormData fd_spinner_2 = new FormData();
		fd_spinner_2.top = new FormAttachment(spinner_1, 0);
		fd_spinner_2.left = new FormAttachment(lblMaximum, 6);
		spinner_2.setLayoutData(fd_spinner_2);
		spinner_2.setSelection(100);
		
		update();
	}


	@Override
	public void loadData(Object data) throws Exception {
	}

	@Override
	public void store() {
		try {
			Row row = new Row();
//			row.setValue("description", GuiTools.nullIfEmptyTrimmed(text.getText()));
			
			if(loadedData == null) {
				MarkQuery.insert(row);	
			} else {
				MarkQuery.update(loadedData.getValueAsLong("id"), row);
			}
			
			shlDialog.close();
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	@Override
	public void update() {
		try {
			if (loadedData != null){
//				text.setText(loadedData.getValueAsStringNotNull("description"));
				shlDialog.setText("Modify a mark or mark type");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
