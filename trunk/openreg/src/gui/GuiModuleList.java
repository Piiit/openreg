package gui;


import java.util.ArrayList;

public class GuiModuleList {
	private ArrayList<GuiModule> modules = new ArrayList<GuiModule>();
	private GroupType groupType;
	private GuiModule visibleModule;
	
	public GuiModuleList(GroupType groupType) {
		super();
		this.groupType = groupType;
	}

	public void add(GuiModule guiModule) {
		modules.add(guiModule);
	}
	
	public void setVisibleModule(GuiModule module) {
		visibleModule = module;
		module.container.setVisible(true);
		for(GuiModule mod : modules) {
			if(! mod.equals(visibleModule)) {
				mod.container.setVisible(false);
			}
		}
	}
	
	public GuiModule getVisibleModule() {
		return visibleModule;
	}
	
	public ArrayList<GuiModule> getModules() {
		return modules;
	}
	
	public GroupType getGroupType() {
		return groupType;
	}
	
	public int size() {
		return modules.size();
	}
}
