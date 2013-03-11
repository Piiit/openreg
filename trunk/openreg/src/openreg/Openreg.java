package openreg;

import log.Log;
import gui.GuiModuleType;
import gui.GuiModuleList;
import gui.MainWindow;
import gui.SWTTools;
import gui.modules.AbilityDescriptionModule;
import gui.modules.AddressesModule;
import gui.modules.AssessmentModule;
import gui.modules.AssessmentTypeModule;
import gui.modules.ClassesModule;
import gui.modules.CoursesModule;
import gui.modules.GradeModule;
import gui.modules.MarkModule;
import gui.modules.StudentsModule;
import gui.modules.TeachersModule;
import gui.modules.TopicModule;
import database.DatabaseConnection;

public class Openreg {

	public static void main(String[] args) throws Exception {

		try {
			DatabaseConnection.setup();
		} catch (Exception e) {
			Log.warn("Can't connect to default database, trying to connect to testing db!");
			try {
				DatabaseConnection.setup(DatabaseConnection.getConnectionURL());
			} catch (Exception e2) {
				Log.error("Unable to connect to specified databases!");
				System.exit(1);
			}
		}
		
		GuiModuleList gmListAdmin = new GuiModuleList(GuiModuleType.Administration);
		gmListAdmin.add(new StudentsModule());
		gmListAdmin.add(new ClassesModule());
		gmListAdmin.add(new TeachersModule());
		gmListAdmin.add(new AddressesModule());
		gmListAdmin.add(new AbilityDescriptionModule());
		gmListAdmin.add(new MarkModule());
		gmListAdmin.add(new CoursesModule());
		gmListAdmin.add(new TopicModule());
		gmListAdmin.add(new AssessmentModule());
		gmListAdmin.add(new AssessmentTypeModule());
		gmListAdmin.add(new GradeModule());
		
		
		GuiModuleList gmListReport = new GuiModuleList(GuiModuleType.Reports);
		
		SWTTools.initSWT();
		MainWindow window = new MainWindow();
		window.addModuleList(gmListAdmin);
		window.addModuleList(gmListReport);
		window.open();
		
		DatabaseConnection.close();
	}

}
