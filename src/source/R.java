package source;


import top.aot.ml.cls.Cls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/5/16 13:05
 * @description：as
 */
public class R {

    public static void main(String[] args) throws IOException {
        DataInputStream data  = new DataInputStream(Cls.class.getResourceAsStream("Cls$C.class"));
        System.out.println(data.available());
        byte[] bytes = new byte[data.available()];
        data.read(bytes);
        for (int i = 0; i< bytes.length; i++) {
            bytes[i]++;
        }
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(Cls.class.getResource("Cls$C.class").getFile()));
        dataOutputStream.write(bytes);
        dataOutputStream.close();
        System.out.println(Arrays.toString(bytes));
    }
}
