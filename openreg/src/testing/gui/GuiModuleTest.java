package testing.gui;

import static org.junit.Assert.*;

import java.util.ArrayList;

import gui.GroupType;
import gui.GuiModule;
import gui.GuiModuleList;

import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GuiModuleTest {
	
	private class MyGuiModule extends GuiModule {

		public MyGuiModule(String name) throws Exception {
			super(name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void createContent(Composite parent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update(Object... parameters) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	GuiModuleList gmList1 = new GuiModuleList(GroupType.Administration);
	GuiModuleList gmList2 = new GuiModuleList(GroupType.Reports);
	
	@Before
	public void setUp() throws Exception {
		MyGuiModule gm1 = new MyGuiModule("Test1");
		MyGuiModule gm2 = new MyGuiModule("Test2");
		MyGuiModule gm3 = new MyGuiModule("Test3");
		gmList1.add(gm1);
		gmList1.add(gm2);
		gmList2.add(gm3);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetGroups() {
		ArrayList<GroupType> groups = GuiModule.getGroups();
		assertEquals(2, groups.size());
		assertEquals("Administration", groups.get(0).toString());
		assertEquals("Reports", groups.get(1).toString());
	}
	
	@Test
	public void testGetModules() {
		ArrayList<GuiModule> modules = GuiModule.getModules();
		assertEquals(3, modules.size());
		assertEquals(gm1, modules.get(0));
	}

}
