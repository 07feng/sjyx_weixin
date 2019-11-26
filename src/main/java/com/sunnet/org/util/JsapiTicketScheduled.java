package com.sunnet.org.util;

import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;  
  
import org.apache.log4j.Logger;  
  
  
/** 
 * jsapi_ticket定时获取类 
 * 由于jsapi_ticket的有效期是7200秒（两小时），在有效期内，可以一直使用，只有当jsapi_ticket过期时，才需要再次调用接口获取jsapi_ticket。 
 * 请勿频繁请求获取jsapi_ticket 
 * 设计思想参考：taobao.code.SystemTimer <br/><br/>  
 * @see JsapiTicketScheduled 
 * @since 
 */  
public class JsapiTicketScheduled  
{  
  
    /** 
     * 日志 
     */  
    private static final Logger log = Logger.getLogger(JsapiTicketScheduled.class);  
  
    /** 
     * jsapi_ticket刷新时间，现阶段有效期为7200秒，刷新时间 = 过期时间 - 200s 
     */  
    private static final long JSAPI_TICKET_SCHEDULED_TICK = 7000L;  
  
    /**
     * jsapi_ticket 初始化采用JsapiTicketUtil获取
     */
    private static volatile String jsapi_ticket = WeixinSign.getJsapi_ticket();
  
    /** 
     * executor 
     */  
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();  
  
    /** 
     * thread 
     */  
    private static class TimerTicker implements Runnable  
    {  
        @Override  
        public void run()  
        {  
            //jsapi_ticket  
            jsapi_ticket = WeixinSign.getJsapi_ticket();  
            log.debug("jsapi_ticket :" + jsapi_ticket) ;  
        }  
    }  
  
    static  
    {  
        //command - 要执行的任务  initialDelay - 首次执行的延迟时间    
        //period - 连续执行之间的周期  unit - initialDelay 和 period 参数的时间单位   
        executor.scheduleAtFixedRate(new TimerTicker(), JSAPI_TICKET_SCHEDULED_TICK,  
            JSAPI_TICKET_SCHEDULED_TICK, TimeUnit.SECONDS);  
  
        //shutdown hook  
        Runtime.getRuntime().addShutdownHook(new Thread()  
        {  
            @Override  
            public void run()  
            {  
                log.info("JsapiTicketScheduled shutdown hook run...");  
                shutdownScheduledExecutor();  
            }  
        });  
    }  
  
    /** 
     * 返回全局的jsapi_ticket 
     * @return  
     * @see 
     */  
    public static String jsapiTicketScheduled()  
    {  
        log.info("jsapiTicketScheduled() return jsapi_ticket : " + jsapi_ticket);  
        return jsapi_ticket;  
    }  
  
    /** 
     * 关闭shutdownScheduledExecutor 
     *   
     * @see 
     */  
    public static void shutdownScheduledExecutor()  
    {  
        log.info("try to shutdown ScheduledExecutor... ");  
        executor.shutdown();  
    }  
  
}  
