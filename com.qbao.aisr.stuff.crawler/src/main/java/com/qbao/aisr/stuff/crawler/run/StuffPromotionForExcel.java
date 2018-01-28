package com.qbao.aisr.stuff.crawler.run;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qbao.aisr.stuff.config.App;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffCoupon;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCategoryDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCouponDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;


/**
 * Unit test for simple App.
 */

public class StuffPromotionForExcel{
/**
 * delete from stuff_promotion_tmp_liaijun1;
delete from stuff_coupon_liaijun1;
 */
Logger logger=Logger.getLogger(StuffPromotionForExcel.class);

  private String tableName;
  
  private StuffPromotionTmpDao stuffPromotionTmpDao;
  
  private StuffCouponDao  stuffCouponDao;
  
  private StuffCategoryDao stuffCategoryDao;
  
  public void init() throws Exception{
	  String paths[] = new String[]{"spring_crawler.xml"};
      //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
      ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
      stuffPromotionTmpDao =  ctx.getBean(StuffPromotionTmpDao.class);
      stuffCouponDao=ctx.getBean(StuffCouponDao.class);
      stuffCategoryDao=ctx.getBean(StuffCategoryDao.class);
      Properties prop = new Properties();//属性集合对象
      InputStream fis;
      fis = App.class.getClassLoader().getResourceAsStream("tbk.properties");
      prop.load(fis);//将属性文件流装载到Properties对象中
      tableName=prop.getProperty("tbk.table.name");
  }


   
    public void android()  {
    	logger.info(">>>>>>>>>>>>>>>程序开始>>>>>>>>>>>>>>>");
    	InputStream inp =null;
    	try{
    	init();
    	// poi读取excel
        //创建要读入的文件的输入流
         inp = new FileInputStream("/Users/allen/Downloads/A.xls");
        //根据上述创建的输入流 创建工作簿对象
        Workbook wb = WorkbookFactory.create(inp);
        //得到第一页 sheet
        //页Sheet是从0开始索引的
        Sheet sheet = wb.getSheetAt(0);
        //利用foreach循环 遍历sheet中的所有行
        List<StuffPromotion> list=new ArrayList<StuffPromotion>();
        List<StuffCoupon> clist=new ArrayList<StuffCoupon>();
        int i=0;
        for (Row row : sheet) {
	        	if(i==0){
	        		i++;continue;
	        	}
        		 String realStuffId=row.getCell(0)+"";
        		 String source=row.getCell(13)+"";
        		 String id="";
        		 if("天猫".equals(source)){
        			 id=realStuffId+"222";
        			 source="tmall";
        		 }else{
        			 id=realStuffId+"111";
        			 source="taobao";
        		 }
        		 String name=row.getCell(1)+"";
        		 String reservePrice=row.getCell(6)+"";//这里有疑问，稍后解决stuff
        		 String finalPrice=row.getCell(6)+""; //excel里的价格就是打折后实际价格
        		 String cat_id="";                   //这里有疑问，稍后解决
        		 String activities="";               //这里有疑问，稍后解决
        		 String introduce="";                //调用qingtaoke接口 coupon  
        		 String type="";                     //调用qingtaoke接口 coupon
        		 String total_count="";              //调用qingtaoke接口 coupon
        		 int status=1;                    //
        		 String link=row.getCell(21)+"";
        		 String value=row.getCell(17)+"";
        		 String androidPromotionUrl=row.getCell(5)+"";
        		 String iosPromotionUrl=row.getCell(22)+"";
        		 String url=row.getCell(3)+"";
        		 String img=row.getCell(2)+"";
        		 String shop_id=row.getCell(11)+"";
        		 String shopName=row.getCell(12)+"";
        		 String orderNum=row.getCell(7)+"";
        		 String catName=row.getCell(4)+"";
        		 if(StringUtils.isNotBlank(catName)){
        			 if(catName.indexOf("女")>=0){
        				 activities="girl";
        			 }else if(catName.indexOf("男")>=0){
        				 activities="man";
        			 }
        		 }
        		 logger.info(">>>>>>>>>>>>>>>程序进行中>>>>>>>>>>>>>>>activities＝"+activities+"&catName="+catName);
        		 String startTime=row.getCell(18)+"";
        		 String endTime=row.getCell(19)+"";
        		 
        		 try{
        		 StuffPromotion stuff=new StuffPromotion();
        		 stuff.setId(new Long(id));
        		 stuff.setRealStuffId(new Long(realStuffId));
        		 stuff.setName(name);
        		 stuff.setReservePrice(new BigDecimal(reservePrice));
        		 stuff.setFinalPrice(new BigDecimal(reservePrice));
        		 BigDecimal promotionRate=new BigDecimal(row.getCell(8)+"").multiply(new BigDecimal(100));
        		 stuff.setPromotionRate(promotionRate.longValue());
        		 stuff.setIosPromotionUrl(iosPromotionUrl);
        		 stuff.setAndroidPromotionUrl(androidPromotionUrl);
        		 stuff.setUrl(url);
        		 stuff.setImgUrl(img);
        		 stuff.setShopId(new Long(shop_id));
        		 stuff.setShopName(shopName);
        		 stuff.setOrderNum(new Integer(orderNum));
        		 Date nowTime=new Date();
        		 stuff.setCreateTime(nowTime);
        		 stuff.setUpdateTime(nowTime);
        		 stuff.setSource(source);
        		 stuff.setOperatorSource(tableName);
        		 stuff.setActivities(activities);
        		 StuffCategory cat=stuffCategoryDao.findStuffCategoryByName("%"+catName+"%");
        		 if(cat!=null){
        		 stuff.setCatId(cat.getCatId());
        		 }else{
        			 String [] cName=catName.split("/");
        			 if(cName.length>1){
        			 for(String str:cName){
        				 cat=stuffCategoryDao.findStuffCategoryByName("%"+str+"%");
                		 if(cat!=null){
	                		 stuff.setCatId(cat.getCatId());
	                		 break;
                		 }
        			  }
        			 }
        		 }
        		 stuff.setCatName(catName);
        		 stuff.setStatus(status);
        		 list.add(stuff);
        		 if(list.size()==1000){
        			 stuffPromotionTmpDao.insertBatch(tableName, list);
        			 list=new ArrayList<StuffPromotion>();
        		 }
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		 try{
        		 //logger.info(stuff);
        		 StuffCoupon stuffCoupon=new StuffCoupon();
        		 stuffCoupon.setStuffId(new Long(id));
        		 stuffCoupon.setRealStuffId(new Long(realStuffId));
        		 stuffCoupon.setLink(link);
        		 value=value.replace("无条件券", "");
        		 value=value.substring(value.indexOf("减")+1, value.length()-1);
        		 stuffCoupon.setValue(new BigDecimal(value));
        		 stuffCoupon.setSource(source);
        		 stuffCoupon.setStartTime(StrToDate(startTime));
        		 stuffCoupon.setEndTime(StrToDate(endTime));
        		 
        		 clist.add(stuffCoupon);
        		 if(clist.size()==1000){
        		   stuffCouponDao.insertBatch(tableName, clist);
        		   list=new ArrayList<StuffPromotion>();
        		 }
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
        }
        if(CollectionUtils.isNotEmpty(list)){
           stuffPromotionTmpDao.insertBatch(tableName, list);
        }
        if(CollectionUtils.isNotEmpty(clist)){
 		   stuffCouponDao.insertBatch(tableName, clist);
 		 }
        //关闭输入流
        inp.close();
        logger.info(">>>>>>>>>>>>>>>程序结束>>>>>>>>>>>>>>>");
     }catch(Exception ex){
    	 logger.error(ex.getMessage(),ex);
    	 ex.printStackTrace();
     }finally{
    	 if(inp!=null){
    		 try {
				inp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	 }
    	 logger.info(">>>>>>>>>>>>>>>程序结束>>>>>>>>>>>>>>>");
     }

    }

  


    
    public void ios() throws  Exception {

	// poi读取excel
       //创建要读入的文件的输入流
       InputStream inp = new FileInputStream("/Users/allen/Desktop/I.xlsx");

       //根据上述创建的输入流 创建工作簿对象
       Workbook wb = WorkbookFactory.create(inp);
       //得到第一页 sheet
       //页Sheet是从0开始索引的
       Sheet sheet = wb.getSheetAt(0);
       HashSet<String> dirSet = new HashSet<String>();
       int rootDirCount =0;
       //利用foreach循环 遍历sheet中的所有行
       for (Row row : sheet) {
    	   if(dirSet.add(row.getCell(1)+"")){
           	try{
               rootDirCount++;
              for(int j=0;j<22;j++){
               System.out.print(row.getCell(j));
              }
           	}catch(Exception ex){
           		ex.printStackTrace();
           	}
           }
       }
       //关闭输入流
       inp.close();


    }


	public static void main(String[] args){
//		String name="满15元减10元";
//		name ="10元无条件券";
//		name=name.replace("无条件券", "");
//		name=name.substring(name.indexOf("减")+1, name.length()-1);
//		System.out.println(name);
		StuffPromotionForExcel test=new StuffPromotionForExcel();
		test.android();
//		String [] cName="a/b".split("/");
//		System.out.println(cName.length);
		
	}
    
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (Exception e) {
	    e.printStackTrace();
	   }
	   return date;
	}
}
