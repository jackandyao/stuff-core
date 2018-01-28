package com.qbao.aisr.stuff.business.alimama.service;

import com.qbao.aisr.stuff.business.alimama.util.PersistTask;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 15:07
 * $$LastChangedDate: 2017-05-21 19:54:56 +0800 (Sun, 21 May 2017) $$
 * $$LastChangedRevision: 937 $$
 * $$LastChangedBy: liaijun $$
 */
public interface IPersistService {
    public boolean persist(PersistTask task);

    public void persistOver();

}
