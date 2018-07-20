package com.tool.coding;

import java.io.*;
import java.util.Map;

/**
 * Read Objects From/To Bytes
 * @author Frankel.Y
 * Created in 14:24 2018/7/17
 * Modified by
 */
public class Serialize {
    public static byte[] map2Byte (Map<String,Object> map) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(map);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return bytes;
    }

    public static Map<String,Object> byte2Map (byte[] bytes) {
        Map<String,Object> obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            //noinspection unchecked
            obj = (Map<String, Object>) ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return obj;
    }
}
