package top.aot.et.submission;

import org.bukkit.entity.Player;
import top.aot.et.role.RcRole;
import top.aot.bean.RcEvent;
import top.aot.et.role.RcRoleList;
import top.aot.cls.Cls;

import java.util.Map;

public class Submission {
	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}
	/**
	 * 任务计数
	 * */
	public static void sendNumber(RcEvent event, Player player, int num) {
		if (Cls.C.ex(player, false)) {
			return;
		}
		if (player.getLevel() >= event.getLevel()) {
			RcRole role = RcRoleList.getRole(player);
			Map<String, Integer> table = role.getEValues();
			int number = table.get(event.getId());
			role.setEValues(event.getId(), number + num);
		}
	}
	
}
