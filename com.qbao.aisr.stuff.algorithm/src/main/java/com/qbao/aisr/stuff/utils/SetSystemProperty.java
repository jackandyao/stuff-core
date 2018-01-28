package com.qbao.aisr.stuff.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Logger;   
  
  
/**  
* @author  
* @version  
*/   
public class SetSystemProperty {  
	static Logger logger = Logger.getLogger("SetSystemProperty");
    
    public static String profilepath="config/system.properties";
    public static final String USER_PROFILE = SetSystemProperty.readValue(profilepath, "USER_PROFILE");
    /**  
    * 采用静态方法  
    */   
    private static Properties props = new Properties();   
    static {   
        try {   
            props.load(new FileInputStream(profilepath));   
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
            System.exit(-1);   
        } catch (IOException e) {          
            System.exit(-1);   
        }   
    }   
  
    /**  
    * 读取属性文件中相应键的值  
    * @param key  
    *            主键  
    * @return String  
    */   
    public static String getKeyValue(String key) {   
        return props.getProperty(key);   
    }   
  
    /**  
    * 根据主键key读取主键的值value  
    * @param filePath 属性文件路径  
    * @param key 键名  
    */   
    public static String readValue(String filePath, String key) {   
        Properties props = new Properties();   
        try {   
            InputStream in = new BufferedInputStream(new FileInputStream(   
                    filePath));   
            props.load(in);   
            String value = props.getProperty(key);   
            logger.info(key +"键的值是："+ value);   
            return value;   
        } catch (Exception e) {   
            e.printStackTrace();   
            return null;   
        }   
    }   
      
    /**  
    * 更新（或插入）一对properties信息(主键及其键值)  
    * 如果该主键已经存在，更新该主键的值；  
    * 如果该主键不存在，则插件一对键值。  
    * @param keyname 键名  
    * @param keyvalue 键值  
    */   
    public static void writeProperties(String keyname,String keyvalue) {          
        try {   
            
            
            OutputStream fos = new FileOutputStream(profilepath);   
            props.setProperty(keyname, keyvalue);   
            
            
            props.store(fos, "Update '" + keyname + "' value");   
        } catch (IOException e) {   
            System.err.println("属性文件更新错误");   
        }   
    }   
  
    /**  
    * 更新properties文件的键值对  
    * 如果该主键已经存在，更新该主键的值；  
    * 如果该主键不存在，则插件一对键值。  
    * @param keyname 键名  
    * @param keyvalue 键值  
    */   
    public void updateProperties(String keyname,String keyvalue) {   
        try {   
            props.load(new FileInputStream(profilepath));   
            
            
            OutputStream fos = new FileOutputStream(profilepath);              
            props.setProperty(keyname, keyvalue);   
            
            
            props.store(fos, "Update '" + keyname + "' value");   
        } catch (IOException e) {   
            System.err.println("属性文件更新错误");   
        }   
    }   
    
    public static void main(String[] args) {   
    	writeProperties("JDBC_DRIVER", "com.mysql.jdbc.Driver");          
    	writeProperties("DB_URL", "jdbc:mysql://192.168.14.107:3306/user_rec?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");          
    	writeProperties("USER", "dcadmin");          
    	writeProperties("PASS", "sadas3432$#%ret@!sd76");          
    	writeProperties("BAOGOU_MINSUP", "2");          
    	writeProperties("MERCHANT_MINSUP", "2");          
        readValue(profilepath, "DB_URL");   
        logger.info("操作完成");   
    }   
} 
