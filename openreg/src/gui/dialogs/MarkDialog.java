package gui.dialogs;

import java.util.ArrayList;

import log.Log;

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
import database.query.AbilityDescriptionQuery;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class MarkDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	protected Row loadedData;
	private Text txtNewMark;
	private Combo combo;
	private ScrolledComposite scrolledComposite;
	private int markCount = 0;
	private Table table;
	
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
		fd_label.top = new FormAttachment(combo, 274);
		FormData fd_combo = new FormData();
		fd_combo.left = new FormAttachment(0, 10);
		combo.setLayoutData(fd_combo);
		
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
		fd_combo.right = new FormAttachment(link_3, -6);
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
		FormData fd_link_3 = new FormData();
		fd_link_3.top = new FormAttachment(0, 32);
		fd_link_3.right = new FormAttachment(label, 0, SWT.RIGHT);
		link_3.setLayoutData(fd_link_3);
		link_3.setText("<a>Rename</a>");
		
		table = new Table(shlDialog, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_table = new FormData();
		fd_table.left = new FormAttachment(label, 0, SWT.LEFT);
		fd_table.top = new FormAttachment(combo, 6);
		fd_table.bottom = new FormAttachment(label, -6);
		fd_table.right = new FormAttachment(100, -14);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnMark = new TableColumn(table, SWT.NONE);
		tblclmnMark.setWidth(100);
		tblclmnMark.setText("Mark");
		
		TableColumn tblclmnBound = new TableColumn(table, SWT.NONE);
		tblclmnBound.setWidth(100);
		tblclmnBound.setText("Bound");
		
		TableColumn tblclmnCommands = new TableColumn(table, SWT.NONE);
		tblclmnCommands.setWidth(100);
		tblclmnCommands.setText("Commands");
		
		update();
	}

	private void createMarkField(String text, int bound) {
		Log.info("Creating " + text);
		markCount++;
		
		TableItem ti = new TableItem(table, 0);
		ti.setText("("+markCount+")");
		
		txtNewMark = new Text(scrolledComposite, SWT.BORDER);
		txtNewMark.setText(text);
		
		Spinner spinner = new Spinner(scrolledComposite, SWT.BORDER);
		spinner.setSelection(bound);
		
//		Link link_1 = new Link(grpOrderOfMarks, SWT.NONE);
//		link_1.setText("<a>Up</a>");
//		
//		Link link_2 = new Link(grpOrderOfMarks, SWT.NONE);
//		link_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
//		link_2.setText("<a>Down</a>");
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
				
				String typeString = loadedData.getValueAsStringNotNull("description");
				combo.select(combo.indexOf(typeString));
				
				ArrayList<Row> marks = MarkQuery.getDataset(loadedData.getValueAsLong("id"));
				for(Row mark : marks) {
					createMarkField(
							mark.getValueAsStringNotNull("representation"), 
							(int)Math.round((double) mark.getValue("bound"))
							);
				}
				
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
