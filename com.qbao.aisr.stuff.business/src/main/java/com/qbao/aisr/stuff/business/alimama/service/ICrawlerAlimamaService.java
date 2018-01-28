package com.qbao.aisr.stuff.business.alimama.service;

import java.util.List;

import com.qbao.aisr.stuff.model.mysql.StuffPromotion;

/**
 *
 */
public interface ICrawlerAlimamaService {
    /**
     *
     * @param auctionid  StuffPromotion表的
     * @param adzoneid
     * @param siteid
     * @param
     * @return
     * @throws Exception
     */
    public  String createConertUrlForItem(String auctionid, String adzoneid, String siteid)throws Exception;

    /**
     *
     * @param catName  类目名称
     * @param startPrice  最低价格
     * @param endPrice    最高价格
     * @param startRate   最低返券比例
     * @param endRate     最高返券比例
     * @param orderNum    销量
     * @param size        一页多少条
     * @return
     * @throws Exception
     */
    public List<StuffPromotion> createGoodsSearchUrl(String catName, String catId, int startPrice, int endPrice, int startRate, int endRate, int orderNum, int size) throws Exception ;


    /**
     *
     * @param auctionid  StuffPromotion表的
     * @param adzoneid
     * @param siteid
     * @param
     * @return
     * @throws Exception
     */
    public  String createConertUrlForItem(Long auctionid, String adzoneid, String siteid,String source,int device,String goodsUrl)throws Exception;

}
