package gui.dialogs;

import java.util.ArrayList;
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
import database.query.MarkTypeQuery;
import gui.GuiDialog;
import gui.GuiTools;

public class MarkTypeDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	private Text text;
	private Row loadedData;
	
	public MarkTypeDialog(Shell parent) {
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
		shlDialog.setSize(300, 142);
		shlDialog.setText("Add a new mark type");
		shlDialog.setLayout(new FormLayout());
		
		Label lblDescription = new Label(shlDialog, SWT.NONE);
		FormData fd_lblDescription = new FormData();
		fd_lblDescription.top = new FormAttachment(0, 10);
		fd_lblDescription.left = new FormAttachment(0, 10);
		lblDescription.setLayoutData(fd_lblDescription);
		lblDescription.setText("Mark Type *");
		
		Label label = new Label(shlDialog, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		fd_label.right = new FormAttachment(100, -10);
		label.setLayoutData(fd_label);
		
		Button btnSave = new Button(shlDialog, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(label, 6);
		fd_btnSave.right = new FormAttachment(100, -10);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlDialog, SWT.NONE);
		fd_btnSave.left = new FormAttachment(btnCancel, 6);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.top = new FormAttachment(label, 6);
		fd_btnCancel.right = new FormAttachment(100, -91);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Label lblMandatoryFields = new Label(shlDialog, SWT.NONE);
		fd_btnCancel.left = new FormAttachment(lblMandatoryFields, 6);
		FormData fd_lblMandatoryFields = new FormData();
		fd_lblMandatoryFields.top = new FormAttachment(label, 9);
		fd_lblMandatoryFields.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		lblMandatoryFields.setLayoutData(fd_lblMandatoryFields);
		lblMandatoryFields.setText("* Mandatory Fields");
		
		text = new Text(shlDialog, SWT.BORDER);
		fd_label.bottom = new FormAttachment(text, 19, SWT.BOTTOM);
		fd_label.top = new FormAttachment(text, 6);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(0, 10);
		fd_text.right = new FormAttachment(100, -10);
		fd_text.top = new FormAttachment(lblDescription, 6);
		text.setLayoutData(fd_text);

		update();
	}
	
	@Override
	public void loadData(Object data) throws Exception {
		ArrayList<Row> ab = MarkTypeQuery.getDataset(data);
		if(ab.size() == 0) {
			throw new Exception("No mark type with ID " + data.toString() + " found.");
		}
		loadedData = ab.get(0); 
	}

	@Override
	public void store() {
		try {
			Row newDescription = new Row();
			newDescription.setValue("description", GuiTools.nullIfEmptyTrimmed(text.getText()));
			
			if(loadedData == null) {
				MarkTypeQuery.insert(newDescription);	
			} else {
				MarkTypeQuery.update(loadedData.getValueAsLong("id"), newDescription);
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
				text.setText(loadedData.getValueAsStringNotNull("description"));
				shlDialog.setText("Modify the mark type");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
