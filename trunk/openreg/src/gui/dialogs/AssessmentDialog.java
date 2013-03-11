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
import database.query.AssessmentQuery;
import database.query.AssessmentTypeQuery;
import database.query.TopicQuery;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AssessmentDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	private Text text;
	private Row loadedDescription;
	private Combo comboType;
	private Combo comboTopic;
	private Text text_1;
	
	public AssessmentDialog(Shell parent) {
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
		shlDialog.setSize(301, 312);
		shlDialog.setText("Add a new assessment description");
		shlDialog.setLayout(new FormLayout());
		
		Label lblDescription = new Label(shlDialog, SWT.NONE);
		FormData fd_lblDescription = new FormData();
		fd_lblDescription.top = new FormAttachment(0, 10);
		fd_lblDescription.left = new FormAttachment(0, 10);
		lblDescription.setLayoutData(fd_lblDescription);
		lblDescription.setText("Description *");
		
		Button btnSave = new Button(shlDialog, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		FormData fd_btnSave = new FormData();
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlDialog, SWT.NONE);
		fd_btnSave.top = new FormAttachment(btnCancel, 0, SWT.TOP);
		fd_btnSave.left = new FormAttachment(0, 209);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.right = new FormAttachment(btnSave, -6);
		fd_btnCancel.bottom = new FormAttachment(100, -21);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Label lblMandatoryFields = new Label(shlDialog, SWT.NONE);
		fd_btnCancel.left = new FormAttachment(lblMandatoryFields, 6);
		FormData fd_lblMandatoryFields = new FormData();
		fd_lblMandatoryFields.top = new FormAttachment(btnSave, 5, SWT.TOP);
		fd_lblMandatoryFields.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		lblMandatoryFields.setLayoutData(fd_lblMandatoryFields);
		lblMandatoryFields.setText("* Mandatory Fields");
		
		text = new Text(shlDialog, SWT.BORDER);
		fd_btnSave.right = new FormAttachment(text, 0, SWT.RIGHT);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(0, 10);
		fd_text.right = new FormAttachment(100, -10);
		fd_text.top = new FormAttachment(lblDescription, 6);
		text.setLayoutData(fd_text);
		
		comboType = new Combo(shlDialog, SWT.NONE);
		FormData fd_comboType = new FormData();
		fd_comboType.right = new FormAttachment(100, -10);
		fd_comboType.top = new FormAttachment(text, 38);
		comboType.setLayoutData(fd_comboType);
		
		comboTopic = new Combo(shlDialog, SWT.NONE);
		FormData fd_comboTopic = new FormData();
		fd_comboTopic.right = new FormAttachment(100, -10);
		fd_comboTopic.top = new FormAttachment(comboType, 12);
		comboTopic.setLayoutData(fd_comboTopic);
		
		Label lblSelectType = new Label(shlDialog, SWT.NONE);
		fd_comboType.left = new FormAttachment(lblSelectType, 36);
		FormData fd_lblSelectType = new FormData();
		fd_lblSelectType.top = new FormAttachment(comboType, 0, SWT.TOP);
		fd_lblSelectType.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		lblSelectType.setLayoutData(fd_lblSelectType);
		lblSelectType.setText("Select type");
		
		Label lblSelectMainToptic = new Label(shlDialog, SWT.NONE);
		fd_comboTopic.left = new FormAttachment(lblSelectMainToptic, 32);
		FormData fd_lblSelectMainToptic = new FormData();
		fd_lblSelectMainToptic.top = new FormAttachment(comboTopic, 0, SWT.TOP);
		fd_lblSelectMainToptic.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		lblSelectMainToptic.setLayoutData(fd_lblSelectMainToptic);
		lblSelectMainToptic.setText("Select topic");
		
		Link link = new Link(shlDialog, SWT.NONE);
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				AssessmentTypeDialog dialog = new AssessmentTypeDialog(shlDialog);
				dialog.open();
				updateTypeField();
				updateTopicField();
			}
		});
		FormData fd_link = new FormData();
		fd_link.left = new FormAttachment(0, 10);
		fd_link.right = new FormAttachment(100, -10);
		fd_link.bottom = new FormAttachment(comboType, -17);
		fd_link.top = new FormAttachment(text, 6);
		link.setLayoutData(fd_link);
		link.setText("<a>Add a new Assessment Type</a>");
		
		text_1 = new Text(shlDialog, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.bottom = new FormAttachment(100, -94);
		fd_text_1.top = new FormAttachment(comboTopic, 10);
		fd_text_1.left = new FormAttachment(comboType, 0, SWT.LEFT);
		fd_text_1.right = new FormAttachment(100, -10);
		text_1.setLayoutData(fd_text_1);
		
		Label lblNotes = new Label(shlDialog, SWT.NONE);
		FormData fd_lblNotes = new FormData();
		fd_lblNotes.top = new FormAttachment(text_1, 3, SWT.TOP);
		fd_lblNotes.left = new FormAttachment(0, 10);
		lblNotes.setLayoutData(fd_lblNotes);
		lblNotes.setText("Notes");

		update();
	}
	
	@Override
	public void loadData(Object data) throws Exception {
		if(data == null) {
			throw new Exception("Set a valid data ID to load this dialog!");
		}
		ArrayList<Row> top = AssessmentQuery.getDataset(data);
		if(top.size() == 0) {
			throw new Exception("No topic description with ID " + data.toString() + " found.");
		}
		loadedDescription = top.get(0); 
	}

	@Override
	public void store() {
		try {
			Row newAssessment = new Row();
			newAssessment.setValue("description", GuiTools.nullIfEmptyTrimmed(text.getText()));
			newAssessment.setValue("assessment_type_id", comboType.getData(comboType.getText()));
			newAssessment.setValue("topic_id", comboTopic.getData(comboTopic.getText()));
			newAssessment.setValue("notes", GuiTools.nullIfEmptyTrimmed(text_1.getText()));
			
			if(loadedDescription == null) {
				result = AssessmentQuery.insert(newAssessment);	
			} else {
				AssessmentQuery.update(loadedDescription.getValueAsLong("assessment_id"), newAssessment);
			}
			
			shlDialog.close();
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	@Override
	public void update() {
		updateTypeField();
		updateTopicField();
		try {
			if (loadedDescription != null){
				text.setText(loadedDescription.getValueAsStringNotNull("assessment_description"));
				text_1.setText(loadedDescription.getValueAsStringNotNull("notes"));
				
				String typeString = loadedDescription.getValueAsStringNotNull("assessment_type_description");
				comboType.select(comboType.indexOf(typeString));
				
				String topicString = loadedDescription.getValueAsStringNotNull("topic_description");
				comboTopic.select(comboTopic.indexOf(topicString));
				
				shlDialog.setText("Modify a topic description");
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}
	
	private void updateTypeField() {
		comboType.removeAll();
		try {
			for(Row ast : AssessmentTypeQuery.getFullDataset()) {
				String typeString = ast.getValueAsStringNotNull("description");
				comboType.add(typeString);
				comboType.setData(typeString, ast.getValue("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}
	
	private void updateTopicField() {
		comboTopic.removeAll();
		try {
			for(Row to : TopicQuery.getFullDataset()) {
				String topicString = to.getValueAsStringNotNull("description");
				comboTopic.add(topicString);
				comboTopic.setData(topicString, to.getValue("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
