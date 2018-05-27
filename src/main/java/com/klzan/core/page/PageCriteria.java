package com.klzan.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageCriteria implements Serializable {
	private final static String ORDER_ASC = "asc";
	private final static String ORDER_DESC = "desc";
	/**
	 * 当前页码
	 */
	private Integer page;
	/**
	 * 每页多少条记录
	 */
	private Integer rows;
	/**
	 * 排序字段
	 */
	private String sort;
	/**
	 * 数据库排序字段，如果此字段为空，则取sort为排序字段
	 */
	private String sortName;
	/**
	 * 排序顺序 asc desc
	 */
	private String order;

	/**
	 * 多字段排序
	 */
	private List<Sort> sorts;

	/**
	 * 查询参数
	 */
	private List<ParamsFilter> params = new ArrayList<>();

	public PageCriteria() {
		this.page = 1;
		this.rows = 10;
	}

	public PageCriteria(Integer page, Integer rows) {
		this.page = page;
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public List<Sort> getSorts() {
		return sorts;
	}

	public void setSorts(List<Sort> sorts) {
		this.sorts = sorts;
	}

	public List<ParamsFilter> getParams() {
		return params;
	}

	public void setParams(List<ParamsFilter> params) {
		this.params = params;
	}

	public int getOffset() {
		if (page == null || rows == null || page <= 1 || rows <= 0) {
			return 0;
		}
		return (page - 1) * rows;
	}

	public int getMaxResults() {
		if (rows != null) {
			return rows;
		}
		return 0;
	}

	public boolean enablePaging() {
		if (rows == null) return false;
		return rows >= 0;
	}

	public void desc(String sortName) {
		this.sortName = sortName;
		this.order = ORDER_DESC;
	}

	public void asc(String sortName) {
		this.sortName = sortName;
		this.order = ORDER_ASC;
	}

	public void addSort(String sortName) {
		this.sortName = sortName;
		this.order = ORDER_ASC;
	}
}
