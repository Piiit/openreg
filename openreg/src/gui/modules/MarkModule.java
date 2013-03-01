package gui.modules;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import database.Row;
import database.query.ClassQuery;
import database.query.MarkQuery;
import database.query.MarkTypeQuery;

import gui.GuiModule;
import gui.GuiTools;
import gui.dialogs.ClassDialog;
import gui.dialogs.MarkDialog;

public class MarkModule extends GuiModule {


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
				MarkDialog dialog = new MarkDialog(group.getShell());
				dialog.open();
				reloadData();
			}
		});
		tltmAdd.setText("Add");
		
		ToolItem tltmRemove = new ToolItem(toolBar, SWT.NONE);
		tltmRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ArrayList<Long> selected = GuiTools.getSelectedItems(table);
				
				if(selected.size() == 0) {
					GuiTools.showMessageBox(container.getShell(), "No marks selected.");
					reloadData();
					return;
				}
				
				int answer = GuiTools.showQuestionBox(container.getShell(), "Delete " + selected.size() + " marks?");
				if(answer == SWT.NO) {
					return;
				}
				
				for(Long classId : selected) {
					try {
						MarkQuery.delete(classId);
					} catch (Exception e) {
						e.printStackTrace();
						GuiTools.showMessageBox(container.getShell(), e.getMessage());
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
				MarkDialog dialog = new MarkDialog(container.getShell());
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					dialog.loadData(ti.getData());
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
		
		TableColumn tblclmnStudents = new TableColumn(table, SWT.NONE);
		tblclmnStudents.setWidth(129);
		tblclmnStudents.setText("Mark Types");
		
		TableColumn tblclmnNotes = new TableColumn(table, SWT.LEFT);
		tblclmnNotes.setWidth(273);
		tblclmnNotes.setText("Marks");
		
		reloadData();
	}
	
	@Override
	public void reloadData() {
		table.removeAll();
		final String markSeparator = "   ";
		try {
			for(Row markType : MarkTypeQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				Long markTypeId = markType.getValueAsLong("id");
				tableItem.setData(markTypeId);
				
				ArrayList<Row> marks = MarkQuery.getAllMarksFromType(markTypeId);
				String markList = "";
				if(marks.size() > 0) {
					for(Row mark : marks) {
						markList += mark.getValueAsStringNotNull("representation") + markSeparator; 
					}
					markList = markList.substring(0, markList.length() - markSeparator.length());
				}
				
				tableItem.setText(new String[] {
						markType.getValueAsStringNotNull("description"),
						markList
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(container.getShell(), 
					"Unable to fetch data from your Database! See stdout for more information!\n\n" + e.getMessage()
					);
		}	
	}

	@Override
	public void reloadData(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Mark Types";
	}

	@Override
	public String getDescription() {
		return null;
	}

}
