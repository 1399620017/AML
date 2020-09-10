package top.aot.ml.gui;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import top.aot.cls.Cls;
import top.aot.plugin.APlugin.AssemblyDynamic;

public abstract class Button<T> extends AssemblyDynamic<T>{

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public Button(T gui) {
		super(gui);
	}
	protected abstract String buttonName();
	/**
	 * 按钮功能介绍 .n换行
	 * */
	protected abstract String explain();
	
	@Override
	protected void init(T gui,ItemMeta itemMeta) {
		setTitle("§b[" + buttonName() + "§b]");
		setLore(Arrays.asList(explain().split("\\.n")));
	}
	
	@Override
	protected Material material() {
		return Material.SKULL_ITEM;
	}

}
