package gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class GuiTools {

	public static String nullIfEmptyTrimmed(String text) {
		if(text == null || text.trim().length() == 0) {
			return null;
		}
		return text.trim();
	}
	
	public static String nullIfEmpty(String text) {
		if(text == null || text.trim().length() == 0) {
			return null;
		}
		return text;
	}
	
	public static ArrayList<Long> getSelectedItems(Table table) {
		TableItem tableItems[] = table.getItems();
		ArrayList<Long> selected = new ArrayList<Long>();
		for(int i = 0; i < tableItems.length; i++) {
			if(tableItems[i].getChecked() == true) {
				selected.add((Long)tableItems[i].getData());
			}
		}
		return selected;
	}
	
	public static void showMessageBox(Shell shell, String message) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setMessage(message);
		messageBox.setText(shell.getText());
		messageBox.open();
	}
	
	public static int showQuestionBox(Shell shell, String question) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage(question);
		messageBox.setText(shell.getText());
		return messageBox.open();
	}
	
}
