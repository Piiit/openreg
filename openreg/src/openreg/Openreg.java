package openreg;

import gui.MainWindow;
import gui.SWTTools;
import database.DatabaseConnection;

public class Openreg {

	public static void main(String[] args) throws Exception {

		DatabaseConnection.setup(DatabaseConnection.TESTURL);
		
		SWTTools.initSWT();
		MainWindow window = new MainWindow();
		window.open();
		
		DatabaseConnection.close();
	}

}
