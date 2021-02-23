package top.aot.cp.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import setting.CopyList;
import setting.MonsterList;
import top.aot.bean.Monster;
import top.aot.bean.Reward;
import top.aot.cp.entity.Copy;
import top.aot.cp.gui.CopyGui;
import top.aot.cp.role.CpRole;
import top.aot.plugin.APlugin.GuiBase;
import top.aot.plugin.APlugin.Msg;
import top.aot.plugin.APlugin.Util.PlayerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/23 10:06
 * @description：
 */
public class ACPTCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender.isOp()) {
            if (args.length == 2) {
                CpRole cpRole = CpRole.getRole("aoisa");
                Copy copy = CopyList.getCopy("copy001");
                cpRole.costNumber(copy);
                int number = cpRole.getNumber(copy);
                System.out.println(number);
            }
        }
        return true;
    }
}
