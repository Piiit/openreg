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
import org.eclipse.swt.widgets.TableItem;

import database.Row;
import database.query.AssessmentQuery;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Link;

public class AssessmentBundleDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	private Table table;
	private Combo combo;
	private Combo combo_1;
	
	public AssessmentBundleDialog(Shell parent) {
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
		shlDialog.setSize(800, 500);
		shlDialog.setText("Combine assessments");
		shlDialog.setLayout(new FormLayout());
		
		Label lblDescription = new Label(shlDialog, SWT.NONE);
		FormData fd_lblDescription = new FormData();
		fd_lblDescription.top = new FormAttachment(0, 10);
		fd_lblDescription.left = new FormAttachment(0, 10);
		lblDescription.setLayoutData(fd_lblDescription);
		lblDescription.setText("Select a main assessment *");
		
		Label label = new Label(shlDialog, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 10);
		fd_label.right = new FormAttachment(100, -10);
		label.setLayoutData(fd_label);
		
		Button btnOk = new Button(shlDialog, SWT.NONE);
		fd_label.bottom = new FormAttachment(btnOk, -1);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(0, 442);
		fd_btnSave.right = new FormAttachment(100, -10);
		fd_btnSave.left = new FormAttachment(0, 709);
		btnOk.setLayoutData(fd_btnSave);
		btnOk.setText("OK");
		
		Label lblMandatoryFields = new Label(shlDialog, SWT.NONE);
		FormData fd_lblMandatoryFields = new FormData();
		fd_lblMandatoryFields.bottom = new FormAttachment(100, -10);
		fd_lblMandatoryFields.left = new FormAttachment(0, 10);
		lblMandatoryFields.setLayoutData(fd_lblMandatoryFields);
		lblMandatoryFields.setText("* Mandatory Fields");
		
		combo = new Combo(shlDialog, SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateAncestorField();
				updateSubAssessmentTable();
			}
		});
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(lblDescription, 6);
		fd_combo.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		fd_combo.right = new FormAttachment(0, 410);
		combo.setLayoutData(fd_combo);
		
		table = new Table(shlDialog, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		FormData fd_table = new FormData();
		fd_table.right = new FormAttachment(label, 0, SWT.RIGHT);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		Label lblSubassessment = new Label(shlDialog, SWT.NONE);
		fd_table.bottom = new FormAttachment(lblSubassessment, 292, SWT.BOTTOM);
		fd_table.top = new FormAttachment(lblSubassessment, 6);
		
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(349);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(226);
		tblclmnType.setText("Type");
		
		TableColumn tblclmnWeight = new TableColumn(table, SWT.NONE);
		tblclmnWeight.setWidth(100);
		tblclmnWeight.setText("Weight");
		FormData fd_lblSubassessment = new FormData();
		fd_lblSubassessment.bottom = new FormAttachment(100, -393);
		fd_lblSubassessment.left = new FormAttachment(0, 10);
		lblSubassessment.setLayoutData(fd_lblSubassessment);
		lblSubassessment.setText("Sub-assessment");
		
		Link link = new Link(shlDialog, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AssessmentDialog dialog = new AssessmentDialog(shlDialog);
				dialog.open();
				updateMainAssessmentField(null);
			}
		});
		FormData fd_link = new FormData();
		fd_link.top = new FormAttachment(lblDescription, 0, SWT.TOP);
		fd_link.left = new FormAttachment(lblDescription, 6);
		link.setLayoutData(fd_link);
		link.setText("<a>New</a>");
		
		Link link_1 = new Link(shlDialog, SWT.NONE);
		link_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AssessmentDialog dialog = new AssessmentDialog(shlDialog);
				dialog.open();
				updateSubAssessmentTable();
			}
		});
		fd_label.top = new FormAttachment(link_1, 36);
		FormData fd_link_1 = new FormData();
		fd_link_1.top = new FormAttachment(table, 6);
		fd_link_1.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		link_1.setLayoutData(fd_link_1);
		link_1.setText("<a>Add</a>");
		
		Link link_2 = new Link(shlDialog, SWT.NONE);
		FormData fd_link_2 = new FormData();
		fd_link_2.top = new FormAttachment(table, 6);
		link_2.setLayoutData(fd_link_2);
		link_2.setText("<a>Remove</a>");
		
		Link link_3 = new Link(shlDialog, SWT.NONE);
		fd_link_2.left = new FormAttachment(link_3, 6);
		FormData fd_link_3 = new FormData();
		fd_link_3.bottom = new FormAttachment(link_1, 0, SWT.BOTTOM);
		fd_link_3.left = new FormAttachment(link_1, 6);
		link_3.setLayoutData(fd_link_3);
		link_3.setText("<a>New</a>");
		
		combo_1 = new Combo(shlDialog, SWT.READ_ONLY);
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String comboSelection = null;
				try {
					comboSelection = combo_1.getItem(combo_1.getSelectionIndex());
				} catch (Exception e) {
					return; //nothing selected... nothing to do!
				}
				Long id = (Long)combo_1.getData(comboSelection);
				updateMainAssessmentField(id);
				updateAncestorField();
				updateSubAssessmentTable();
			}
		});
		FormData fd_combo_1 = new FormData();
		fd_combo_1.left = new FormAttachment(label, -295);
		fd_combo_1.top = new FormAttachment(0, 31);
		fd_combo_1.right = new FormAttachment(100, -10);
		combo_1.setLayoutData(fd_combo_1);
		
		Label lblSelectHierarch = new Label(shlDialog, SWT.NONE);
		FormData fd_lblSelectHierarch = new FormData();
		fd_lblSelectHierarch.bottom = new FormAttachment(lblDescription, 0, SWT.BOTTOM);
		fd_lblSelectHierarch.left = new FormAttachment(combo_1, 0, SWT.LEFT);
		lblSelectHierarch.setLayoutData(fd_lblSelectHierarch);
		lblSelectHierarch.setText("This assessment belongs to...");
		
		Label lblChooseOneTo = new Label(shlDialog, SWT.NONE);
		FormData fd_lblChooseOneTo = new FormData();
		fd_lblChooseOneTo.top = new FormAttachment(combo_1, 6);
		fd_lblChooseOneTo.left = new FormAttachment(combo_1, 0, SWT.LEFT);
		lblChooseOneTo.setLayoutData(fd_lblChooseOneTo);
		lblChooseOneTo.setText("(Hint: Choose one to edit it\u2026)");

		update();
	}
	
	@Override
	public void loadData(Object data) throws Exception {
	}

	@Override
	public void store() {
	}

	@Override
	public void update() {
		try {
			updateMainAssessmentField(null);
			updateAncestorField();
			updateSubAssessmentTable();
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	private void updateSubAssessmentTable() {
		table.removeAll();
		String comboSelection = null;
		try {
			comboSelection = combo.getItem(combo.getSelectionIndex());
		} catch (Exception e) {
			return; //nothing selected... nothing to do!
		}
		try {
			for(Row row: AssessmentQuery.getSubAssessments(combo.getData(comboSelection))) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(row.getValueAsLong("assessment_id"));
				tableItem.setText(new String[] {
						row.getValueAsString("description"),
						row.getValueAsString("assessment_type_description"),
						row.getValueAsString("weight")
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	private void updateAncestorField() {
		combo_1.removeAll();
		String comboSelection = null;
		try {
			comboSelection = combo.getItem(combo.getSelectionIndex());
		} catch (Exception e) {
			return; //nothing selected... nothing to do!
		}
		try {
			for(Row row : AssessmentQuery.getAncestors(combo.getData(comboSelection))) {
				String assessmentString = row.getValueAsStringNotNull("description");
				combo_1.add(assessmentString);
				combo_1.setData(assessmentString, row.getValueAsLong("main_assessment_id"));
			}
			combo_1.select(0);
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	private void updateMainAssessmentField(Long id) {
		try {
			combo.removeAll();
			int i = 0;
			for(Row row : AssessmentQuery.getFullDataset()) {
				i++;
				String assessmentString = row.getValueAsStringNotNull("assessment_description") + 
						" (" + row.getValueAsStringNotNull("assessment_type_description") + ")";
				combo.add(assessmentString);
				combo.setData(assessmentString, row.getValueAsLong("assessment_id"));
				if(row.getValueAsLong("assessment_id") == id) {
					combo.select(i);
				}
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
