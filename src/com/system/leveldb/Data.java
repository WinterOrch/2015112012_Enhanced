package com.system.leveldb;

import com.system.constant.SystemConstant;
import org.iq80.leveldb.*;
import static org.fusesource.leveldbjni.JniDBFactory.*;

import java.io.File;
import java.io.IOException;

public class Data {
    private static DB dataBase;

    /**
     * Usage:
     Data.initialize();
     * created in 13:10 2018/6/16
     */
    public static void initialize() throws IOException {
        Options options = new Options();
        options.createIfMissing(true);
        dataBase = factory.open(new File(SystemConstant.CONFIG_DATABASE), options);
    }

    /**
     * Usage:
     Data.close();
     * created in 13:14 2018/6/16
     */
    public static void close() throws IOException {
        dataBase.close();
    }

    /**
     * Usage:
     WriteBatch batch = Data.createWriteBatch();
     try {
     batch.delete(bytes("Denver"));
     batch.put(bytes("Tampa"), bytes("green"));
     batch.put(bytes("London"), bytes("red"));

     Data.write(batch);
     } finally {
     // Make sure you close the batch to avoid resource leaks.
     batch.close();
     }
     * created in 13:14 2018/6/16
     */
    public static WriteBatch createWriteBatch() {
        return dataBase.createWriteBatch();
    }
    public static void write(WriteBatch batch) {
        dataBase.write(batch);
    }

    /**
     * Usage:
     Data.delete(key);
     * created in 13:21 2018/6/16
     */
    public static void delete(byte[] key) {
        dataBase.delete(key);
    }

    /**
     * Usage:
     Data.get(key);
     * created in 13:21 2018/6/16
     */
    public static byte[] get(byte[] key) {
        return dataBase.get(key);
    }

    /**
     * Usage:
     DBIterator iterator = Data.iterator();
     try {
     for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
     String key = asString(iterator.peekNext().getKey());
     String value = asString(iterator.peekNext().getValue());
     System.out.println(key+" = "+value);
     }
     } finally {
     // Make sure you close the iterator to avoid resource leaks.
     iterator.close();
     }
     * created in 13:37 2018/6/16
     */
    public static DBIterator iterator() {
        return dataBase.iterator();
    }

    /**
     * Usage:
     Data.put(key,value);
     * created in 13:21 2018/6/16
     */
    public static void put(byte[] key, byte[] value) {
        dataBase.put(key,value);
    }
}
