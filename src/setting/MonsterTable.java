package setting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import top.aot.cls.Cls;
import top.aot.plugin.APlugin.AsxConfig;

public class MonsterTable extends AsxConfig {

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    private static MonsterTable table;

    public static MonsterTable getMonsterTable() {
        if (Cls.C.ex(table, false)) {
            table = new MonsterTable();
        }
        return table;
    }

    public static void reloadMonsterTable() {
        table = new MonsterTable();
    }

    private List<String> aj; // 1
    private String ajName; // 1
    private int ajId;
    private List<String> bj; // 2
    private String bjName; // 2
    private int bjId;
    private List<String> cj; // 3
    private String cjName; // 3
    private int cjId;
    private List<String> dj; // 4
    private String djName; // 4
    private int djId;
    private List<String> ej; // 5
    private String ejName; // 5
    private int ejId;
    private List<String> fj; // 6
    private String fjName; // 6
    private int fjId;

    private int ajIndex;
    private int bjIndex;
    private int cjIndex;
    private int djIndex;
    private int ejIndex;
    private int fjIndex;

    private int ajItemId;

    public int getAjItemId() {
        return ajItemId;
    }

    public void setAjItemId(int ajItemId) {
        this.ajItemId = ajItemId;
    }

    public int getBjItemId() {
        return bjItemId;
    }

    public void setBjItemId(int bjItemId) {
        this.bjItemId = bjItemId;
    }

    public int getCjItemId() {
        return cjItemId;
    }

    public void setCjItemId(int cjItemId) {
        this.cjItemId = cjItemId;
    }

    public int getDjItemId() {
        return djItemId;
    }

    public void setDjItemId(int djItemId) {
        this.djItemId = djItemId;
    }

    public int getEjItemId() {
        return ejItemId;
    }

    public void setEjItemId(int ejItemId) {
        this.ejItemId = ejItemId;
    }

    public int getFjItemId() {
        return fjItemId;
    }

    public void setFjItemId(int fjItemId) {
        this.fjItemId = fjItemId;
    }

    private int bjItemId;
    private int cjItemId;
    private int djItemId;
    private int ejItemId;
    private int fjItemId;

    private final int[] sizes = new int[6];

    public int[] getSizes() {
        return sizes;
    }

    public List<String> getSw() {
        return fj;
    }

    public MonsterTable() {
        super("table");
        sizes[0] = this.getAj().size();
        sizes[1] = this.getBj().size();
        sizes[2] = this.getCj().size();
        sizes[3] = this.getDj().size();
        sizes[4] = this.getEj().size();
        sizes[5] = this.getFj().size();
    }

    @Override
    protected void defaultValue() {
        aj = new ArrayList<>(); // 1
        aj.add("a");
        bj = new ArrayList<>(); // 2
        cj = new ArrayList<>(); // 3
        dj = new ArrayList<>(); // 4
        ej = new ArrayList<>(); // 5
        fj = new ArrayList<>(); // 6
        ajId = 1;
        bjId = 1;
        cjId = 1;
        djId = 1;
        ejId = 1;
        fjId = 1;
        ajItemId = 397;
        bjItemId = 397;
        cjItemId = 397;
        djItemId = 397;
        ejItemId = 397;
        fjItemId = 397;
        ajName = "普通怪物图鉴";
        bjName = "精英怪物图鉴";
        cjName = "稀有怪物图鉴";
        djName = "BOSS怪物图鉴";
        ejName = "上古怪物图鉴";
        fjName = "异兽图鉴";
        ajIndex = 11;
        bjIndex = 12;
        cjIndex = 13;
        djIndex = 14;
        ejIndex = 15;
        fjIndex = 16;
    }

    @Override
    protected void saveConfig(FileConfiguration config) {
        config.set("ajId", ajId);
        config.set("aj", aj);
        config.set("ajName", ajName);
        config.set("bjId", bjId);
        config.set("bj", bj);
        config.set("bjName", bjName);
        config.set("cjId", cjId);
        config.set("cj", cj);
        config.set("cjName", cjName);
        config.set("djId", djId);
        config.set("dj", dj);
        config.set("djName", djName);
        config.set("ejId", ejId);
        config.set("ej", ej);
        config.set("ejName", ejName);
        config.set("fjId", fjId);
        config.set("fj", fj);
        config.set("fjName", fjName);
        config.set("ajIndex", ajIndex);
        config.set("bjIndex", bjIndex);
        config.set("cjIndex", cjIndex);
        config.set("djIndex", djIndex);
        config.set("ejIndex", ejIndex);
        config.set("fjIndex", fjIndex);
        config.set("ajItemId", ajItemId);
        config.set("bjItemId", bjItemId);
        config.set("cjItemId", cjItemId);
        config.set("djItemId", djItemId);
        config.set("ejItemId", ejItemId);
        config.set("fjItemId", fjItemId);
    }

    @Override
    protected void loadConfig(FileConfiguration config) {
        ajId = config.getInt("ajId", 0);
        aj = config.getStringList("aj");
        bjId = config.getInt("bjId", 0);
        bj = config.getStringList("bj");
        cjId = config.getInt("cjId", 0);
        cj = config.getStringList("cj");
        djId = config.getInt("djId", 0);
        dj = config.getStringList("dj");
        ejId = config.getInt("ejId", 0);
        ej = config.getStringList("ej");
        fjId = config.getInt("fjId", 0);
        fj = config.getStringList("fj");
        ajName = config.getString("ajName", "普通怪物图鉴");
        bjName = config.getString("bjName", "精英怪物图鉴");
        cjName = config.getString("cjName", "稀有怪物图鉴");
        djName = config.getString("djName", "BOSS怪物图鉴");
        ejName = config.getString("ejName", "上古怪物图鉴");
        fjName = config.getString("fjName", "异兽图鉴");

        ajIndex = config.getInt("ajIndex", 11);
        bjIndex = config.getInt("bjIndex", 12);
        cjIndex = config.getInt("cjIndex", 13);
        djIndex = config.getInt("djIndex", 14);
        ejIndex = config.getInt("ejIndex", 15);
        fjIndex = config.getInt("fjIndex", 16);

        ajItemId = config.getInt("ajItemId", 397);
        bjItemId = config.getInt("bjItemId", 397);
        cjItemId = config.getInt("cjItemId", 397);
        djItemId = config.getInt("djItemId", 397);
        ejItemId = config.getInt("ejItemId", 397);
        fjItemId = config.getInt("fjItemId", 397);
    }

    public List<String> getAj() {
        return aj;
    }

    public void setAj(List<String> aj) {
        this.aj = aj;
    }

    public int getAjId() {
        return ajId;
    }

    public void setAjId(int ajId) {
        this.ajId = ajId;
    }

    public List<String> getBj() {
        return bj;
    }

    public void setBj(List<String> bj) {
        this.bj = bj;
    }

    public int getBjId() {
        return bjId;
    }

    public void setBjId(int bjId) {
        this.bjId = bjId;
    }

    public List<String> getCj() {
        return cj;
    }

    public void setCj(List<String> cj) {
        this.cj = cj;
    }

    public int getCjId() {
        return cjId;
    }

    public void setCjId(int cjId) {
        this.cjId = cjId;
    }

    public List<String> getDj() {
        return dj;
    }

    public void setDj(List<String> dj) {
        this.dj = dj;
    }

    public int getDjId() {
        return djId;
    }

    public void setDjId(int djId) {
        this.djId = djId;
    }

    public List<String> getEj() {
        return ej;
    }

    public void setEj(List<String> ej) {
        this.ej = ej;
    }

    public int getEjId() {
        return ejId;
    }

    public void setEjId(int ejId) {
        this.ejId = ejId;
    }

    public List<String> getFj() {
        return fj;
    }

    public void setFj(List<String> fj) {
        this.fj = fj;
    }

    public int getFjId() {
        return fjId;
    }

    public void setFjId(int fjId) {
        this.fjId = fjId;
    }

    public String getAjName() {
        return ajName;
    }

    public String getBjName() {
        return bjName;
    }

    public String getCjName() {
        return cjName;
    }

    public String getDjName() {
        return djName;
    }

    public String getEjName() {
        return ejName;
    }

    public String getFjName() {
        return fjName;
    }

    public static MonsterTable getTable() {
        return table;
    }

    public int getAjIndex() {
        return ajIndex;
    }

    public int getBjIndex() {
        return bjIndex;
    }

    public int getCjIndex() {
        return cjIndex;
    }

    public int getDjIndex() {
        return djIndex;
    }

    public int getEjIndex() {
        return ejIndex;
    }

    public int getFjIndex() {
        return fjIndex;
    }

}
