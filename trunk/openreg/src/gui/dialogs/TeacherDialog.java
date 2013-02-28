package gui.dialogs;

import java.sql.Date;
import java.util.ArrayList;

import gui.GuiDialog;
import gui.GuiTools;
import log.Log;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import data.SimpleDate;
import database.Row;
import database.query.AddressQuery;
import database.query.StudentQuery;
import database.query.TeacherQuery;

import org.eclipse.swt.widgets.Link;

public class TeacherDialog extends GuiDialog {

	protected Object result;
	protected Shell shlAddTeacher;
	private Text teacherName;
	private Text teacherSurname;
	private Text addressStreet;
	private Text addressNo;
	private Text addressZip;
	private Text addressCity;
	private Text addressCountry;
	private Text teacherPhone;
	private StyledText teacherNotes;
	private DateTime teacherBirthday;
	private Row loadedTeacher;
	private Row loadedAddress;

	public TeacherDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAddTeacher.open();
		shlAddTeacher.layout();
		Display display = getParent().getDisplay();
		while (!shlAddTeacher.isDisposed()) {
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
		shlAddTeacher = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAddTeacher.setSize(640, 700);
		shlAddTeacher.setText("Add Teacher");
		shlAddTeacher.setLayout(new FormLayout());
		
		Canvas canvas = new Canvas(shlAddTeacher, SWT.BORDER);
		canvas.setBackground(SWTResourceManager.getColor(255, 255, 240));
		canvas.setToolTipText("Click to add an image");
		FormData fd_canvas = new FormData();
		fd_canvas.bottom = new FormAttachment(0, 160);
		fd_canvas.right = new FormAttachment(0, 160);
		fd_canvas.top = new FormAttachment(0, 10);
		fd_canvas.left = new FormAttachment(0, 10);
		canvas.setLayoutData(fd_canvas);
		
		Label lblName = new Label(shlAddTeacher, SWT.NONE);
		FormData fd_lblName = new FormData();
		fd_lblName.top = new FormAttachment(canvas, 0, SWT.TOP);
		fd_lblName.left = new FormAttachment(canvas, 6);
		lblName.setLayoutData(fd_lblName);
		lblName.setText("Name *");
		
		teacherName = new Text(shlAddTeacher, SWT.BORDER);
		FormData fd_teacherName = new FormData();
		fd_teacherName.right = new FormAttachment(canvas, 464, SWT.RIGHT);
		fd_teacherName.bottom = new FormAttachment(lblName, 27, SWT.BOTTOM);
		fd_teacherName.top = new FormAttachment(lblName, 6);
		fd_teacherName.left = new FormAttachment(canvas, 6);
		teacherName.setLayoutData(fd_teacherName);
		
		Label lblSurname = new Label(shlAddTeacher, SWT.NONE);
		FormData fd_lblSurname = new FormData();
		fd_lblSurname.top = new FormAttachment(teacherName, 6);
		fd_lblSurname.left = new FormAttachment(canvas, 6);
		lblSurname.setLayoutData(fd_lblSurname);
		lblSurname.setText("Surname *");
		
		teacherSurname = new Text(shlAddTeacher, SWT.BORDER);
		FormData fd_teacherSurname = new FormData();
		fd_teacherSurname.right = new FormAttachment(teacherName, 0, SWT.RIGHT);
		fd_teacherSurname.top = new FormAttachment(lblSurname, 6);
		fd_teacherSurname.left = new FormAttachment(canvas, 6);
		teacherSurname.setLayoutData(fd_teacherSurname);
		
		Label lblBirthday = new Label(shlAddTeacher, SWT.NONE);
		FormData fd_lblBirthday = new FormData();
		fd_lblBirthday.top = new FormAttachment(teacherSurname, 6);
		fd_lblBirthday.left = new FormAttachment(canvas, 6);
		lblBirthday.setLayoutData(fd_lblBirthday);
		lblBirthday.setText("Birthday *");
		
		teacherBirthday = new DateTime(shlAddTeacher, SWT.BORDER);
		FormData fd_teacherBirthday = new FormData();
		fd_teacherBirthday.right = new FormAttachment(100, -362);
		fd_teacherBirthday.left = new FormAttachment(canvas, 6);
		fd_teacherBirthday.top = new FormAttachment(lblBirthday, 6);
		teacherBirthday.setLayoutData(fd_teacherBirthday);
		
		Group grpAddress = new Group(shlAddTeacher, SWT.NONE);
		grpAddress.setText("Address");
		FormData fd_grpAddress = new FormData();
		fd_grpAddress.bottom = new FormAttachment(canvas, 131, SWT.BOTTOM);
		fd_grpAddress.top = new FormAttachment(canvas, 6);
		fd_grpAddress.right = new FormAttachment(teacherName, 0, SWT.RIGHT);
		fd_grpAddress.left = new FormAttachment(0, 10);
		grpAddress.setLayoutData(fd_grpAddress);
		
		Link link_2 = new Link(grpAddress, SWT.NONE);
		link_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AddressSelectionDialog addressDialog = new AddressSelectionDialog(shlAddTeacher);
				loadedAddress = (Row)addressDialog.open();
				updateAddressFields();
			}
		});
		link_2.setBounds(191, 0, 194, 15);
		link_2.setText("[<a>Choose an existing address</a>]");
		
		Label lblStreet = new Label(grpAddress, SWT.NONE);
		lblStreet.setBounds(10, 1, 130, 15);
		lblStreet.setText("Street *");
		
		addressStreet = new Text(grpAddress, SWT.BORDER);
		addressStreet.setBounds(10, 21, 492, 21);
		
		Label lblNo = new Label(grpAddress, SWT.NONE);
		lblNo.setBounds(508, 1, 70, 15);
		lblNo.setText("No. *");
		
		addressNo = new Text(grpAddress, SWT.BORDER);
		addressNo.setBounds(508, 21, 96, 21);
		
		Label lblNewLabel = new Label(grpAddress, SWT.NONE);
		lblNewLabel.setBounds(10, 48, 96, 15);
		lblNewLabel.setText("ZIP Code *");
		
		addressZip = new Text(grpAddress, SWT.BORDER);
		addressZip.setBounds(10, 69, 130, 21);
		
		Label lblNewLabel_1 = new Label(grpAddress, SWT.NONE);
		lblNewLabel_1.setBounds(170, 48, 55, 15);
		lblNewLabel_1.setText("City *");
		
		addressCity = new Text(grpAddress, SWT.BORDER);
		addressCity.setBounds(170, 69, 200, 21);
		
		Label lblCountry = new Label(grpAddress, SWT.NONE);
		lblCountry.setBounds(396, 48, 106, 15);
		lblCountry.setText("Country *");
		
		addressCountry = new Text(grpAddress, SWT.BORDER);
		addressCountry.setBounds(396, 69, 208, 21);
		
		Group grpAdditionalInformation = new Group(shlAddTeacher, SWT.NONE);
		grpAdditionalInformation.setText("Additional Information");
		FormData fd_grpAdditionalInformation = new FormData();
		fd_grpAdditionalInformation.bottom = new FormAttachment(grpAddress, 326, SWT.BOTTOM);
		fd_grpAdditionalInformation.top = new FormAttachment(grpAddress, 6);
		fd_grpAdditionalInformation.right = new FormAttachment(teacherName, 0, SWT.RIGHT);
		fd_grpAdditionalInformation.left = new FormAttachment(0, 10);
		grpAdditionalInformation.setLayoutData(fd_grpAdditionalInformation);
		
		Label label = new Label(grpAdditionalInformation, SWT.NONE);
		label.setText("Phone");
		label.setBounds(10, 30, 124, 15);
		
		teacherPhone = new Text(grpAdditionalInformation, SWT.BORDER);
		teacherPhone.setBounds(10, 51, 240, 21);
		
		Label lblNotes = new Label(grpAdditionalInformation, SWT.NONE);
		lblNotes.setBounds(10, 78, 55, 15);
		lblNotes.setText("Notes");
		
		teacherNotes = new StyledText(grpAdditionalInformation, SWT.BORDER);
		teacherNotes.setBounds(10, 99, 594, 211);
		
		Button btnSave = new Button(shlAddTeacher, SWT.CENTER);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.bottom = new FormAttachment(100, -10);
		fd_btnSave.right = new FormAttachment(teacherName, 0, SWT.RIGHT);
		fd_btnSave.width = 80;
		fd_btnSave.left = new FormAttachment(100, -121);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlAddTeacher, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.right = new FormAttachment(btnSave, -6);
		fd_btnCancel.left = new FormAttachment(0, 404);
		fd_btnCancel.top = new FormAttachment(btnSave, 0, SWT.TOP);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Label label_1 = new Label(shlAddTeacher, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(btnSave, -6);
		fd_label_1.top = new FormAttachment(grpAdditionalInformation, 6);
		fd_label_1.right = new FormAttachment(teacherName, 0, SWT.RIGHT);
		fd_label_1.left = new FormAttachment(0, 10);
		label_1.setLayoutData(fd_label_1);
		
		Label infoLabel = new Label(shlAddTeacher, SWT.CENTER);
		infoLabel.setAlignment(SWT.CENTER);
		infoLabel.setEnabled(false);
		FormData fd_infoLabel = new FormData();
		fd_infoLabel.top = new FormAttachment(0);
		fd_infoLabel.left = new FormAttachment(lblName, 19);
		fd_infoLabel.width = 200;
		infoLabel.setLayoutData(fd_infoLabel);
		
		Label lblMandatoryFields = new Label(shlAddTeacher, SWT.NONE);
		FormData fd_lblMandatoryFields = new FormData();
		fd_lblMandatoryFields.top = new FormAttachment(btnSave, 0, SWT.TOP);
		fd_lblMandatoryFields.left = new FormAttachment(canvas, 0, SWT.LEFT);
		lblMandatoryFields.setLayoutData(fd_lblMandatoryFields);
		lblMandatoryFields.setText("* Mandatory Fields");
		
		update();
	}
	
	@Override
	public void update() {
		try {
			if (loadedTeacher != null){
				teacherName.setText(loadedTeacher.getValueAsString("name").toString());
				teacherSurname.setText(loadedTeacher.getValueAsString("surname"));
				SimpleDate date = new SimpleDate((Date)loadedTeacher.getValue("birthday"));
				teacherBirthday.setDate(date.getYear(), date.getMonth(), date.getDay());
				teacherPhone.setText(loadedTeacher.getValueAsString("phone_number"));
				teacherNotes.setText(loadedTeacher.getValueAsString("notes"));
				loadedAddress = loadedTeacher;
			}
			updateAddressFields();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	@Override
	public void loadData(Object data) throws Exception {
		ArrayList<Row> teacher = TeacherQuery.getDataset((Long)data);
		if(teacher.size() == 0) {
			throw new Exception("No teacher with ID " + data.toString() + " found.");
		}
		loadedTeacher = teacher.get(0); 
	}
	
	public void updateAddressFields() {
		if(loadedAddress == null) {
			return;
		}
		addressStreet.setText(loadedAddress.getValueAsStringNotNull("street"));
		addressNo.setText(loadedAddress.getValueAsStringNotNull("no"));
		addressZip.setText(loadedAddress.getValueAsStringNotNull("zip_code"));
		addressCity.setText(loadedAddress.getValueAsStringNotNull("city"));
		addressCountry.setText(loadedAddress.getValueAsStringNotNull("country"));
	}

	@Override
	public void store() {
		Long addressId = null;
		try {
			Row newAddress = new Row();
			newAddress.setValue("street", GuiTools.nullIfEmptyTrimmed(addressStreet.getText()));
			newAddress.setValue("no", GuiTools.nullIfEmptyTrimmed(addressNo.getText()));
			newAddress.setValue("zip_code", GuiTools.nullIfEmptyTrimmed(addressZip.getText()));
			newAddress.setValue("city", GuiTools.nullIfEmptyTrimmed(addressCity.getText()));
			newAddress.setValue("country", GuiTools.nullIfEmptyTrimmed(addressCountry.getText()));
			
			if(loadedAddress == null) {
				addressId = AddressQuery.insert(newAddress);
			} else {
				addressId = loadedAddress.getValueAsLong("id");
				if(addressId == null) {
					addressId = loadedAddress.getValueAsLong("address_id");
				}
				AddressQuery.update(addressId, newAddress);
			}
			
			Row newTeacher = new Row();
			newTeacher.setValue("name", GuiTools.nullIfEmptyTrimmed(teacherName.getText()));
			newTeacher.setValue("surname", GuiTools.nullIfEmptyTrimmed(teacherSurname.getText()));
			//TODO login and password declaration
			newTeacher.setValue("login", "login");
			newTeacher.setValue("password", "password");
			newTeacher.setValue("birthday", new SimpleDate(teacherBirthday.getDay(), teacherBirthday.getMonth(), teacherBirthday.getYear()));
			newTeacher.setValue("phone_number", GuiTools.nullIfEmptyTrimmed(teacherPhone.getText()));
			newTeacher.setValue("address_id", addressId);
			
			if(loadedTeacher == null) {
				TeacherQuery.insert(newTeacher);
			} else {
				TeacherQuery.update(loadedTeacher.getValueAsLong("teacher_id"), newTeacher);
			}

			shlAddTeacher.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			
			if(addressId != null) {
				Log.info("Address with ID " + addressId + " already inserted, but without a valid teacher record. Deleting...");
				try {
					AddressQuery.delete(addressId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			MessageBox message = new MessageBox(shlAddTeacher, SWT.ICON_INFORMATION | SWT.OK);
			message.setMessage(e.getMessage());
			message.setText(shlAddTeacher.getText());
			message.open();
		}
	}

	@Override
	public void cancel() {
		shlAddTeacher.dispose();
	}
}
