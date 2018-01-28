//package com.qbao.aisr.stuff.repository.restful.tbk.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.qbao.aisr.stuff.common.page.Page;
//import com.qbao.aisr.stuff.model.tbk.CatItem;
//import com.qbao.aisr.stuff.model.tbk.GoodsItem;
//import com.qbao.aisr.stuff.model.tbk.ShopItem;
//import com.qbao.aisr.stuff.repository.restful.tbk.ITBKRest;
//import com.qbao.aisr.stuff.repository.restful.tbk.TBKConfigure;
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.TbkItemGetRequest;
//import com.taobao.api.response.TbkItemGetResponse;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author shuaizhihu
// * @createTime 2017/3/6 11:17
// * $$LastChangedDate$$
// * $$LastChangedRevision$$
// * $$LastChangedBy$$
// */
//@Service
//public class TBKRestImpl implements ITBKRest {
//    @Autowired
//    TBKConfigure tbkConfigure;
//
//    @Override
//    /**
//     * 淘宝客商品查询接口
//     q 	String 	特殊可选 	女装 		查询词
//     cat 	String 	特殊可选 	16,18 		后台类目ID，用,分割，最大10个，该ID可以通过taobao.itemcats.get接口获取到
//     itemloc 	String 	可选 	杭州 		所在地
//     sort 	String 	可选 	tk_rate_des 		排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi）
//     is_tmall 	Boolean 	可选 	false 		是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
//     is_overseas 	Boolean 	可选 	false 		是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
//     start_price 	Number 	可选 	10 		折扣价范围下限，单位：元
//     end_price 	Number 	可选 	10 		折扣价范围上限，单位：元
//     start_tk_rate 	Number 	可选 	123 		淘客佣金比率上限，如：1234表示12.34%
//     end_tk_rate 	Number 	可选 	123 		淘客佣金比率下限，如：1234表示12.34%
//     platform 	Number 	可选 	1 		链接形式：1：PC，2：无线，默认：１
//     page_no 	Number 	可选 	123 默认值：0   第几页，默认：１
//     page_size 	Number 	可选 	20  默认值：0   页大小，默认20，1~100
//     */
//    public Page<GoodsItem> search(String q, String cat, String itemloc, String sort, boolean is_tmall, boolean is_overseas, long start_price, long end_price, long start_tk_rate, long end_tk_rate, long platform, long page_no, long page_size) {
//        TaobaoClient client = new DefaultTaobaoClient(tbkConfigure.getTb_url(), tbkConfigure.getTb_appkey(), tbkConfigure.getTb_appsecret());
//        TbkItemGetRequest req = new TbkItemGetRequest();
//        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,tk_rate");
//        if(!StringUtils.isEmpty(q)) {
//            req.setQ(q);
//        }
//        if(!StringUtils.isEmpty(cat)) {
//            req.setCat(cat);
//        }
//        if(!StringUtils.isEmpty(itemloc)) {
//            req.setItemloc(itemloc);
//        }
//        if(!StringUtils.isEmpty(sort)) {
//            req.setSort(sort);
//        }
//        req.setIsTmall(is_tmall);
//        req.setIsOverseas(is_overseas);
//        if(start_price>0) {
//            req.setStartPrice(start_price);
//        }
//        if(end_price>0) {
//            req.setEndPrice(end_price);
//        }
//        if(start_tk_rate>0) {
//            req.setStartTkRate(start_tk_rate);
//        }
//        if(end_tk_rate>0) {
//            req.setEndTkRate(end_tk_rate);
//        }
//        if(platform>0) {
//            req.setPlatform(platform);
//        }
//        if(page_no>0) {
//            req.setPageNo(page_no);
//        }
//        if(page_size>0) {
//            req.setPageSize(page_size);
//        }
//        TbkItemGetResponse rsp = null;
//        try {
//            rsp = client.execute(req);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        return this.parse(rsp.getBody(),page_no,page_size);
//
//    }
//
//    @Override
//    /**
//     * 淘宝客店铺搜素接口
//     q 	String 	必须 	女装 		查询词
//     sort 	String 	可选 	commission_rate_des 		排序_des（降序），排序_asc（升序），佣金比率（commission_rate）， 商品数量（auction_count），销售总数量（total_auction）
//     is_tmall 	Boolean 	可选 	false 		是否商城的店铺，设置为true表示该是属于淘宝商城的店铺，设置为false或不设置表示不判断这个属性
//     start_credit 	Number 	可选 	1 		信用等级下限，1~20
//     end_credit 	Number 	可选 	20 		信用等级上限，1~20
//     start_commission_rate 	Number 	可选 	2000 		淘客佣金比率下限，1~10000
//     end_commission_rate 	Number 	可选 	123 		淘客佣金比率上限，1~10000
//     start_total_action 	Number 	可选 	1 		店铺商品总数下限
//     end_total_action 	Number 	可选 	100 		店铺商品总数上限
//     start_auction_count 	Number 	可选 	123 		累计推广商品下限
//     end_auction_count 	Number 	可选 	200 		累计推广商品上限
//     platform 	Number 	可选 	1   默认值：1   链接形式：1：PC，2：无线，默认：１
//     page_no 	Number 	可选 	1  默认值：1    第几页，默认1，1~100
//     page_size 	Number 	可选 	20  默认值：20  页大小，默认20，1~100
//      */
//    public Page<ShopItem> searchShop(String q, String sort, boolean is_tmall, long start_credit, long start_commission_rate, long end_commission_rate, long start_total_action, long end_total_action, long start_auction_count, long end_auction_count, long platform, long page_no, long page_size) {
//        return null;
//    }
//
//    @Override
//    /**
//     * 获取类目列表
//        cids 	Number [] 	特殊可选 	18957,19562 最大列表长度：1000 商品所属类目ID列表，用半角逗号(,)分隔 例如:(18957,19562,) (cids、parent_cid至少传一个)
//        parent_cid 	Number 	特殊可选 	50011999 		父商品类目 id，0表示根节点, 传输该参数返回所有子类目。 (cids、parent_cid至少传一个)
//     */
//    public Page<CatItem> getCat(long[] cids, long parentCid) {
////        TaobaoClient client = new DefaultTaobaoClient(tbkConfigure.getTb_url(), tbkConfigure.getTb_appkey(), tbkConfigure.getTb_appsecret());
////        ItemcatsGetRequest req = new ItemcatsGetRequest();
////        req.setCids("18957,19562");
////        req.setDatetime(StringUtils.parseDateTime("2000-01-01 00:00:00"));
////        req.setFields("cid,parent_cid,name,is_parent");
////        req.setParentCid(50011999L);
////        ItemcatsGetResponse rsp = client.execute(req);
////        System.out.println(rsp.getBody());
//        return null;
//    }
//
//    private Page<GoodsItem> parse(String json,long pageNo,long pageSize){
//        JSONObject jsonObject = JSON.parseObject(json);
//        if(jsonObject.containsKey("error_response")){
//            throw new RuntimeException(json);
//        }else{
//            List<GoodsItem> list = new ArrayList<GoodsItem>();
//            JSONObject resultObject = jsonObject.getJSONObject("tbk_item_get_response");
//            long total_results= resultObject.getLong("total_results");
//            JSONObject resultsObject = resultObject.getJSONObject("results");
//            JSONArray jsonArray = resultsObject.getJSONArray("n_tbk_item");
//            for(int i=0; i<jsonArray.size();i++){
//                JSONObject itemObject = jsonArray.getJSONObject(i);
//                GoodsItem item = new GoodsItem();
//                item.setItemUrl(itemObject.getString("item_url"));
//                item.setNick(itemObject.getString("nick"));
//                item.setNum_iid(itemObject.getLong("num_iid"));
//                item.setPictUrl(itemObject.getString("pict_url"));
//                item.setProvcity(itemObject.getString("provcity"));
//                item.setReservePrice(itemObject.getString("reserve_price"));
//                JSONObject smallImagesObject = itemObject.getJSONObject("small_images");
//                if(smallImagesObject != null) {
//                    JSONArray array = smallImagesObject.getJSONArray("string");
//                    item.setSmallImages(array.toArray(new String[] {}));
//                }
//                item.setSellerId(itemObject.getLong("seller_id"));
//                item.setTitle(itemObject.getString("title"));
//                item.setUserType(itemObject.getInteger("user_type"));
//                item.setVolume(itemObject.getLong("volume"));
//                item.setZkFinalPrice(itemObject.getString("zk_final_price"));
//                list.add(item);
//            }
//            Page<GoodsItem> page = new Page<GoodsItem>((int)total_results,(int)pageNo,(int) pageSize,list);
//            return page;
//        }
//    }
//
//
//
//}
