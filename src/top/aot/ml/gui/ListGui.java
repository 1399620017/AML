package top.aot.ml.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import setting.GuiSetup;
import setting.MonsterList;
import top.aot.bean.Monster;
import top.aot.cls.Cls;
import top.aot.ml.utils.pu;
import top.aot.plugin.APlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/11/1 12:44
 * @description：
 */
public class ListGui extends APlugin.Gui {

    MLGui mlGui;
    List<String> listName;
    private GuiSetup newTable;

    public ListGui(APlugin.Gui beforeGui, Player owner) {
        super(beforeGui, owner, "§e怪物列表", 6);
    }

    @Override
    public boolean clickRegion(String clickedRegionName, ClickType clickType, ItemStack itemStack) {
        return false;
    }

    @Override
    protected void initWindow() {
        newTable = GuiSetup.table;
        mlGui = (MLGui) getBeforeGui();
        if (newTable.isEnable()) {
            listName = mlGui.getCurrentTgi().getMonsterList();
        } else {
            switch (mlGui.getListType()) {
                case "aj":
                    listName = mlGui.getTable().getAj();
                    break;
                case "bj":
                    listName = mlGui.getTable().getBj();
                    break;
                case "cj":
                    listName = mlGui.getTable().getCj();
                    break;
                case "dj":
                    listName = mlGui.getTable().getDj();
                    break;
                case "ej":
                    listName = mlGui.getTable().getEj();
                    break;
                case "fj":
                    listName = mlGui.getTable().getFj();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void updateWindow() {
        setAssembly(9, 6, APlugin.BackButton.getButton(this));
        int index = 0;
        Cls.Role role = Cls.Role.getRole(getOwnerName());
        for (String name : listName) {
            Monster monster = MonsterList.list.getMonsterById(name);
            if (Cls.C.ex(monster, true)) {
                boolean unlock = role.isUnlock(monster);
                APlugin.AssemblyDynamic<ListGui> monsterAssembly = new APlugin.AssemblyDynamic<ListGui>(this) {

                    @Override
                    protected short secondID() {
                        return (short) (unlock ? monster.getTouId() : 0);
                    }

                    @Override
                    protected void init(ListGui gui, ItemMeta itemMeta) {
                        itemMeta.setDisplayName(monster.getName() + "§a§l【图鉴】");
                        List<String> lore = new ArrayList<>();
                        lore.add("");
                        if (role.isUnlock(monster)) {
                            List<String> customDesc = monster.getCustomDesc();
                            if (customDesc.size() > 0) {
                                lore.addAll(customDesc);
                            } else {
                                lore.add("§f§l[怪物介绍]");
                                lore.addAll(monster.getDesc());
                                lore.add("§f§l[怪物属性]");
                                lore.addAll(monster.getAttrs());
                                lore.add("§a生命值 " + monster.getHealth());
                                lore.add("§f§l[怪物掉落]");
                                lore.addAll(monster.getDrops());
                                lore.add("§d§l[所在地点:" + monster.getLocation() + "]");
                            }
                            if (monster.getOnlyList().size() > 0 && !role.isReceive(monster)) {
                                lore.add(monster.getOnlyExplain());
                                lore.add("§a§l>左键点击领取奖励<§b 可领取一次");
                            } else if (monster.getRepeatList().size() > 0) {
                                lore.add(monster.getRepeatExplain());
                                lore.add("§a§l>左键点击执行<§b 图鉴解锁功能");
                            }
                        } else {
                            lore.add("§c*击杀一次此怪物后解锁此图鉴*");
                        }
                        itemMeta.setLore(lore);
                    }

                    @Override
                    protected Material material() {
                        return unlock ? Material.SKULL_ITEM : (Cls.C.is17Version() ? Material.ARROW : Material.BARRIER);
                    }

                    @Override
                    protected int itemId() {
                        return unlock ? monster.getItemId() : (Cls.C.is17Version() ? 262 : 166);
                    }
                };
                if (role.isUnlock(monster)) {
                    if (monster.getOnlyList().size() > 0 && !role.isReceive(monster)) {
                        monsterAssembly.setClickListener((APlugin.LeftClickListener) () -> {
                            List<String> onlyList = monster.getOnlyList();
                            Player player = getOwner();
                            int slot = APlugin.Util.PlayerUtil.getNullSoltNumber(player);
                            if (slot >= monster.getOnlySlot()) {
                                role.receive(monster);
                                if (player.isOp()) {
                                    exeOp(player, onlyList);
                                } else {
                                    exe(player, onlyList);
                                }
                            } else {
                                APlugin.Msg.sendMsgFalse(player, "请将背包至少留出" + monster.getOnlySlot() + "个空位再进行领取！");
                            }
                            player.closeInventory();
                        });
                    } else if (monster.getRepeatList().size() > 0) {
                        monsterAssembly.setClickListener((APlugin.LeftClickListener) () -> {
                            List<String> repeatList = monster.getRepeatList();
                            Player player = getOwner();
                            if (player.isOp()) {
                                exeOp(player, repeatList);
                            } else {
                                exe(player, repeatList);
                            }
                            getOwner().closeInventory();
                        });
                    }
                }
                setAssembly(index++, monsterAssembly);
            }
        }
    }

    private void exe(Player player, List<String> onlyList) {
        try {
            pu.a(player);
            for (String cmd : onlyList) {
                if (cmd.substring(0, 1).equals("/")) {
                    Bukkit.dispatchCommand(player, cmd.substring(1));
                } else {
                    Bukkit.dispatchCommand(APlugin.serverSender,
                            cmd.replaceAll("<p>", player.getName()));
                }
            }
        } catch (Exception ignored) {

        } finally {
            pu.b(player);
        }
    }

    private void exeOp(Player player, List<String> onlyList) {
        try {
            for (String cmd : onlyList) {
                if (cmd.substring(0, 1).equals("/")) {
                    Bukkit.dispatchCommand(player, cmd.substring(1));
                } else {
                    Bukkit.dispatchCommand(APlugin.serverSender,
                            cmd.replaceAll("<p>", player.getName()));
                }
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public boolean closeEvent() {

        return false;
    }

}
