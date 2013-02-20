package gui;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public abstract class GuiModule {

	private boolean visible = true;
	private static GuiModule visibleModule;
	private static ArrayList<GuiModule> modules;
	private String name;
	private GroupType groupType;
	protected Group container;
	
	public GuiModule(final String name, final GroupType groupType) throws Exception {
		if(name.length() == 0 || groupType == null) {
			throw new Exception("GuiModules must have a name and a group!");
		}
		this.name = name;
		this.groupType = groupType;
	}

	
	public static void setVisibleModule(GuiModule module) {
		visibleModule = module;
		module.setVisible(true);
		for(GuiModule mod : modules) {
			if(! mod.equals(visibleModule)) {
				mod.setVisible(false);
			}
		}
	}

	
	public abstract void show(Composite parent);
	public abstract void update(Object... parameters);
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType group) {
		this.groupType = group;
	}

	public static GuiModule getVisibleModule() {
		return visibleModule;
	}
	
	
	
}
