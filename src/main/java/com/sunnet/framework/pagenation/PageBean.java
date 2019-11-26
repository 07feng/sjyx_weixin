package com.sunnet.framework.pagenation;

import java.io.Serializable;

public class PageBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int totalRecord = 0;//总记录数
  private int totalPage = 0;//一共有多少页
  private int currentPage = 1;//当前页
  private int pageSize = 20;//一页多少条
  private String pageUrl;//页面地址
  private String sql;
  private String hql;
  private int starRow = 0;//从第几条开始
  /* app分页参数 */
  private int startPage=0;
  
  public PageBean() {}
  
  public PageBean(int currentPage, int pageSize)
  {
    this.currentPage = currentPage;
    this.pageSize = pageSize;
  }
  
  public Integer getStartRow()
  {
    if(starRow == 0) {
      return Integer.valueOf((getCurrentPage() - 1) * getPageSize()+1);
    }else{
      return this.starRow;
    }
  }
  public void setStartRow(Integer startRow)
  {
    this.starRow=startRow;
  }
  
  public Integer getEndRow()
  {
    return Integer.valueOf(getCurrentPage() * getPageSize());
  }
  
  public int getTotalRecord()
  {
    return this.totalRecord;
  }
  
  public void setTotalRecord(int totalRecord)
  {
    this.totalRecord = totalRecord;
    this.totalPage = ((totalRecord + this.pageSize - 1) / this.pageSize);
  }
  
  public int getTotalPage()
  {
    return this.totalPage;
  }
  
  public void setTotalPage(int totalPage)
  {
    this.totalPage = totalPage;
  }
  
  public int getCurrentPage()
  {
    if (this.currentPage < 1) {
      this.currentPage = 1;
    }
    return this.currentPage;
  }
  
  
  public int getCurrent_Page()
  {
    return this.currentPage;
  }
  
  public void setCurrentPage(int currentPage)
  {
    this.currentPage = currentPage;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public String getPageUrl()
  {
    return this.pageUrl;
  }
  
  public void setPageUrl(String pageUrl)
  {
    this.pageUrl = pageUrl;
  }
  
  public String getSql()
  {
    return this.sql;
  }
  
  /**
   * sql 只能小写
   * @param sql
   */
  public void setSql(String sql)
  {
    this.sql = sql;
  }
  
  public String getHql()
  {
    return this.hql;
  }
  
  public void setHql(String hql)
  {
    this.hql = hql;
  }

public int getStartPage() {
	return startPage;
}

public void setStartPage(int startPage) {
	this.startPage = startPage;
}
}
