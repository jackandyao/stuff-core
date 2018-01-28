package com.qbao.aisr.stuff.common.page;

/**
 * 
 * @author Bimu
 *
 * @param <T> 源对象
 * @param <K> 目标对象
 */
public interface PageItemChange<T,K> {
	
	/**
	 * 把T对象转换为K对象
	 * 
	 * @param t
	 * @return
	 */
	public K change(T t);
	
}
