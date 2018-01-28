package com.qbao.aisr.stuff.crawler.cmp.parse;

import jxl.read.biff.BiffException;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 13:34
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public interface IParseService {
    public boolean parse(ParseContext context) throws ParseException, IOException, BiffException;
}
