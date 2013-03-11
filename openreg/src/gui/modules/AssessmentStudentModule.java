package gui.modules;

import gui.GuiModule;
import gui.dialogs.TeacherClassCourseDialog;
import gui.GuiTools;
import gui.dialogs.TeacherDialog;
import java.util.ArrayList;
import log.Log;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import database.Row;
import database.query.AssessmentStudentQuery;
import database.query.TeacherClassCourseQuery;
import database.query.TeacherQuery;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AssessmentStudentModule extends GuiModule {
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
					GuiTools.showMessageBox(container.getShell(), "No teachers selected.");
					return;
				}
				
				int answer = GuiTools.showQuestionBox(container.getShell(), "Delete " + selected.size() + " teachers?");
				if(answer == SWT.NO) {
					return;
				}
				
				for(Long assStudent : selected) {
					try {
						AssessmentStudentQuery.delete(assStudent);
					} catch (Exception e) {
						e.printStackTrace();
						GuiTools.showMessageBox(container.getShell(), e.getMessage());
					}
				}
				reloadData();
			}
		});
		tltmRemove.setText("Remove");
		
		ToolItem tltmFilter = new ToolItem(toolBar, SWT.DROP_DOWN);
		tltmFilter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Log.info("Filter not implemented yet");
			}
		});
		tltmFilter.setText("All classes");
		
		table = new Table(group, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				TeacherDialog addDialog = new TeacherDialog(container.getShell());
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					addDialog.loadData(ti.getData());
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(container.getShell(), e.getMessage());
				}
				addDialog.open();
				reloadData();
			}
		});
		GridData gd_table = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		gd_table.heightHint = 293;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(27);
		tableColumn.setText("#");
		
		TableColumn tblclmnStudentId = new TableColumn(table, SWT.NONE);
		tblclmnStudentId.setWidth(70);
		tblclmnStudentId.setText("Student ID");
		
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnMainDescription = new TableColumn(table, SWT.NONE);
		tblclmnMainDescription.setWidth(100);
		tblclmnMainDescription.setText("Assessment Main Description");
		
		TableColumn tblclmnSubDescription = new TableColumn(table, SWT.NONE);
		tblclmnSubDescription.setWidth(100);
		tblclmnSubDescription.setText("Assessment Sub Description");
		
		TableColumn tblclmnMark = new TableColumn(table, SWT.NONE);
		tblclmnMark.setWidth(100);
		tblclmnMark.setText("Mark");
	}

	@Override
	public String getName() {
		return "Assessment Student";
	}

	@Override
	public void reloadData(Object filterId) {
		table.removeAll();
		int i = 1;
		try {
			for(Row assStudent : AssessmentStudentQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(assStudent.getValueAsLong("assessment_student_id"));
				tableItem.setText(new String[] {
						Integer.toString(i++),
						assStudent.getValueAsString("student_id"),
						assStudent.getValueAsString("name") + " " + assStudent.getValueAsString("surname"),
						assStudent.getValueAsString("main_description"),
						assStudent.getValueAsString("sub_description"),
						assStudent.getValueAsString("mark")
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
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
