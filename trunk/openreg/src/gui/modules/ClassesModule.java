package gui.modules;

import gui.GuiModule;
import gui.dialogs.ClassDialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import database.Row;
import database.query.ClassQuery;

public class ClassesModule extends GuiModule {

	private Table table;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createContent(Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(this.getName());
		group.setLayout(new GridLayout(1, false));
		container = group;
		
		ToolBar toolBar = new ToolBar(group, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		ToolItem tltmAdd = new ToolItem(toolBar, SWT.NONE);
		tltmAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ClassDialog classAddDialog = new ClassDialog(group.getShell());
				classAddDialog.open();
				reloadData();
			}
		});
		tltmAdd.setText("Add");
		
		ToolItem tltmRemove = new ToolItem(toolBar, SWT.NONE);
		tltmRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem tableItems[] = table.getItems();
				ArrayList<Long> selected = new ArrayList<Long>();
				for(int i = 0; i < tableItems.length; i++) {
					if(tableItems[i].getChecked() == true) {
						selected.add((Long)tableItems[i].getData());
					}
				}
				
				if(selected.size() == 0) {
					MessageBox message = new MessageBox(container.getShell(), SWT.ICON_INFORMATION | SWT.OK);
					message.setMessage("No classes selected.");
					message.setText(container.getShell().getText());
					message.open();
					reloadData();
					return;
				}
				
				MessageBox messageBox = new MessageBox(container.getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBox.setMessage("Delete " + selected.size() + " classes?");
				messageBox.setText(container.getShell().getText());
				
				if(messageBox.open() == SWT.NO) {
					return;
				}
				
				for(Long classId : selected) {
					try {
							ClassQuery.delete(classId);
					} catch (Exception e) {
						e.printStackTrace();
	
						MessageBox message = new MessageBox(container.getShell(), SWT.ICON_INFORMATION | SWT.OK);
						message.setMessage(e.getMessage());
						message.setText(container.getShell().getText());
						message.open();
					}
				}
				reloadData();
			}
		});
		tltmRemove.setText("Remove");
		
		table = new Table(group, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				ClassDialog dialog = new ClassDialog(container.getShell());
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					dialog.loadData((Long)ti.getData());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.open();
				reloadData();
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableColumn tblclmnLevel = new TableColumn(table, SWT.NONE);
		tblclmnLevel.setWidth(100);
		tblclmnLevel.setText("Level / Stream");
		
		TableColumn tblclmnStudents = new TableColumn(table, SWT.NONE);
		tblclmnStudents.setWidth(100);
		tblclmnStudents.setText("Students");
		
		TableColumn tblclmnNotes = new TableColumn(table, SWT.LEFT);
		tblclmnNotes.setWidth(200);
		tblclmnNotes.setText("Notes");
		
		reloadData();
	}

	@Override
	public void reloadData() {
		table.removeAll();
		try {
			for(Row thisClass : ClassQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(thisClass.getValueAsLong("id"));
				tableItem.setText(new String[] {
						thisClass.getValueAsStringNotNull("level") + thisClass.getValueAsStringNotNull("stream"),
						thisClass.getValueAsStringNotNull("students_count"),
						thisClass.getValueAsStringNotNull("notes")
						});
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageBox message = new MessageBox(container.getShell(), SWT.ICON_ERROR | SWT.OK);
			message.setMessage("Unable to fetch data from your Database! See stdout for more information!\n\n" + e.getMessage());
			message.setText(this.getName());
			message.open();	
		}	
	}

	@Override
	public String getName() {
		return "Classes";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reloadData(Object o) {
		// TODO Auto-generated method stub
		
	}
}
