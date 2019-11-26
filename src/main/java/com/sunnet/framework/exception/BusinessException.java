package com.sunnet.framework.exception;

public class BusinessException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  private String code;
  private String message;
  
  public BusinessException() {}
  
  public BusinessException(String code, String message)
  {
    this.code = code;
    this.message = message;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
}
