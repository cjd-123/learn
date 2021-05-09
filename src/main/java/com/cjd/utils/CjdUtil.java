package com.cjd.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class CjdUtil {

    static boolean printFlag = true;

    // 获取uuid
    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    // 获取时间戳
    public static Timestamp getTime(){
        return new Timestamp(new Date().getTime());
    }

    // 打印
    public static void print(String msg){
        if (printFlag){
            System.out.println("JDMagi:=>"+msg);
        }
    }

}
