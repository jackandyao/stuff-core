package com.qbao.aisr.stuff.business.alimama.service;

import com.qbao.aisr.stuff.common.page.Page;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;

/**
 * Created by shuaizhihu on 2017/3/1.
 */
public interface IAlimamaService {

    public Page<StuffPromotion> search(String keyword,String sourceType,String sort);
}
