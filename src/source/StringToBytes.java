package source;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author ：aoisa
 * @date ：Created in 2020/5/30 14:33
 * @description：d
 */
public class StringToBytes {
    public static void main(String[] args) {
        String string = "org.bukkit.permissions.ServerOperator";
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        String bytesString = Arrays.toString(bytes);
        System.out.println(String.format("new String(new byte[]{%s}, StandardCharsets.UTF_8)",
                bytesString.substring(1, bytesString.length() - 1)));
    }
}
