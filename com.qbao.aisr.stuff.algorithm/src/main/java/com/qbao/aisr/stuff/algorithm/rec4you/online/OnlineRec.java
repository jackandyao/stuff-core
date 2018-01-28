package com.qbao.aisr.stuff.algorithm.rec4you.online;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.qbao.aisr.app.reids.StuffItem;
import com.qbao.aisr.app.reids.UserStuffRecItems;
import com.qbao.aisr.app.repository.redis.dao.UserStuffRecItemsDao;
import com.qbao.aisr.stuff.algorithm.rec4you.offline.OfflineRec;
import com.qbao.aisr.stuff.cache.Rec4youCache;
import com.qbao.aisr.stuff.utils.JDBCUtil;

@Service
public class OnlineRec {

	@Autowired
	private UserStuffRecItemsDao userStuffRecItemsDao;
	
	private static Timer timer;

	static {
		startTask();
	}
	private static void startTask() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					OfflineRec.retriveOuterStuff(50);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}, 0, 1 * 60 * 60 * 1000);

	}
	
	private String getDiridByStuffId(String stuffId){
		
		String dirid = Rec4youCache.stuffIdDirIdMap.get(stuffId);
		if(dirid==null){
			JDBCUtil jdbcutil = new JDBCUtil();
			jdbcutil.createConnStuff();
			ResultSet rs = jdbcutil.getResultSet("select cat_id from stuff_promotion where stuff_id = " + stuffId);
			try {
				if(rs.next()){
					dirid = rs.getString(1);
					Rec4youCache.stuffIdDirIdMap.put(stuffId, dirid);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				jdbcutil.closeConn();
			}
		}
		return dirid;
	}

	private List<String> getRecList(String stuffId) {
		if(timer==null) startTask();
		String dirid = getDiridByStuffId(stuffId);
		return Rec4youCache.dirIdStuffIdListMap.get(dirid);
		
	}
	
	public void updateRecResult(String userId, String stuffId) {
		List<String> recList = this.getRecList(stuffId);
		if(CollectionUtils.isEmpty(recList)) return;
		UserStuffRecItems items = new UserStuffRecItems();
		ArrayList<StuffItem> itemList = Lists.newArrayList();
		for(String recId : recList) {
			StuffItem item = new StuffItem();
			item.setStuffId(Long.valueOf(recId));
			itemList.add(item);
		}
		items.setGoodsItems(itemList);
		String key = userStuffRecItemsDao.generateKey(userId);
		userStuffRecItemsDao.insert(key, items);
	}
}
