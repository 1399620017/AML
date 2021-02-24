package top.aot.bean;

import java.util.List;

public class RcEvent {

    private String name;
    private String type;
    private String content;
    private int number;
    private List<String> cmds;
    private List<String> desc;
    private int point;
    private int slot;
    private final String id;
    private int level;
    private String permission = "";

    public RcEvent(String key) {
        this.id = key;
    }

    public String getContent() {
        return content;
    }

    public RcEvent setContent(String content) {
        this.content = content;
        return this;
    }

    public String getName() {
        return name;
    }

    public RcEvent setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public RcEvent setType(String type) {
        this.type = type;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public RcEvent setNumber(int number) {
        this.number = number;
        return this;
    }

    public List<String> getCmds() {
        return cmds;
    }

    public RcEvent setCmds(List<String> cmds) {
        this.cmds = cmds;
        return this;
    }

    public List<String> getDesc() {
        return desc;
    }

    public RcEvent setDesc(List<String> desc) {
        this.desc = desc;
        return this;
    }

    public int getPoint() {
        return point;
    }

    public RcEvent setPoint(int point) {
        this.point = point;
        return this;
    }

    public int getSlot() {
        return slot;
    }

    public RcEvent setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public RcEvent setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public RcEvent setPermission(String permission) {
        this.permission = permission;
        return this;
    }

}
