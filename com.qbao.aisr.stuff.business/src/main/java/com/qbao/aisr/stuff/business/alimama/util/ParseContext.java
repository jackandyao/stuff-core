package com.qbao.aisr.stuff.business.alimama.util;

import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 13:42
 * $$LastChangedDate: 2017-05-21 18:37:40 +0800 (Sun, 21 May 2017) $$
 * $$LastChangedRevision: 935 $$
 * $$LastChangedBy: liaijun $$
 */
public class ParseContext<T> {
    private List<T> parseResult;
    private ParseTask parseTask;

    public ParseContext(ParseTask parseTask){
        this.parseTask = parseTask;
        parseResult=null;
    }

    public List<T> getParseResult() {
        return parseResult;
    }

    public void setParseResult(List<T> parseResult) {
        this.parseResult = parseResult;
    }

    public ParseTask getParseTask() {
        return parseTask;
    }

    public void setParseTask(ParseTask parseTask) {
        this.parseTask = parseTask;
    }
}
