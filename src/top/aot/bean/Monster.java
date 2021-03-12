package top.aot.bean;

import java.util.List;

/**
 * @author ：aoisa
 * @date ：Created in 2020/10/30 13:31
 * @description：
 */
public class Monster {

    /** 怪物名字 */
    private String name;
    private List<String> attrs;
    private List<String> desc;
    private List<String> drops;
    private String location;
    /** 物品id */
    private int itemId;
    /** 子ID */
    private int touId;
    private final String id;
    private String health;

    /** 单次执行的命令列表 */
    private List<String> onlyList;
    private String onlyExplain;
    /** 无限次奖励的命令列表 */
    private List<String> repeatList;
    /** 无限制奖励的提示信息 */
    private String repeatExplain;
    private int onlySlot;
    /** 自定义提示 */
    private List<String> customDesc;
    /** 是否为npc */
    private boolean npc;

    public Monster(String key) {
        this.id = key;
    }

    public String getName() {
        return name;
    }

    public Monster setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getAttrs() {
        return attrs;
    }

    public Monster setAttrs(List<String> attrs) {
        this.attrs = attrs;
        return this;
    }

    public List<String> getDesc() {
        return desc;
    }

    public Monster setDesc(List<String> desc) {
        this.desc = desc;
        return this;
    }

    public String getId() {
        return id;
    }

    public int getTouId() {
        return touId;
    }

    public Monster setTouId(int touId) {
        this.touId = touId;
        return this;
    }

    public List<String> getDrops() {
        return drops;
    }

    public Monster setDrops(List<String> drops) {
        this.drops = drops;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Monster setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getHealth() {
        return health;
    }

    public Monster setHealth(String health) {
        this.health = health;
        return this;
    }

    public List<String> getOnlyList() {
        return onlyList;
    }

    public Monster setOnlyList(List<String> onlyList) {
        this.onlyList = onlyList;
        return this;
    }

    public String getOnlyExplain() {
        return onlyExplain;
    }

    public Monster setOnlyExplain(String onlyExplain) {
        this.onlyExplain = onlyExplain;
        return this;
    }

    public List<String> getRepeatList() {
        return repeatList;
    }

    public Monster setRepeatList(List<String> repeatList) {
        this.repeatList = repeatList;
        return this;
    }

    public String getRepeatExplain() {
        return repeatExplain;
    }

    public Monster setRepeatExplain(String repeatExplain) {
        this.repeatExplain = repeatExplain;
        return this;
    }

    public List<String> getCustomDesc() {
        return customDesc;
    }

    public Monster setCustomDesc(List<String> customDesc) {
        this.customDesc = customDesc;
        return this;
    }

    public boolean isNpc() {
        return npc;
    }

    public Monster setNpc(boolean npc) {
        this.npc = npc;
        return this;
    }

    public int getItemId() {
        return itemId;
    }

    public Monster setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getOnlySlot() {
        return onlySlot;
    }

    public Monster setOnlySlot(int onlySlot) {
        this.onlySlot = onlySlot;
        return this;
    }
}
