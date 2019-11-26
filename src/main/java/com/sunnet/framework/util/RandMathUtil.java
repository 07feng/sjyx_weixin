package com.sunnet.framework.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandMathUtil {

    public static int[] getRandMath(int max){
        int [] randArry={-1,-1,-1,-1,-1};
        Random random=new Random();
        for(int i=0;i<randArry.length;i++){
            int num=0;
            do {
                num = random.nextInt(max);
                //System.out.println("num="+num);
            }while (!isThere(num,randArry));
            randArry[i]=num;
        }
        return randArry;
    }

    private static boolean isThere(int num,int arr []){

        for(int i=0;i<arr.length;i++){
            if(num == arr[i]){
                return false;
            }
        }
        return true;
    }
//    public static void main(String[] args){
//        int [] randArry=getRandMath(5);
//        for(int i=0;i<randArry.length;i++){
//            System.out.println("rand="+randArry[i]+"  ");
//        }
//        Map<String,Object> map=new HashMap();
//        map.put("sessionId","123456");
//        map.put("memberId","6+548794");
//        System.out.println("rand="+map.toString());
//    }
}
