package com.qbao.aisr.stuff.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import com.qbao.aisr.stuff.algorithm.rec4you.offline.OfflineRec;
 
 
public class ReadHDFS {
	
	private static Logger logger = Logger.getLogger("ReadHDFS");
	private static boolean skip = false;
    public static void main(String[] args){
    	getUserProfile();
    }
    
    
    
   
    public static void getUserProfile(){
        String uri = SetSystemProperty.USER_PROFILE;
        
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", 
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        	    );
        conf.addResource(new Path("config/core-site.xml"));
        conf.addResource(new Path("config/hdfs-site.xml"));
        try {
        	FileSystem fs = FileSystem.get(conf);
        	readUserProfileHDFS(fs, uri);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
    
	private static void readUserProfileHDFS(FileSystem fs, String uri){
    	logger.info("正在扫描..." + uri);
    	FSDataInputStream in = null;
        BufferedReader br = null;
        try {
        	int waitCount = 0;
        	
        	FileStatus[] status = null;
        	do {
        		status = fs.listStatus(new Path(uri));
        		if((status==null || status.length<=1) && waitCount<10 ){
	        		try {
	        			waitCount++;
						Thread.sleep(1000*60*10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					logger.info("最新"+uri+"文件夹下没有找到文件,等待十分钟后重试,"+waitCount+"次！");
        		}
        	} while((status==null || status.length==0) && waitCount<10 );
        	
        	for(int i=status.length-1; i>=0; i--){
        		FileStatus file = status[i];
        		if(file.isDirectory() && file.getPath().getName().startsWith("dt")) {
        			if(skip) return;
        			readUserProfileHDFS(fs, uri+"/"+file.getPath().getName());
        			continue;
        		}
        		if(file.isFile()){
	        		FSDataInputStream inputStream = fs.open(file.getPath());
	        		br = new BufferedReader(new InputStreamReader(inputStream));
	        		String readline;
	                int counter = 0;
	                while ((readline = br.readLine()) != null) {
	                	OfflineRec.retriveUserAction(readline);
	                    if(++counter%100000==0) {
	                    	skip = true;
	                    	logger.info("获得"+counter+"条用户数据！" + skip);
	                    }
	                }
	        		logger.info("获得"+counter+"条用户数据！");
        		}
        	}
        } catch (IOException e) {
            logger.warning(e.getMessage());
        } finally {
            IOUtils.closeStream(in);
        }
    }
}