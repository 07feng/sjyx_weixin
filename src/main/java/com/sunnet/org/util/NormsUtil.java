package com.sunnet.org.util;

/**
 * 规格设置 【静态】
 * @author Administrator
 *
 */
public class NormsUtil
{

	/**
	 * 可查看司机的最大范围 [千米]
	 */
	public static Integer scope = 3; 
	
	
	/**
	 * 千米/元 
	 */
	public static Double money=5.0;
	
	
	
	/**
	 * 间轮 时间[更新司机位置/分钟]
	 */
	public static Integer stor=3;
	
	
	/**
	 * 司机可抢多少范围内的订单【千米】
	 */
	
	public static Integer lu=3;
	
	/**
	 * 司机可取消的次数
	 */
	public static Integer quxiao=3;
	
	
	/*默认出租车计价规则*/
	public static Double startingfare = 4000.0;// 起步价(R)
	public static Double journey = 120.0;// 路程(m)
	public static Double min = 160.0;// 米数(m)
	public static Double moneybymin = 400.0;// 每160米跳多少钱(r)
	public static Double moneybytime = 200.0;// 每等待1分钟跳多少钱(R)
	public static Integer index_chu = 0;// 出租车下标
	
	/*默认摩的计价规则*/
	public static Double startingfarebymo = 2000.0;// 起步价(R)
	public static Double journeybymo = 1000.0;// 路程(m)
	public static Double minbymo = 1000.0;// 米数(m)
	public static Double moneybyminbymo = 1000.0;// 每1000米跳多少钱(r)
	public static Integer index_mo = 2;// 出租车下标
}
