package com.qbao.aisr.stuff.crawler.cmp.persist;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 15:07
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public interface IPersistService {
    public boolean persist(PersistTask task);

    public void persistOver();

}
