package gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import database.Row;
import database.query.MarkQuery;
import database.query.MarkTypeQuery;
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
	private Combo combo;
	
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
		fd_label.bottom = new FormAttachment(btnSave, -6);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(0, 340);
		fd_btnSave.right = new FormAttachment(100, -14);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlDialog, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
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
		
		combo = new Combo(shlDialog, SWT.READ_ONLY);
		FormData fd_combo = new FormData();
		fd_combo.left = new FormAttachment(0, 10);
		combo.setLayoutData(fd_combo);
		
		Label lblMinimum = new Label(shlDialog, SWT.NONE);
		FormData fd_lblMinimum = new FormData();
		fd_lblMinimum.top = new FormAttachment(combo, 10);
		fd_lblMinimum.left = new FormAttachment(label, 0, SWT.LEFT);
		lblMinimum.setLayoutData(fd_lblMinimum);
		lblMinimum.setText("Minimum >");
		
		Label lblMaximum = new Label(shlDialog, SWT.NONE);
		FormData fd_lblMaximum = new FormData();
		fd_lblMaximum.top = new FormAttachment(lblMinimum, 0, SWT.TOP);
		fd_lblMaximum.right = new FormAttachment(btnSave, 0, SWT.RIGHT);
		lblMaximum.setLayoutData(fd_lblMaximum);
		lblMaximum.setText("< Maximum");
		
		Group grpOrderOfMarks = new Group(shlDialog, SWT.NONE);
		fd_label.top = new FormAttachment(grpOrderOfMarks, 6);
		grpOrderOfMarks.setText("Order of marks...");
		grpOrderOfMarks.setLayout(new GridLayout(5, false));
		FormData fd_grpOrderOfMarks = new FormData();
		fd_grpOrderOfMarks.right = new FormAttachment(label, 0, SWT.RIGHT);
		fd_grpOrderOfMarks.bottom = new FormAttachment(100, -58);
		fd_grpOrderOfMarks.left = new FormAttachment(0, 10);
		grpOrderOfMarks.setLayoutData(fd_grpOrderOfMarks);
		
		Label label_1 = new Label(grpOrderOfMarks, SWT.NONE);
		label_1.setText("(1)");
		
		txtNewMark = new Text(grpOrderOfMarks, SWT.BORDER);
		GridData gd_txtNewMark = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtNewMark.widthHint = 146;
		txtNewMark.setLayoutData(gd_txtNewMark);
		txtNewMark.setText("New mark");
		
		Spinner spinner = new Spinner(grpOrderOfMarks, SWT.BORDER);
		spinner.setSelection(50);
		
		Link link_1 = new Link(grpOrderOfMarks, SWT.NONE);
		link_1.setText("<a>Up</a>");
		
		Link link_2 = new Link(grpOrderOfMarks, SWT.NONE);
		link_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		link_2.setText("<a>Down</a>");
		
		Spinner spinner_1 = new Spinner(shlDialog, SWT.BORDER);
		fd_grpOrderOfMarks.top = new FormAttachment(spinner_1, 11);
		FormData fd_spinner_1 = new FormData();
		fd_spinner_1.top = new FormAttachment(combo, 6);
		fd_spinner_1.left = new FormAttachment(lblMinimum, 6);
		spinner_1.setLayoutData(fd_spinner_1);
		
		Spinner spinner_2 = new Spinner(shlDialog, SWT.BORDER);
		FormData fd_spinner_2 = new FormData();
		fd_spinner_2.top = new FormAttachment(combo, 6);
		fd_spinner_2.right = new FormAttachment(lblMaximum, -2);
		spinner_2.setLayoutData(fd_spinner_2);
		spinner_2.setSelection(100);
		
		Link link = new Link(shlDialog, SWT.NONE);
		fd_combo.top = new FormAttachment(link, 3);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MarkTypeDialog dialog = new MarkTypeDialog(shlDialog);
				dialog.open();
				updateMarkTypeField();
			}
		});
		FormData fd_link = new FormData();
		fd_link.bottom = new FormAttachment(100, -351);
		fd_link.left = new FormAttachment(label, 0, SWT.LEFT);
		link.setLayoutData(fd_link);
		link.setText("Create a <a>new type</a> or choose one from the list...");
		
		Link link_3 = new Link(shlDialog, SWT.NONE);
		link_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MarkTypeDialog dialog = new MarkTypeDialog(shlDialog);
				try {
					String comboSelection = combo.getItem(combo.getSelectionIndex());
					dialog.loadData(combo.getData(comboSelection));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.open();
				updateMarkTypeField();
			}
		});
		fd_combo.right = new FormAttachment(link_3, -6);
		FormData fd_link_3 = new FormData();
		fd_link_3.top = new FormAttachment(combo, 2, SWT.TOP);
		fd_link_3.left = new FormAttachment(0, 277);
		link_3.setLayoutData(fd_link_3);
		link_3.setText("<a>Rename</a>");
		
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
//				MarkQuery.insert(row);	
			} else {
//				MarkQuery.update(loadedData.getValueAsLong("id"), row);
			}
			
			shlDialog.close();
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}
	
	private void updateMarkTypeField() {
		//TODO if a new type has been added this jumps to a wrong position...
		int selected = combo.getSelectionIndex();
		combo.removeAll();
		try {
			for(Row row : MarkTypeQuery.getFullDataset()) {
				combo.add(row.getValueAsStringNotNull("description"));
				combo.setData(row.getValueAsStringNotNull("description"), row.getValueAsLong("id"));
			}
			combo.select(selected);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		try {
			
			updateMarkTypeField();
			
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
		shlDialog.dispose();
	}
}
