package gui;

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
import data.Class;
import org.eclipse.swt.widgets.Link;

public class StudentsAdd extends Dialog {

	protected Object result;
	protected Shell shlAddStudent;
	private Text text;
	private Text text_1;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_2;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public StudentsAdd(Shell parent, int style) {
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
		
		text = new Text(shlAddStudent, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(canvas, 464, SWT.RIGHT);
		fd_text.bottom = new FormAttachment(lblName, 27, SWT.BOTTOM);
		fd_text.top = new FormAttachment(lblName, 6);
		fd_text.left = new FormAttachment(canvas, 6);
		text.setLayoutData(fd_text);
		
		Label lblSurname = new Label(shlAddStudent, SWT.NONE);
		FormData fd_lblSurname = new FormData();
		fd_lblSurname.top = new FormAttachment(text, 6);
		fd_lblSurname.left = new FormAttachment(canvas, 6);
		lblSurname.setLayoutData(fd_lblSurname);
		lblSurname.setText("Surname");
		
		text_1 = new Text(shlAddStudent, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_text_1.top = new FormAttachment(lblSurname, 6);
		fd_text_1.left = new FormAttachment(canvas, 6);
		text_1.setLayoutData(fd_text_1);
		
		Label lblBirthday = new Label(shlAddStudent, SWT.NONE);
		FormData fd_lblBirthday = new FormData();
		fd_lblBirthday.top = new FormAttachment(text_1, 6);
		fd_lblBirthday.left = new FormAttachment(canvas, 6);
		lblBirthday.setLayoutData(fd_lblBirthday);
		lblBirthday.setText("Birthday");
		
		DateTime dateTime = new DateTime(shlAddStudent, SWT.BORDER);
		FormData fd_dateTime = new FormData();
		fd_dateTime.left = new FormAttachment(canvas, 6);
		fd_dateTime.top = new FormAttachment(lblBirthday, 6);
		dateTime.setLayoutData(fd_dateTime);
		
		Label lblEnrolmentYear = new Label(shlAddStudent, SWT.NONE);
		FormData fd_lblEnrolmentYear = new FormData();
		fd_lblEnrolmentYear.top = new FormAttachment(text_1, 6);
		lblEnrolmentYear.setLayoutData(fd_lblEnrolmentYear);
		lblEnrolmentYear.setText("Enrolment Year");
		
		Spinner spinner = new Spinner(shlAddStudent, SWT.BORDER);
		fd_dateTime.right = new FormAttachment(spinner, -28);
		spinner.setPageIncrement(5);
		spinner.setMaximum(2100);
		spinner.setMinimum(1990);
		FormData fd_spinner = new FormData();
		fd_spinner.right = new FormAttachment(lblEnrolmentYear, 0, SWT.RIGHT);
		fd_spinner.top = new FormAttachment(lblEnrolmentYear, 6);
		fd_spinner.left = new FormAttachment(0, 300);
		spinner.setLayoutData(fd_spinner);
		
		Label lblClass = new Label(shlAddStudent, SWT.NONE);
		fd_lblEnrolmentYear.right = new FormAttachment(100, -253);
		FormData fd_lblClass = new FormData();
		fd_lblClass.top = new FormAttachment(text_1, 6);
		fd_lblClass.left = new FormAttachment(lblEnrolmentYear, 39);
		lblClass.setLayoutData(fd_lblClass);
		lblClass.setText("Class");
		
		Combo comboClasses = new Combo(shlAddStudent, SWT.NONE);
		FormData fd_comboClasses = new FormData();
		fd_comboClasses.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_comboClasses.left = new FormAttachment(spinner, 38);
		fd_comboClasses.top = new FormAttachment(lblClass, 6);
		comboClasses.setLayoutData(fd_comboClasses);
		comboClasses.select(0);
		
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
		fd_grpAddress.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_grpAddress.left = new FormAttachment(0, 10);
		grpAddress.setLayoutData(fd_grpAddress);
		
		Label lblStreet = new Label(grpAddress, SWT.NONE);
		lblStreet.setBounds(10, 26, 55, 15);
		lblStreet.setText("Street");
		
		text_3 = new Text(grpAddress, SWT.BORDER);
		text_3.setBounds(10, 47, 492, 21);
		
		Label lblNo = new Label(grpAddress, SWT.NONE);
		lblNo.setBounds(508, 26, 35, 15);
		lblNo.setText("No.");
		
		text_4 = new Text(grpAddress, SWT.BORDER);
		text_4.setBounds(508, 47, 96, 21);
		
		Label lblNewLabel = new Label(grpAddress, SWT.NONE);
		lblNewLabel.setBounds(10, 74, 55, 15);
		lblNewLabel.setText("ZIP Code");
		
		text_5 = new Text(grpAddress, SWT.BORDER);
		text_5.setBounds(10, 91, 130, 21);
		
		Label lblNewLabel_1 = new Label(grpAddress, SWT.NONE);
		lblNewLabel_1.setBounds(170, 74, 55, 15);
		lblNewLabel_1.setText("City");
		
		text_6 = new Text(grpAddress, SWT.BORDER);
		text_6.setBounds(170, 91, 200, 21);
		
		Label lblCountry = new Label(grpAddress, SWT.NONE);
		lblCountry.setBounds(396, 74, 55, 15);
		lblCountry.setText("Country");
		
		text_7 = new Text(grpAddress, SWT.BORDER);
		text_7.setBounds(396, 91, 208, 21);
		
		Group grpAdditionalInformation = new Group(shlAddStudent, SWT.NONE);
		grpAdditionalInformation.setText("Optional Information");
		FormData fd_grpAdditionalInformation = new FormData();
		fd_grpAdditionalInformation.bottom = new FormAttachment(grpAddress, 326, SWT.BOTTOM);
		fd_grpAdditionalInformation.top = new FormAttachment(grpAddress, 6);
		
		Link link_2 = new Link(grpAddress, SWT.NONE);
		link_2.setBounds(61, 0, 146, 15);
		link_2.setText("[<a>Choose existing address</a>]");
		fd_grpAdditionalInformation.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_grpAdditionalInformation.left = new FormAttachment(0, 10);
		grpAdditionalInformation.setLayoutData(fd_grpAdditionalInformation);
		
		Link link = new Link(grpAdditionalInformation, SWT.NONE);
		link.setBounds(369, 26, 64, 15);
		link.setText("[<a>New</a>]");
		
		Label label = new Label(grpAdditionalInformation, SWT.NONE);
		label.setText("Phone");
		label.setBounds(10, 26, 34, 15);
		
		text_2 = new Text(grpAdditionalInformation, SWT.BORDER);
		text_2.setBounds(10, 47, 240, 21);
		
		Label lblAbilityDescription = new Label(grpAdditionalInformation, SWT.NONE);
		lblAbilityDescription.setBounds(268, 25, 124, 15);
		lblAbilityDescription.setText("Ability Description");
		
		Combo combo_1 = new Combo(grpAdditionalInformation, SWT.NONE);
		combo_1.setBounds(268, 46, 336, 23);
		
		Label lblNotes = new Label(grpAdditionalInformation, SWT.NONE);
		lblNotes.setBounds(10, 83, 55, 15);
		lblNotes.setText("Notes");
		
		StyledText styledText = new StyledText(grpAdditionalInformation, SWT.BORDER);
		styledText.setBounds(10, 104, 594, 195);
		
		Button btnSave = new Button(shlAddStudent, SWT.CENTER);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.bottom = new FormAttachment(100, -10);
		fd_btnSave.right = new FormAttachment(text, 0, SWT.RIGHT);
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
		fd_label_1.right = new FormAttachment(text, 0, SWT.RIGHT);
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
				comboClasses.add(cl.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
