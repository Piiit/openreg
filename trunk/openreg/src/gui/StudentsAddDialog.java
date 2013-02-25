package gui;

import java.security.SecureRandom;
import java.util.Random;

import org.eclipse.swt.widgets.Dialog;
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

import data.Address;
import data.Class;
import data.SimpleDate;
import data.Student;
import database.DatabaseTools;

import org.eclipse.swt.widgets.Link;

public class StudentsAddDialog extends Dialog {

	protected Object result;
	protected Shell shlAddStudent;
	private Text studentName;
	private Text studentSurname;
	private Text addressStreet;
	private Text addressNo;
	private Text addressZip;
	private Text addressCity;
	private Text addressCountry;
	private Text studentPhone;
	private Long addressID;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public StudentsAddDialog(Shell parent, int style) {
		super(parent, style);
		setText("Add a new student");
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
		lblName.setText("Name");
		
		studentName = new Text(shlAddStudent, SWT.BORDER);
		studentName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
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
		lblSurname.setText("Surname");
		
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
		lblBirthday.setText("Birthday");
		
		final DateTime studentBirthday = new DateTime(shlAddStudent, SWT.BORDER);
		FormData fd_studentBirthday = new FormData();
		fd_studentBirthday.left = new FormAttachment(canvas, 6);
		fd_studentBirthday.top = new FormAttachment(lblBirthday, 6);
		studentBirthday.setLayoutData(fd_studentBirthday);
		
		Label lblEnrolmentYear = new Label(shlAddStudent, SWT.NONE);
		FormData fd_lblEnrolmentYear = new FormData();
		fd_lblEnrolmentYear.top = new FormAttachment(studentSurname, 6);
		lblEnrolmentYear.setLayoutData(fd_lblEnrolmentYear);
		lblEnrolmentYear.setText("Enrolment Year");
		
		final Spinner studentYear = new Spinner(shlAddStudent, SWT.BORDER);
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
		lblClass.setText("Class");
		
		final Combo studentClass = new Combo(shlAddStudent, SWT.READ_ONLY);
		FormData fd_studentClass = new FormData();
		fd_studentClass.right = new FormAttachment(studentName, 0, SWT.RIGHT);
		fd_studentClass.left = new FormAttachment(studentYear, 38);
		fd_studentClass.top = new FormAttachment(lblClass, 6);
		studentClass.setLayoutData(fd_studentClass);
		studentClass.select(0);
		
		Link link_1 = new Link(shlAddStudent, 0);
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
		link_2.setBounds(61, 0, 146, 15);
		link_2.setText("[<a>Choose existing address</a>]");
		
		Label lblStreet = new Label(grpAddress, SWT.NONE);
		lblStreet.setBounds(10, 26, 55, 15);
		lblStreet.setText("Street");
		
		addressStreet = new Text(grpAddress, SWT.BORDER);
		addressStreet.setBounds(10, 47, 492, 21);
		
		Label lblNo = new Label(grpAddress, SWT.NONE);
		lblNo.setBounds(508, 26, 35, 15);
		lblNo.setText("No.");
		
		addressNo = new Text(grpAddress, SWT.BORDER);
		addressNo.setBounds(508, 47, 96, 21);
		
		Label lblNewLabel = new Label(grpAddress, SWT.NONE);
		lblNewLabel.setBounds(10, 74, 55, 15);
		lblNewLabel.setText("ZIP Code");
		
		addressZip = new Text(grpAddress, SWT.BORDER);
		addressZip.setBounds(10, 91, 130, 21);
		
		Label lblNewLabel_1 = new Label(grpAddress, SWT.NONE);
		lblNewLabel_1.setBounds(170, 74, 55, 15);
		lblNewLabel_1.setText("City");
		
		addressCity = new Text(grpAddress, SWT.BORDER);
		addressCity.setBounds(170, 91, 200, 21);
		
		Label lblCountry = new Label(grpAddress, SWT.NONE);
		lblCountry.setBounds(396, 74, 55, 15);
		lblCountry.setText("Country");
		
		addressCountry = new Text(grpAddress, SWT.BORDER);
		addressCountry.setBounds(396, 91, 208, 21);
		
		Group grpAdditionalInformation = new Group(shlAddStudent, SWT.NONE);
		grpAdditionalInformation.setText("Optional Information");
		FormData fd_grpAdditionalInformation = new FormData();
		fd_grpAdditionalInformation.bottom = new FormAttachment(grpAddress, 326, SWT.BOTTOM);
		fd_grpAdditionalInformation.top = new FormAttachment(grpAddress, 6);
		fd_grpAdditionalInformation.right = new FormAttachment(studentName, 0, SWT.RIGHT);
		fd_grpAdditionalInformation.left = new FormAttachment(0, 10);
		grpAdditionalInformation.setLayoutData(fd_grpAdditionalInformation);
		
		Link link = new Link(grpAdditionalInformation, SWT.NONE);
		link.setBounds(369, 26, 64, 15);
		link.setText("[<a>New</a>]");
		
		Label label = new Label(grpAdditionalInformation, SWT.NONE);
		label.setText("Phone");
		label.setBounds(10, 26, 124, 15);
		
		studentPhone = new Text(grpAdditionalInformation, SWT.BORDER);
		studentPhone.setBounds(10, 47, 240, 21);
		
		Label lblAbilityDescription = new Label(grpAdditionalInformation, SWT.NONE);
		lblAbilityDescription.setBounds(268, 25, 124, 15);
		lblAbilityDescription.setText("Ability Description");
		
		Combo studentAbility = new Combo(grpAdditionalInformation, SWT.READ_ONLY);
		studentAbility.setBounds(268, 46, 336, 23);
		
		Label lblNotes = new Label(grpAdditionalInformation, SWT.NONE);
		lblNotes.setBounds(10, 83, 55, 15);
		lblNotes.setText("Notes");
		
		StyledText studentNotes = new StyledText(grpAdditionalInformation, SWT.BORDER);
		studentNotes.setBounds(10, 104, 594, 195);
		
		Button btnSave = new Button(shlAddStudent, SWT.CENTER);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(addressID == null) {
					addressID = DatabaseTools.getRandomID();
				}
				Address newAddress = new Address(
						addressID,
						addressStreet.getText(),
						addressNo.getText(),
						addressZip.getText(),
						addressCity.getText(),
						addressCountry.getText()
						);
				Student newStudent = new Student(
						studentName.getText(),
						studentSurname.getText(),
						new SimpleDate(studentBirthday.getDay(), studentBirthday.getMonth(), studentBirthday.getYear()),
						studentYear.getSelection(),
						(Long)studentClass.getData(studentClass.getText()),
						addressID
						);
				try {
					newAddress.store();
					newStudent.store();
					shlAddStudent.dispose();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addressID = null;
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
		
		try {
			for(Class cl : Class.getAllClasses()) {
				studentClass.add(cl.toString());
				studentClass.setData(cl.toString(), cl.getID());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}