package gui.modules;

import gui.GuiModule;
import gui.dialogs.ClassDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
				ClassDialog classAddDialog = new ClassDialog(group.getShell(), SWT.NONE);
				classAddDialog.open();
				reloadData();
			}
		});
		tltmAdd.setText("Add");
		
		ToolItem tltmRemove = new ToolItem(toolBar, SWT.NONE);
		tltmRemove.setText("Remove");
		
		table = new Table(group, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableColumn tblclmnLevel = new TableColumn(table, SWT.NONE);
		tblclmnLevel.setWidth(100);
		tblclmnLevel.setText("Level / Stream");
		
		TableColumn tblclmnNotes = new TableColumn(table, SWT.LEFT);
		tblclmnNotes.setWidth(200);
		tblclmnNotes.setText("Notes");
	}

	@Override
	public void reloadData() {
		table.removeAll();
		try {
			for(Row thisClass : ClassQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {
						thisClass.getValueAsString("level") + " " + thisClass.getValueAsString("stream"),
						thisClass.getValueAsString("notes")
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
}
