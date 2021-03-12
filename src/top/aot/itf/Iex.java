package top.aot.itf;

/**
 * @author ：aoisa
 * @date ：Created in 2020/9/9 11:49
 * @description： 类加载方法接口
 */
public interface Iex {

    default <T> T ex(Class<T> z, String n, Object... o) {
        return null;
    }

    default void ex(String n, Object... o) {

    }

    default boolean ex(Object o, boolean i) {
        return i;
    }

    default Class<?> c(byte[] b, int len) {
        return null;
    }
}
