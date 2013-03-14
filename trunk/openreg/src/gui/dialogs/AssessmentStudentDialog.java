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
import database.query.AssessmentStudentQuery;
import database.query.StudentQuery;
import database.query.WeightedAssessmentQuery;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AssessmentStudentDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	private Table table;
	private Combo combo;
	private Combo comboName;
	
	public AssessmentStudentDialog(Shell parent) {
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
		fd_lblDescription.bottom = new FormAttachment(combo, -6);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateSubAssessmentTable();
			}
		});
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(0, 81);
		fd_combo.right = new FormAttachment(100, -418);
		fd_combo.left = new FormAttachment(0, 10);
		combo.setLayoutData(fd_combo);
		
		table = new Table(shlDialog, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		fd_label.top = new FormAttachment(table, 57);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				AssessmentMarkDialog dialog = new AssessmentMarkDialog(shlDialog);
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					dialog.loadData(GuiTools.getIdFromCombo(combo), ti.getData(), GuiTools.getIdFromCombo(comboName));
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(shlDialog, e.getMessage());
				}
				dialog.open();
				updateSubAssessmentTable();
			}
		});
		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -101);
		fd_table.top = new FormAttachment(combo, 12);
		fd_table.right = new FormAttachment(label, 0, SWT.RIGHT);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		Label lblSubassessment = new Label(shlDialog, SWT.NONE);
		
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(450);
		tblclmnName.setText("Description");
		
		TableColumn tblclmnMark = new TableColumn(table, SWT.NONE);
		tblclmnMark.setWidth(100);
		tblclmnMark.setText("Mark");
		FormData fd_lblSubassessment = new FormData();
		fd_lblSubassessment.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		lblSubassessment.setLayoutData(fd_lblSubassessment);
		lblSubassessment.setText("Student Name");
		
		comboName = new Combo(shlDialog, SWT.READ_ONLY);
		fd_lblSubassessment.top = new FormAttachment(comboName, 3, SWT.TOP);
		comboName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateNameField(GuiTools.getIdFromCombo(comboName));
			}
		});
		FormData fd_combo_2 = new FormData();
		fd_combo_2.bottom = new FormAttachment(lblDescription, -17);
		fd_combo_2.right = new FormAttachment(combo, 0, SWT.RIGHT);
		fd_combo_2.left = new FormAttachment(0, 104);
		comboName.setLayoutData(fd_combo_2);

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
			updateNameField(null);
			updateMainAssessmentField(null);
			updateSubAssessmentTable();
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}
	
	private void updateSubAssessmentTable() {
		table.removeAll();
		Long studentId = GuiTools.getIdFromCombo(comboName);
		Long assessmentId = GuiTools.getIdFromCombo(combo);
		if(studentId == null) {
			return; //nothing selected... nothing to do!
		}
		try {
			for(Row row: AssessmentStudentQuery.getSubAssessments(assessmentId, studentId)) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(row.getValueAsLong("sub_assessment_id"));
				tableItem.setText(new String[] {
						row.getValueAsString("description"),
						row.getValueAsString("mark")
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	private void updateNameField(Long id) {
		try {
			comboName.removeAll();
			int i = 0;
			for(Row row : StudentQuery.getFullDataset()) {
				String assessmentString = row.getValueAsStringNotNull("name") + 
						" " + row.getValueAsStringNotNull("surname");
				comboName.add(assessmentString);
				comboName.setData(assessmentString, row.getValueAsLong("student_id"));
				if(row.getValueAsLong("student_id").equals(id)) {
					comboName.select(i);
				}
				i++;
			}
			if(id == null) {
				comboName.select(0);
			}
			updateMainAssessmentField(null);
			updateSubAssessmentTable();
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}
	
	private void updateMainAssessmentField(Long id) {
		try {
			combo.removeAll();
			int i = 0;
			for(Row row : AssessmentStudentQuery.getFullDataset1(GuiTools.getIdFromCombo(comboName))) {
				String assessmentString = row.getValueAsStringNotNull("assessment_description");// + 
//						" (" + row.getValueAsStringNotNull("assessment_type_description") + ")";
				combo.add(assessmentString);
				combo.setData(assessmentString, row.getValueAsLong("assessment_id"));
				if(row.getValueAsLong("assessment_id").equals(id)) {
					combo.select(i);
				}
				i++;
			}
			if(id == null) {
				combo.select(0);
			}
			updateSubAssessmentTable();
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
