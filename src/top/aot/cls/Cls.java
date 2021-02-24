package top.aot.cls;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import setting.*;
import top.aot.bean.Monster;
import top.aot.bean.RcEvent;
import top.aot.cp.cpm;
import top.aot.cp.entity.Copy;
import top.aot.cp.role.CpRole;
import top.aot.et.command.MOpenRcCommand;
import top.aot.et.command.MReloadRcCommand;
import top.aot.et.gui.etgui;
import top.aot.et.listener.KillListener;
import top.aot.et.rcm;
import top.aot.et.role.RcRole;
import top.aot.et.role.RcRoleList;
import top.aot.et.submission.Submission;
import top.aot.itf.*;
import top.aot.ml.command.*;
import top.aot.ml.listener.KillEntityListener;
import top.aot.ml.listener.PlayerLoginListener;
import top.aot.ml.utils.pt;
import top.aot.ml.utils.pu;
import top.aot.ml.variable.Variable;
import top.aot.plugin.APlugin;
import top.aot.plugin.APlugin.*;
import top.aot.sp.command.ShopAddCommand;
import top.aot.sp.command.ShopCommand;
import top.aot.sp.command.ShopDelCommand;
import top.aot.sp.command.ShopSetCommand;
import top.aot.sp.spm;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/5/16 14:59
 * @description：ad
 */
@SuppressWarnings("all")
public enum Cls implements Main, iex, is, iu, ce, ircu {
    // 核心代码
    C {
        private Map<String, Method> mp = new HashMap<>();

        private Plugin papi = null;
        private String version = "";

        @Override
        public void init() {
            Method[] ms = cls.getClass().getDeclaredMethods();
            for (Method m : ms) {
                mp.put(m.getName(), m);
            }
        }

        /**
         * 获取开发者信息
         */
        public String version() {
            return ex(String.class, Cls.C.s(10));
        }

        // C类方法有返回值调用
        public <T> T ex(Class<T> z, String n, Object... o) {
            try {
                return z.cast(mp.get(n).invoke(null, o));
            } catch (Exception e) {
                return null;
            }
        }

        // C类方法空判断 i为true对象不为空返回true， i为false判断对象为空true
        public boolean ex(Object o, boolean i) {
            try {
                return i == (boolean) mp.get("b").invoke(null, o);
            } catch (Exception e) {
                return true;
            }
        }

        // C类方法无返回值调用
        public void ex(String n, Object... o) {
            try {
                mp.get(n).invoke(null, o);
            } catch (Exception ignored) {

            }
        }

        // 常量字符串表
        public String s(int s) {
            return ex(String.class, "s", s);
        }

        // 工具加载
        private void s(Main i) {
            if (i != null) i.init();
        }

        // 插件初始化代码
        public void d() {
            version = Bukkit.getVersion();
            MonsterTable.getMonsterTable();
            GuiSetup.reload();
            MonsterList.reload();
            papi = Bukkit.getPluginManager().getPlugin(Cls.C.s(11)); // 用服务端获取PAPI插件
            if (C.ex(papi, true)) {
                boolean b = Variable.register();
                if (b) {
                    Msg.sendConMsgTrue(C.s(6));
                } else {
                    Msg.sendConMsgFalse(C.s(7));
                }
            }
            // 初始化通用nms
            s(Cls.D);
            // 初始化rc主函数
            s(rcm.A);
            s(spm.A);
            s(cpm.A);
            s(pu.A);
            s(pt.A);
            // 设置各个模块命令
            APlugin.plugin.getCommand(C.s(1)).setExecutor(new AMLCommand());
        }

        // 玩家击杀怪物事件处理
        public void _kill(EntityDeathEvent e) {
            LivingEntity le = e.getEntity();
            if (!(le instanceof Player)) {
                Player killer = le.getKiller();
                if (killer != null) {
                    Role role = Role.getRole(killer);
                    Map<String, Monster> nameTable = MonsterList.list.getMonsterNameList();
                    String typeString = le.getType().toString();
                    boolean isNpc = typeString.toUpperCase().contains("NPC");
                    String customName = isNpc ? Cls.D.getName(le) : le.getCustomName();
                    if (nameTable.containsKey(customName)) {
                        Monster monster = nameTable.get(customName);
                        if (isNpc == monster.isNpc()) {
                            role.addKillNum(monster);
                            // 判断击杀者是否解锁了怪物
                            if (!role.isUnlock(monster)) {
                                role.unlockMonster(monster);
                                Msg.sendMsgTrue(killer, C.s(5));
                            }
                            // 判断击杀者是否正在进行副本
                            CpRole cpRole = CpRole.getRole(killer);
                            // 如果玩家有副本已经完成，则不进行任何操作
                            if (cpRole.hasFinish()) {
                                return;
                            }
                            // 此处取出玩家正在进行的副本id
                            String currCopyId = cpRole.getCurrentCopyId();
                            if (currCopyId != null) {
                                // 如果玩家正在进行副本
                                Copy copy = CopyList.getCopy(currCopyId);
                                int ckillNumber = copy.getKillNumber(monster);
                                // 大于0则代表这个怪物属于这个副本
                                if (ckillNumber > 0) {
                                    // 执行玩家击杀此怪物计数 并返回击杀数量
                                    int pkillNubmer = cpRole.killMonster(monster);
                                    // 玩家击杀数量大于要求数量
                                    if (pkillNubmer > ckillNumber) {
                                        Msg.sendMsgTrue(killer, "此怪物的击杀数量已经足够！");
                                    } else {
                                        Msg.sendMessage(killer, String.format(
                                                "你击杀了一只副本怪物 %s §a当前进度: %s/%s", monster.getName(),
                                                pkillNubmer, ckillNumber));
                                        if (pkillNubmer == ckillNumber) {
                                            Msg.sendMessage(killer, "你完成了当前副本的一项击杀要求,请使用/acp open打开副本首页查看。");
                                            // 判断玩家是否完成副本全部任务
                                            if (copy.isFinish(cpRole)) {
                                                // 如果完成了将玩家设置为完成副本状态
                                                cpRole.setFinish(copy);
                                                Msg.sendMessage(killer, "你已经完成当前副本,请使用/acp open打开副本首页领取奖励。");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public void co() {
            new OpenCommand("m", 0, "", C.s(2), false);
            new ListCommand("list", 0, "", "查看所有已注册的图鉴id", true);
            new ReloadCommand("r", 0, "", C.s(3), true);
            new SwitchGuiSetupCommand("sgs", 0, "", C.s(12), true);
            new MOpenRcCommand("rcm", 0, "", "打开悬赏板", false);
            new MReloadRcCommand("rc", 0, "", "重载配置文件", true);
            new ShopCommand("shop", 0, "", "打开怪物商城", false);
            new ShopAddCommand("add", 2, "<monsterId> <point>", "添加手持商品到商城,怪物类型monsterId", true);
            new ShopSetCommand("set", 3, "<index> <monsterId> <point>", "设置手持商品到商城固定栏位", true);
            new ShopDelCommand("del", 1, "<index>", "将商品从这个栏位上下架", true);
        }

        public void e() {
            CorePlugin.regListener(new KillEntityListener());
            CorePlugin.regListener(new PlayerLoginListener());
            CorePlugin.regListener(new KillListener());
        }

        public String f() {
            return C.s(4);
        }

        public boolean is17Version() {
            return version.contains("1.7");
        }

    },
    // NPC模块版本兼容
    D {
        @Override
        public String getName(Entity entity) {
            return Ce.getName(entity);
        }

        private ce Ce;

        @Override
        public void init() {
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            if (packageName.contains("v1_7_R4")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_7_R2")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_7_R2.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_7_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_8_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_8_R2")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_8_R3")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_9_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_10_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_11_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_12_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            }
        }
    },
    // 悬赏击杀处理
    E {
        private final Map<String, RcEvent> killEventMap = new HashMap<>();
        private final Map<String, RcEvent> damageEventMap = new HashMap<>();

        @Override
        public void init() {

        }

        // 悬赏击杀判定
        public void _kill_rc(EntityDeathEvent e) {
            LivingEntity entity = e.getEntity();
            Player player = entity.getKiller();
            if (entity instanceof Player) {
                if (killEventMap.containsKey("Player")) {
                    RcEvent event = killEventMap.get("Player");
                    Submission.sendNumber(event, player, 1);
                }
            } else {
                if (killEventMap.containsKey(entity.getCustomName())) {
                    RcEvent event = killEventMap.get(entity.getCustomName());
                    Submission.sendNumber(event, player, 1);
                }
            }
        }

        // 悬赏伤害类型记录
        @SuppressWarnings("deprecation")
        public void _damage_rc(EntityDamageByEntityEvent e) {
            Entity entity = e.getDamager();
            int damage = (int) e.getDamage(EntityDamageEvent.DamageModifier.MAGIC);
            Player player = null;
            if (entity instanceof Projectile) {
                ProjectileSource ps = ((Projectile) entity).getShooter();
                if (ps instanceof Player) {
                    player = (Player) ps;
                }
            } else if (entity instanceof Player) {
                player = (Player) entity;
            } else {
                return;
            }
            if (player != null) {
                ItemStack itemStack = Util.PlayerUtil.getItemInHand(player);
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    String type = itemStack.getType().toString();
                    for (Map.Entry<String, RcEvent> entry : damageEventMap.entrySet()) {
                        if (type.contains(entry.getKey())) {
                            Submission.sendNumber(entry.getValue(), player, damage);
                        }
                    }
                }
            }
        }

        // 实体击杀类型判断
        public void ak(RcEvent ee) {
            killEventMap.put("killEntity".equals(ee.getType()) ? ee.getContent() : "Player", ee);
        }

        // 伤害类型悬赏判断
        public void ad(RcEvent ee) {
            damageEventMap.put(ee.getContent(), ee);
        }

        // 清理事件监听列表
        public void ck() {
            killEventMap.clear();
        }

        // 清理伤害类型监听列表
        public void cd() {
            damageEventMap.clear();
        }
    };

    // 核心代码加载
    public static Object cls;

    // 反编译声明
    public static String 请勿随意反编译此插件此插件创作者aoisa() {
        return "";
    }

    // 用于反编译声明的空方法
    public static String ts(istr istr) {
        return istr.v();
    }

    /**
     * 核心类定义
     */
    public static class C {

        // 系统常量表
        private static final String[] s = new String[]{
                // "插件名称AML|开发人员aoisa|QQ1399620017"  0
                new String(new byte[]{
                        -26, -113, -110, -28, -69, -74, -27, -112, -115,
                        -25, -89, -80, 65, 77, 76, 124, -27, -68, -128,
                        -27, -113, -111, -28, -70, -70, -27, -111, -104,
                        97, 111, 105, 115, 97, 124, 81, 81, 49, 51, 57,
                        57, 54, 50, 48, 48, 49, 55
                }, StandardCharsets.UTF_8),
                // "aml" 1
                new String(new byte[]{
                        97, 109, 108
                }, StandardCharsets.UTF_8),
                // "打开怪物图鉴" 2
                new String(new byte[]{
                        -26, -119, -109, -27, -68, -128, -26, -128, -86,
                        -25, -119, -87, -27, -101, -66, -23, -119, -76
                }, StandardCharsets.UTF_8),
                // "重载怪物图鉴" 3
                new String(new byte[]{
                        -23, -121, -115, -24, -67, -67, -26, -128, -86,
                        -25, -119, -87, -27, -101, -66, -23, -119, -76
                }, StandardCharsets.UTF_8),
                // "怪物图鉴" 4
                new String(new byte[]{
                        -62, -89, 99, -26, -128, -86, -25, -119, -87,
                        -27, -101, -66, -23, -119, -76
                }, StandardCharsets.UTF_8),
                // "恭喜你已经解锁一项怪物图鉴,请使用/aml命令打开怪物图鉴查看" 5
                new String(new byte[]{
                        -26, -127, -83, -27, -106, -100, -28, -67, -96,
                        -27, -73, -78, -25, -69, -113, -24, -89, -93, -23,
                        -108, -127, -28, -72, -128, -23, -95, -71, -26,
                        -128, -86, -25, -119, -87, -27, -101, -66, -23,
                        -119, -76, 44, -24, -81, -73, -28, -67, -65, -25,
                        -108, -88, 47, 97, 109, 108, -27, -111, -67, -28,
                        -69, -92, -26, -119, -109, -27, -68, -128, -26, -128,
                        -86, -25, -119, -87, -27, -101, -66, -23, -119, -76,
                        -26, -97, -91, -25, -100, -117
                }, StandardCharsets.UTF_8),
                // "变量添加成功！" 6
                new String(new byte[]{
                        -27, -113, -104, -23, -121, -113, -26, -73, -69,
                        -27, -118, -96, -26, -120, -112, -27, -118, -97,
                        -17, -68, -127
                }, StandardCharsets.UTF_8),
                // "变量添加失败！" 7
                new String(new byte[]{
                        -27, -113, -104, -23, -121, -113, -26, -73, -69,
                        -27, -118, -96, -27, -92, -79, -24, -76, -91,
                        -17, -68, -127
                }, StandardCharsets.UTF_8),
                // "ml" 8
                new String(new byte[]{109, 108}, StandardCharsets.UTF_8),
                // "系统" 9
                new String(new byte[]{-25, -77, -69, -25, -69, -97},
                        StandardCharsets.UTF_8),
                // "version" 10
                new String(new byte[]{118, 101, 114, 115, 105, 111, 110},
                        StandardCharsets.UTF_8),
                // "PlaceholderAPI" 11
                new String(new byte[]{80, 108, 97, 99, 101, 104, 111, 108,
                        100, 101, 114, 65, 80, 73},
                        StandardCharsets.UTF_8),
                // "切换新版GUI配置" 12
                new String(new byte[]{-27, -120, -121, -26, -115,
                        -94, -26, -106, -80, -25, -119, -120, 71, 85, 73, -23,
                        -123, -115, -25, -67, -82}, StandardCharsets.UTF_8),
                // "工具箱" 13
                new String(new byte[]{-27, -73, -91, -27, -123,
                        -73, -25, -82, -79}, StandardCharsets.UTF_8),
                // "aois" 14
                new String(new byte[]{97, 111, 105, 115, 97}, StandardCharsets.UTF_8),
        };

        // 常量表取值
        public static String s(int S) {
            return s[S];
        }

        // 对象空判断
        public static boolean b(Object object) {
            return object != null;
        }

        // 字符串返回方法
        public static String i(String string) {
            return string;
        }

        // 原类型返回
        public static <T> T j(T t) {
            return t;
        }

        // 开发者说明
        public static String version() {
            return s[0];
        }

    }

    public static class CDef extends ClassLoader {
        private static CDef cdef;

        static {
            CDef.C.c.init();
        }

        public enum C implements Main, iex {
            c {
                @Override
                public void init() {
                    cdef = new CDef();
                }

                public Class<?> c(byte[] b, int len) {
                    return cdef.defineClass(Cls.class.getName() + "$C", b, 0, len);
                }
            }
        }
    }

    public static class Role extends AsxConfig {

        private static Map<String, Role> roleList = new HashMap<>();

        public static Role getRole(Player player) {
            return getRole(player.getName());
        }

        public static Role getRole(String name) {
            if (!roleList.containsKey(name)) {
                roleList.put(name, new Role(name));
            }
            return roleList.get(name);
        }

        public static void reloadRole(Player player) {
            reloadRole(player.getName());
        }

        public static void reloadRole(String name) {
            roleList.put(name, new Role(name));
        }

        private List<String> monsterList;
        private List<String> receiveList;

        public Role(String fileName) {
            super(fileName);
        }

        @Override
        protected void defaultValue() {
            monsterList = new ArrayList<>();
            receiveList = new ArrayList<>();
        }

        @Override
        protected void saveConfig(FileConfiguration config) {
            config.set("monsterList", monsterList);
            config.set("receiveList", receiveList);
        }

        @Override
        protected void loadConfig(FileConfiguration config) {
            monsterList = config.getStringList("monsterList");
            receiveList = config.getStringList("receiveList");
        }

        /**
         * 解锁怪物
         */
        public void unlockMonster(Monster monster) {
            if (!monsterList.contains(monster.getId())) {
                monsterList.add(monster.getId());
                update();
            }
        }

        /**
         * 锁定怪物
         */
        public void lockMonster(Monster monster) {
            if (monsterList.contains(monster.getId())) {
                monsterList.remove(monster.getId());
                update();
            }
        }

        /**
         * 领取奖励
         */
        public void receive(Monster monster) {
            if (!receiveList.contains(monster.getId())) {
                receiveList.add(monster.getId());
                update();
            }
        }

        /**
         * 判断是否解锁
         */
        public boolean isUnlock(Monster monster) {
            return monsterList.contains(monster.getId());
        }

        /**
         * 判断是否领取
         */
        public boolean isReceive(Monster monster) {
            return receiveList.contains(monster.getId());
        }

        public int getUnlockNum() {
            return monsterList.size();
        }

        /**
         * 获取击杀数量
         */
        public int getKillNum(Monster monster) {
            return customConfig.getInt("killNumList." + monster.getId(), 0);
        }

        /**
         * 获取击杀数量
         */
        public int getKillNum(String monsterId) {
            return customConfig.getInt("killNumList." + monsterId, 0);
        }

        /**
         * 添加击杀数量
         */
        public void addKillNum(Monster monster) {
            setKillNum(monster, getKillNum(monster) + 1);
            update();
        }

        /**
         * 添加击杀数量
         */
        public void addKillNum(String monsterId, int num) {
            setKillNum(monsterId, getKillNum(monsterId) + num);
            update();
        }

        /**
         * 添加击杀数量
         */
        public void reduceKillNum(Monster monster, int num) {
            setKillNum(monster, getKillNum(monster) - num);
            update();
        }

        /**
         * 添加击杀数量
         */
        public void reduceKillNum(String monsterId, int num) {
            setKillNum(monsterId, getKillNum(monsterId) - num);
            update();
        }

        /**
         * 设置击杀数量
         */
        private void setKillNum(Monster monster, int num) {
            customConfig.set("killNumList." + monster.getId(), num);
        }

        /**
         * 设置击杀数量
         */
        private void setKillNum(String monsterId, int num) {
            customConfig.set("killNumList." + monsterId, num);
        }
    }

    // 怪物实体


    public static class RcGui extends etgui {

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
            Map<String, RcEvent> eTable = MLEventList.list.getEventTable();
            Player player = getOwner();
            int level = player.getLevel();
            for (String id : rcm.setting.geteList()) {
                RcEvent event = eTable.get(id);
                boolean complete = role.getBoxList().contains(event.getId());
                boolean perm = Objects.equals(event.getPermission(), "") ? true
                        : player.hasPermission(event.getPermission());
                AssemblyDynamic<RcGui> eventAssembly = new AssemblyDynamic<RcGui>(this) {

                    @Override
                    protected short secondID() {
                        return 0;
                    }

                    @Override
                    protected void init(RcGui gui, ItemMeta itemMeta) {
                        String name = "§a悬赏:" + event.getName();
                        List<String> lore = new ArrayList<>();
                        if (!perm) {
                            name += "§c[未解锁]";
                            lore.add("§c*你的会员等级不足无法解锁*");
                        } else if (level < event.getLevel()) {
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
                        lore.add("§d§l[悬赏点:" + event.getPoint() + "]");
                        setTitle(name);
                        setLore(lore);
                    }

                    @Override
                    protected Material material() {
                        return complete || !perm ? Material.WRITTEN_BOOK : Material.BOOK;
                    }
                };
                if (level >= event.getLevel() && perm) {
                    eventAssembly.setClickListener((LeftClickListener) () -> {
                        boolean perm12 = Objects.equals(event.getPermission(), "") ? true
                                : player.hasPermission(event.getPermission());
                        if (perm12) {
                            RcRole role12 = RcRoleList.getRole(getOwnerName());
                            Player player12 = getOwner();
                            if (!role12.getBoxList().contains(event.getId())) {
                                Map<String, Integer> eValues12 = role12.getEValues();
                                int value = eValues12.get(event.getId());
                                if (value >= event.getNumber()) {
                                    int slot = APlugin.Util.PlayerUtil.getNullSoltNumber(player12);
                                    player12.closeInventory();
                                    if (slot >= event.getSlot()) {
                                        role12.setEvent(event);
                                        for (String cmd : event.getCmds()) {
                                            Bukkit.dispatchCommand(APlugin.serverSender,
                                                    cmd.replace("%player%", player12.getName()));
                                        }
                                        Msg.sendMsgTrue(player12, "已经了领取悬赏奖励！");
                                    } else {
                                        Msg.sendMsgFalse(player12, "请将背包至少留出" + event.getSlot() + "个空位再进行领取！");
                                    }
                                } else {
                                    player12.closeInventory();
                                    Msg.sendMsgFalse(player12, "未达到此悬赏要求！");
                                }
                            } else {
                                player12.closeInventory();
                                Msg.sendMsgFalse(player12, "此悬赏今日已经领取完毕！");
                            }
                        } else {
                            player.closeInventory();
                            Msg.sendMsgFalse(player, "你暂无权限完成此悬赏！");
                        }
                    });
                }
                setAssembly(eventIndex++, eventAssembly);
            }

            // 宝箱
            int boxIndex = 45;
            Map<String, RcEvent> bTable = MLEventList.list.getBoxTable();
            for (String id : rcm.setting.getbList()) {
                RcEvent box = bTable.get(id);
                boolean complete = role.getBoxList().contains(box.getId());
                boolean perm = Objects.equals(box.getPermission(), "") ? true
                        : player.hasPermission(box.getPermission());
                AssemblyDynamic<RcGui> boxAssembly = new AssemblyDynamic<RcGui>(this) {

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
                        } else if (level < box.getLevel()) {
                            name += "§c[未解锁]";
                            lore.add("§c*等级达到" + box.getLevel() + "后解锁*");
                        } else if (complete) {
                            name += "§a[已开启]";
                            lore.add("§7*此宝箱已经开启*");
                        } else {
                            name += "§7[未开启]";
                            lore.add("§d§l[悬赏点要求:" + box.getNumber() + "]");
                            lore.add("§d§l[当前悬赏点:" + eValues.get("point") + "]");
                        }
                        lore.add("§f§l[任务奖励]");
                        lore.addAll(box.getDesc());
                        setTitle(name);
                        setLore(lore);
                    }

                    @Override
                    protected Material material() {
                        return complete || !perm ? Material.REDSTONE_TORCH_ON : Material.CHEST;
                    }
                };
                if (level >= box.getLevel() && perm) {
                    boxAssembly.setClickListener((LeftClickListener) () -> {
                        boolean perm1 = Objects.equals(box.getPermission(), "") ? true
                                : player.hasPermission(box.getPermission());
                        if (perm1) {
                            RcRole role1 = RcRoleList.getRole(getOwnerName());
                            Player player1 = getOwner();
                            if (!role1.getBoxList().contains(box.getId())) {
                                Map<String, Integer> eValues1 = role1.getEValues();
                                int value = eValues1.get("point");
                                if (value >= box.getNumber()) {
                                    int slot = APlugin.Util.PlayerUtil.getNullSoltNumber(player1);
                                    if (slot >= box.getSlot()) {
                                        role1.setBox(box);
                                        for (String cmd : box.getCmds()) {
                                            Bukkit.dispatchCommand(APlugin.serverSender,
                                                    cmd.replace("%player%", player1.getName()));
                                        }
                                        RcGui.this.updateWindow();
                                        Msg.sendMsgTrue(player1, "已经了领取悬赏奖励！");
                                    } else {
                                        player1.closeInventory();
                                        Msg.sendMsgFalse(player1, "请将背包至少留出" + box.getSlot() + "个空位再打开此宝箱！");
                                    }
                                } else {
                                    player1.closeInventory();
                                    Msg.sendMsgFalse(player1, "未达到此宝箱要求！");
                                }
                            } else {
                                player1.closeInventory();
                                Msg.sendMsgFalse(player1, "此宝箱今日已开启过！");
                            }
                        } else {
                            player.closeInventory();
                            Msg.sendMsgFalse(player, "请提升你的会员等级再尝试开启此宝箱！");
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

    private static class EtGui extends etgui {

        public EtGui(Player owner) {
            super(owner, C.s(13), 1);
        }

        @Override
        public boolean clickRegion(String clickedRegionName, ClickType clickType, ItemStack itemStack) {
            return false;
        }

        @Override
        public boolean closeEvent() {
            return false;
        }

        @Override
        public void updateWindow() {

        }

        @Override
        protected void initWindow() {

        }
    }
}

