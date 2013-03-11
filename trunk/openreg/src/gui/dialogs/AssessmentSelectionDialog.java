package gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import database.Row;
import database.query.AssessmentQuery;

public class AssessmentSelectionDialog extends GuiDialog {
	
	protected Object result;
	protected Shell shlDialog;
	private Table table;
	
	public AssessmentSelectionDialog(Shell parent) {
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
		shlDialog.setSize(536, 300);
		shlDialog.setText("Choose an assessment");
		shlDialog.setLayout(new FormLayout());
		
		table = new Table(shlDialog, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				chooseSelection();
			}
		});
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		fd_table.bottom = new FormAttachment(0, 231);
		fd_table.right = new FormAttachment(0, 526);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnStreet = new TableColumn(table, SWT.NONE);
		tblclmnStreet.setWidth(173);
		tblclmnStreet.setText("Assessment");
		
		Button btnOk = new Button(shlDialog, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				chooseSelection();
			}
		});
		FormData fd_btnOk = new FormData();
		fd_btnOk.right = new FormAttachment(table, 0, SWT.RIGHT);
		fd_btnOk.top = new FormAttachment(table, 9);
		btnOk.setLayoutData(fd_btnOk);
		btnOk.setText("OK");
		
		Button btnCancel = new Button(shlDialog, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		fd_btnOk.left = new FormAttachment(btnCancel, 6);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.right = new FormAttachment(100, -85);
		fd_btnCancel.top = new FormAttachment(table, 9);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(142);
		tblclmnNewColumn.setText("Type");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(190);
		tblclmnNewColumn_1.setText("Topic");
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		update();
	}
	
	private void chooseSelection() {
		try {
//			Long id = (Long)table.getItems()[table.getSelectionIndex()].getData();
			result = GuiTools.getSelectedItems(table);
//			result = AssessmentQuery.getDataset(id).get(0);
		} catch (Exception e) {
		}
		shlDialog.dispose();
	}

	@Override
	public void loadData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		table.removeAll();
		try {
			for(Row address : AssessmentQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(address.getValueAsLong("assessment_id"));
				tableItem.setText(new String[] {
						address.getValueAsStringNotNull("assessment_description"), 
						address.getValueAsStringNotNull("assessment_type_description"),
						address.getValueAsStringNotNull("topic_description")
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, 
					"Unable to fetch data from your Database! See stdout for more information!\n\n" + e.getMessage()
					);
		}		
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
