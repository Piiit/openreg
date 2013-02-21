package openreg;

import log.Log;
import gui.ClassesModule;
import gui.GroupType;
import gui.GuiModuleList;
import gui.MainWindow;
import gui.SWTTools;
import gui.StudentsModule;
import gui.TeachersModule;
import database.DatabaseConnection;

public class Openreg {

	public static void main(String[] args) throws Exception {

		try {
			DatabaseConnection.setup();
		} catch (Exception e) {
			Log.warn("Can't connect to default database, trying to connect to testing db!");
			try {
				DatabaseConnection.setup(DatabaseConnection.TESTURL);
			} catch (Exception e2) {
				Log.error("Unable to connect to specified databases!");
				System.exit(1);
			}
		}
		
		GuiModuleList gmListAdmin = new GuiModuleList(GroupType.Administration);
		gmListAdmin.add(new StudentsModule());
		gmListAdmin.add(new ClassesModule());
		gmListAdmin.add(new TeachersModule());
		
		GuiModuleList gmListReport = new GuiModuleList(GroupType.Reports);
		
		SWTTools.initSWT();
		MainWindow window = new MainWindow();
		window.addModuleList(gmListAdmin);
		window.addModuleList(gmListReport);
		window.open();
		
		DatabaseConnection.close();
	}

}
