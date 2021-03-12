package top.aot.cp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import setting.CopyList;
import top.aot.constant.IntegerConstant;
import top.aot.cp.entity.Copy;
import top.aot.cp.role.CpRole;

/**
 * @author ：aoisa
 * @date ：Created in 2020/10/23 10:06
 * @description：
 */
public class ACPTCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender.isOp()) {
            if (args.length == IntegerConstant.COMMAND_ARGS_SPLIT_LENGTH_2) {
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
