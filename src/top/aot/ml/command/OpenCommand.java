package top.aot.ml.command;

import org.bukkit.entity.Player;
import top.aot.ml.gui.MLGui;
import top.aot.plugin.aml.APlugin.Command;
import top.aot.plugin.aml.APlugin.GuiBase;

/**
 * @author aoisa
 */
public class OpenCommand extends Command {

    public OpenCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        GuiBase.openWindow(player, new MLGui(player));
        return true;
    }

}
