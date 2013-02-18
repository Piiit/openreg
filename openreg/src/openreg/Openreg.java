package openreg;

import gui.MainWindow;
import gui.SWTTools;
import database.DatabaseConnection;

public class Openreg {

	public static void main(String[] args) throws Exception {

		SWTTools.initSWT();
		MainWindow window = new MainWindow();
		window.open();
		
		DatabaseConnection.close();
	}

}
