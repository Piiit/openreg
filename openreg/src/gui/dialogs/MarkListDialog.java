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

public class MarkListDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	protected Row loadedData;
	private Combo combo;
	private Table table;
	
	public MarkListDialog(Shell parent) {
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
				cancel();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(0, 340);
		fd_btnSave.right = new FormAttachment(100, -14);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("OK");
		fd_btnSave.left = new FormAttachment(0, 258);
		
		combo = new Combo(shlDialog, SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String comboSelection = combo.getItem(combo.getSelectionIndex());
				try {
					loadData(combo.getData(comboSelection));
					updateMarksTable();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
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
		
		table = new Table(shlDialog, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				MarkDialog dialog = new MarkDialog(shlDialog);
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					dialog.loadData(ti.getData());
				} catch (Exception e) {
					e.printStackTrace();
				}
				dialog.open();
				updateMarksTable();
			}
		});
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(combo, 6);
		fd_table.left = new FormAttachment(0, 10);
		fd_table.right = new FormAttachment(100, -14);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnMark = new TableColumn(table, SWT.NONE);
		tblclmnMark.setWidth(100);
		tblclmnMark.setText("Mark");
		
		TableColumn tblclmnBound = new TableColumn(table, SWT.NONE);
		tblclmnBound.setWidth(60);
		tblclmnBound.setText("Bound");
		
		Link link_1 = new Link(shlDialog, SWT.NONE);
		link_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ArrayList<Long> selected = GuiTools.getSelectedItems(table);
				
				if(selected.size() == 0) {
					GuiTools.showMessageBox(shlDialog, "No marks selected.");
					updateMarksTable();
					return;
				}
				
				int answer = GuiTools.showQuestionBox(shlDialog, "Delete " + selected.size() + " marks?");
				if(answer == SWT.NO) {
					return;
				}

				for(Long markId : selected) {
					try {
						MarkQuery.delete(markId);
					} catch (Exception e) {
						e.printStackTrace();
						GuiTools.showMessageBox(shlDialog, e.getMessage());
					}
				}
				updateMarksTable();
			}
		});
		fd_table.bottom = new FormAttachment(link_1, -6);
		FormData fd_link_1 = new FormData();
		fd_link_1.bottom = new FormAttachment(label, -6);
		fd_link_1.left = new FormAttachment(0, 10);
		link_1.setLayoutData(fd_link_1);
		link_1.setText("<a>Remove</a>");
		
		Link link_2 = new Link(shlDialog, SWT.NONE);
		link_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MarkDialog dialog = new MarkDialog(shlDialog);
				try {
					String comboSelection = combo.getItem(combo.getSelectionIndex());
					dialog.setMarkType((Long)combo.getData(comboSelection));
				} catch (Exception e) {
					e.printStackTrace();
				}
				dialog.open();
				updateMarksTable();
			}
		});
		FormData fd_link_2 = new FormData();
		fd_link_2.top = new FormAttachment(table, 6);
		fd_link_2.left = new FormAttachment(link_1, 6);
		link_2.setLayoutData(fd_link_2);
		link_2.setText("<a>Add</a>");
		
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
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		try {
			
			updateMarkTypeField();
			
			if (loadedData != null){
				shlDialog.setText("Modify a mark or mark type");
				
				String typeString = loadedData.getValueAsStringNotNull("description");
				combo.select(combo.indexOf(typeString));
				
				updateMarksTable();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMarksTable() {
		table.removeAll();
		ArrayList<Row> marks = null;
		try {
			marks = MarkQuery.getAllMarksOfType(loadedData.getValueAsLong("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(Row mark : marks) {
			TableItem ti = new TableItem(table, SWT.NONE);
			ti.setData(mark.getValueAsLong("mark_id"));
			ti.setText(new String[] {
					mark.getValueAsStringNotNull("representation"), 
					Integer.toString((int)Math.round((double) mark.getValue("bound")))
			});
		}
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
