package com.sunnet.org.util;

public class Constants {
    /**
     * api 返回成功code
     */
    public static final String SUCCESS_CODE = "0";

    /**
     * api 返回成功的data
     */
    public static final String SUCCESS_DATA = "success";

    /**
     * api 业务处理异常code
     */
    public static final String SERVICE_EXCEPTION_CODE = "1";

    /**
     * api 业务异常Data
     */
    public static final String SERVICE_EXCEPTION_DATA = "系统异常";

    /**
     * 系统编码
     */
    public static final String CHARSET = "UTF-8";
    public static final Integer DEFAULT_PAGESIZE=20;
    //用户session的key值
    public static final String SESSIONKEY="SHOUJIYINXIANG";

    /**redis中session失效的时间*/
    public static final int USER_SESSION_TIME = 60*60*24*7;

    //对应数据不存在
    public static final String DATA_NOTEXIST = "-1";

    //影展图片的类别
    public static final int FESTIVAL_DOC_TYPE =50;


    //系统相册编号-全部
    public static final int PHOTOALBUM_ALL = -1;
    //系统相册编号-参赛
    public static final int PHOTOALBUM_CON = -2;
    //系统相册编号-视频
    public static final int PHOTOALBUM_VIDEO = -3;
    //系统相册编号-公开
    public static final int PHOTOALBUM_PUBLIC = -4;
    //系统相册编号-不公开
    public static final int PHOTOALBUM_NOPUBLIC = -5;
    //系统相册编号-回收站
    public static final int PHOTOALBUM_DELETE = -6;

    public static final int MAXVOTENUM = 3;

    public static final int VOTEID = 52;

    //图片设定
    public static final String DOC_PATH_END = "?x-oss-process=image/resize,h_350";
    //图片设定1
    public static final String DOC_PATH_END1 = "?x-oss-process=image/resize,h_100";
    //图片设定2
    public static final String DOC_PATH_END3 = "?x-oss-process=image/resize,h_450";
    //图片设定2
    public static final String DOC_PATH_END2 = "?x-oss-process=image/resize,h_500";
    //图片设定1
    public static final String DOC_PATH_END4 = "?x-oss-process=image/resize,h_150";

    //默认背景图片
    public static final String BACKGROUNDIMG = "http://image.91sjyx.com/sjyx/img/background.jpg";
}
