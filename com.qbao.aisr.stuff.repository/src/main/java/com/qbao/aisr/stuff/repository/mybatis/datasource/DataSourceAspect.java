/**
 * 
 */
package com.qbao.aisr.stuff.repository.mybatis.datasource;

import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author shuaizhihu
 *
 * $LastChangedDate: 2016-08-12 10:24:49 +0800 (周五, 12 八月 2016) $
 * $LastChangedRevision: 84 $
 * $LastChangedBy: shuaizhihu $
 */
public class DataSourceAspect {

    public void before(JoinPoint point)
    {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m
                        .getAnnotation(DataSource.class);
               
                MultipleDataSource.setDataSourceKey(data.value());
//                System.out.println(data.value());
            }
            
        } catch (Exception e) {
            
        }
    }
}