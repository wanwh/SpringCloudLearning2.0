package com.wanwh.api.util;

import java.util.Random;

public class PassWordUtil {

	 /** 
     * 获得密码 
     * @param len 密码长度 
     * @return 
     */  
    public static String createPassWord(int len){
    	PassWordUtil pwc = new PassWordUtil();
        int random = pwc.createRandomInt();  
        return pwc.createPassWord(random, len).toUpperCase();
    }  
      
    public String createPassWord(int random,int len){  
        Random rd = new Random(random);  
        final int  maxNum = 36;  
        StringBuffer sb = new StringBuffer();  
        int rdGet;//取得随机数  
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
                'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };  //去除 字母o 和 i 后的字符组
          
        int count=0;  
        while(count < len){  
            rdGet = Math.abs(rd.nextInt(maxNum));//生成的数最大为36-1
            if (rdGet >= 0 && rdGet < str.length) {  
                sb.append(str[rdGet]);  
                count ++;  
            }  
        }  
        return sb.toString();  
    }  
      
    public int createRandomInt(){  
        //得到0.0到1.0之间的数字，并扩大100000倍  
        double temp = Math.random()*100000;  
        //如果数据等于100000，则减少1  
        if(temp>=100000){  
            temp = 99999;  
        }  
        int tempint = (int)Math.ceil(temp);
        return tempint;  
    }  
      
    public static void main(String[] args){  
    	PassWordUtil pwc = new PassWordUtil();  
        System.out.println(pwc.createPassWord(6));  
    } 
}
