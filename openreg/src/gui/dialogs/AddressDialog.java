package gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import gui.GuiDialog;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import database.Row;
import database.query.AddressQuery;

public class AddressDialog extends GuiDialog {
	
	protected Object result;
	protected Shell shlAddressDialog;
	private Table table;
	
	public AddressDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAddressDialog.open();
		shlAddressDialog.layout();
		Display display = getParent().getDisplay();
		while (!shlAddressDialog.isDisposed()) {
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
		shlAddressDialog = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAddressDialog.setSize(536, 300);
		shlAddressDialog.setText("Choose an address");
		shlAddressDialog.setLayout(new FormLayout());
		
		table = new Table(shlAddressDialog, SWT.BORDER | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		fd_table.bottom = new FormAttachment(0, 231);
		fd_table.right = new FormAttachment(0, 526);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnStreet = new TableColumn(table, SWT.NONE);
		tblclmnStreet.setWidth(315);
		tblclmnStreet.setText("Street");
		
		TableColumn tblclmnCityzip = new TableColumn(table, SWT.NONE);
		tblclmnCityzip.setWidth(217);
		tblclmnCityzip.setText("City (ZIP) / Country");
		
		Button btnOk = new Button(shlAddressDialog, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		FormData fd_btnOk = new FormData();
		fd_btnOk.right = new FormAttachment(table, 0, SWT.RIGHT);
		fd_btnOk.top = new FormAttachment(table, 9);
		btnOk.setLayoutData(fd_btnOk);
		btnOk.setText("OK");
		
		Button btnCancel = new Button(shlAddressDialog, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		fd_btnOk.left = new FormAttachment(btnCancel, 6);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.right = new FormAttachment(100, -85);
		fd_btnCancel.top = new FormAttachment(table, 9);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		update();
	}

	@Override
	public void loadData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		table.removeAll();
		try {
			for(Row address : AddressQuery.getFullDataset()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(address.getValueAsLong("id"));
				tableItem.setText(new String[] {
						address.getValueAsStringNotNull("street") + " " + 
						address.getValueAsStringNotNull("no"),
						address.getValueAsStringNotNull("city") + 
						"(" + address.getValueAsStringNotNull("zip_code") + ")" +
						address.getValueAsStringNotNull("country")
						});
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageBox message = new MessageBox(this.getParent(), SWT.ICON_ERROR | SWT.OK);
			message.setMessage("Unable to fetch data from your Database! See stdout for more information!\n\n" + e.getMessage());
			message.setText(this.getText());
			message.open();	
		}		}

	@Override
	public void cancel() {
		shlAddressDialog.dispose();
	}
}
