package top.aot.et;

import setting.EventList;
import setting.EventSetup;
import top.aot.et.command.ARCCommand;
import top.aot.ml.MListMain;
import top.aot.cls.Cls;

public class RCMain {
	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}
	public static EventSetup setting;
	public static EventList eventList;

	public static void start(MListMain mListMain) {
		setting = new EventSetup();
		eventList = new EventList();
		mListMain.getCommand("arc").setExecutor(new ARCCommand());
	}

}
