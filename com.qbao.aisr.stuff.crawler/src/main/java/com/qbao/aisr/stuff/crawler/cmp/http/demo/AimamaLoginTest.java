package com.qbao.aisr.stuff.crawler.cmp.http.demo;

import com.qbao.aisr.stuff.crawler.cmp.http.HttpClientUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpHeader;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shuaizhihu on 2017/2/28.
 */
public class AimamaLoginTest {

//    <input id="J_NcoSig" name="ncoSig" type="hidden">
//    <input id="J_NcoSessionid" name="ncoSessionid" type="hidden">
//    <input id="J_NcoToken" name="ncoToken" value="5855b4bfcbf8eebff8099f30e411d4dc4636148b" type="hidden">
//    <input id="J_NcoShow" name="slideCodeShow" value="false" type="hidden">
//
//
//    <input name="useMobile" value="false" type="hidden">
//    <input id="J_lang" name="lang" value="zh_CN" type="hidden">
//    <input name="loginsite" value="0" id="J_loginsite" type="hidden"> <input name="newlogin" value="" type="hidden">
//
//    <input id="J_TPL_redirect_url" name="TPL_redirect_url" value="http://www.alimama.com" type="hidden">
//    <input id="J_From" name="from" value="restful" type="hidden">
//    <input name="fc" value="default" type="hidden">
//    <input id="J_CssStyle" name="style" value="mini" type="hidden">
//    <input id="J_CssStyle2" name="css_style" value="restful" type="hidden">
//    <input name="keyLogin" value="false" type="hidden">
//    <input name="qrLogin" value="true" type="hidden">
//    <input name="newMini" value="false" type="hidden">
//    <input name="newMini2" value="true" type="hidden">
//
//    <input name="tid" type="hidden">
//    <input name="loginType" value="3" type="hidden">
//    <input name="minititle" value="restful" type="hidden">
//    <input name="minipara" value="" type="hidden">
//    <input name="pstrong" value="" type="hidden">
//
//    <input id="J_sign" name="sign" value="" type="hidden">
//    <input id="J_need_sign" name="need_sign" value="" type="hidden">
//
//    <input id="J_isIgnore" name="isIgnore" value="" type="hidden">
//    <input id="J_full_redirect" name="full_redirect" value="true" type="hidden">
//    <!-- 子账号跳转方式 -->
//    <input name="sub_jump" value="" type="hidden">
//
//    <input name="popid" value="" type="hidden">
//    <input name="callback" value="" type="hidden">
//
//    <input id="J_guf" name="guf" value="" type="hidden">
//    <input id="J_not_duplite_str" name="not_duplite_str" value="" type="hidden">
//    <input name="need_user_id" value="" type="hidden">
//
//    <input name="poy" type="hidden">
//
//    <input id="gvfdc" name="gvfdcname" value="10" type="hidden">
//    <input name="gvfdcre" value="" type="hidden">
//
//    <input id="J_from_encoding" name="from_encoding" value="" type="hidden">
//
//    <input id="J_sub" name="sub" value="false" type="hidden">
//
//    <input name="TPL_password_2" id="TPL_password_2" type="hidden">
//    <input id="J_PBK" value="9a39c3fefeadf3d194850ef3a1d707dfa7bec0609a60bfcc7fe4ce2c615908b9599c8911e800aff684f804413324dc6d9f982f437e95ad60327d221a00a2575324263477e4f6a15e3b56a315e0434266e092b2dd5a496d109cb15875256c73a2f0237c5332de28388693c643c8764f137e28e8220437f05b7659f58c4df94685" type="hidden">
//    <input name="loginASR" value="1" type="hidden">
//    <input name="loginASRSuc" value="0" type="hidden">
//
//    <input id="J_allp" name="allp" value="" type="hidden">
//    <input name="oslanguage" value="zh-CN" type="hidden">
//    <input name="sr" value="1366*768" type="hidden">
//    <input name="osVer" value="windows|6.1" type="hidden">
//    <input name="naviVer" value="firefox|51" type="hidden">
//    <input name="osACN" value="Mozilla" type="hidden">
//    <input name="osAV" value="5.0 (Windows)" type="hidden">
//    <input name="osPF" value="Win32" type="hidden">
//    <input name="miserHardInfo" id="M_hard_info" type="hidden">
//    <input id="J_Appkey" name="appkey" value="" type="hidden">
//    <input id="J_Bindlogin" name="bind_login" value="false" type="hidden">
//    <input id="J_Bindtoken" name="bind_token" value="" type="hidden">
//    <input name="nickLoginLink" value="" type="hidden">
//    <input name="mobileLoginLink" value="https://login.taobao.com/member/login.jhtml?style=mini&amp;newMini2=true&amp;css_style=alimama&amp;from=alimama&amp;redirectURL=http://www.alimama.com&amp;full_redirect=true&amp;disableQuickLogin=true&amp;useMobile=true" type="hidden">
//    <input name="showAssistantLink" value="false" type="hidden">

    public static void main(String[] args) throws HttpProcessException {
        String indexUrl = "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=restful&from=restful&redirectURL=http://www.restful.com&full_redirect=true&disableQuickLogin=true";
        String tgUrl = "http://pub.restful.com/common/code/getAuctionCode.json";

        Header[] headers = HttpHeader.custom().userAgent("User-Agent: Mozilla/5.0").build();
        HttpCookies cookies = HttpCookies.custom();
        HttpConfig config =HttpConfig.custom().url(indexUrl).context(cookies.getContext());
        String loginform = HttpClientUtil.get(config.headers(headers));
//        System.out.print(loginform);
        //组装参数
        Map<String, Object> map = new HashMap<String, Object>();
        Document htmlDocument = Jsoup.parse(loginform);
        Element element = htmlDocument.getElementById("J_Form");
//        System.out.println(element.html());
        Elements divs = element.getElementsByClass("submit");
        Iterator<Element> es = divs.iterator();
        if(es.hasNext()){
            Elements inputs = es.next().getElementsByTag("input");
            Iterator<Element> ins = inputs.iterator();
            while(ins.hasNext()){
                Element in = ins.next();
                if(!in.attr("name").equals("")) {
//                    System.out.println(in.attr("name") + ":" + in.val());
                    map.put(in.attr("name"), in.val());
                }
            }
        }



        for (Cookie cookie : cookies.getCookieStore().getCookies()) {
			System.out.println(cookie.toString());
		}


        String result = HttpClientUtil.post(config.map(map));
        System.out.println(result);
//        "self.location.href"
        String regex = "top.location.href = \"(.*?)\"";
        String loginCallUrl = regex(regex,result)[0];
        System.out.println(loginCallUrl);
        result = HttpClientUtil.get(config.url(loginCallUrl).headers(headers).context(cookies.getContext()));
        System.out.println(result);

//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","_tb_token_","iKJ61MMIGMPq","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","alimamapw","\"QCYOQSRRR3xSEiZ8FyFTRHByQCB7QSdQUQhUAG0JBwFXV1UHUlJbUVFbUFsCBwcKBVZQAwUHVAQNVQIHAA==\"","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","alimamapwag","TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgNi4xOyBXT1c2NDsgcnY6NTEuMCkgR2Vja28vMjAxMDAxMDEgRmlyZWZveC81MS4w","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","cna","1QEkEcpO01ICAXT3f8rtHom8","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","cookie2","77dc41284686863bd88dcff5caee5a9f","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","cookie31","MTcwNzczMTUsJUU1JUEzJUFFJUU1JUJGJTk3JUU5JTlCJTg0JUU4JTk5JThFMzAwNyxzemhfMzQyMzE1NDY1QHFxLmNvbSxUQg==","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","cookie32","82a56426358b187962bc812762aa4542","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","isg","AgIC-dci7mp7WvKrLyExHf0fUgg8dQbtKZH-dEwbLnUgn6IZNGNW_YjdOSQT","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","l","AnZ2nICZK1WK8w9jFKpKfreOxqZ4l7rR","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","login","Vq8l+KCLz3/65A==","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","rurl","aHR0cDovL3d3dy5hbGltYW1hLmNvbS9pbmRleC5odG0=","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","t","2787317fb5bf81be9867e4af633e2658","/"));
//        cookies.getCookieStore().addCookie(createBasicCookie("restful.com","v","0","/"));

        String _tb_token_ = "";
        for (Cookie cookie : cookies.getCookieStore().getCookies()) {
            System.out.println(cookie.getName()+"--"+cookie.getValue());
            if(cookie.getName().equals("_tb_token_")){
                _tb_token_=cookie.getValue();
            }
        }


//

        tgUrl+="?auctionid=542177363361&adzoneid=71086882&siteid=21068365&_tb_token_="+_tb_token_;
        System.out.println(tgUrl);
        result = HttpClientUtil.get(config.url(tgUrl).headers(headers).context(cookies.getContext()));
        System.out.println(result);




    }


    /**
     * 通过正则表达式获取内容
     *
     * @param regex		正则表达式
     * @param from		原字符串
     * @return
     */
    public static String[] regex(String regex, String from){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(from);
        List<String> results = new ArrayList<String>();
        while(matcher.find()){
            for (int i = 0; i < matcher.groupCount(); i++) {
                results.add(matcher.group(i+1));
            }
        }
        return results.toArray(new String[]{});
    }

    public static BasicClientCookie createBasicCookie(String domain ,String name,String value,String path){
        BasicClientCookie c1 = new BasicClientCookie(name,value);
        c1.setDomain(domain);
        c1.setPath(path);
        c1.setVersion(0);
        return c1;
    }
}
