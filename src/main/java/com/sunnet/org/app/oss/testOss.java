package com.sunnet.org.app.oss;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import com.sunnet.org.app.oss.util.ResultValue;
import com.sunnet.org.app.oss.util.getHttppostResult;

public class testOss {
	static Logger logger = Logger.getLogger(HelloOSS.class);

	private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
	private static String accessKeyId = "LTAI67K1f1TOC7KQ";
	private static String accessKeySecret = "mNJhSMDUTtOvSbpii7SOmHoexI4WvP";
	private static String bucketName = "sjyximage";
	private static String url = "http://sjyximage.oss-cn-shenzhen.aliyuncs.com/temp/1DEB4D83-B347-4B3A-A767-CBC14AE8FF82.jpg";
	private static String a = "x-oss-process";
	
	public static void main(String[] args) {
		   OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		   BucketInfo info = ossClient.getBucketInfo(bucketName);
		   getHttppostResult hr=new getHttppostResult();
		   JSONObject jso=httpClientHelp.httpGet(url+"?x-oss-process=image/info");
		   //我是
		   // getHttppostResult tr=(getHttppostResult)JSONObject.toBean(jso,getHttppostResult.class);
		   Object apertureValue=jso.get("ApertureValue");//镜头光圈
		   Object whitebalance=jso.get("WhiteBalance");// 白平衡
		   Object shootingtime=jso.get("DateTimeOriginal");// 拍摄时间 原始时间
		   Object focallength=jso.get("FocalLength");//焦距
		   Object flash=jso.get("Flash");//闪光灯
		   Object largestaperture=jso.get("MaxApertureValue");//最大光圈值
		   Object isorate=jso.get("ISOSpeedRatings");//ISO速率
		   Object gpslat=jso.get("GPSLatitude");// 纬度
		   Object gpslon=jso.get("GPSLongitude");//经度
		   Object storageformat=jso.get("Format");//存储格式
		   Object exposuretime=jso.get("ExposureTime");//曝光时间
		   Object devicenumber=jso.get("Model");//  设备型号
		   Object shutterspeed=jso.get("ShutterSpeedValue"); //快门速度
		   Object exposureprogram=jso.get("ExposureProgram");//曝光程序
		   Object aperture=jso.get("FNumber");//FNumber	光圈值
		   Object exposuremodel=jso.get("ExposureMode");//曝光模式
		   Object fileSize=jso.get("FileSize");//文件大小
		   net.sf.json.JSONObject jsonapertureValue= net.sf.json.JSONObject.fromObject(apertureValue);
		   net.sf.json.JSONObject jsonwhitebalance= net.sf.json.JSONObject.fromObject(whitebalance);
		   net.sf.json.JSONObject jsonshootingtime= net.sf.json.JSONObject.fromObject(shootingtime);
		   net.sf.json.JSONObject jsonfocallength= net.sf.json.JSONObject.fromObject(focallength);
		   net.sf.json.JSONObject jsonflash= net.sf.json.JSONObject.fromObject(flash);
		   net.sf.json.JSONObject jsonlargestaperture= net.sf.json.JSONObject.fromObject(largestaperture);
		   net.sf.json.JSONObject jsonisorate= net.sf.json.JSONObject.fromObject(isorate);
		   net.sf.json.JSONObject jsongpslat= net.sf.json.JSONObject.fromObject(gpslat);
		   net.sf.json.JSONObject jsongpslon= net.sf.json.JSONObject.fromObject(gpslon);
		   net.sf.json.JSONObject jsonstorageformat= net.sf.json.JSONObject.fromObject(storageformat);
		   net.sf.json.JSONObject jsonexposuretime= net.sf.json.JSONObject.fromObject(exposuretime);
		   net.sf.json.JSONObject jsondevicenumber= net.sf.json.JSONObject.fromObject(devicenumber);
		   net.sf.json.JSONObject jsonshutterspeed= net.sf.json.JSONObject.fromObject(shutterspeed);
		   net.sf.json.JSONObject jsonexposureprogram= net.sf.json.JSONObject.fromObject(exposureprogram);
		   net.sf.json.JSONObject jsonaperture= net.sf.json.JSONObject.fromObject(aperture);
		   net.sf.json.JSONObject jsonfileSize= net.sf.json.JSONObject.fromObject(fileSize);
		   net.sf.json.JSONObject jsonexposuremodel= net.sf.json.JSONObject.fromObject(exposuremodel);
		   
		   ResultValue drtapertureValue=(ResultValue) net.sf.json.JSONObject.toBean(jsonapertureValue, ResultValue.class);
		   ResultValue drtwhitebalance=(ResultValue) net.sf.json.JSONObject.toBean(jsonwhitebalance, ResultValue.class);
		   ResultValue drtshootingtime=(ResultValue) net.sf.json.JSONObject.toBean(jsonshootingtime, ResultValue.class);
		   ResultValue drtfocallength=(ResultValue) net.sf.json.JSONObject.toBean(jsonfocallength, ResultValue.class);
		   ResultValue drtflash=(ResultValue) net.sf.json.JSONObject.toBean(jsonflash, ResultValue.class);
		   ResultValue drtlargestaperture=(ResultValue) net.sf.json.JSONObject.toBean(jsonlargestaperture, ResultValue.class);
		   ResultValue drtisorate=(ResultValue) net.sf.json.JSONObject.toBean(jsonisorate, ResultValue.class);
		   ResultValue drtgpslat=(ResultValue) net.sf.json.JSONObject.toBean(jsongpslat, ResultValue.class);
		   ResultValue drtgpslon=(ResultValue) net.sf.json.JSONObject.toBean(jsongpslon, ResultValue.class);
		   ResultValue drtstorageformat=(ResultValue) net.sf.json.JSONObject.toBean(jsonstorageformat, ResultValue.class);
		   ResultValue drtexposuretime=(ResultValue) net.sf.json.JSONObject.toBean(jsonexposuretime, ResultValue.class);
		   ResultValue drtdevicenumber=(ResultValue) net.sf.json.JSONObject.toBean(jsondevicenumber, ResultValue.class);
		   ResultValue drtshutterspeed=(ResultValue) net.sf.json.JSONObject.toBean(jsonshutterspeed, ResultValue.class);
		   ResultValue drtexposureprogram=(ResultValue) net.sf.json.JSONObject.toBean(jsonexposureprogram, ResultValue.class);
		   ResultValue drtaperture=(ResultValue) net.sf.json.JSONObject.toBean(jsonaperture, ResultValue.class);
		   ResultValue drtfileSize=(ResultValue) net.sf.json.JSONObject.toBean(jsonfileSize, ResultValue.class);
		   ResultValue drtexposuremodel=(ResultValue) net.sf.json.JSONObject.toBean(jsonexposuremodel, ResultValue.class);
		   
           System.out.println(url+"?x-oss-process=image/info");
           //你只诶是要干嘛？  获取这个链接的一部分参数，然后干嘛？存到数据库，哦那为什么你不写，直接放入，放不了，no no no
           //让我来，别动
           
           
           //如果你要测试，那么你可以防Test  我先去下，等叫我  
           
	} 

}
