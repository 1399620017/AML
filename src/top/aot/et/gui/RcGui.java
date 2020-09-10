package top.aot.et.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.aot.et.RCMain;
import top.aot.bean.RcEvent;
import top.aot.et.role.RcRole;
import top.aot.et.role.RcRoleList;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RcGui extends APlugin.Gui {

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public RcGui(Player owner, int point) {
		super(owner, "§e§l悬赏榜|每日刷新§b§l[悬赏点:" + point + "]", 6);
	}

	@Override
	public boolean clickRegion(String clickedRegionName, ClickType clickType, ItemStack itemStack) {
		return false;
	}

	@Override
	protected void initWindow() {

	}

	@Override
	public void updateWindow() {
		int eventIndex = 0;
		RcRole role = RcRoleList.getRole(getOwnerName());
		Map<String, Integer> eValues = role.getEValues();
		Map<String, RcEvent> eTable = RCMain.eventList.getEventTable();
		Player player = getOwner();
		int level = player.getLevel();
		for (String id : RCMain.setting.geteList()) {
			RcEvent event = eTable.get(id);
			boolean complete = role.getBoxList().contains(event.getId());
			boolean perm = Objects.equals(event.getPermission(), "") ? true
					: player.hasPermission(event.getPermission());
			APlugin.AssemblyDynamic<RcGui> eventAssembly = new APlugin.AssemblyDynamic<RcGui>(this) {

				@Override
				protected short secondID() {
					return 0;
				}

				@Override
				protected void init(RcGui gui, ItemMeta itemMeta) {
					String name = "§a日程:" + event.getName();
					List<String> lore = new ArrayList<>();
					if (!perm) {
						name += "§c[未解锁]";
						lore.add("§c*你的会员等级不足无法解锁*");
					}else if (level < event.getLevel()) {
						name += "§c[未解锁]";
						lore.add("§c*等级达到" + event.getLevel() + "后解锁*");
					} else if (complete) {
						name += "§a[已完成]";
						lore.add("§7*此任务已经完成*");
					} else {
						name += "§7[进行中]";
					}
					lore.add("§f§l[任务要求]");
					int value = eValues.get(event.getId()); // 任务实际完成次数
					int maxValue = event.getNumber(); // 任务要求的数量
					switch (event.getType()) {
					case "killEntity":
						lore.add("§b击杀名为 " + event.getContent() + " §b的怪物 §f" + value + "/" + maxValue);
						break;
					case "killPlayer":
						lore.add("§b击杀任意玩家 §f" + value + "/" + maxValue);
						break;
					case "damageType":
						lore.add("§b使用 §a" + event.getContent() + " §b类型的武器造成伤害 " + value + "/" + maxValue);
						break;
					default:
						lore.add("§b未知任务要求,请自行探索！");
						break;
					}
					lore.add("§f§l[任务奖励]");
					lore.addAll(event.getDesc());
					lore.add("§d§l[日程点:" + event.getPoint() + "]");
					setTitle(name);
					setLore(lore);
				}

				@Override
				protected Material material() {
					return complete || !perm ? Material.WRITTEN_BOOK : Material.BOOK;
				}
			};
			if (level >= event.getLevel() && perm) {
				eventAssembly.setClickListener(new APlugin.LeftClickListener() {

					@Override
					public void leftClick() {
						boolean perm = Objects.equals(event.getPermission(), "") ? true
								: player.hasPermission(event.getPermission());
						if (perm) {
							RcRole role = RcRoleList.getRole(getOwnerName());
							Player player = getOwner();
							if (!role.getBoxList().contains(event.getId())) {
								Map<String, Integer> eValues = role.getEValues();
								int value = eValues.get(event.getId());
								if (value >= event.getNumber()) {
									int slot = APlugin.Util.PlayerUtil.getNullSoltNumber(player);
									player.closeInventory();
									if (slot >= event.getSlot()) {
										role.setEvent(event);
										for (String cmd : event.getCmds()) {
											Bukkit.dispatchCommand(APlugin.serverSender,
													cmd.replace("%player%", player.getName()));
										}
										APlugin.Msg.sendMsgTrue(player, "已经了领取日程奖励！");
									} else {
										APlugin.Msg.sendMsgFalse(player, "请将背包至少留出" + event.getSlot() + "个空位再进行领取！");
									}
								} else {
									player.closeInventory();
									APlugin.Msg.sendMsgFalse(player, "未达到此日程要求！");
								}
							} else {
								player.closeInventory();
								APlugin.Msg.sendMsgFalse(player, "此日程今日已经领取完毕！");
							}
						} else {
							player.closeInventory();
							APlugin.Msg.sendMsgFalse(player, "请提升你的会员等级再尝试完成此日程！");
						}
					}
				});
			}
			setAssembly(eventIndex++, eventAssembly);
		}

		/**
		 * 宝箱
		 */
		int boxIndex = 45;
		Map<String, RcEvent> bTable = RCMain.eventList.getBoxTable();
		for (String id : RCMain.setting.getbList()) {
			RcEvent box = bTable.get(id);
			boolean complete = role.getBoxList().contains(box.getId());
			boolean perm = Objects.equals(box.getPermission(), "") ? true
					: player.hasPermission(box.getPermission());
			APlugin.AssemblyDynamic<RcGui> boxAssembly = new APlugin.AssemblyDynamic<RcGui>(this) {

				@Override
				protected short secondID() {
					return 0;
				}

				@Override
				protected void init(RcGui gui, ItemMeta itemMeta) {
					String name = "§a宝箱:" + box.getName();
					List<String> lore = new ArrayList<>();
					if (!perm) {
						name += "§c[未解锁]";
						lore.add("§c*你的会员等级不足无法解锁*");
					}else if (level < box.getLevel()) {
						name += "§c[未解锁]";
						lore.add("§c*等级达到" + box.getLevel() + "后解锁*");
					} else if (complete) {
						name += "§a[已开启]";
						lore.add("§7*此宝箱已经开启*");
					} else {
						name += "§7[未开启]";
						lore.add("§d§l[日程点要求:" + box.getNumber() + "]");
						lore.add("§d§l[当前日程点:" + eValues.get("point") + "]");
					}
					lore.add("§f§l[任务奖励]");
					lore.addAll(box.getDesc());
					setTitle(name);
					setLore(lore);
				}

				@Override
				protected Material material() {
					return complete || ! perm? Material.BARRIER : Material.CHEST;
				}
			};
			if (level >= box.getLevel() && perm) {
				boxAssembly.setClickListener(new APlugin.LeftClickListener() {

					@Override
					public void leftClick() {
						boolean perm = Objects.equals(box.getPermission(), "") ? true
								: player.hasPermission(box.getPermission());
						if (perm) {
							RcRole role = RcRoleList.getRole(getOwnerName());
							Player player = getOwner();
							if (!role.getBoxList().contains(box.getId())) {
								Map<String, Integer> eValues = role.getEValues();
								int value = eValues.get("point");
								if (value >= box.getNumber()) {
									int slot = APlugin.Util.PlayerUtil.getNullSoltNumber(player);
									if (slot >= box.getSlot()) {
										role.setBox(box);
										for (String cmd : box.getCmds()) {
											Bukkit.dispatchCommand(APlugin.serverSender,
													cmd.replace("%player%", player.getName()));
										}
										RcGui.this.updateWindow();
										APlugin.Msg.sendMsgTrue(player, "已经了领取日程奖励！");
									} else {
										player.closeInventory();
										APlugin.Msg.sendMsgFalse(player, "请将背包至少留出" + box.getSlot() + "个空位再打开此宝箱！");
									}
								} else {
									player.closeInventory();
									APlugin.Msg.sendMsgFalse(player, "未达到此宝箱要求！");
								}
							} else {
								player.closeInventory();
								APlugin.Msg.sendMsgFalse(player, "此宝箱今日已开启过！");
							}
						} else {
							player.closeInventory();
							APlugin.Msg.sendMsgFalse(player, "请提升你的会员等级再尝试开启此宝箱！");
						}
					}
				});
			}
			setAssembly(boxIndex++, boxAssembly);
		}
	}

	@Override
	public boolean closeEvent() {
		return false;
	}

}
