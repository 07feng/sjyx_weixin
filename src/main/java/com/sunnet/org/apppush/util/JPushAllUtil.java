package com.sunnet.org.apppush.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 
 * @author 强强
 *
 *         时间: 2017年1月3日
 */
@SuppressWarnings("all")
public class JPushAllUtil {
	private static Logger logger = Logger.getLogger(JPushAllUtil.class);

	/* 注意事项 ：a. 安卓：1 ios:0 b.用户端：1 司机端：0 */
	/*
	 * Android 极光推送
	 * 
	 * 司机端： appkey:497093789d0c51ea04d588b9
	 * 极光注册ID（实际测试会在接口中传给后台）：140fe1da9ea6817eec9 secret:0d859c7132f3adcf6d7f96fd
	 * 
	 * 用户端： appkey：c15a1524dfaa9d614d742963
	 * 极光注册ID（实际测试会在接口中传给后台）：100d8559094a33f826b secret:a6f82a018afaea9bec48f31f
	 */

	/* 苹果 */
	private final static String yh_appKey = "bd76f247c1076599c185784e";
	private final static String yh_masterSecret = "e637286d6954410340bebe40";

	/**
	 * 如果目标平台为 iOS 平台 需要在 options 中通过 apns_production 字段来制定推送环境。 True
	 * 表示推送生产环境，False 表示要推送开发环境； 如 果不指定则为推送生产环境
	 */
	private final static boolean flg = false;

	/**
	 * 声音
	 */
	private static String send;

	/**
	 * 测试方法
	 */
	public static void main(String[] args) {
		// 发送通知
		Map map = new HashMap();
		jSend_notification("101d8559097d5d7aec5",
				"去不去南昌火车司机端->false，哈哈哈哈4561", map, 0);
	}

	/**
	 * 推送全部
	 * 
	 * @param registrationId
	 * @param alert
	 * @param map
	 * @param ios_android
	 */
	public static void jSend_notificationAll(String registrationId,
			String alert, Map map, Integer ios_android) {
		if (registrationId != null) {
			jSend_notification(registrationId, alert, map, ios_android);
			jSend_notification(registrationId, alert, map, ios_android);
		}
	}

	/**
	 * 
	 * @param registrationId 极光推送id 标识号
	 * @param alert  内容
	 * @param map
	 *            集合
	 * @param type
	 * @param ios_android
	 *            推送类型 安卓：1 ios:0
	 */
	public static void jSend_notification(String registrationId, String alert,
			Map map, Integer ios_android) {
		if (registrationId != null) {
			JPushClient jpushClient = new JPushClient(yh_masterSecret, yh_appKey, 3);
			try {
				PushPayload payload = send_N(registrationId, alert, map,
						ios_android);
				logger.info("返回的推送信息:" + payload);
				PushResult result = jpushClient.sendPush(payload);
				logger.info(result);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("错误信息" + e);
			}
		}
	}

	/**
	 * 发送信息
	 * 
	 * @param registrationId
	 *            标识号
	 * @param alert
	 *            内容
	 * @param map
	 *            参数集合
	 * @param type
	 *            用户端/司机断
	 * @param ios_android
	 *            ios或者andorid
	 * @return
	 */
	public static PushPayload send_N(String registrationId, String alert,
			Map map, Integer ios_android) {
		if (ios_android == null) {
			return null;
		}

		// if (map != null) {
		// if (map.get("send") == null) {
		// send = "default";
		// } else {
		// send = map.get("send").toString();
		// }
		// map.remove("send");
		// }

		if (ios_android == null || ios_android == 1) {
			logger.info("开始进入安卓推送信息!");
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.android_ios())
					// 必填/推送平台设置
					.setAudience(Audience.registrationId(registrationId))
					.setNotification(Notification.android(alert, "全球行", map))
					.setOptions(
							Options.newBuilder().setApnsProduction(flg).build())
					.build();
		} else {
			logger.info("开始进入IOS推送信息!");
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.android_ios())
					// 必填/推送平台设置
					.setAudience(Audience.registrationId(registrationId))
					.setNotification(
							Notification
									.newBuilder()
									.addPlatformNotification(
											IosNotification.newBuilder()
													.setSound(send).setBadge(1)
													.setAlert(alert)
													.addExtras(map).build())
									.build())
					.setOptions(
							Options.newBuilder().setApnsProduction(flg).build())
					.build();
		}
	}

}