package top.aot.ml.nms;

import org.bukkit.entity.Entity;
import top.aot.ml.cls.Cls;
import top.aot.ml.plugin.APlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public enum Currency implements ci, ni, coi, ei {
    /**
     * nms工具类
     */
    n {
        {
            Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
        }
        private final String[] nmsVersionList = {
                "v1_7_R4",
                "v1_7_R3",
                "v1_7_R2",
                "v1_7_R1"
        };
        private String nmsVersion = "";

        @Override
        public void init() {
            APlugin.Msg.sendConMsgTrue("Start net.minecraft.server version search!");
            for (String versionName : nmsVersionList) {
                try {
                    Class.forName("net.minecraft.server." + versionName + ".ItemStack");
                    nmsVersion = versionName;
                    break;
                } catch (ClassNotFoundException ignored) {

                }
            }
            APlugin.Msg.sendConMsgTrue("Current net.minecraft.server version - " + nmsVersion);
        }

        public String getNmsVersion() {
            return nmsVersion;
        }

        public Class<?> getNmsClass(String className) {
            try {
                return Class.forName("net.minecraft.server." + nmsVersion + "." + className);
            } catch (Exception e) {
                return null;
            }
        }

        public Class<?> getClassOfPath(String path) {
            try {
                return Class.forName(path);
            } catch (Exception e) {
                return null;
            }
        }

        public Class<?> getCraftClass(String path, String className) {
            try {
                return Class.forName("org.bukkit.craftbukkit." + nmsVersion + "." + path + "." + className);
            } catch (Exception e) {
                return null;
            }
        }

        public Method getMethod(Class<?> cls, String methodName) {
            Method[] k = cls.getDeclaredMethods();
            for (Method method : k) {
                if (Objects.equals(method.getName(), methodName)) {
                    method.setAccessible(true);
                    return method;
                }
            }
            return null;
        }

        public Method getMethod(Class<?> cls, Class<?> resultCls) {
            Method[] k = cls.getDeclaredMethods();
            for (Method method : k) {
                if (Objects.equals(method.getReturnType(), resultCls)) {
                    method.setAccessible(true);
                    return method;
                }
            }
            return null;
        }

        public Method getMethod(Class<?> cls, Class<?> resultCls, Class<?>... params) {
            Method[] k = cls.getDeclaredMethods();
            for (Method method : k) {
                if ((resultCls == null || Objects.equals(method.getReturnType(), resultCls))
                        && Arrays.equals(method.getParameterTypes(), params)) {
                    method.setAccessible(true);
                    return method;
                }
            }
            return null;
        }

        public Field getField(Class<?> cls, String fieldName) {
            try {
                Field field = cls.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (Exception e) {
                for (Field field : cls.getDeclaredFields()) {
                    APlugin.Msg.sendConMsgFalse(field.getName() + " type : " + field.getType());
                }
                return null;
            }

        }

        public Field getField(Class<?> cls, Class<?> fieldType) {
            try {
                Field[] fields = cls.getDeclaredFields();
                Field field = null;
                for (Field f : fields) {
                    if (f.getType() == fieldType) {
                        field = f;
                    }
                }
                if (field == null) {
                    return null;
                }
                field.setAccessible(true);
                return field;
            } catch (Exception e) {
                for (Field field : cls.getDeclaredFields()) {
                    APlugin.Msg.sendConMsgFalse(field.getName() + " type : " + field.getType());
                }
                return null;
            }

        }

        public void setValue(Field field, Object obj, Object args) {
            try {
                field.set(obj, args);
            } catch (Exception ignored) {
            }
        }

        public Object getValue(Field field, Object obj) {
            try {
                return field.get(obj);
            } catch (Exception ignored) {
            }
            return null;
        }

        public Object invokeMethod(Method method, Object obj, Object... args) {
            try {
                return method.invoke(obj, args);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public Object newNBTTag(Constructor<?> constructor, Object... args) {
            try {
                return constructor.newInstance(args);
            } catch (Exception e) {
                return null;
            }
        }

        public Constructor<?> getConstructor(Class<?> nmsNBTTagLongCls, Class<?>... classes) {
            try {
                Constructor<?> constructor = nmsNBTTagLongCls.getDeclaredConstructor(classes);
                constructor.setAccessible(true);
                return constructor;
            } catch (Exception e) {
                return null;
            }
        }
    },
    /**
     * compound工具类
     */
    c {
        {
            Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
        }
        private Class<?> nmsNBTBaseCls;

        private Class<?> nmsNBTTagByteCls;
        private Constructor<?> nmsNBTTagByteConstructor;
        private Field nmsNBTTagByteField;

        private Class<?> nmsNBTTagByteArrayCls;
        private Constructor<?> nmsNBTTagByteArrayConstructor;
        private Field nmsNBTTagByteArrayField;

        private Class<?> nmsNBTTagCompoundCls;
        private Method cMethod;
        private Method nmsNBTTagCompoundSetMethod;
        private Method nmsNBTTagCompoundGetMethod;
        private Method nmsNBTTagCompoundDelMethod;
        private Constructor<?> nmsNBTTagCompoundConstructor;

        private Class<?> nmsNBTTagDoubleCls;
        private Constructor<?> nmsNBTTagDoubleConstructor;
        private Field nmsNBTTagDoubleField;

        private Class<?> nmsNBTTagFloatCls;
        private Constructor<?> nmsNBTTagFloatConstructor;
        private Field nmsNBTTagFloatField;

        private Class<?> nmsNBTTagIntCls;
        private Constructor<?> nmsNBTTagIntConstructor;
        private Field nmsNBTTagIntField;

        private Class<?> nmsNBTTagIntArrayCls;
        private Constructor<?> nmsNBTTagIntArrayConstructor;
        private Field nmsNBTTagIntArrayField;

        private Class<?> nmsNBTTagListCls;
        private Constructor<?> nmsNBTTagListConstructor;
        private Field nmsNBTTagListListField;
        private Method nmsNBTTagListAddMethod;

        private Class<?> nmsNBTTagLongCls;
        private Constructor<?> nmsNBTTagLongConstructor;
        private Field nmsNBTTagLongField;

        private Class<?> nmsNBTTagShortCls;
        private Constructor<?> nmsNBTTagShortConstructor;
        private Field nmsNBTTagShortField;

        private Class<?> nmsNBTTagStringCls;
        private Constructor<?> nmsNBTTagStringConstructor;
        private Field nmsNBTTagStringField;

        @Override
        public void init() {
            APlugin.Msg.sendConMsgTrue("Start initialization CurrencyItemStack");
            long time = System.nanoTime();

            // 加载NBTBase
            lm(nmsNBTBaseCls = n.getNmsClass("NBTBase"),
                    "NBTBase");

            // 加载NBTTagByte
            lm(nmsNBTTagByteCls = n.getNmsClass("NBTTagByte"),
                    "NBTTagByte");
            lm(nmsNBTTagByteConstructor = n.getConstructor(nmsNBTTagByteCls, byte.class),
                    "NBTTagByte > constructor()");
            lm(nmsNBTTagByteField = n.getField(nmsNBTTagByteCls, "data"),
                    "NBTTagByte > data");

            // 加载NBTTagByteArray
            lm(nmsNBTTagByteArrayCls = n.getNmsClass("NBTTagByteArray"),
                    "NBTTagByteArray");
            lm(nmsNBTTagByteArrayConstructor = n.getConstructor(nmsNBTTagByteArrayCls, byte[].class),
                    "NBTTagByteArray > constructor()");
            lm(nmsNBTTagByteArrayField = n.getField(nmsNBTTagByteArrayCls, "data"),
                    "NBTTagByteArray > data");

            // 加载NBTTagCompound
            lm(nmsNBTTagCompoundCls = n.getNmsClass("NBTTagCompound"),
                    "NBTTagCompound");
            lm(cMethod = n.getMethod(nmsNBTTagCompoundCls, Set.class),
                    "NBTTagCompound > c()");
            lm(nmsNBTTagCompoundSetMethod = n.getMethod(nmsNBTTagCompoundCls,
                    null, String.class, nmsNBTBaseCls),
                    "NBTTagCompound > set()");
            lm(nmsNBTTagCompoundGetMethod = n.getMethod(nmsNBTTagCompoundCls,
                    nmsNBTBaseCls, String.class), "NBTTagCompound > get()");
            lm(nmsNBTTagCompoundDelMethod = n.getMethod(nmsNBTTagCompoundCls,
                    void.class, String.class), "NBTTagCompound > del()");
            lm(nmsNBTTagCompoundConstructor = n.getConstructor(nmsNBTTagCompoundCls),
                    "NBTTagCompound > constructor()");

            // 加载NBTTagDouble
            lm(nmsNBTTagDoubleCls = n.getNmsClass("NBTTagDouble"),
                    "NBTTagDouble");
            lm(nmsNBTTagDoubleConstructor = n.getConstructor(nmsNBTTagDoubleCls, double.class),
                    "NBTTagDouble > constructor()");
            lm(nmsNBTTagDoubleField = n.getField(nmsNBTTagDoubleCls, "data"),
                    "NBTTagDouble > data");

            // 加载NBTTagFloat
            lm(nmsNBTTagFloatCls = n.getNmsClass("NBTTagFloat"),
                    "NBTTagFloat");
            lm(nmsNBTTagFloatConstructor = n.getConstructor(nmsNBTTagFloatCls, float.class),
                    "NBTTagFloat > constructor()");
            lm(nmsNBTTagFloatField = n.getField(nmsNBTTagFloatCls, "data"),
                    "NBTTagFloat > data");

            // 加载NBTTagInt
            lm(nmsNBTTagIntCls = n.getNmsClass("NBTTagInt"),
                    "NBTTagInt");
            lm(nmsNBTTagIntConstructor = n.getConstructor(nmsNBTTagIntCls, int.class),
                    "NBTTagInt > constructor()");
            lm(nmsNBTTagIntField = n.getField(nmsNBTTagIntCls, "data"),
                    "NBTTagInt > data");

            // 加载NBTTagIntArray
            lm(nmsNBTTagIntArrayCls = n.getNmsClass("NBTTagIntArray"),
                    "NBTTagIntArray");
            lm(nmsNBTTagIntArrayConstructor = n.getConstructor(nmsNBTTagIntArrayCls, int[].class),
                    "NBTTagIntArray > constructor()");
            lm(nmsNBTTagIntArrayField = n.getField(nmsNBTTagIntArrayCls, "data"),
                    "NBTTagIntArray > data");

            // 加载NBTTagList
            lm(nmsNBTTagListCls = n.getNmsClass("NBTTagList"),
                    "NBTTagList");
            lm(nmsNBTTagListConstructor = n.getConstructor(nmsNBTTagListCls),
                    "NBTTagList > constructor()");
            lm(nmsNBTTagListListField = n.getField(nmsNBTTagListCls, "list"),
                    "NBTTagList > list");
            lm(nmsNBTTagListAddMethod = n.getMethod(nmsNBTTagListCls,
                    null, nmsNBTBaseCls), "NBTTagList > add()");

            // 加载NBTTagLong
            lm(nmsNBTTagLongCls = n.getNmsClass("NBTTagLong"),
                    "NBTTagLong");
            lm(nmsNBTTagLongConstructor = n.getConstructor(nmsNBTTagLongCls, long.class),
                    "NBTTagLong > constructor()");
            lm(nmsNBTTagLongField = n.getField(nmsNBTTagLongCls, "data"),
                    "NBTTagLong > data");

            // 加载NBTTagShort
            lm(nmsNBTTagShortCls = n.getNmsClass("NBTTagShort"),
                    "NBTTagShort");
            lm(nmsNBTTagShortConstructor = n.getConstructor(nmsNBTTagShortCls, short.class),
                    "NBTTagShort > constructor()");
            lm(nmsNBTTagShortField = n.getField(nmsNBTTagShortCls, "data"),
                    "NBTTagShort > data");

            // 加载NBTTagString
            lm(nmsNBTTagStringCls = n.getNmsClass("NBTTagString"),
                    "NBTTagString");
            lm(nmsNBTTagStringConstructor = n.getConstructor(nmsNBTTagStringCls, String.class),
                    "NBTTagString > constructor()");
            lm(nmsNBTTagStringField = n.getField(nmsNBTTagStringCls, "data"),
                    "NBTTagString > data");

            APlugin.Msg.sendConMsgTrue("time:" + (System.nanoTime() - time));
            APlugin.Msg.sendConMsgTrue("Initialization CurrencyItemStack complete");
        }

        /**
         * 生成一个Byte类型的NBT
         */
        public Object createByteNBT(byte num) {
            return n.newNBTTag(nmsNBTTagByteConstructor, num);
        }

        /**
         * 生成一个ByteArray类型的NBT
         */
        public Object createByteArrayNBT(byte[] num) {
            return n.newNBTTag(nmsNBTTagByteArrayConstructor, num);
        }

        /**
         * 生成一个Compound类型的NBT
         */
        public Object createCompoundNBT() {
            return n.newNBTTag(nmsNBTTagCompoundConstructor);
        }

        /**
         * 生成一个Double类型的NBT
         */
        public Object createDoubleNBT(double num) {
            return n.newNBTTag(nmsNBTTagDoubleConstructor, num);
        }

        /**
         * 生成一个Float类型的NBT
         */
        public Object createFloatNBT(float num) {
            return n.newNBTTag(nmsNBTTagFloatConstructor, num);
        }

        /**
         * 生成一个Int类型的NBT
         */
        public Object createIntNBT(int num) {
            return n.newNBTTag(nmsNBTTagIntConstructor, num);
        }

        /**
         * 生成一个IntArray类型的NBT
         */
        public Object createIntArrayNBT(int[] num) {
            return n.newNBTTag(nmsNBTTagIntArrayConstructor, num);
        }

        /**
         * 生成一个空的List类型的NBT
         */
        public Object createListNBT() {
            return n.newNBTTag(nmsNBTTagListConstructor);
        }

        /**
         * 生成一个long的NBT
         */
        public Object createLongNBT(long num) {
            return n.newNBTTag(nmsNBTTagLongConstructor, num);
        }

        /**
         * 生成一个short的NBT
         */
        public Object createShortNBT(short num) {
            return n.newNBTTag(nmsNBTTagShortConstructor, num);
        }

        /**
         * 生成一个string的NBT
         */
        public Object createStringNBT(String string) {
            return n.newNBTTag(nmsNBTTagStringConstructor, string);
        }

        /**
         * 获取Byte类型NBT的byte值
         */
        public byte getTagByte(Object byteTag) {
            return (byte) n.getValue(nmsNBTTagByteField, byteTag);
        }

        /**
         * 获取ByteArray类型NBT的byteArray值
         */
        public byte[] getTagArrayByte(Object byteArrayTag) {
            return (byte[]) n.getValue(nmsNBTTagByteArrayField, byteArrayTag);
        }

        /**
         * 获取Double类型NBT的double值
         */
        public double getTagDouble(Object doubleTag) {
            return (double) n.getValue(nmsNBTTagDoubleField, doubleTag);
        }

        /**
         * 获取Float类型NBT的float值
         */
        public float getTagFloat(Object floatTag) {
            return (float) n.getValue(nmsNBTTagFloatField, floatTag);
        }

        /**
         * 获取Int类型NBT的int值
         */
        public int getTagInt(Object intTag) {
            return (int) n.getValue(nmsNBTTagIntField, intTag);
        }


        /**
         * 获取IntArray类型NBT的intArray值
         */
        public int[] getTagIntArray(Object intArrayTag) {
            return (int[]) n.getValue(nmsNBTTagIntArrayField, intArrayTag);
        }

        /**
         * 获取list类型的NBT的List
         */
        public List getTagList(Object listTag) {
            return (List) n.getValue(nmsNBTTagListListField, listTag);
        }

        /**
         * 给List类型的NBT添加一个元素
         */
        public void tagListAdd(Object listTag, Object addElement) {
            n.invokeMethod(nmsNBTTagListAddMethod, listTag, addElement);
        }

        /**
         * 给List类型的NBT删除一个元素
         */
        public boolean tagListDel(Object listTag, int index) {
            List<?> list = getTagList(listTag);
            try {
                list.remove(index);
            } catch (Exception e) {
                return false;
            }
            setTagList(listTag, list);
            return true;
        }

        /**
         * 清除List类型的所有元素
         */
        public boolean tagListClear(Object listTag) {
            List<?> list = getTagList(listTag);
            list.clear();
            setTagList(listTag, list);
            return true;
        }

        /**
         * 设置List类型NBT的List属性 参数为List<NBTBase>
         *
         * @param NBTTagList, List<NBTBase>
         */
        public void setTagList(Object listTag, Object list) {
            n.setValue(nmsNBTTagListListField, listTag, list);
        }

        /**
         * 获取Long类型NBT的long值
         */
        public long getTagLong(Object longTag) {
            return (long) n.getValue(nmsNBTTagLongField, longTag);
        }

        /**
         * 获取Short类型NBT的short值
         */
        public short getTagShort(Object shortTag) {
            return (short) n.getValue(nmsNBTTagShortField, shortTag);
        }

        /**
         * 获取String类型NBT的String值
         */
        public String getTagString(Object stringTag) {
            return (String) n.getValue(nmsNBTTagStringField, stringTag);
        }

        /**
         * 获取
         */
        public cco getCurrencyCompound(Object object) {
            return new cco(object);
        }

        /**
         * 为compound类型NBT添加一个子节点
         */
        public void addCompoundTag(Object tag, String key, Object obj) {
            n.invokeMethod(nmsNBTTagCompoundSetMethod, tag, key, obj);
        }

        /**
         * 获取compound类型NBT的子节点
         */
        public Object getCompoundTag(Object compoundTag, String key) {
            return n.invokeMethod(nmsNBTTagCompoundGetMethod, compoundTag, key);
        }

        /**
         * 删除compound类型NBT的子节点
         */
        public Object delCompoundTag(Object compoundTag, String key) {
            return n.invokeMethod(nmsNBTTagCompoundDelMethod, compoundTag, key);
        }

        /**
         * 获取compound类型NBT的键集合 如果不是compoundTag则返回空
         */
        public Set<String> getTagKeySet(Object compoundTag) {
            if (nmsNBTTagCompoundCls == compoundTag.getClass()) {
                return (Set<String>) n.invokeMethod(cMethod, compoundTag);
            } else {
                return Collections.emptySet();
            }
        }

        public boolean isTagBase(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTBaseCls == object.getClass().getSuperclass();
        }

        public boolean isByte(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagByteCls == object.getClass();
        }

        public boolean isByteArray(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagByteArrayCls == object.getClass();
        }

        public boolean isCompound(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagCompoundCls == object.getClass();
        }

        public boolean isDouble(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagDoubleCls == object.getClass();
        }

        public boolean isFloat(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagFloatCls == object.getClass();
        }

        public boolean isInt(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagIntCls == object.getClass();
        }

        public boolean isIntArray(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagIntArrayCls == object.getClass();
        }

        public boolean isList(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagListCls == object.getClass();
        }

        public boolean isLong(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagLongCls == object.getClass();
        }

        public boolean isShort(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagShortCls == object.getClass();
        }

        public boolean isString(Object compoundTag, String key) {
            Object object = getCompoundTag(compoundTag, key);
            return nmsNBTTagStringCls == object.getClass();
        }

        public Class<?> getNmsNBTTagCompoundCls() {
            return nmsNBTTagCompoundCls;
        }
    },
    /**
     * entity工具类
     */
    e {
        {
            Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
        }

        private Class<?> nmsEntityCls;

        private Class<?> craftEntityCls;
        private Field nmsEntityField;

        private Class<?> entityCreatureCls;

        // org.bukkit.craftbukkit.v1_7_R4.CraftCreature
        private Class<?> craftCreatureCls;
        private Method craftCreatureGetHandleMethod;
        private Method craftNmGetHandleMethod;
        private Method nmEGetHandleMethod;

        private Class<?> entityInsentientCls;
        private Field entityNbt;

        // net.minecraft.entity.EntityCreature
        private Class<?> nmEntityCreatureCls;

        // net.minecraft.entity.Entity
        private Class<?> nmEntityCls;

        @Override
        public void init() {

            long time = System.nanoTime();

            lm(nmsEntityCls = n.getNmsClass("Entity"), "Entity");

            lm(craftEntityCls = n.getCraftClass("entity", "CraftEntity"),
                    "CraftEntity");
            lm(nmsEntityField = n.getField(craftEntityCls, nmsEntityCls),
                    "CraftEntity > entity");

            lm(entityCreatureCls = n.getNmsClass("EntityCreature"),
                    "EntityCreature");

            lm(craftCreatureCls = n.getCraftClass("entity", "CraftCreature"),
                    "CraftCreature");

            lm(craftCreatureGetHandleMethod = n.getMethod(craftCreatureCls, entityCreatureCls),
                    "CraftCreature > getHandle()");

            lm(entityInsentientCls = (entityCreatureCls == null ? null : entityCreatureCls.getSuperclass()),
                    "EntityInsentient");
            lm(entityNbt = n.getField(entityInsentientCls, c.getNmsNBTTagCompoundCls()),
                    "EntityInsentient > entityNbt");

            lm(nmEntityCreatureCls = n.getClassOfPath("net.minecraft.entity.EntityCreature"),
                    "nmEntityCreature");
            lm(craftNmGetHandleMethod = n.getMethod(craftCreatureCls, nmEntityCreatureCls),
                    "CraftCreature > (nm)getHandle()");

            lm(nmEntityCls = n.getClassOfPath("net.minecraft.entity.Entity"),
                    "nmEntityCreature");
            lm(nmEGetHandleMethod = n.getMethod(craftCreatureCls, nmEntityCls),
                    "CraftCreature > (nme)getHandle()");

            APlugin.Msg.sendConMsgTrue("time:" + (System.nanoTime() - time));
        }

        public Object getNmsEntity(Entity entity) {
            Object craftEntity = craftEntityCls.cast(entity);
            return getValue(nmsEntityField, craftEntity);
        }

        public Object getCraftCreature(Entity entity) {
            if (entity.getClass() == craftCreatureCls) {
                return craftCreatureCls.cast(entity);
            }
            return null;
        }

        public Object getEntityNbt(Object entityCreature) {
            return getValue(entityNbt, entityInsentientCls.cast(entityCreature));
        }

        public cee getCurrencyEntity(Entity entity) {
            return new cee(entity);
        }

        public Object getEntityCreature(Object craftCreature) {
            return invokeMethod(craftCreatureGetHandleMethod, craftCreature);
        }

        public Object getNmEntityCreature(Object craftCreature) {
            System.out.println(craftNmGetHandleMethod);
            System.out.println(craftCreature);
            return invokeMethod(craftNmGetHandleMethod, craftCreature);
        }

        public Object getNmEEntityCreature(Object craftCreature) {
            return invokeMethod(nmEGetHandleMethod, craftCreature);
        }

    },

    ;

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    /**
     * 显示载入类、方法、字段的结果信息
     */
    private static void lm(Object nmscls, String objectName) {
        if (nmscls == null) {
            APlugin.Msg.sendConMsgFalse("Failed to load " + objectName + "");
        } else {
            APlugin.Msg.sendConMsgTrue("Loading " + objectName + " succeeded");
        }
    }

    /**
     * 初始化所有项目
     */
    public static void il() {
        for (Currency currency : Currency.values()) {
            currency.init();
        }
    }

    /**
     * 通用NBTCompound类
     */
    public static class cco {
        static {
            Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
        }

        private static coi compound = c;

        private Object currencyCompound;

        /**
         * 创建通用Compound
         */
        private cco(Object object) {
            this.currencyCompound = object;
        }

        /**
         * 获取path下的所有键.
         */
        public Set<String> getKeySet(String path) {
            if (".".equals(path)) {
                return compound.getTagKeySet(currencyCompound);
            } else {
                String[] paths = path.split("\\.");
                Object iCompound = currencyCompound;
                boolean hasPath = true;
                for (String pt : paths) {
                    if (compound.isCompound(iCompound, pt)) {
                        iCompound = compound.getCompoundTag(iCompound, pt);
                    } else {
                        hasPath = false;
                    }
                }
                if (hasPath) {
                    return compound.getTagKeySet(iCompound);
                }
                return Collections.emptySet();
            }
        }

        /**
         * 获取path下的String.
         */
        public String getString(String path) {
            if (".".equals(path)) {
                return "";
            } else {
                String[] paths = path.split("\\.");
                Object iCompound = currencyCompound;
                boolean hasPath = true;
                for (String pt : paths) {
                    if (compound.isCompound(iCompound, pt)) {
                        iCompound = compound.getCompoundTag(iCompound, pt);
                    } else if (compound.isString(iCompound, pt)) {
                        iCompound = compound.getCompoundTag(iCompound, pt);
                        break;
                    } else {
                        hasPath = false;
                    }
                }
                if (hasPath) {
                    return compound.getTagString(iCompound);
                }
                return "";
            }
        }

        public Object getCurrencyCompound() {
            return currencyCompound;
        }

        @Override
        public String toString() {
            return currencyCompound.toString();
        }
    }

    /**
     * 通用NBTEntity类
     */
    public static class cee {
        static {
            Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
        }

        private static ei ne = e;

        private Object craftCreature;

        private Object nmEEntityCreature;
        private Object entityNbt;

        public cee(Entity entity) {
            System.out.println("entity " + entity);
            this.craftCreature = getCraftCreature(entity);
            System.out.println("craftCreature " + craftCreature);
            for (Method m : craftCreature.getClass().getDeclaredMethods()) {
                System.out.println(String.format("name: %s return: %s params: %s",
                        m.getName(), m.getReturnType(),
                        Arrays.toString(m.getParameterTypes())
                ));
            }
            this.nmEEntityCreature = getNmEEntityCreature(this.craftCreature);
            System.out.println("nmEEntityCreature " + nmEEntityCreature);
            for (Field f : nmEEntityCreature.getClass().getDeclaredFields()) {
                System.out.println(String.format("name: %s type: %s",
                        f.getName(), f.getType()
                ));
            }
            this.entityNbt = getEntityNbt(this.nmEEntityCreature);
            System.out.println("entityNbt " + entityNbt);
        }

        private Object getEntityCreature(Object craftCreature) {
            return ne.getEntityCreature(craftCreature);
        }

        private Object getNmEntityCreature(Object craftCreature) {
            return ne.getNmEntityCreature(craftCreature);
        }


        private Object getNmEEntityCreature(Object craftCreature) {
            return ne.getNmEEntityCreature(craftCreature);
        }

        /**
         * 获取NMS实体
         */
        private Object getNmsEntity(Entity entity) {
            return ne.getNmsEntity(entity);
        }

        /**
         * 获取NM实体
         */
        private Object getCraftCreature(Entity entity) {
            return ne.getCraftCreature(entity);
        }

        private Object getEntityNbt(Object entityCreature) {
            return ne.getEntityNbt(entityCreature);
        }

        public cco getNbt() {
            return c.getCurrencyCompound(entityNbt);
        }

        public Object getCraftCreature() {
            return craftCreature;
        }

    }
}

/**
 * 项目接口
 */
@FunctionalInterface
interface ci {
    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    void init();
}

/**
 * 实体接口
 */
interface ei {
    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    default Object getNmsEntity(Entity entity) {
        return null;
    }

    default Object getCraftCreature(Entity entity) {
        return null;
    }

    default Object getEntityNbt(Object nmsEntity) {
        return null;
    }

    default Currency.cee getCurrencyEntity(Entity entity) {
        return null;
    }

    default Object getEntityCreature(Object craftCreature) {
        return null;
    }

    default Object getNmEntityCreature(Object craftCreature) {
        return null;
    }

    default Object getNmEEntityCreature(Object craftCreature) {
        return null;
    }
}

/**
 * Compound接口
 */
interface coi {
    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    default Object createByteNBT(byte num) {
        return null;
    }

    default Object createByteArrayNBT(byte[] num) {
        return null;
    }

    default Object createCompoundNBT() {
        return null;
    }

    default Object createDoubleNBT(double num) {
        return null;
    }

    default Object createFloatNBT(float num) {
        return null;
    }

    default Object createIntNBT(int num) {
        return null;
    }

    default Object createIntArrayNBT(int[] num) {
        return null;
    }

    default Object createListNBT() {
        return null;
    }

    default Object createLongNBT(long num) {
        return null;
    }

    default Object createShortNBT(short num) {
        return null;
    }

    default Object createStringNBT(String string) {
        return null;
    }

    default byte getTagByte(Object byteTag) {
        return 0;
    }

    default byte[] getTagArrayByte(Object byteArrayTag) {
        return new byte[0];
    }

    default double getTagDouble(Object doubleTag) {
        return 0;
    }

    default float getTagFloat(Object floatTag) {
        return 0;
    }

    default int getTagInt(Object intTag) {
        return 0;
    }

    default int[] getTagIntArray(Object intArrayTag) {
        return new int[0];
    }

    default List getTagList(Object listTag) {
        return null;
    }

    default void tagListAdd(Object listTag, Object addElement) {

    }

    default boolean tagListDel(Object listTag, int index) {
        return false;
    }

    default boolean tagListClear(Object listTag) {
        return false;
    }

    default void setTagList(Object listTag, Object list) {

    }

    default long getTagLong(Object longTag) {
        return 0;
    }

    default short getTagShort(Object shortTag) {
        return 0;
    }

    default String getTagString(Object stringTag) {
        return null;
    }

    default Currency.cco getCurrencyCompound(Object object) {
        return null;
    }

    default void addCompoundTag(Object tag, String key, Object obj) {

    }

    default Object getCompoundTag(Object compoundTag, String key) {
        return null;
    }

    default Object delCompoundTag(Object compoundTag, String key) {
        return null;
    }

    default Set<String> getTagKeySet(Object compoundTag) {
        return null;
    }

    default boolean isTagBase(Object compoundTag, String key) {
        return false;
    }

    default boolean isByte(Object compoundTag, String key) {
        return false;
    }

    default boolean isByteArray(Object compoundTag, String key) {
        return false;
    }

    default boolean isCompound(Object compoundTag, String key) {
        return false;
    }

    default boolean isDouble(Object compoundTag, String key) {
        return false;
    }

    default boolean isFloat(Object compoundTag, String key) {
        return false;
    }

    default boolean isInt(Object compoundTag, String key) {
        return false;
    }

    default boolean isIntArray(Object compoundTag, String key) {
        return false;
    }

    default boolean isList(Object compoundTag, String key) {
        return false;
    }

    default boolean isLong(Object compoundTag, String key) {
        return false;
    }

    default boolean isShort(Object compoundTag, String key) {
        return false;
    }

    default boolean isString(Object compoundTag, String key) {
        return false;
    }

    default Class<?> getNmsNBTTagCompoundCls() {
        return null;
    }
}

/**
 * NMS接口
 */
interface ni {
    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    default String getNmsVersion() {
        return null;
    }

    default Class<?> getNmsClass(String className) {
        return null;
    }

    default Class<?> getCraftClass(String path, String className) {
        return null;
    }

    default Method getMethod(Class<?> cls, String methodName) {
        return null;
    }

    default Method getMethod(Class<?> cls, Class<?> resultCls) {
        return null;
    }

    default Method getMethod(Class<?> cls, Class<?> resultCls, Class<?>... params) {
        return null;
    }

    default Field getField(Class<?> cls, String fieldName) {
        return null;
    }

    default Field getField(Class<?> cls, Class<?> fieldType) {
        return null;
    }

    default void setValue(Field field, Object obj, Object args) {

    }

    default Object getValue(Field field, Object obj) {
        return null;
    }

    default Object invokeMethod(Method method, Object obj, Object... args) {
        return null;
    }

    default Object newNBTTag(Constructor<?> constructor, Object... args) {
        return null;
    }

    default Constructor<?> getConstructor(Class<?> nmsNBTTagLongCls, Class<?>... classes) {
        return null;
    }

    default Class<?> getClassOfPath(String path) {
        return null;
    }
}