package com.sunnet.framework.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

public class IDGenerator
  implements IdentifierGenerator, Configurable
{
  private static final Logger log = Logger.getLogger(IDGenerator.class);
  
  public Serializable generate(SessionImplementor session, Object object)
    throws HibernateException
  {
    String fdId = new Date().getTime() + RandomStringGenerator.getRandomStringByLength(6);
    log.info(getClass().getName() + "--fdId:" + fdId);
    return fdId;
  }
  
  public void configure(Type type, Properties params, Dialect d)
    throws MappingException
  {}
}
