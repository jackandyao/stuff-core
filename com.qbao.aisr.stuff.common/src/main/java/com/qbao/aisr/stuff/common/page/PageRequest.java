package com.qbao.aisr.stuff.common.page;

public class PageRequest {

	private int pageIndex;
	private int pageSize;
	private int start;

	public PageRequest(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		if (pageIndex <= 1)
			start = 0;
		else
			start = (pageIndex - 1) * pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

}
