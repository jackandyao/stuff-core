package com.qbao.aisr.stuff.business.alimama.rest;

import com.alibaba.dubbo.container.Main;
import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BusinessRestMain{
        public static void main(String[] args) throws IOException {
          String[]path=new String[]{"dubbo_rest.xml"};
          ClassPathXmlApplicationContext context =
                  new ClassPathXmlApplicationContext(path);
          context.start();
          Main.main(args);
        }

}