package top.aot.ml.command;

import org.bukkit.entity.Player;
import setting.MonsterList;
import top.aot.bean.Monster;
import top.aot.plugin.aml.APlugin.Command;

import java.util.Map;

/**
 * @author ：aoisa
 * @date ：Created in 2020/9/22 13:16
 * @description：
 */
public class ListCommand extends Command {
    /**
     * 创建命令类 name 命令字符串 len 命令参数长度
     *
     * @param name
     * @param len
     * @param usage
     * @param desc
     * @param op
     */
    public ListCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        Map<String, Monster> monsterMap = MonsterList.list.getMonsterNameList();
        sendTrueMsg(player, "已经注册的怪物列表：");
        String[] strings = new String[monsterMap.size()];
        int i = 0;
        for (Monster monster : monsterMap.values()) {
            strings[i++] = "Id:" + monster.getId() + " Name:" + monster.getName();
        }
        player.sendMessage(strings);
        return true;
    }
}
