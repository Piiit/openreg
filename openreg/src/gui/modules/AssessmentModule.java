package gui.modules;

import gui.GuiModule;
import gui.dialogs.AssessmentDialog;
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
import database.query.AssessmentQuery;
import database.query.TeacherClassCourseQuery;
import database.query.TeacherQuery;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Label;

public class AssessmentModule extends GuiModule {
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

				AssessmentDialog assessmentDialog = new AssessmentDialog(group.getShell());
				assessmentDialog.open();
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
					GuiTools.showMessageBox(container.getShell(), "No Assessments selected.");
					reloadData();
					return;
				}
				
				int answer = GuiTools.showQuestionBox(container.getShell(), "Delete " + selected.size() + " assessments?");
				if(answer == SWT.NO) {
					return;
				}

				for(Long assessmentId : selected) {
					try {
						AssessmentQuery.delete(assessmentId);
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
				AssessmentDialog assessment = new AssessmentDialog(container.getShell());
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					
					assessment.loadData(ti.getData());
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(container.getShell(), e.getMessage());
				}
				assessment.open();
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
		
		TableColumn tblclmnDescription = new TableColumn(table, SWT.NONE);
		tblclmnDescription.setWidth(100);
		tblclmnDescription.setText("Description");
		
		TableColumn tblclmnAssessmentType = new TableColumn(table, SWT.NONE);
		tblclmnAssessmentType.setWidth(100);
		tblclmnAssessmentType.setText("Assessment Type");
		
		TableColumn tblclmnTopic = new TableColumn(table, SWT.NONE);
		tblclmnTopic.setWidth(100);
		tblclmnTopic.setText("Topic");
	}

	@Override
	public String getName() {
		return "Assessments";
	}

	@Override
	public void reloadData(Object filterId) {
		table.removeAll();
		int i = 1;
		try {
			for(Row assessment : AssessmentQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(assessment.getValueAsLong("assessment_id"));
				tableItem.setText(new String[] {
						Integer.toString(i++), 
						assessment.getValueAsString("assessment_description"),
						assessment.getValueAsString("assessment_type_description"),
						assessment.getValueAsString("topic_description")
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
