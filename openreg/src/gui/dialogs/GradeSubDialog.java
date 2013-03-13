package gui.dialogs;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
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
import org.eclipse.swt.widgets.Text;

import database.Row;
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

public class GradeSubDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	protected Row loadedData;
	private Table table;
	
	public GradeSubDialog(Shell parent) {
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
		fd_label.right = new FormAttachment(100, -10);
		label.setLayoutData(fd_label);
		
		Button btnSave = new Button(shlDialog, SWT.NONE);
		fd_label.bottom = new FormAttachment(100, -59);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(label, 6);
		fd_btnSave.right = new FormAttachment(100, -10);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Done");
		fd_btnSave.left = new FormAttachment(0, 262);
		
		table = new Table(shlDialog, SWT.BORDER | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		fd_label.top = new FormAttachment(table, 6);
		fd_label.left = new FormAttachment(table, 0, SWT.LEFT);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				MarkDialog dialog = new MarkDialog(shlDialog);
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					dialog.loadData(ti.getData());
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(shlDialog, e.getMessage());
				}
				dialog.open();
				updateSubGradeTable();
			}
		});
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		fd_table.right = new FormAttachment(100, -14);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnMark = new TableColumn(table, SWT.NONE);
		tblclmnMark.setWidth(100);
		tblclmnMark.setText("Description");
		
		TableColumn tblclmnBound = new TableColumn(table, SWT.NONE);
		tblclmnBound.setWidth(60);
		tblclmnBound.setText("Differentiated");
		fd_table.bottom = new FormAttachment(100, -78);
		
		TableColumn tblclmnPoints = new TableColumn(table, SWT.NONE);
		tblclmnPoints.setWidth(100);
		tblclmnPoints.setText("Mark");
		
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
	}
	

	@Override
	public void update() {
		try {
			
			
			if (loadedData != null){
				
				shlDialog.setText("Enter Grades for Sub Assessments");
				updateSubGradeTable();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	private void updateSubGradeTable() {
		
		table.removeAll();
		ArrayList<Row> sub_assessments = null;
		try {
			sub_assessments = GradeQuery.getSubAssessmentDataset(loadedData.getValueAsLong("id"));
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
		for(Row sub_ass : sub_assessments) {
			
			TableItem ti = new TableItem(table, SWT.NONE);
			
			TableEditor editor = new TableEditor(table);
			Text text = new Text(table, SWT.NONE);
		    text.setText("");
		    editor.grabHorizontal = true;
		    editor.setEditor(text, ti, 2);
		      
			ti.setData(sub_ass.getValueAsLong("student_id"));
			ti.setText(new String[] {
					sub_ass.getValueAsStringNotNull("description"), 
					sub_ass.getValueAsStringNotNull("differentiated_evaluation")
			});
		}
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
