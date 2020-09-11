package top.aot.et;

import setting.EventList;
import setting.EventSetup;
import top.aot.et.command.ARCCommand;
import top.aot.ml.MListMain;
import top.aot.cls.Cls;
import top.aot.itf.i;
import top.aot.plugin.APlugin;

public enum rcm implements i {
	A{
		@Override
		public void init() {
			setting = new EventSetup();
			eventList = new EventList();
			APlugin.plugin.getCommand("arc").setExecutor(new ARCCommand());
		}
	};
	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}
	public static EventSetup setting;
	public static EventList eventList;

}
