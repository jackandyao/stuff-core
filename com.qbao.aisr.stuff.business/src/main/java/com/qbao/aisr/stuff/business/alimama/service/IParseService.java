package com.qbao.aisr.stuff.business.alimama.service;

import java.io.IOException;
import java.text.ParseException;

import com.qbao.aisr.stuff.business.alimama.util.ParseContext;
import jxl.read.biff.BiffException;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 13:34
 * $$LastChangedDate: 2017-05-21 19:54:56 +0800 (Sun, 21 May 2017) $$
 * $$LastChangedRevision: 937 $$
 * $$LastChangedBy: liaijun $$
 */
public interface IParseService {
    public boolean parse(ParseContext context) throws ParseException, IOException, BiffException;
}
