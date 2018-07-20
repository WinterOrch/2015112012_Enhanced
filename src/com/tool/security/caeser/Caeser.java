package com.tool.security.caeser;

import com.tool.coding.HexConver;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Custom Caeser Algorithm
 * @author Frankel.Y
 * Created in 17:02 2018/7/19
 */
public class Caeser {

    /**
     * Transfer Faked Key to Real Key
     * created in 17:02 2018/7/19
     */
    public static byte getRealKey(byte[] fakedKey) {
        byte[] temp = DigestUtils.md5(fakedKey);

        byte result = temp[0];
        for (byte aTemp : temp) {
            result = (byte)(result ^ aTemp & 0xFF);
        }

        System.out.println(result);
        return result;
    }

    /**
     * Cipher Do Final
     * created in 17:04 2018/7/19
     */
    public static byte[] doFinal(byte realKey, byte[] content) {
        byte[] result = new byte[content.length];
        System.out.println(HexConver.byte2HexStr(content,content.length));

        for(int i = 0; i < content.length; i++) {
            result[i] = (byte)(content[i] ^ realKey & 0xFF);
        }

        System.out.println(HexConver.byte2HexStr(result,result.length));
        return result;
    }
}
