package source;


import top.aot.cls.Cls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ：aoisa
 * @date ：Created in 2020/5/16 13:05
 * @description：as
 */
@SuppressWarnings("all")
public class R {

    public static void main(String[] args) throws IOException {
        DataInputStream data  = new DataInputStream(Cls.class.getResourceAsStream("Cls$C.class"));
//        System.out.println(data.available());
        byte[] bytes = new byte[data.available()];
        data.read(bytes);
        if (bytes[0] == -54) {
            for (int i = 0; i< bytes.length; i++) {
                bytes[i]++;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(Cls.class.getResource("Cls$C.class").getFile()));
            dataOutputStream.write(bytes);
            dataOutputStream.close();
        }
        System.out.println(bytes[0] == -53? "执行完毕！可以进行编译":"执行失败！请重建项目后重新执行此方法");
    }
}
