package com.qbao.aisr.stuff.business.alimama.rest;

import java.util.Map;

public abstract interface IBusinessRestFacade
{
  public Map<String, Object> findUrl(String auctionid, String adzoneid, String siteid);

  public Map<String, Object> queryGoods(String catName, String catId, int startPrice, int endPrice, int startRate,  int endRate, int orderNum, int size);
  /**
   * 提供给手机端,后面的流程跟后台dbas会不同
   * auctionid 淘宝或者天猫的原始商品id
   * type:输入:IOS 或者Android
   * source: 来源:taobao  tmall
   * url:商品路径
   */
  public Map<String, Object> findUrlByPhone(Long auctionid, int device,String source,String name);
}