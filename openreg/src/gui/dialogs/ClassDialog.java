package gui.dialogs;

import java.util.ArrayList;
import gui.GuiDialog;
import gui.GuiTools;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import database.Row;
import database.query.ClassQuery;

public class ClassDialog extends GuiDialog {

	protected Object result;
	protected Shell shlAddANew;
	private Text classLevel;
	private Text classStream;
	private StyledText classNotes;
	private Row loadedClass;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ClassDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAddANew.open();
		shlAddANew.layout();
		Display display = getParent().getDisplay();
		while (!shlAddANew.isDisposed()) {
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
		shlAddANew = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAddANew.setSize(300, 300);
		shlAddANew.setText("Add a new class");
		shlAddANew.setLayout(new FormLayout());
		
		Label lblLevel = new Label(shlAddANew, SWT.NONE);
		FormData fd_lblLevel = new FormData();
		fd_lblLevel.top = new FormAttachment(0, 10);
		fd_lblLevel.left = new FormAttachment(0, 10);
		lblLevel.setLayoutData(fd_lblLevel);
		lblLevel.setText("Level *");
		
		classLevel = new Text(shlAddANew, SWT.BORDER);
		FormData fd_classLevel = new FormData();
		fd_classLevel.top = new FormAttachment(lblLevel, 6);
		fd_classLevel.left = new FormAttachment(0, 10);
		fd_classLevel.right = new FormAttachment(0, 140);
		classLevel.setLayoutData(fd_classLevel);
		
		Label lblStream = new Label(shlAddANew, SWT.NONE);
		FormData fd_lblStream = new FormData();
		fd_lblStream.top = new FormAttachment(lblLevel, 0, SWT.TOP);
		fd_lblStream.right = new FormAttachment(100, -98);
		lblStream.setLayoutData(fd_lblStream);
		lblStream.setText("Stream");
		
		classStream = new Text(shlAddANew, SWT.BORDER);
		FormData fd_classStream = new FormData();
		fd_classStream.top = new FormAttachment(classLevel, 0, SWT.TOP);
		fd_classStream.left = new FormAttachment(lblStream, 0, SWT.LEFT);
		classStream.setLayoutData(fd_classStream);
		
		Label lblNotes = new Label(shlAddANew, SWT.NONE);
		FormData fd_lblNotes = new FormData();
		fd_lblNotes.top = new FormAttachment(classLevel, 6);
		fd_lblNotes.left = new FormAttachment(lblLevel, 0, SWT.LEFT);
		lblNotes.setLayoutData(fd_lblNotes);
		lblNotes.setText("Notes");
		
		classNotes = new StyledText(shlAddANew, SWT.BORDER);
		fd_classStream.right = new FormAttachment(classNotes, 0, SWT.RIGHT);
		FormData fd_classNotes = new FormData();
		fd_classNotes.right = new FormAttachment(100, -10);
		fd_classNotes.left = new FormAttachment(0, 10);
		fd_classNotes.bottom = new FormAttachment(lblNotes, 145, SWT.BOTTOM);
		fd_classNotes.top = new FormAttachment(lblNotes, 6);
		classNotes.setLayoutData(fd_classNotes);
		
		Label label = new Label(shlAddANew, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(classNotes, 18);
		fd_label.right = new FormAttachment(classNotes, 0, SWT.RIGHT);
		fd_label.left = new FormAttachment(0, 10);
		label.setLayoutData(fd_label);
		
		Button btnSave = new Button(shlAddANew, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				store();
			}
		});
		fd_label.bottom = new FormAttachment(btnSave, -6);
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(0, 240);
		fd_btnSave.right = new FormAttachment(100, -10);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlAddANew, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.top = new FormAttachment(btnSave, 0, SWT.TOP);
		fd_btnCancel.right = new FormAttachment(btnSave, -6);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
		Label lblMandatoryFields = new Label(shlAddANew, SWT.NONE);
		FormData fd_lblMandatoryFields = new FormData();
		fd_lblMandatoryFields.top = new FormAttachment(btnSave, 7, SWT.TOP);
		fd_lblMandatoryFields.left = new FormAttachment(lblLevel, 0, SWT.LEFT);
		lblMandatoryFields.setLayoutData(fd_lblMandatoryFields);
		lblMandatoryFields.setText("* Mandatory Fields");

		update();
	}

	@Override
	public void loadData(Object data) throws Exception {
		ArrayList<Row> classes = ClassQuery.getDataset((Long)data);
		if(classes.size() == 0) {
			throw new Exception("No class with ID " + data.toString() + " found.");
		}
		loadedClass = classes.get(0); 
	}

	@Override
	public void store() {
		try {
			Row newClass = new Row();
			newClass.setValue("level", GuiTools.nullIfEmptyTrimmed(classLevel.getText()));
			newClass.setValue("stream", GuiTools.nullIfEmptyTrimmed(classStream.getText()));
			newClass.setValue("notes", GuiTools.nullIfEmpty(classNotes.getText()));
			
			if(loadedClass == null) {
				ClassQuery.insert(newClass);	
			} else {
				ClassQuery.update(loadedClass.getValueAsLong("id"), newClass);
			}
			
			shlAddANew.close();
		} catch (Exception e) {
			e.printStackTrace();

			MessageBox message = new MessageBox(shlAddANew, SWT.ICON_INFORMATION | SWT.OK);
			message.setMessage(e.getMessage());
			message.setText(shlAddANew.getText());
			message.open();
		}
	}

	@Override
	public void update() {
		try {
			if (loadedClass != null){
				classLevel.setText(loadedClass.getValueAsString("level"));
				classStream.setText(loadedClass.getValueAsString("stream"));
				classNotes.setText(loadedClass.getValueAsString("notes"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cancel() {
		shlAddANew.dispose();
	}
}
