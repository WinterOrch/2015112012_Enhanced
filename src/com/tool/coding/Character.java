package com.tool.coding;

import java.util.Random;

public class Character {
        public static String getRandomString(int length){
            String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890.,/=+-";
            Random random=new Random();
            StringBuilder sb=new StringBuilder();
            for(int i=0; i<length; ++i){
                int number=random.nextInt(str.length());
                sb.append(str.charAt(number));
            }
            return sb.toString();
        }
}
