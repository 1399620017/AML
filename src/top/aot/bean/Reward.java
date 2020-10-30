package top.aot.bean;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/30 14:48
 * @description：
 */
public class Reward {

    private String name;

    // 类型 item command
    private String type;

    private ItemStack itemStack;

    private String command;

    private Integer probability;

    public Reward() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }
}
