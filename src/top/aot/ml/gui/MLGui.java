package top.aot.ml.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import setting.GuiSetup;
import setting.MonsterTable;
import top.aot.cls.Cls;
import top.aot.itf.Tgi;
import top.aot.plugin.APlugin;

import java.util.Map;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/11/1 12:42
 * @description：
 */
public class MLGui extends APlugin.Gui {

    private MonsterTable table;
    // 用于获取列表的Key
    private String listType;
    private GuiSetup newTable;
    private Tgi currentTgi;

    public MLGui(Player owner) {
        super(owner, Cls.C.f(), 6);
    }

    @Override
    public boolean clickRegion(String clickedRegionName, ClickType clickType, ItemStack itemStack) {
        return false;
    }

    @Override
    protected void initWindow() {
        newTable = GuiSetup.table;
        if (newTable.isEnable()) {
            listType = "";
        } else {
            listType = "aj";
            setTable(MonsterTable.getMonsterTable());
        }
    }

    @Override
    public void updateWindow() {
        if (newTable.isEnable()) {
            for (Map.Entry<String, Tgi> tgiEr : newTable.getTgiMap().entrySet()) {
                Tgi tgi = tgiEr.getValue();
                APlugin.AssemblyDynamic<MLGui> iAssembly = new APlugin.AssemblyDynamic<MLGui>(this) {
                    @Override
                    protected void init(MLGui gui, ItemMeta itemMeta) {
                        setTitle(tgi.getName());
                        setLore(tgi.getDesc());
                        setLevel(tgi.getNumber());
                    }

                    @Override
                    protected Material material() {
                        return tgi.getMaterial();
                    }

                    @Override
                    protected short secondID() {
                        return tgi.getDataId();
                    }
                }.setClickListener((APlugin.LeftClickListener) () -> {
                    currentTgi = tgi;
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(tgi.getSlot(), iAssembly);
            }
        } else {
            int[] sizes = table.getSizes();
            if (sizes[0] > 0) {
                Button<MLGui> ajButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return Cls.C.ex(String.class, "i", table.getAjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getAjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + Cls.C.ex(String.class, "i", table.getAjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getAjId();
                    }
                };
                ajButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("aj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getAjIndex(), ajButton);
            }
            if (sizes[1] > 0) {
                Button<MLGui> bjButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return Cls.C.ex(String.class, "i", table.getBjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getBjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + Cls.C.ex(String.class, "i", table.getBjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getBjId();
                    }
                };
                bjButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("bj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getBjIndex(), bjButton);
            }
            if (sizes[2] > 0) {
                Button<MLGui> cjButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return Cls.C.ex(String.class, "i", table.getCjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getCjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + Cls.C.ex(String.class, "i", table.getCjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getCjId();
                    }

                };
                cjButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("cj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getCjIndex(), cjButton);
            }
            if (sizes[3] > 0) {
                Button<MLGui> djButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return Cls.C.ex(String.class, "i", table.getDjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getDjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + Cls.C.ex(String.class, "i", table.getDjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getDjId();
                    }

                };
                djButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("dj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getDjIndex(), djButton);
            }

            if (sizes[4] > 0) {
                Button<MLGui> ejButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return Cls.C.ex(String.class, "i", table.getEjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getEjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + Cls.C.ex(String.class, "i", table.getEjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getEjId();
                    }
                };
                ejButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("ej");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getEjIndex(), ejButton);
            }
            if (sizes[5] > 0) {
                Button<MLGui> fjButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return Cls.C.ex(String.class, "i", table.getFjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getFjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + Cls.C.ex(String.class, "i", table.getFjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getFjId();
                    }

                };
                fjButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("fj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getFjIndex(), fjButton);
            }
        }
    }

    @Override
    public boolean closeEvent() {
        return false;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public MonsterTable getTable() {
        return table;
    }

    public void setTable(MonsterTable table) {
        this.table = table;
    }

    public Tgi getCurrentTgi() {
        return currentTgi;
    }
}
