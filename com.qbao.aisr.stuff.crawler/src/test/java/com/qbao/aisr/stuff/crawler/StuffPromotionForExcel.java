package com.qbao.aisr.stuff.crawler;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;


/**
 * Unit test for simple App.
 */

public class StuffPromotionForExcel
{




     @Test
    public void rootDirInit() throws Exception {
    	// poi读取excel
        //创建要读入的文件的输入流
        InputStream inp = new FileInputStream("/Users/allen/Downloads/A.xls");

        //根据上述创建的输入流 创建工作簿对象
        Workbook wb = WorkbookFactory.create(inp);
        //得到第一页 sheet
        //页Sheet是从0开始索引的
        Sheet sheet = wb.getSheetAt(0);
        HashSet<String> dirSet = new HashSet<String>();
        //利用foreach循环 遍历sheet中的所有行
        for (Row row : sheet) {
        	 if(dirSet.add(row.getCell(0)+"")){
        	 for(int j=0;j<22;j++){
                 System.out.print(row.getCell(j));
                }
          }
        }
        //关闭输入流
        inp.close();

    }




    //@Test
    public void secDirInit() throws  Exception {

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



    

}
