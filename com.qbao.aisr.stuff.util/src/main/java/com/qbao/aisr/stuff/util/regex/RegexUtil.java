package com.qbao.aisr.stuff.util.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shuaizhihu on 2017/3/1.
 */
public class RegexUtil {
    public static String[] regex(String regex, String from){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(from);
        List<String> results = new ArrayList<String>();
        while(matcher.find()){
            for (int i = 0; i < matcher.groupCount(); i++) {
                results.add(matcher.group(i+1));
            }
        }
        return results.toArray(new String[]{});
    }
}
