package com.qbao.aisr.stuff.business.alimama.service;

import com.qbao.aisr.stuff.business.alimama.util.DownloadContext;
import org.springframework.stereotype.Component;

/**
 * Created by shuaizhihu on 2017/3/1.
 */
@Component
public interface IDownloadService {



    public boolean  downLoad(DownloadContext context) throws Exception;

//

}
