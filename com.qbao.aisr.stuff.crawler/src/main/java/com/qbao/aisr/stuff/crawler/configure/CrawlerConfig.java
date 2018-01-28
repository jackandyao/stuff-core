package com.qbao.aisr.stuff.crawler.configure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 15:33
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class CrawlerConfig {

    private String configFile;//配置文件路径
    private String proxy_ips;//代理ip
    private int dl_time_sleep;//下载间隙
    private int download_thread_num;//下载线程数
    private int parse_thread_num;//解析线程数
    private int save_thread_num;//保存线程数
    private int max_dl_queue_size;//下载队列最大长度
    private int max_parse_queue_size;//解析队列最大长度
    private int max_persist_queue_size;//持久化队列最大长度
    private String login_service_clazz;//登录服务类
    private String dl_task_provide_clazz;//下载队列生产者类
    private String dl_service_clazz;//下载服务类
    private String parse_service_clazz;//解析服务类
    private String persist_service_clazz;//持久化服务类




    public CrawlerConfig(String configFilePath) throws IOException {
        Properties prop = new Properties();//属性集合对象
        InputStream fis;
        fis = CrawlerConfig.class.getClassLoader().getResourceAsStream(configFilePath);
        prop.load(fis);//将属性文件流装载到Properties对象中
        this.proxy_ips=prop.getProperty("proxy_ips");
        this.dl_time_sleep=Integer.parseInt(prop.getProperty("dl_time_sleep"));
        this.download_thread_num=Integer.parseInt(prop.getProperty("download_thread_num"));
        this.parse_thread_num=Integer.parseInt(prop.getProperty("parse_thread_num"));
        this.save_thread_num=Integer.parseInt(prop.getProperty("save_thread_num"));
        this.max_dl_queue_size=Integer.parseInt(prop.getProperty("max_dl_queue_size"));
        this.max_parse_queue_size=Integer.parseInt(prop.getProperty("max_parse_queue_size"));
        this.max_persist_queue_size=Integer.parseInt(prop.getProperty("max_persist_queue_size"));
        this.login_service_clazz = prop.getProperty("login_service_clazz");
        this.dl_task_provide_clazz = prop.getProperty("dl_task_provide_clazz");
        this.dl_service_clazz = prop.getProperty("dl_service_clazz");
        this.parse_service_clazz= prop.getProperty("parse_service_clazz");
        this.persist_service_clazz=prop.getProperty("persist_service_clazz");

    }

    public int getMax_persist_queue_size() {
        return max_persist_queue_size;
    }

    public void setMax_persist_queue_size(int max_persist_queue_size) {
        this.max_persist_queue_size = max_persist_queue_size;
    }

    public int getMax_dl_queue_size() {
        return max_dl_queue_size;
    }

    public void setMax_dl_queue_size(int max_dl_queue_size) {
        this.max_dl_queue_size = max_dl_queue_size;
    }

    public int getMax_parse_queue_size() {
        return max_parse_queue_size;
    }

    public void setMax_parse_queue_size(int max_parse_queue_size) {
        this.max_parse_queue_size = max_parse_queue_size;
    }

    public String getDl_service_clazz() {
        return dl_service_clazz;
    }

    public void setDl_service_clazz(String dl_service_clazz) {
        this.dl_service_clazz = dl_service_clazz;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getProxy_ips() {
        return proxy_ips;
    }

    public void setProxy_ips(String proxy_ips) {
        this.proxy_ips = proxy_ips;
    }

    public int getDownload_thread_num() {
        return download_thread_num;
    }

    public void setDownload_thread_num(int download_thread_num) {
        this.download_thread_num = download_thread_num;
    }

    public int getParse_thread_num() {
        return parse_thread_num;
    }

    public void setParse_thread_num(int parse_thread_num) {
        this.parse_thread_num = parse_thread_num;
    }

    public int getSave_thread_num() {
        return save_thread_num;
    }

    public void setSave_thread_num(int save_thread_num) {
        this.save_thread_num = save_thread_num;
    }

    public String getLogin_service_clazz() {
        return login_service_clazz;
    }

    public void setLogin_service_clazz(String login_service_clazz) {
        this.login_service_clazz = login_service_clazz;
    }

    public String getDl_task_provide_clazz() {
        return dl_task_provide_clazz;
    }

    public void setDl_task_provide_clazz(String dl_task_provide_clazz) {
        this.dl_task_provide_clazz = dl_task_provide_clazz;
    }

    public String getPersist_service_clazz() {
        return persist_service_clazz;
    }

    public void setPersist_service_clazz(String persist_service_clazz) {
        this.persist_service_clazz = persist_service_clazz;
    }

    public String getParse_service_clazz() {
        return parse_service_clazz;
    }

    public void setParse_service_clazz(String parse_service_clazz) {
        this.parse_service_clazz = parse_service_clazz;
    }

    public int getDl_time_sleep() {
        return dl_time_sleep;
    }

    public void setDl_time_sleep(int dl_time_sleep) {
        this.dl_time_sleep = dl_time_sleep;
    }
}
