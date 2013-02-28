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
import org.eclipse.swt.widgets.Text;
import database.Row;
import database.query.AddressQuery;
import gui.GuiDialog;
import gui.GuiTools;

public class AddressDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	private Text street;
	private Row loadedData;
	private Text no;
	private Text zip;
	private Text city;
	private Text country;
	
	public AddressDialog(Shell parent) {
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
		shlDialog.setSize(600, 159);
		shlDialog.setText("Add a new address");
		shlDialog.setLayout(new FormLayout());
		
		Label lblStreet = new Label(shlDialog, SWT.NONE);
		FormData fd_lblStreet = new FormData();
		fd_lblStreet.top = new FormAttachment(0, 10);
		fd_lblStreet.left = new FormAttachment(0, 10);
		lblStreet.setLayoutData(fd_lblStreet);
		lblStreet.setText("Street *");
		
		Label label = new Label(shlDialog, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 10);
		fd_label.right = new FormAttachment(100, -10);
		label.setLayoutData(fd_label);
		
		Button btnSave = new Button(shlDialog, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(label, 6);
		fd_btnSave.right = new FormAttachment(label, 0, SWT.RIGHT);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlDialog, SWT.NONE);
		fd_btnSave.left = new FormAttachment(btnCancel, 11);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.top = new FormAttachment(label, 6);
		fd_btnCancel.right = new FormAttachment(100, -96);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Label lblMandatoryFields = new Label(shlDialog, SWT.NONE);
		FormData fd_lblMandatoryFields = new FormData();
		fd_lblMandatoryFields.top = new FormAttachment(label, 6);
		fd_lblMandatoryFields.left = new FormAttachment(0, 10);
		lblMandatoryFields.setLayoutData(fd_lblMandatoryFields);
		lblMandatoryFields.setText("* Mandatory Fields");
		
		street = new Text(shlDialog, SWT.BORDER);
		FormData fd_street = new FormData();
		fd_street.top = new FormAttachment(0, 10);
		fd_street.left = new FormAttachment(0, 95);
		street.setLayoutData(fd_street);
		
		Label lblNo = new Label(shlDialog, SWT.NONE);
		fd_street.right = new FormAttachment(lblNo, -16);
		FormData fd_lblNo = new FormData();
		fd_lblNo.left = new FormAttachment(0, 447);
		fd_lblNo.top = new FormAttachment(lblStreet, 0, SWT.TOP);
		lblNo.setLayoutData(fd_lblNo);
		lblNo.setText("No. *");
		
		no = new Text(shlDialog, SWT.BORDER);
		FormData fd_no = new FormData();
		fd_no.right = new FormAttachment(100, -10);
		fd_no.left = new FormAttachment(lblNo, 6);
		fd_no.top = new FormAttachment(0, 10);
		no.setLayoutData(fd_no);
		
		Label lblZipCode = new Label(shlDialog, SWT.NONE);
		FormData fd_lblZipCode = new FormData();
		fd_lblZipCode.top = new FormAttachment(lblStreet, 20);
		fd_lblZipCode.left = new FormAttachment(lblStreet, 0, SWT.LEFT);
		lblZipCode.setLayoutData(fd_lblZipCode);
		lblZipCode.setText("ZIP Code *");
		
		zip = new Text(shlDialog, SWT.BORDER);
		fd_label.bottom = new FormAttachment(zip, 33, SWT.BOTTOM);
		fd_label.top = new FormAttachment(zip, 14);
		FormData fd_zip = new FormData();
		fd_zip.left = new FormAttachment(street, 0, SWT.LEFT);
		fd_zip.top = new FormAttachment(street, 12);
		fd_zip.right = new FormAttachment(lblZipCode, 129, SWT.RIGHT);
		zip.setLayoutData(fd_zip);
		
		Label lblCity = new Label(shlDialog, SWT.NONE);
		FormData fd_lblCity = new FormData();
		fd_lblCity.bottom = new FormAttachment(lblZipCode, 0, SWT.BOTTOM);
		fd_lblCity.left = new FormAttachment(zip, 22);
		lblCity.setLayoutData(fd_lblCity);
		lblCity.setText("City *");
		
		city = new Text(shlDialog, SWT.BORDER);
		FormData fd_text_3 = new FormData();
		fd_text_3.right = new FormAttachment(lblCity, 121, SWT.RIGHT);
		fd_text_3.top = new FormAttachment(street, 10);
		fd_text_3.left = new FormAttachment(lblCity, 6);
		city.setLayoutData(fd_text_3);
		
		Label lblCountry = new Label(shlDialog, SWT.NONE);
		fd_btnCancel.left = new FormAttachment(lblCountry, 0, SWT.LEFT);
		FormData fd_lblCountry = new FormData();
		fd_lblCountry.bottom = new FormAttachment(lblZipCode, 0, SWT.BOTTOM);
		fd_lblCountry.left = new FormAttachment(city, 24);
		lblCountry.setLayoutData(fd_lblCountry);
		lblCountry.setText("Country *");
		
		country = new Text(shlDialog, SWT.BORDER);
		FormData fd_text_4 = new FormData();
		fd_text_4.right = new FormAttachment(100, -10);
		fd_text_4.left = new FormAttachment(lblCountry, 6);
		fd_text_4.top = new FormAttachment(no, 10);
		country.setLayoutData(fd_text_4);

		update();
	}

	@Override
	public void loadData(Object data) throws Exception {
		ArrayList<Row> ab = AddressQuery.getDataset(data);
		if(ab.size() == 0) {
			throw new Exception("No address with ID " + data.toString() + " found.");
		}
		loadedData = ab.get(0); 
	}

	@Override
	public void store() {
		try {
			Row newAddress = new Row();
			newAddress.setValue("street", GuiTools.nullIfEmptyTrimmed(street.getText()));
			newAddress.setValue("no", GuiTools.nullIfEmptyTrimmed(no.getText()));
			newAddress.setValue("zip_code", GuiTools.nullIfEmptyTrimmed(zip.getText()));
			newAddress.setValue("city", GuiTools.nullIfEmptyTrimmed(city.getText()));
			newAddress.setValue("country", GuiTools.nullIfEmptyTrimmed(country.getText()));
			
			if(loadedData == null) {
				AddressQuery.insert(newAddress);	
			} else {
				AddressQuery.update(loadedData.getValueAsLong("id"), newAddress);
			}
			
			shlDialog.close();
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	@Override
	public void update() {
		try {
			if (loadedData != null){
				street.setText(loadedData.getValueAsStringNotNull("street"));
				no.setText(loadedData.getValueAsStringNotNull("no"));
				zip.setText(loadedData.getValueAsStringNotNull("zip_code"));
				city.setText(loadedData.getValueAsStringNotNull("city"));
				country.setText(loadedData.getValueAsStringNotNull("country"));
				shlDialog.setText("Modify an address");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
