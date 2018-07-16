package com.system.leveldb;

import com.system.constant.SystemConstant;
import org.iq80.leveldb.DBIterator;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.system.leveldb.Data.iterator;

public class Output {
    /**
     * Get All KeyID From Data Base
     * created in 21:17 2018/7/15
     */
    public ArrayList<String> getKeyList() {
        DBIterator iterator = iterator();

        ArrayList<String> key = new ArrayList<>();

        for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {

            String[] tt = new String[0];

            try {
                tt = new String(iterator.peekNext().getKey(), SystemConstant.DATABASE_CODE_FORMAT).split("\\s+");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            key.addAll(Arrays.asList(tt));
        }

        return key;
    }

    /**
     * Search For Results According to Clue
     * created in 21:49 2018/7/15
     */
    public ArrayList<String> getSearchResult(String clue) {
        DBIterator iterator = iterator();

        ArrayList<String> key = new ArrayList<>();

        for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
            try {
                if(new String(iterator.peekNext().getKey(), SystemConstant.DATABASE_CODE_FORMAT).contains(clue)) {
                    key.add(new String(iterator.peekNext().getKey(), SystemConstant.DATABASE_CODE_FORMAT));
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return key;
    }

    public static void main(String[] args) {
        String line = "good12";

        String[] tt=line.split("\\s+");
        for(String s:tt)
        {
            System.out.println(s);
        }
        System.out.println("#####");
    }
}
