package com.qbao.aisr.stuff.crawler.cmp.parse.impl;

import com.qbao.aisr.stuff.common.stuffenum.TaokeOrderStatus;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseTask;
import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.model.mysql.StuffRebate;
import com.qbao.aisr.stuff.model.mysql.TaokeDetail;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffRebateDao;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/15 19:04
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="alimamaOrderDetailParseService")
public class AlimamaOrderDetailParseService implements IParseService {
    Logger logger = Logger.getLogger(AlimamaOrderDetailParseService.class);

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;

    @Autowired
    StuffRebateDao stuffRebateDao;

    @Override
    public boolean parse(ParseContext context) throws ParseException, IOException, BiffException {
        ParseTask parseTask  = context.getParseTask();
        String filePath = parseTask.getContent();
        System.out.println(filePath);
        List<TaokeDetail> list = this.parseList(filePath);
        context.setParseResult(list);
        return true;
    }

    public List<TaokeDetail> parseList(String filepath) throws IOException, BiffException, ParseException {
        List<TaokeDetail> list = new ArrayList<TaokeDetail>();
        jxl.Workbook readwb = null;
        File file = new File(filepath);
        //FileUtils.sizeOf(file) ==
            InputStream instream = new FileInputStream(file);
            readwb = Workbook.getWorkbook(instream);
            //Sheet的下标是从0开始
            //获取第一张Sheet表
            Sheet readsheet = readwb.getSheet(0);
            //获取Sheet表中所包含的总列数
            int rsColumns = readsheet.getColumns();
            //获取Sheet表中所包含的总行数
            int rsRows = readsheet.getRows();
            //获取指定单元格的对象引用
            for (int i = 0; i < rsRows; i++)
            {
                if(i>0) {
                    TaokeDetail taokeDetail = new TaokeDetail();
                    for (int j = 0; j < rsColumns; j++) {
                        Cell cell = readsheet.getCell(j, i);
                        System.out.print(cell.getContents()+"  ");
                        switch (j){
                        case 0:
                            taokeDetail.setOrderTime(format.parse(cell.getContents()));
                            break;
                        case 1:
                            taokeDetail.setClickTime(format.parse(cell.getContents()));
                            break;
                        case 2:
                            taokeDetail.setName(cell.getContents());
                            break;
                        case 3:
                            taokeDetail.setRealStuffId(Long.parseLong(cell.getContents()));
                            break;
                        case 4:
                            taokeDetail.setWangwang(cell.getContents());
                            break;
                        case 5:
                            taokeDetail.setShopName(cell.getContents());
                            break;
                        case 6:
                            taokeDetail.setStuffNum(Integer.parseInt(cell.getContents()));
                            break;
                        case 7:
                            taokeDetail.setPrice(new BigDecimal(cell.getContents()));
                            break;
                        case 8:
                            taokeDetail.setOrderStatus(cell.getContents());
                            if(cell.getContents().equals(TaokeOrderStatus.FAIL.getName())){
                                taokeDetail.setStatus(TaokeOrderStatus.FAIL.getCode());
                            }else if(cell.getContents().equals(TaokeOrderStatus.PAY.getName())){
                                taokeDetail.setStatus(TaokeOrderStatus.PAY.getCode());
                            }else if(cell.getContents().equals(TaokeOrderStatus.SUCCESS.getName())){
                                taokeDetail.setStatus(TaokeOrderStatus.SUCCESS.getCode());
                            }else if(cell.getContents().equals(TaokeOrderStatus.SETTLEMENT.getName())){
                                taokeDetail.setStatus(TaokeOrderStatus.SETTLEMENT.getCode());
                            }
                            break;
                        case 9:
                            taokeDetail.setOrderType(cell.getContents());
                            break;
                        case 10:
                            taokeDetail.setIncomeRate(this.parseRate(cell.getContents()));
                            break;
                        case 11:
                            taokeDetail.setSharingRate(this.parseRate(cell.getContents()));
                            break;
                        case 12:
                            taokeDetail.setPayValue(new BigDecimal(cell.getContents()));
                        case 14:
                            taokeDetail.setSettlementValue(new BigDecimal(cell.getContents()));
                            break;
                        case 13:
                            taokeDetail.setEstimateValue(new BigDecimal(cell.getContents()));
                            break;
                        case 17:
                            taokeDetail.setCommissionRate(this.parseRate(cell.getContents()));
                            break;
                        case 18:
                            taokeDetail.setCommissionValue(new BigDecimal(cell.getContents()));
                            break;
                        case 19:
                            taokeDetail.setSubsidyRate(this.parseRate(cell.getContents()));
                            break;
                        case 21:
                            taokeDetail.setSubsidyType(cell.getContents());
                            break;
                        case 22:
                            taokeDetail.setPlatform(cell.getContents());
                            break;
                        case 23:
                            taokeDetail.setThirdPartySerivce(cell.getContents());
                            break;
                        case 24:
                            taokeDetail.setOrderId(Long.parseLong(cell.getContents()));
                            break;
                        case 26:
                            taokeDetail.setSourceId(Long.parseLong(cell.getContents()));
                            break;
                        case 27:
                            taokeDetail.setMediaName(cell.getContents());
                            break;
                        case 28:
                            taokeDetail.setAdvId(Long.parseLong(cell.getContents()));
                            break;
                        case 29:
                            taokeDetail.setAdvName(cell.getContents());
                            break;
                        }
                    }

                    TaokeDetail detail = this.create(taokeDetail);
                    if(null != detail) {
                        list.add(detail);
                    }
                    System.out.println("");
                }
            }
        logger.info("taoke Detail total size =" +list.size());
        return list;
    }

    private Double parseRate(String rate){
        rate = StringUtils.trimToEmpty(rate.replace("%",""));
        Double r =  Double.parseDouble(rate);
        return r/100;
    }

    private TaokeDetail create(TaokeDetail taokeDetail) throws ParseException {
         StuffPromotion stuffPromotion = stuffPromotionTmpDao.findStuffPromotionByRealStuffId(taokeDetail.getRealStuffId());
        if(stuffPromotion!=null){
            //taokeDetail.setClickUrl(stuffPromotion.getPromotionUrl());
            taokeDetail.setAndroidClickUrl(stuffPromotion.getAndroidPromotionUrl());
            taokeDetail.setIosClickUrl(stuffPromotion.getIosPromotionUrl());
            taokeDetail.setImgUrl(stuffPromotion.getImgUrl());
            taokeDetail.setStuffId(stuffPromotion.getId());
            StuffRebate stuffRebate = stuffRebateDao.findStuffReBate((int)stuffPromotion.getRebateId());
            Date ad = DateUtils.parseDate("2017-06-27 00:00:00", "yyyy-MM-dd HH:mm:ss");
            if(stuffRebate != null && taokeDetail.getOrderTime().before(ad)){//2017-06-27之前的订单返宝券50%
                taokeDetail.setRebateType(0);// 返利类型 0  宝券 1 RMB
                if(stuffRebate.getIsAbsolute()==1){
                    taokeDetail.setRebateValue(stuffRebate.getValue().longValue()*taokeDetail.getStuffNum());
                }else{
                    long rebateValue = (long) (taokeDetail.getPayValue().doubleValue()*taokeDetail.getSharingRate()*taokeDetail.getCommissionRate()*(50));
                    taokeDetail.setRebateValue(rebateValue);
                }
            }else if (stuffRebate != null && taokeDetail.getOrderTime().after(ad)){//2017-06-27之前的订单返返RMB
                taokeDetail.setRebateType(1);// 返利类型 0  宝券 1 RMB
                if(stuffRebate.getIsAbsolute()==1){
                    taokeDetail.setRebateValue(stuffRebate.getValue().longValue()*taokeDetail.getStuffNum());
                }else{
                    long rebateValue = (long) (taokeDetail.getPayValue().doubleValue()*taokeDetail.getSharingRate()*taokeDetail.getCommissionRate()*(stuffRebate.getValue().intValue()) /100);
                    taokeDetail.setRebateValue(rebateValue);
                }
            }else{
                taokeDetail.setRebateValue(0);
            }

            taokeDetail.setUpdateTime(new Date());
           // return taokeDetail;
        }//TODO 淘宝推广的bug?
        return taokeDetail;


    }

    public static void main (String[] args) throws ParseException, IOException, BiffException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(args == null || args.length <0){
            System.err.println("please input tbkOrderDetails.xls file");
            System.exit(-1);
        }
        String paths[] = new String[] { "spring_crawler.xml" };
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        AlimamaOrderDetailParseService alimamaOrderDetailParseService = ctx.getBean("alimamaOrderDetailParseService", AlimamaOrderDetailParseService.class);
        IPersistService iPersistService = ctx.getBean("taokeDetailPersistService", IPersistService.class);
        String file = StringUtils.trimToEmpty(args[0]);
        System.out.println("file name :" + file);
        List<TaokeDetail> details = alimamaOrderDetailParseService.parseList(file);
        int count = 0;
        for (TaokeDetail detail : details) {
            PersistTask persistTask = new PersistTask();
            persistTask.setPersistData(detail);
            iPersistService.persist(persistTask);
            count++;
            System.out.println(count+ " task are done!");
        }
        System.out.println(count+ " tasks are done!");

    }


}
