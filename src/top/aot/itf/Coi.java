package top.aot.itf;

import top.aot.ml.nms.Cur;

import java.util.List;
import java.util.Set;

/**
 * Compound接口
 * @author aoisa
 */
public interface Coi {

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

    default Cur.Cco getCurrencyCompound(Object object) {
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
