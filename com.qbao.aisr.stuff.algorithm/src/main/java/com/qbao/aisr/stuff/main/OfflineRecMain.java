package com.qbao.aisr.stuff.main;

import java.sql.SQLException;
import java.util.logging.Logger;

import com.qbao.aisr.stuff.algorithm.cloud.CloudRec;
import com.qbao.aisr.stuff.algorithm.detail.DetailRec;
import com.qbao.aisr.stuff.algorithm.hotsearch.SearchRec;
import com.qbao.aisr.stuff.algorithm.oneday.OnedayRec;
import com.qbao.aisr.stuff.algorithm.rec4you.offline.OfflineRec;
import com.qbao.aisr.stuff.utils.PhoneMessageUtil;

public class OfflineRecMain {

	private static Logger logger = Logger.getLogger("OfflineRecMain");
	private static String[] phoneList = new String[]{"18602507935"};
	private static void sendMsg(String msg) {
		for(String phone : phoneList) PhoneMessageUtil.executeSendPhoneMessage(phone, msg);
	}
	public static void main(String[] args) throws SQLException {
		
//		createTables("rec_cloud_stuff_new","rec_search_stuff_new","rec_user_stuff_new");
		
		long begin = System.currentTimeMillis();
		String msg = "开始执行有好货V2推荐算法!";
		sendMsg(msg);
		logger.info(msg);
		try {
			OfflineRec.mainLogic();
			SearchRec.mainLogic();
			CloudRec.mainLogic(30);
			OnedayRec.mainLogic();
			DetailRec.mainLogic();
			long now = System.currentTimeMillis();
			msg = "结束有好货V2推荐算法!耗时"+(now-begin)/1000+"s";
			sendMsg(msg);
			logger.info(msg);
		} catch(Exception e){
			msg = "有好货V2推荐算法执行失败!" + e.getMessage();
			sendMsg(msg);
			logger.warning(msg);
			e.printStackTrace();
		}
		
//		deleteTables("rec_cloud_stuff_bak","rec_search_stuff_bak","rec_user_stuff_bak");
//		renameTables("rec_cloud_stuff","rec_cloud_stuff_bak");
//		renameTables("rec_cloud_stuff_new","rec_cloud_stuff");
//		renameTables("rec_search_stuff","rec_search_stuff_bak");
//		renameTables("rec_search_stuff_new","rec_search_stuff");
//		renameTables("rec_user_stuff","rec_user_stuff_bak");
//		renameTables("rec_user_stuff_new","rec_user_stuff");
	}

}
