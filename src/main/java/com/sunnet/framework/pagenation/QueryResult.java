package com.sunnet.framework.pagenation;

import java.util.List;

public class QueryResult<T>
{
  private List<T> resultList;
  private PageBean pageBean;
  
  public List<T> getResultList()
  {
    return this.resultList;
  }
  
  public void setResultList(List<T> resultList)
  {
    this.resultList = resultList;
  }
  
  public PageBean getPageBean()
  {
    return this.pageBean;
  }
  
  public void setPageBean(PageBean pageBean)
  {
    this.pageBean = pageBean;
  }
}
