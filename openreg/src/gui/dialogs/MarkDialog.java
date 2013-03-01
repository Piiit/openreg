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
import database.query.MarkQuery;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.widgets.Spinner;

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
		
		Combo combo = new Combo(shlDialog, SWT.READ_ONLY);
		FormData fd_combo = new FormData();
		fd_combo.right = new FormAttachment(lblMarkTypeName, 158, SWT.RIGHT);
		fd_combo.top = new FormAttachment(0, 10);
		fd_combo.left = new FormAttachment(lblMarkTypeName, 6);
		combo.setLayoutData(fd_combo);
		
		Link link = new Link(shlDialog, SWT.NONE);
		FormData fd_link = new FormData();
		fd_link.top = new FormAttachment(lblMarkTypeName, 0, SWT.TOP);
		fd_link.left = new FormAttachment(combo, 6);
		link.setLayoutData(fd_link);
		link.setText("<a>New</a>");
		
		Scale scale = new Scale(shlDialog, SWT.NONE);
		FormData fd_scale = new FormData();
		fd_scale.top = new FormAttachment(combo, 6);
		fd_scale.right = new FormAttachment(combo, 0, SWT.RIGHT);
		scale.setLayoutData(fd_scale);
		
		Scale scale_1 = new Scale(shlDialog, SWT.NONE);
		FormData fd_scale_1 = new FormData();
		fd_scale_1.top = new FormAttachment(scale, 6);
		fd_scale_1.right = new FormAttachment(combo, 0, SWT.RIGHT);
		scale_1.setLayoutData(fd_scale_1);
		
		Label lblMinimum = new Label(shlDialog, SWT.NONE);
		fd_scale.left = new FormAttachment(lblMinimum, 8);
		FormData fd_lblMinimum = new FormData();
		fd_lblMinimum.top = new FormAttachment(lblMarkTypeName, 14);
		fd_lblMinimum.right = new FormAttachment(lblMarkTypeName, 0, SWT.RIGHT);
		lblMinimum.setLayoutData(fd_lblMinimum);
		lblMinimum.setText("Minimum:");
		
		Label lblMaximum = new Label(shlDialog, SWT.NONE);
		fd_scale_1.left = new FormAttachment(lblMaximum, 8);
		FormData fd_lblMaximum = new FormData();
		fd_lblMaximum.top = new FormAttachment(lblMinimum, 7);
		fd_lblMaximum.right = new FormAttachment(lblMarkTypeName, 0, SWT.RIGHT);
		lblMaximum.setLayoutData(fd_lblMaximum);
		lblMaximum.setText("Maximum:");
		
		Group grpOrderOfMarks = new Group(shlDialog, SWT.NONE);
		fd_label.top = new FormAttachment(grpOrderOfMarks, 6);
		grpOrderOfMarks.setText("Order of marks...");
		FormData fd_grpOrderOfMarks = new FormData();
		fd_grpOrderOfMarks.top = new FormAttachment(scale_1, 6);
		fd_grpOrderOfMarks.bottom = new FormAttachment(100, -63);
		fd_grpOrderOfMarks.right = new FormAttachment(link, 27, SWT.RIGHT);
		fd_grpOrderOfMarks.left = new FormAttachment(0, 10);
		grpOrderOfMarks.setLayoutData(fd_grpOrderOfMarks);
		
		txtNewMark = new Text(grpOrderOfMarks, SWT.BORDER);
		txtNewMark.setText("New mark");
		txtNewMark.setBounds(40, 10, 140, 19);
		
		Spinner spinner = new Spinner(grpOrderOfMarks, SWT.BORDER);
		spinner.setBounds(186, 8, 51, 22);
		
		Label label_1 = new Label(grpOrderOfMarks, SWT.NONE);
		label_1.setBounds(10, 13, 24, 14);
		label_1.setText("(1)");
		
		Link link_1 = new Link(grpOrderOfMarks, SWT.NONE);
		link_1.setBounds(243, 12, 24, 15);
		link_1.setText("<a>Up</a>");
		
		Link link_2 = new Link(grpOrderOfMarks, SWT.NONE);
		link_2.setBounds(273, 12, 45, 15);
		link_2.setText("<a>Down</a>");
		
		Spinner spinner_1 = new Spinner(shlDialog, SWT.BORDER);
		FormData fd_spinner_1 = new FormData();
		fd_spinner_1.top = new FormAttachment(link, 10);
		fd_spinner_1.left = new FormAttachment(combo, 6);
		spinner_1.setLayoutData(fd_spinner_1);
		
		Spinner spinner_2 = new Spinner(shlDialog, SWT.BORDER);
		FormData fd_spinner_2 = new FormData();
		fd_spinner_2.top = new FormAttachment(spinner_1, 0);
		fd_spinner_2.left = new FormAttachment(combo, 6);
		spinner_2.setLayoutData(fd_spinner_2);
		
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
