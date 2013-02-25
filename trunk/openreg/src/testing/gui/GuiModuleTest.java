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
	
	private class MyGuiModule1 extends GuiModule {

		@Override
		public void createContent(Composite parent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getName() {
			return "Test1";
		}
	}	
	
	private class MyGuiModule2 extends GuiModule {

		@Override
		public void createContent(Composite parent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getName() {
			return "Test2";
		}
	}	
	
	private class MyGuiModule3 extends GuiModule {

		@Override
		public void createContent(Composite parent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getName() {
			return "Test3";
		}
	}
	
	
	GuiModuleList gmList1 = new GuiModuleList(GroupType.Administration);
	GuiModuleList gmList2 = new GuiModuleList(GroupType.Reports);
	
	@Before
	public void setUp() throws Exception {
		MyGuiModule1 gm1 = new MyGuiModule1();
		MyGuiModule2 gm2 = new MyGuiModule2();
		MyGuiModule3 gm3 = new MyGuiModule3();
		gmList1.add(gm1);
		gmList1.add(gm2);
		gmList2.add(gm3);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetModules() {
		ArrayList<GuiModule> modules = gmList1.getModules();
		assertEquals(2, modules.size());
		assertEquals("Test1", modules.get(0).getName());
		assertEquals("Test2", modules.get(1).getName());
		
		modules = gmList2.getModules();
		assertEquals(1, modules.size());
		assertEquals("Test3", modules.get(0).getName());
	}

}
