package top.aot.itf;

import top.aot.cls.Cls;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/9/9 11:50
 * @description： 核心代码接口
 */
public interface is {

    default String version() {
        return null;
    }

    default String s(int s) {
        return null;
    }

    default String f() {
        return null;
    }

}
