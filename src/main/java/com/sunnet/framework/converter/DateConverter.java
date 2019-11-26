package com.sunnet.framework.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

public class DateConverter
  implements Converter<String, Date>
{
  public Date convert(String source)
  {
    try
    {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
