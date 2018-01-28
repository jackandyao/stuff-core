package com.qbao.aisr.stuff.repository.restful.tbk;

import com.qbao.aisr.stuff.common.page.Page;
import com.qbao.aisr.stuff.model.tbk.CatItem;
import com.qbao.aisr.stuff.model.tbk.GoodsItem;
import com.qbao.aisr.stuff.model.tbk.ShopItem;

/**
 * Created by shuaizhihu on 2017/3/6.
 */
public interface ITBKRest {

    public Page<GoodsItem> search(String q,String cat,String itemloc,String sort,boolean is_tmall,boolean is_overseas,long start_price,long end_price,long start_tk_rate,long end_tk_rate,long platform,long page_no,long page_size);

    public Page<ShopItem> searchShop(String q,  String sort, boolean is_tmall,  long start_credit, long start_commission_rate, long end_commission_rate, long start_total_action, long end_total_action, long start_auction_count, long end_auction_count,long platform,long page_no,long page_size );

    public Page<CatItem> getCat(long[] cids,long parentCid);
}