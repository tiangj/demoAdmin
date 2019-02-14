package com.example.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****
 * @author  TGJ
 */
public class StringUtil {

    /*****
     * 截取一个字符串中的所有数字
     * @param string
     * @return
     */
    public static String subNumber(String string){
        String resultNumber="";
        String regex = "\\d+(\\.\\d+)?";
        Pattern pattern =  Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        while(matcher.find()){
            resultNumber=resultNumber+matcher.group();
        }
        return resultNumber;
    }
}
