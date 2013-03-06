package gui.dialogs;

import java.util.ArrayList;

import log.Log;

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
import database.query.CourseQuery;
import database.query.TopicQuery;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Combo;

public class TopicDialog extends GuiDialog {

	protected Object result;
	protected Shell shlDialog;
	private Text text;
	private Row loadedDescription;
	private Combo comboCourse;
	private Combo comboMainTopic;
	
	public TopicDialog(Shell parent) {
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
		shlDialog.setSize(300, 276);
		shlDialog.setText("Add a new topic description");
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
		fd_btnSave.bottom = new FormAttachment(100, -39);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlDialog, SWT.NONE);
		fd_btnSave.left = new FormAttachment(0, 209);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.top = new FormAttachment(btnSave, 0, SWT.TOP);
		fd_btnCancel.right = new FormAttachment(btnSave, -6);
		fd_btnCancel.left = new FormAttachment(0, 115);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Label lblMandatoryFields = new Label(shlDialog, SWT.NONE);
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
		
		comboCourse = new Combo(shlDialog, SWT.NONE);
		FormData fd_comboCourse = new FormData();
		fd_comboCourse.right = new FormAttachment(100, -10);
		fd_comboCourse.left = new FormAttachment(0, 149);
		comboCourse.setLayoutData(fd_comboCourse);
		
		comboMainTopic = new Combo(shlDialog, SWT.NONE);
		fd_comboCourse.bottom = new FormAttachment(100, -146);
		FormData fd_comboMainTopic = new FormData();
		fd_comboMainTopic.right = new FormAttachment(btnSave, 0, SWT.RIGHT);
		fd_comboMainTopic.top = new FormAttachment(comboCourse, 24);
		fd_comboMainTopic.left = new FormAttachment(comboCourse, 0, SWT.LEFT);
		comboMainTopic.setLayoutData(fd_comboMainTopic);
		
		Label lblSelectCourse = new Label(shlDialog, SWT.NONE);
		FormData fd_lblSelectCourse = new FormData();
		fd_lblSelectCourse.top = new FormAttachment(comboCourse, 0, SWT.TOP);
		fd_lblSelectCourse.left = new FormAttachment(0, 10);
		lblSelectCourse.setLayoutData(fd_lblSelectCourse);
		lblSelectCourse.setText("Select course");
		
		Label lblSelectMainToptic = new Label(shlDialog, SWT.NONE);
		FormData fd_lblSelectMainToptic = new FormData();
		fd_lblSelectMainToptic.top = new FormAttachment(comboMainTopic, 0, SWT.TOP);
		fd_lblSelectMainToptic.left = new FormAttachment(lblDescription, 0, SWT.LEFT);
		lblSelectMainToptic.setLayoutData(fd_lblSelectMainToptic);
		lblSelectMainToptic.setText("Select main toptic");

		update();
	}
	
	@Override
	public void loadData(Object data) throws Exception {
		ArrayList<Row> top = TopicQuery.getDataset(data);
		if(top.size() == 0) {
			throw new Exception("No topic description with ID " + data.toString() + " found.");
		}
		loadedDescription = top.get(0); 
	}

	@Override
	public void store() {
		try {
			Row newTopic = new Row();
			newTopic.setValue("description", GuiTools.nullIfEmptyTrimmed(text.getText()));
			newTopic.setValue("course_id", comboCourse.getData(comboCourse.getText()));
			newTopic.setValue("topic_id", comboMainTopic.getData(comboMainTopic.getText()));
			
			if(loadedDescription == null) {
				TopicQuery.insert(newTopic);	
			} else {
				TopicQuery.update(loadedDescription.getValueAsLong("id"), newTopic);
			}
			
			shlDialog.close();
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(shlDialog, e.getMessage());
		}
	}

	@Override
	public void update() {
		updateCourseField();
		updateMainTopicField();
		try {
			if (loadedDescription != null){
				text.setText(loadedDescription.getValueAsStringNotNull("sub_topic_description"));
				
				String courseString = loadedDescription.getValueAsStringNotNull("name");
				comboCourse.select(comboCourse.indexOf(courseString));
				
				String mainTopicString = loadedDescription.getValueAsStringNotNull("description");
				comboMainTopic.select(comboMainTopic.indexOf(mainTopicString));
				
				shlDialog.setText("Modify a topic description");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateCourseField() {
		comboCourse.removeAll();
		try {
			for(Row co : CourseQuery.getFullDataset()) {
				String courseString = co.getValueAsStringNotNull("name");
				comboCourse.add(courseString);
				comboCourse.setData(courseString, co.getValue("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateMainTopicField() {
		comboMainTopic.removeAll();
		try {
			for(Row to : TopicQuery.getFullDataset()) {
				String mainTopicString = to.getValueAsStringNotNull("description");
				comboMainTopic.add(mainTopicString);
				comboMainTopic.setData(mainTopicString, to.getValue("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cancel() {
		shlDialog.dispose();
	}
}
