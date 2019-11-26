package com.sunnet.framework.util;

public class JsonResult
{
  private String code;
  private String message;
  private Object result;
  private String msg;
  private Object data;

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

  public Object getResult()
  {
    return this.result;
  }

  public void setResult(Object result)
  {
    this.result = result;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
