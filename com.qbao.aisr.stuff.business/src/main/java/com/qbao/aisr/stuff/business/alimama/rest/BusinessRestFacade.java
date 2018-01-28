package com.qbao.aisr.stuff.business.alimama.rest;

/**
 * @author liaijun
 * @createTime 17/5/22 下午4:59
 * $$LastChangedDate: 2017-06-05 15:50:23 +0800 (Mon, 05 Jun 2017) $$
 * $$LastChangedRevision: 1091 $$
 * $$LastChangedBy: liaijun $$
 */
import com.qbao.aisr.stuff.business.alimama.service.ICrawlerAlimamaService;
import com.qbao.aisr.stuff.business.alimama.util.Result;
import com.qbao.aisr.stuff.business.alimama.util.Utils;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Path("/business")
public class BusinessRestFacade
        implements IBusinessRestFacade
{
    @Autowired
    private ICrawlerAlimamaService crawlerAlimamaService;
    Logger logger = LoggerFactory.getLogger(BusinessRestFacade.class);

    @Value("${tbk.name}")
    private String tbkName;
    @Value("${tbk.android.site.id}")
    private String tbkAndroidSiteId;
    @Value("${tbk.android.adzone.id}")
    private String tbkAndroidAdzoneId;
    @Value("${tbk.ios.site.id}")
    private String tbkIosSiteId;
    @Value("${tbk.ios.adzone.id}")
    private String tbkIosAdzoneId;
    //提供给后台dbas
    @GET
    @Path("/findUrl")
    @Produces({"application/json; charset=UTF-8"})
    public Map<String, Object> findUrl(@QueryParam("auctionid") String auctionid, @QueryParam("adzoneid") String adzoneid, @QueryParam("siteid") String siteid)
    {
        this.logger.warn(">>>>>>>>>>>>>>>/findUrl&auctionid=" + auctionid + "&adzoneid=" + adzoneid + "&siteid=" + siteid);
        Map<String, Object> response = new HashMap();
        try
        {
            String url = this.crawlerAlimamaService.createConertUrlForItem(auctionid, adzoneid, siteid);
            response.put("returnCode", "1000");
            response.put("returnMsg", "ok");
            response.put("data", url);
            this.logger.info("结果返回成功>>>>>url=" + url + ">>>>>>>>>>/findUrl&auctionid=" + auctionid + "&adzoneid=" + adzoneid + "&siteid=" + siteid);
        }
        catch (Exception e)
        {
            response.put("returnCode", "1001");
            response.put("returnMsg", "查询数据出错了");
            this.logger.error(">>>>>>>>>>>>>>>/findUrl&auctionid=" + auctionid + "&adzoneid=" + adzoneid + "&siteid=" + siteid + ">>>>>>>>>>>>>>EXception:" + ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @POST
    @Path("/queryGoods")
    @Produces({"application/json; charset=UTF-8"})
    public Map<String, Object> queryGoods(@FormParam("catName") String catName, @FormParam("catId") String catId, @FormParam("startPrice") int startPrice, @FormParam("endPrice") int endPrice, @FormParam("startRate") int startRate, @FormParam("endRate") int endRate, @FormParam("orderNum") int orderNum, @FormParam("size") int size)
    {
        this.logger.warn(">>>>>>>>>>>>>>>>>>>/queryGoods?catName=" + catName + "&catId=" + catId + "&startPrice=" + startPrice + "&endPrice=" + endPrice + "&startRate=" + startRate + "&endRate=" + endRate + "&orderNum=" + orderNum + "&size=" + size);
        Map<String, Object> response = new HashMap();
        try
        {
            List<StuffPromotion> list = this.crawlerAlimamaService.createGoodsSearchUrl(catName, catId, startPrice, endPrice, startRate, endRate, orderNum, size);
            response.put("returnCode", "1000");
            response.put("returnMsg", "ok");
            response.put("data", list);
            this.logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>/queryGoods  调用接口成功");
        }
        catch (Exception e)
        {
            response.put("returnCode", "1001");
            response.put("returnMsg", "查询数据出错了");
            this.logger.error(">>>>>>>>>>>>>>>>>>>/queryGoods?catName=" + catName + "&catId=" + catId + "&startPrice=" + startPrice + "&endPrice=" + endPrice + "&startRate=" + startRate + "&endRate=" + endRate + "&orderNum=" + orderNum + "&size=" + size + ">>>>Exception:" + ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 提供给手机端,后面的流程跟后台dbas会不同
     * auctionid 淘宝或者天猫的原始商品id
     * device 设备类型 1: ios , 2 :android
     * source: 来源:taobao  tmall
     */

    @POST
    @Path("/queryUrl")
    @Produces({"application/json; charset=UTF-8"})
    public Map<String, Object> findUrlByPhone(@FormParam("auctionid") Long auctionid, @FormParam("device")int device, @FormParam("source")String source, @FormParam("name")String name)
    {
        this.logger.warn(">>>>>>>>>>>>>>>/queryUrl&auctionid=" + auctionid + "&device=" + device +"&source="+source+"&name="+name);
        Map<String, Object> response = new HashMap();
        try
        {
            String adzoneid="";
            String siteid="";
            if(Utils.PHONETYPE_IOS==device){
                adzoneid=tbkIosAdzoneId;
                siteid=tbkIosSiteId;
            }else{
                adzoneid=tbkAndroidAdzoneId;
                siteid=tbkAndroidSiteId;
            }
            String url = this.crawlerAlimamaService.createConertUrlForItem(auctionid, adzoneid, siteid,source,device,name);
            if(StringUtils.isBlank(url)){
                response.put("responseCode", "1001");
                response.put("returnMsg", "查询数据出错了");
                response.put("operatorSource",tbkName);
            }else{
                Result result=new Result();
                result.setUrl(url);
                result.setOperatorSource(tbkName);
                response.put("responseCode", "1000");
                response.put("returnMsg", "ok");
                response.put("data", result);
            }

            this.logger.info("结果返回成功>>>>>url=" + url + ">>>>>>>>>>/queryUrl&auctionid=" + auctionid + "&adzoneid=" + adzoneid + "&siteid=" + siteid+"&source="+source+"&device="+device+"&name="+name);
        }
        catch (Exception e)
        {
            response.put("responseCode", "1001");
            response.put("returnMsg", "查询数据出错了");
            response.put("operatorSource",tbkName);
            this.logger.error(">>>>>>>>>>>>>>>/queryUrl&auctionid=" + auctionid + "&device=" +  device+"&source="+source+"&name="+name+">>>>>>>>>>>>>>EXception:" + ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
