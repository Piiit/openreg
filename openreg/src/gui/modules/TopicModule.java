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
import database.query.TopicQuery;
import gui.GuiModule;
import gui.GuiTools;
import gui.dialogs.TopicDialog;

public class TopicModule extends GuiModule {

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
				TopicDialog topicDialog = new TopicDialog(group.getShell());
				topicDialog.open();
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
					GuiTools.showMessageBox(container.getShell(), "No Topic selected.");
					reloadData();
					return;
				}
				
				int answer = GuiTools.showQuestionBox(container.getShell(), "Delete " + selected.size() + " topics?");
				if(answer == SWT.NO) {
					return;
				}

				for(Long topicId : selected) {
					try {
						TopicQuery.delete(topicId);
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
				TopicDialog topic = new TopicDialog(container.getShell());
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					topic.loadData(ti.getData());
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(container.getShell(), e.getMessage());
				}
				topic.open();
				reloadData();
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableColumn tblclmnLevel = new TableColumn(table, SWT.NONE);
		tblclmnLevel.setWidth(400);
		tblclmnLevel.setText("Topic Description");
	}

	@Override
	public void reloadData(Object filterId) {
		table.removeAll();
		try {
			for(Row topic : TopicQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(topic.getValueAsLong("id"));
				tableItem.setText(new String[] {
						topic.getValueAsStringNotNull("description")
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
	public String getName() {
		return "Topics";
	}

	@Override
	public String getDescription() {
		return null;
	}

}
