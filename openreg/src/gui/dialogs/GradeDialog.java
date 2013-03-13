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
import org.eclipse.swt.widgets.TableItem;
import database.Row;
import database.query.ClassQuery;
import database.query.GradeQuery;
import database.query.MarkQuery;
import database.query.MarkTypeQuery;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class GradeDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	protected Row loadedData;
	private Combo combo;
	private Table table;
	
	public GradeDialog(Shell parent) {
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
		fd_label.bottom = new FormAttachment(btnSave, -17);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(0, 340);
		fd_btnSave.right = new FormAttachment(100, -14);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Done");
		fd_btnSave.left = new FormAttachment(0, 258);
		
		combo = new Combo(shlDialog, SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String comboSelection = combo.getItem(combo.getSelectionIndex());
				try {
					loadData(combo.getData(comboSelection));
					updateStudentsTable();
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(shlDialog, e.getMessage());
				}
			}
		});
		FormData fd_combo = new FormData();
		fd_combo.right = new FormAttachment(100, -240);
		fd_combo.left = new FormAttachment(0, 10);
		combo.setLayoutData(fd_combo);
		
		table = new Table(shlDialog, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		fd_label.top = new FormAttachment(table, 27);
		fd_combo.bottom = new FormAttachment(100, -325);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				GradeSubDialog dialog = new GradeSubDialog(shlDialog);
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					dialog.loadData(ti.getData());
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(shlDialog, e.getMessage());
				}
				dialog.open();
				updateStudentsTable();
			}
		});
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 53);
		fd_table.left = new FormAttachment(0, 10);
		fd_table.right = new FormAttachment(100, -14);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnSurname = new TableColumn(table, SWT.NONE);
		tblclmnSurname.setWidth(100);
		tblclmnSurname.setText("Surname");
		fd_table.bottom = new FormAttachment(100, -78);
		
		TableColumn tblclmnMark = new TableColumn(table, SWT.NONE);
		tblclmnMark.setWidth(50);
		tblclmnMark.setText("Mark");
		
		update();
	}

	@Override
	public void loadData(Object data) throws Exception {
		ArrayList<Row> ab = GradeQuery.getStudentDataset(data);
		if(ab.size() == 0) {
			throw new Exception("No assessment ID " + data.toString() + " found.");
		}
		loadedData = ab.get(0); 
	}

	@Override
	public void store() {
	}
	
	private void updateMarkTypeField(Object id) {

		String classString = "Show all";
		combo.add(classString);
		combo.select(combo.indexOf(classString));
		try {
			for(Row cl : ClassQuery.getFullDataset()) {
				classString = cl.getValueAsStringNotNull("level") + cl.getValueAsStringNotNull("stream");
				combo.add(classString);
				combo.setData(classString, cl.getValue("id"));
				if(id != null && id.equals(cl.getValue("id"))) {
					combo.select(combo.indexOf(classString));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		try {
			
			updateMarkTypeField(null);
			
			if (loadedData != null){
				
				shlDialog.setText("Select the student you want to grade");
				String typeString = loadedData.getValueAsStringNotNull("description");
				combo.select(combo.indexOf(typeString));
				
				updateStudentsTable();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	private void updateStudentsTable() {
		table.removeAll();
		ArrayList<Row> students = null;
		try {
			students = GradeQuery.getStudentDataset(loadedData.getValueAsLong("id"));
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
		for(Row stud : students) {
			TableItem ti = new TableItem(table, SWT.NONE);
			ti.setData(stud.getValueAsLong("id"));
			ti.setText(new String[] {
					stud.getValueAsStringNotNull("name"), 
					stud.getValueAsStringNotNull("surname"),
					stud.getValueAsStringNotNull("representation")
			});
		}
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
