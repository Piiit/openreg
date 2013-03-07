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
import database.query.AbilityDescriptionQuery;
import database.query.CourseQuery;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Spinner;

public class CourseDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	private Text text;
	private Row loadedData;
	private Text textCP;
	
	public CourseDialog(Shell parent) {
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
		shlDialog.setSize(300, 200);
		shlDialog.setText("Add a new course");
		shlDialog.setLayout(new FormLayout());
		
		Label lblDescription = new Label(shlDialog, SWT.NONE);
		FormData fd_lblDescription = new FormData();
		fd_lblDescription.top = new FormAttachment(0, 10);
		fd_lblDescription.left = new FormAttachment(0, 10);
		lblDescription.setLayoutData(fd_lblDescription);
		lblDescription.setText("Name *");
		
		Label label = new Label(shlDialog, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		label.setLayoutData(fd_label);
		
		Button btnSave = new Button(shlDialog, SWT.NONE);
		fd_label.bottom = new FormAttachment(btnSave, -6);
		fd_label.right = new FormAttachment(btnSave, 0, SWT.RIGHT);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.right = new FormAttachment(100, -10);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlDialog, SWT.NONE);
		fd_btnSave.top = new FormAttachment(btnCancel, 0, SWT.TOP);
		fd_btnSave.left = new FormAttachment(btnCancel, 6);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.bottom = new FormAttachment(100, -10);
		fd_btnCancel.right = new FormAttachment(100, -91);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Label lblMandatoryFields = new Label(shlDialog, SWT.NONE);
		fd_btnCancel.left = new FormAttachment(lblMandatoryFields, 6);
		FormData fd_lblMandatoryFields = new FormData();
		fd_lblMandatoryFields.bottom = new FormAttachment(100, -10);
		fd_lblMandatoryFields.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		lblMandatoryFields.setLayoutData(fd_lblMandatoryFields);
		lblMandatoryFields.setText("* Mandatory Fields");
		
		text = new Text(shlDialog, SWT.BORDER);
		fd_label.top = new FormAttachment(text, 72);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(0, 10);
		fd_text.right = new FormAttachment(100, -10);
		fd_text.top = new FormAttachment(lblDescription, 6);
		text.setLayoutData(fd_text);
		
		Label lblCreditPoints = new Label(shlDialog, SWT.NONE);
		FormData fd_lblCreditPoints = new FormData();
		fd_lblCreditPoints.top = new FormAttachment(text, 6);
		fd_lblCreditPoints.left = new FormAttachment(0, 10);
		lblCreditPoints.setLayoutData(fd_lblCreditPoints);
		lblCreditPoints.setText("Credit Points");
		
		textCP = new Text(shlDialog, SWT.BORDER);
		FormData fd_textCP = new FormData();
		fd_textCP.right = new FormAttachment(lblMandatoryFields, 0, SWT.RIGHT);
		fd_textCP.top = new FormAttachment(lblCreditPoints, 6);
		fd_textCP.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		textCP.setLayoutData(fd_textCP);

		update();
	}
	
	@Override
	public void loadData(Object data) throws Exception {
		ArrayList<Row> ab = CourseQuery.getDataset(data);
		if(ab.size() == 0) {
			throw new Exception("No course with ID " + data.toString() + " found.");
		}
		loadedData = ab.get(0); 
	}

	@Override
	public void store() {
		try {
			Row course = new Row();
			course.setValue("name", GuiTools.nullIfEmptyTrimmed(text.getText()));
			String cp = GuiTools.nullIfEmptyTrimmed(textCP.getText());
			course.setValue("credit_points", (cp == null ? null : Integer.parseInt(cp)));
			
			if(loadedData == null) {
				CourseQuery.insert(course);	
			} else {
				CourseQuery.update(loadedData.getValueAsLong("id"), course);
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
				text.setText(loadedData.getValueAsStringNotNull("name"));
				textCP.setText(loadedData.getValueAsStringNotNull("credit_points"));
				shlDialog.setText("Modify a course");
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
