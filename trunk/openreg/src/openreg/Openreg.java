package openreg;

import log.Log;
import gui.Classes;
import gui.GroupType;
import gui.GuiModuleList;
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
			Log.warn("Can't connect to default database, trying to connect to testing db!");
			try {
				DatabaseConnection.setup(DatabaseConnection.TESTURL);
			} catch (Exception e2) {
				Log.error("Unable to connect to specified databases!");
				System.exit(1);
			}
		}
		
		GuiModuleList gmListAdmin = new GuiModuleList(GroupType.Administration);
		gmListAdmin.add(new Students());
		gmListAdmin.add(new Classes());
		gmListAdmin.add(new Teachers());
		
		GuiModuleList gmListReport = new GuiModuleList(GroupType.Reports);
		
		SWTTools.initSWT();
		MainWindow window = new MainWindow();
		window.addModuleList(gmListAdmin);
		window.addModuleList(gmListReport);
		window.open();
		
		DatabaseConnection.close();
	}

}
