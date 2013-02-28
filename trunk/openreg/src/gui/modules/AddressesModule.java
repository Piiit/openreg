package gui.modules;

import java.util.ArrayList;
import gui.GuiModule;
import org.eclipse.swt.SWT;
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
import database.query.AddressQuery;

public class AddressesModule extends GuiModule {

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
					message.setMessage("No addresses selected.");
					message.setText(container.getShell().getText());
					message.open();
					reloadData();
					return;
				}
				
				MessageBox messageBox = new MessageBox(container.getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBox.setMessage("Delete " + selected.size() + " addresses?");
				messageBox.setText(container.getShell().getText());
				
				if(messageBox.open() == SWT.NO) {
					return;
				}

				for(Long addressId : selected) {
					try {
						AddressQuery.delete(addressId);
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
		
		ToolItem tltmShowAll = new ToolItem(toolBar, SWT.DROP_DOWN);
		tltmShowAll.setText("Show all");
		
		table = new Table(group, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableColumn tblclmnLevel = new TableColumn(table, SWT.NONE);
		tblclmnLevel.setWidth(400);
		tblclmnLevel.setText("Address");
		
		reloadData();
	}

	@Override
	public void reloadData() {
		table.removeAll();
		try {
			for(Row address : AddressQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(address.getValueAsLong("id"));
				tableItem.setText(new String[] {
						address.getValueAsStringNotNull("street") + " " + 
						address.getValueAsStringNotNull("no") + ", " +
						address.getValueAsStringNotNull("zip_code") + " " +
						address.getValueAsStringNotNull("city") + ", " +
						address.getValueAsStringNotNull("country")
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
	public void reloadData(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Addresses";
	}

	@Override
	public String getDescription() {
		return null;
	}

}
