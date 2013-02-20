package openreg;

import log.Log;
import gui.Classes;
import gui.GroupType;
import gui.MainWindow;
import gui.SWTTools;
import gui.Students;
import gui.Teachers;
import database.DatabaseConnection;

public class Openreg {

	public static void main(String[] args) throws Exception {

		try {
			DatabaseConnection.setup();
		} catch (Exception e) {
			Log.warn("Can't connect to default database, trying to connect with testing db!");
			DatabaseConnection.setup(DatabaseConnection.TESTURL);
		}
		
		SWTTools.initSWT();
		MainWindow window = new MainWindow();
		window.addModule(new Students("Students", GroupType.Administration));
		window.addModule(new Classes("Classes", GroupType.Administration));
		window.addModule(new Teachers("Teachers", GroupType.Reports));
		window.open();
		
		DatabaseConnection.close();
	}

}
