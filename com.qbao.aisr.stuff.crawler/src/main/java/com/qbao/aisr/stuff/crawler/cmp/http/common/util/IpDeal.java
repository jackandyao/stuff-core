package com.qbao.aisr.stuff.crawler.cmp.http.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xueming on 2017/3/31.
 */
public class IpDeal {
    private static Integer ipPosition = 0;
    private static Integer userAgentPosition=0;
    private static List<String> ipList = new ArrayList<String>();
    private static List<String> userAgentList = new ArrayList<String>();
    static{
       /* try {
            readFile(ipList,"D:\\tengrong-workspace\\stuff-core\\com.qbao.aisr.stuff.config\\src\\main\\resources\\dev\\proxyip.txt");
            readFile(userAgentList,"D:\\tengrong-workspace\\stuff-core\\com.qbao.aisr.stuff.config\\src\\main\\resources\\dev\\useragent.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static void readFile(List<String> ipList,String filePath) throws IOException {
        BufferedReader br =null;
        br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath)));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            ipList.add(line);

        }
        br.close();
    }
    public static String getIp(){
        if(ipPosition-1==ipList.size()){
            //调用远程接口，获取新一批的ip
        }

        String currentIp = ipList.get(ipPosition);
        System.out.println("=======================第"+ipPosition+"次获取IP:"+currentIp+"==================================");
        ipPosition+=1;
        return currentIp;
    }
    public static String getUserAgent(){
        if(userAgentPosition-1==userAgentList.size()){
            //清0，重新获取用户代理
            userAgentPosition = 0;
        }
        String currentUserAgent = userAgentList.get(userAgentPosition);
        userAgentPosition+=1;
        return currentUserAgent;
    }

}
