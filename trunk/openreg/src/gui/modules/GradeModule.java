package gui.modules;

import gui.GuiModule;
import gui.dialogs.AssessmentBundleDialog;
import gui.dialogs.AssessmentDialog;
import gui.dialogs.GradeDialog;
import gui.GuiTools;
import java.util.ArrayList;
import log.Log;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import database.Row;
import database.query.AssessmentQuery;
import database.query.AssessmentTypeQuery;
import database.query.CourseQuery;
import database.query.GradeQuery;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Label;

public class GradeModule extends GuiModule {
	private Table table;
	private ToolItem tltmFilter;
	private Combo filterType;
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
		
		table = new Table(group, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				GradeDialog grade = new GradeDialog(container.getShell());
				
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());	
					grade.loadData(ti.getData());
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(container.getShell(), e.getMessage());
				}
				grade.open();
				reloadData();
			}
		});
		
		filterType = new Combo(toolBar, SWT.READ_ONLY);
		
		
		tltmFilter = new ToolItem(toolBar, SWT.SEPARATOR);
		tltmFilter.setControl(filterType);
		tltmFilter.setWidth(100);
		filterType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Long typeID = (Long)filterType.getData(filterType.getText());
				Log.debug("Type ID " + typeID);
				reloadData(typeID);
			}
		});
		
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
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
		
		TableColumn tblclmnGradeType = new TableColumn(table, SWT.NONE);
		tblclmnGradeType.setWidth(100);
		tblclmnGradeType.setText("Date");
		
		TableColumn tblclmnTopic = new TableColumn(table, SWT.NONE);
		tblclmnTopic.setWidth(100);
		tblclmnTopic.setText("Notes");
	}

	@Override
	public String getName() {
		return "Grade Assessment";
	}

	@Override
	public void reloadData(Object id) {
		table.removeAll();
		filterType.removeAll();
		int i = 1;
		try {	
			ArrayList<Row> dataset = (id == null ? GradeQuery.getFullDataset() : GradeQuery.getCourseDataset(id));
			for(Row grade : dataset) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(grade.getValueAsLong("student_id"));
				tableItem.setText(new String[] {
						Integer.toString(i++), 
						grade.getValueAsString("description"),
						grade.getValueAsString("date"),
						grade.getValueAsString("notes")
						});
			}
			String typeString = "Show all";
			filterType.add(typeString);
			filterType.select(filterType.indexOf(typeString));
			for(Row co : CourseQuery.getFullDataset()) {
				typeString = co.getValueAsStringNotNull("name");
				filterType.add(typeString);
				filterType.setData(typeString, co.getValue("id"));
				if(id != null && id.equals(co.getValue("id"))) {
					filterType.select(filterType.indexOf(typeString));
				}
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
