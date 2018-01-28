package com.qbao.aisr.stuff.crawler.cmp.parse;

import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 13:42
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
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
