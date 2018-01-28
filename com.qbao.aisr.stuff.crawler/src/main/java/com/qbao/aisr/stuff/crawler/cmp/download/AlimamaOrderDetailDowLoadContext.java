package com.qbao.aisr.stuff.crawler.cmp.download;

import com.qbao.aisr.stuff.common.stuffenum.AlimamaOrderStatus;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;

/**
 * @author wangping
 * @createTime 2017/6/18 下午11:18
 * $$LastChangedDate: 2017-06-18 23:52:37 +0800 (Sun, 18 Jun 2017) $$
 * $$LastChangedRevision: 1281 $$
 * $$LastChangedBy: wangping $$
 */
public class AlimamaOrderDetailDowLoadContext extends  DownloadContext {
    private AlimamaOrderStatus orderStatus;

    public AlimamaOrderDetailDowLoadContext(HttpConfig httpConfig) {
        super(httpConfig);
    }

    public AlimamaOrderStatus getOrderStatus() {

        return orderStatus;
    }

    public void setOrderStatus(AlimamaOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
