package gui.dialogs;

import gui.GuiDialog;
import gui.GuiTools;
import java.sql.Date;
import java.util.ArrayList;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import data.SimpleDate;
import database.Row;
import database.query.AbilityDescriptionQuery;
import database.query.AddressQuery;
import database.query.ClassQuery;
import database.query.StudentQuery;
import org.eclipse.swt.widgets.Link;
import log.Log;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class StudentDialog extends GuiDialog {

	protected Object result;
	protected Shell shlAddStudent;
	private Combo studentClass;
	private Text studentName;
	private Text studentSurname;
	private Text addressStreet;
	private Text addressNo;
	private Text addressZip;
	private Text addressCity;
	private Text addressCountry;
	private Text studentPhone;
	private DateTime studentBirthday;
	private Spinner studentYear;
	private StyledText studentNotes;
	private Combo studentAbility;
	private Row loadedData;
	private Row loadedAddress;

	public StudentDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAddStudent.open();
		shlAddStudent.layout();
		Display display = getParent().getDisplay();
		while (!shlAddStudent.isDisposed()) {
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
		shlAddStudent = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAddStudent.setSize(640, 700);
		shlAddStudent.setText("Add student");
		shlAddStudent.setLayout(new FormLayout());
		
		Canvas canvas = new Canvas(shlAddStudent, SWT.BORDER);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				Log.info("Image selection has not been implemented yet!");
				GuiTools.showMessageBox(shlAddStudent, "Image selection has not been implemented yet!");
			}
		});
		canvas.setBackground(SWTResourceManager.getColor(255, 255, 240));
		canvas.setToolTipText("Click to add an image");
		FormData fd_canvas = new FormData();
		fd_canvas.bottom = new FormAttachment(0, 160);
		fd_canvas.right = new FormAttachment(0, 160);
		fd_canvas.top = new FormAttachment(0, 10);
		fd_canvas.left = new FormAttachment(0, 10);
		canvas.setLayoutData(fd_canvas);
		
		Label lblName = new Label(shlAddStudent, SWT.NONE);
		FormData fd_lblName = new FormData();
		fd_lblName.top = new FormAttachment(canvas, 0, SWT.TOP);
		fd_lblName.left = new FormAttachment(canvas, 6);
		lblName.setLayoutData(fd_lblName);
		lblName.setText("Name *");
		
		studentName = new Text(shlAddStudent, SWT.BORDER);
		FormData fd_studentName = new FormData();
		fd_studentName.right = new FormAttachment(canvas, 464, SWT.RIGHT);
		fd_studentName.bottom = new FormAttachment(lblName, 27, SWT.BOTTOM);
		fd_studentName.top = new FormAttachment(lblName, 6);
		fd_studentName.left = new FormAttachment(canvas, 6);
		studentName.setLayoutData(fd_studentName);
		
		Label lblSurname = new Label(shlAddStudent, SWT.NONE);
		FormData fd_lblSurname = new FormData();
		fd_lblSurname.top = new FormAttachment(studentName, 6);
		fd_lblSurname.left = new FormAttachment(canvas, 6);
		lblSurname.setLayoutData(fd_lblSurname);
		lblSurname.setText("Surname *");
		
		studentSurname = new Text(shlAddStudent, SWT.BORDER);
		FormData fd_studentSurname = new FormData();
		fd_studentSurname.right = new FormAttachment(studentName, 0, SWT.RIGHT);
		fd_studentSurname.top = new FormAttachment(lblSurname, 6);
		fd_studentSurname.left = new FormAttachment(canvas, 6);
		studentSurname.setLayoutData(fd_studentSurname);
		
		Label lblBirthday = new Label(shlAddStudent, SWT.NONE);
		FormData fd_lblBirthday = new FormData();
		fd_lblBirthday.top = new FormAttachment(studentSurname, 6);
		fd_lblBirthday.left = new FormAttachment(canvas, 6);
		lblBirthday.setLayoutData(fd_lblBirthday);
		lblBirthday.setText("Birthday *");
		
		studentBirthday = new DateTime(shlAddStudent, SWT.BORDER | SWT.DROP_DOWN);
		FormData fd_studentBirthday = new FormData();
		fd_studentBirthday.left = new FormAttachment(canvas, 6);
		fd_studentBirthday.top = new FormAttachment(lblBirthday, 6);
		studentBirthday.setLayoutData(fd_studentBirthday);
		
		Label lblEnrolmentYear = new Label(shlAddStudent, SWT.NONE);
		FormData fd_lblEnrolmentYear = new FormData();
		fd_lblEnrolmentYear.top = new FormAttachment(studentSurname, 6);
		lblEnrolmentYear.setLayoutData(fd_lblEnrolmentYear);
		lblEnrolmentYear.setText("Enrolment Year *");
		
		studentYear = new Spinner(shlAddStudent, SWT.BORDER);
		fd_studentBirthday.right = new FormAttachment(studentYear, -28);
		studentYear.setPageIncrement(5);
		studentYear.setMaximum(2100);
		studentYear.setMinimum(1990);
		FormData fd_studentYear = new FormData();
		fd_studentYear.right = new FormAttachment(lblEnrolmentYear, 0, SWT.RIGHT);
		fd_studentYear.top = new FormAttachment(lblEnrolmentYear, 6);
		fd_studentYear.left = new FormAttachment(0, 300);
		studentYear.setLayoutData(fd_studentYear);
		
		Label lblClass = new Label(shlAddStudent, SWT.NONE);
		fd_lblEnrolmentYear.right = new FormAttachment(100, -253);
		FormData fd_lblClass = new FormData();
		fd_lblClass.top = new FormAttachment(studentSurname, 6);
		fd_lblClass.left = new FormAttachment(lblEnrolmentYear, 39);
		lblClass.setLayoutData(fd_lblClass);
		lblClass.setText("Class *");
		
		studentClass = new Combo(shlAddStudent, SWT.READ_ONLY);
		FormData fd_studentClass = new FormData();
		fd_studentClass.right = new FormAttachment(studentName, 0, SWT.RIGHT);
		fd_studentClass.left = new FormAttachment(studentYear, 38);
		fd_studentClass.top = new FormAttachment(lblClass, 6);
		studentClass.setLayoutData(fd_studentClass);
		studentClass.select(0);
		
		Link link_1 = new Link(shlAddStudent, 0);
		link_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ClassDialog classAddDialog = new ClassDialog(shlAddStudent);
				classAddDialog.open();
				updateClassField();
			}
		});
		link_1.setText("[<a>New</a>]");
		FormData fd_link_1 = new FormData();
		fd_link_1.bottom = new FormAttachment(lblBirthday, 0, SWT.BOTTOM);
		fd_link_1.left = new FormAttachment(lblClass, 6);
		link_1.setLayoutData(fd_link_1);
		
		Group grpAddress = new Group(shlAddStudent, SWT.NONE);
		grpAddress.setText("Address");
		FormData fd_grpAddress = new FormData();
		fd_grpAddress.bottom = new FormAttachment(canvas, 131, SWT.BOTTOM);
		fd_grpAddress.top = new FormAttachment(canvas, 6);
		fd_grpAddress.right = new FormAttachment(studentName, 0, SWT.RIGHT);
		fd_grpAddress.left = new FormAttachment(0, 10);
		grpAddress.setLayoutData(fd_grpAddress);
		
		Link link_2 = new Link(grpAddress, SWT.NONE);
		link_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AddressSelectionDialog addressDialog = new AddressSelectionDialog(shlAddStudent);
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
		
		Group grpAdditionalInformation = new Group(shlAddStudent, SWT.NONE);
		grpAdditionalInformation.setText("Additional Information");
		FormData fd_grpAdditionalInformation = new FormData();
		fd_grpAdditionalInformation.bottom = new FormAttachment(grpAddress, 326, SWT.BOTTOM);
		fd_grpAdditionalInformation.top = new FormAttachment(grpAddress, 6);
		fd_grpAdditionalInformation.right = new FormAttachment(studentName, 0, SWT.RIGHT);
		fd_grpAdditionalInformation.left = new FormAttachment(0, 10);
		grpAdditionalInformation.setLayoutData(fd_grpAdditionalInformation);
		
		Link link = new Link(grpAdditionalInformation, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AbilityDescriptionDialog abilityDescriptionDialog = new AbilityDescriptionDialog(shlAddStudent);
				abilityDescriptionDialog.open();
				updateAbilityDescriptionField();
			}
		});
		link.setBounds(424, 4, 124, 15);
		link.setText("[<a>New</a>]");
		
		Label label = new Label(grpAdditionalInformation, SWT.NONE);
		label.setText("Phone");
		label.setBounds(10, 10, 124, 15);
		
		studentPhone = new Text(grpAdditionalInformation, SWT.BORDER);
		studentPhone.setBounds(10, 24, 240, 21);
		
		Label lblAbilityDescription = new Label(grpAdditionalInformation, SWT.NONE);
		lblAbilityDescription.setBounds(268, 5, 145, 15);
		lblAbilityDescription.setText("Ability Description");
		
		studentAbility = new Combo(grpAdditionalInformation, SWT.READ_ONLY);
		studentAbility.setBounds(268, 24, 336, 23);
		
		Label lblNotes = new Label(grpAdditionalInformation, SWT.NONE);
		lblNotes.setBounds(10, 51, 55, 15);
		lblNotes.setText("Notes");
		
		studentNotes = new StyledText(grpAdditionalInformation, SWT.BORDER);
		studentNotes.setBounds(10, 72, 594, 211);
		
		Button btnSave = new Button(shlAddStudent, SWT.CENTER);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.bottom = new FormAttachment(100, -10);
		fd_btnSave.right = new FormAttachment(studentName, 0, SWT.RIGHT);
		fd_btnSave.width = 80;
		fd_btnSave.left = new FormAttachment(100, -121);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlAddStudent, SWT.NONE);
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
		
		Label label_1 = new Label(shlAddStudent, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(btnSave, -6);
		fd_label_1.top = new FormAttachment(grpAdditionalInformation, 6);
		fd_label_1.right = new FormAttachment(studentName, 0, SWT.RIGHT);
		fd_label_1.left = new FormAttachment(0, 10);
		label_1.setLayoutData(fd_label_1);
		
		Label infoLabel = new Label(shlAddStudent, SWT.CENTER);
		infoLabel.setAlignment(SWT.CENTER);
		infoLabel.setEnabled(false);
		FormData fd_infoLabel = new FormData();
		fd_infoLabel.top = new FormAttachment(0);
		fd_infoLabel.left = new FormAttachment(lblName, 19);
		fd_infoLabel.width = 200;
		infoLabel.setLayoutData(fd_infoLabel);
		
		Label lblMandatoryFields = new Label(shlAddStudent, SWT.NONE);
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
			updateClassField();
			updateAbilityDescriptionField();
			
			if(loadedData != null) {
				shlAddStudent.setText("Modify student data");
				studentName.setText(loadedData.getValueAsString("name"));
				studentSurname.setText(loadedData.getValueAsString("surname"));
				SimpleDate date = new SimpleDate((Date)loadedData.getValue("birthday"));
				studentBirthday.setDate(date.getYear(), date.getMonth(), date.getDay());
				studentYear.setSelection(loadedData.getValueAsInt("enrolment_year"));
				
				String classString = loadedData.getValueAsStringNotNull("level") + loadedData.getValueAsStringNotNull("stream");
				studentClass.select(studentClass.indexOf(classString));
				
				studentPhone.setText(loadedData.getValueAsStringNotNull("phonenumber"));
				studentNotes.setText(loadedData.getValueAsStringNotNull("student_notes"));
				
				String abilityString = loadedData.getValueAsStringNotNull("description");
				studentAbility.select(studentAbility.indexOf(abilityString));
				
				loadedAddress = loadedData;
			}
			
			updateAddressFields();

		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlAddStudent, e.getMessage());
		}
	}

	private void updateAbilityDescriptionField() {
		studentAbility.removeAll();
		studentAbility.add("-");
		studentAbility.setData("-", null);
		studentAbility.select(studentAbility.indexOf("-"));
		try {
			for(Row ab : AbilityDescriptionQuery.getFullDataset()) {
				String abilityString = ab.getValueAsStringNotNull("description");
				studentAbility.add(abilityString);
				studentAbility.setData(abilityString, ab.getValue("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlAddStudent, e.getMessage());
		}
	}

	private void updateClassField() {
		studentClass.removeAll();
		try {
			for(Row cl : ClassQuery.getFullDataset()) {
				String classString = cl.getValueAsStringNotNull("level") + cl.getValueAsStringNotNull("stream");
				studentClass.add(classString);
				studentClass.setData(classString, cl.getValue("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlAddStudent, e.getMessage());
		}
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
	public void loadData(Object data) throws Exception {
		ArrayList<Row> students = StudentQuery.getDataset(data);
		if(students.size() == 0) {
			throw new Exception("No student with ID " + data.toString() + " found.");
		}
		loadedData = students.get(0);
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

			Row newStudent = new Row();
			newStudent.setValue("name", GuiTools.nullIfEmptyTrimmed(studentName.getText()));
			newStudent.setValue("surname", GuiTools.nullIfEmptyTrimmed(studentSurname.getText()));
			newStudent.setValue("birthday", new SimpleDate(studentBirthday.getDay(), studentBirthday.getMonth(), studentBirthday.getYear()));
			newStudent.setValue("enrolment_year", studentYear.getSelection());
			newStudent.setValue("phonenumber", GuiTools.nullIfEmptyTrimmed(studentPhone.getText()));
			newStudent.setValue("notes", GuiTools.nullIfEmptyTrimmed(studentNotes.getText()));
			newStudent.setValue("class_id", studentClass.getData(studentClass.getText()));
			newStudent.setValue("ability_description_id", studentAbility.getData(studentAbility.getText()));
			newStudent.setValue("address_id", addressId);
			
			if(loadedData == null) {
				StudentQuery.insert(newStudent);
			} else {
				StudentQuery.update(loadedData.getValueAsLong("student_id"), newStudent);
			}

			shlAddStudent.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			
			if(addressId != null) {
				//TODO solve this with a SQL transaction with 2 inserts/updates!
				Log.info("Address with ID " + addressId + " already inserted, but without a valid student record. Deleting...");
				try {
					AddressQuery.delete(addressId);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			GuiTools.showMessageBox(shlAddStudent, e.getMessage());
		}
	}

	@Override
	public void cancel() {
		shlAddStudent.dispose();		
	}
}
