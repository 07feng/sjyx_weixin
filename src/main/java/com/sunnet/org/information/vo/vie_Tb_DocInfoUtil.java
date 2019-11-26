package com.sunnet.org.information.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.information.model.vie_Tb_DocInfo;

/**
 * vie_Tb_DocInfo 返回数据的加载
 * @author 强强
 *
 */
public class vie_Tb_DocInfoUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<vie_Tb_DocInfo> list){
		List item= new ArrayList();
		for (vie_Tb_DocInfo obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("memberiddz", obj.getMemberiddz());
 map.put("memberidsc", obj.getMemberidsc());
 map.put("fid", obj.getFid());
 map.put("isboutique", obj.getIsboutique());
 map.put("doctitle", obj.getDoctitle());
 map.put("filedescribe", obj.getFiledescribe());
 map.put("filetype", obj.getFiletype());
 map.put("uploadtime", obj.getUploadtime());
 map.put("shootingtime", obj.getShootingtime());
 map.put("publictime", obj.getPublictime());
 map.put("createdate", obj.getCreatedate());
 map.put("filetypeid", obj.getFiletypeid());
 map.put("memberid", obj.getMemberid());
 map.put("photoalbumid", obj.getPhotoalbumid());
 map.put("devicenumber", obj.getDevicenumber());
 map.put("iwidht", obj.getIwidht());
 map.put("iheight", obj.getIheight());
 map.put("exposuretime", obj.getExposuretime());
 map.put("exposureprogram", obj.getExposureprogram());
 map.put("exposurecompensation", obj.getExposurecompensation());
 map.put("exposuremodel", obj.getExposuremodel());
 map.put("isorate", obj.getIsorate());
 map.put("shutterspeed", obj.getShutterspeed());
 map.put("aperture", obj.getAperture());
 map.put("lensaperture", obj.getLensaperture());
 map.put("largestaperture", obj.getLargestaperture());
 map.put("flash", obj.getFlash());
 map.put("focallength", obj.getFocallength());
 map.put("whitebalance", obj.getWhitebalance());
 map.put("filelength", obj.getFilelength());
 map.put("gpslat", obj.getGpslat());
 map.put("gpslon", obj.getGpslon());
 map.put("pcthumbnailpathimg", obj.getPcthumbnailpathimg());
 map.put("phonethumbnailpathimg", obj.getPhonethumbnailpathimg());
 map.put("docstatus", obj.getDocstatus());
 map.put("padthumbnailpathimg", obj.getPadthumbnailpathimg());
 map.put("filepath", obj.getFilepath());
 map.put("filescore", obj.getFilescore());
 map.put("nsfw", obj.getNsfw());
 map.put("ispublic", obj.getIspublic());
 map.put("isdelete", obj.getIsdelete());
 map.put("isparticipating", obj.getIsparticipating());
 map.put("isportrait", obj.getIsportrait());
 map.put("isdouble", obj.getIsdouble());
 map.put("fileviewcount", obj.getFileviewcount());
 map.put("filegoodcount", obj.getFilegoodcount());
 map.put("filekeepcount", obj.getFilekeepcount());
 map.put("filecommentscount", obj.getFilecommentscount());
 map.put("filepaycount", obj.getFilepaycount());
 map.put("fileserverid", obj.getFileserverid());
 map.put("storageformat", obj.getStorageformat());
 map.put("actityforwarcount", obj.getActityforwarcount());
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(vie_Tb_DocInfo obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("memberiddz", obj.getMemberiddz());
 map.put("memberidsc", obj.getMemberidsc());
 map.put("fid", obj.getFid());
 map.put("isboutique", obj.getIsboutique());
 map.put("doctitle", obj.getDoctitle());
 map.put("filedescribe", obj.getFiledescribe());
 map.put("filetype", obj.getFiletype());
 map.put("uploadtime", obj.getUploadtime());
 map.put("shootingtime", obj.getShootingtime());
 map.put("publictime", obj.getPublictime());
 map.put("createdate", obj.getCreatedate());
 map.put("filetypeid", obj.getFiletypeid());
 map.put("memberid", obj.getMemberid());
 map.put("photoalbumid", obj.getPhotoalbumid());
 map.put("devicenumber", obj.getDevicenumber());
 map.put("iwidht", obj.getIwidht());
 map.put("iheight", obj.getIheight());
 map.put("exposuretime", obj.getExposuretime());
 map.put("exposureprogram", obj.getExposureprogram());
 map.put("exposurecompensation", obj.getExposurecompensation());
 map.put("exposuremodel", obj.getExposuremodel());
 map.put("isorate", obj.getIsorate());
 map.put("shutterspeed", obj.getShutterspeed());
 map.put("aperture", obj.getAperture());
 map.put("lensaperture", obj.getLensaperture());
 map.put("largestaperture", obj.getLargestaperture());
 map.put("flash", obj.getFlash());
 map.put("focallength", obj.getFocallength());
 map.put("whitebalance", obj.getWhitebalance());
 map.put("filelength", obj.getFilelength());
 map.put("gpslat", obj.getGpslat());
 map.put("gpslon", obj.getGpslon());
 map.put("pcthumbnailpathimg", obj.getPcthumbnailpathimg());
 map.put("phonethumbnailpathimg", obj.getPhonethumbnailpathimg());
 map.put("docstatus", obj.getDocstatus());
 map.put("padthumbnailpathimg", obj.getPadthumbnailpathimg());
 map.put("filepath", obj.getFilepath());
 map.put("filescore", obj.getFilescore());
 map.put("nsfw", obj.getNsfw());
 map.put("ispublic", obj.getIspublic());
 map.put("isdelete", obj.getIsdelete());
 map.put("isparticipating", obj.getIsparticipating());
 map.put("isportrait", obj.getIsportrait());
 map.put("isdouble", obj.getIsdouble());
 map.put("fileviewcount", obj.getFileviewcount());
 map.put("filegoodcount", obj.getFilegoodcount());
 map.put("filekeepcount", obj.getFilekeepcount());
 map.put("filecommentscount", obj.getFilecommentscount());
 map.put("filepaycount", obj.getFilepaycount());
 map.put("fileserverid", obj.getFileserverid());
 map.put("storageformat", obj.getStorageformat());
 map.put("actityforwarcount", obj.getActityforwarcount());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<vie_Tb_DocInfo> list) {
		List item = new ArrayList();
		for (vie_Tb_DocInfo obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("memberiddz", obj.getMemberiddz());
 map.put("memberidsc", obj.getMemberidsc());
 map.put("fid", obj.getFid());
 map.put("isboutique", obj.getIsboutique());
 map.put("doctitle", obj.getDoctitle());
 map.put("filedescribe", obj.getFiledescribe());
 map.put("filetype", obj.getFiletype());
 map.put("uploadtime", obj.getUploadtime());
 map.put("shootingtime", obj.getShootingtime());
 map.put("publictime", obj.getPublictime());
 map.put("createdate", obj.getCreatedate());
 map.put("filetypeid", obj.getFiletypeid());
 map.put("memberid", obj.getMemberid());
 map.put("photoalbumid", obj.getPhotoalbumid());
 map.put("devicenumber", obj.getDevicenumber());
 map.put("iwidht", obj.getIwidht());
 map.put("iheight", obj.getIheight());
 map.put("exposuretime", obj.getExposuretime());
 map.put("exposureprogram", obj.getExposureprogram());
 map.put("exposurecompensation", obj.getExposurecompensation());
 map.put("exposuremodel", obj.getExposuremodel());
 map.put("isorate", obj.getIsorate());
 map.put("shutterspeed", obj.getShutterspeed());
 map.put("aperture", obj.getAperture());
 map.put("lensaperture", obj.getLensaperture());
 map.put("largestaperture", obj.getLargestaperture());
 map.put("flash", obj.getFlash());
 map.put("focallength", obj.getFocallength());
 map.put("whitebalance", obj.getWhitebalance());
 map.put("filelength", obj.getFilelength());
 map.put("gpslat", obj.getGpslat());
 map.put("gpslon", obj.getGpslon());
 map.put("pcthumbnailpathimg", obj.getPcthumbnailpathimg());
 map.put("phonethumbnailpathimg", obj.getPhonethumbnailpathimg());
 map.put("docstatus", obj.getDocstatus());
 map.put("padthumbnailpathimg", obj.getPadthumbnailpathimg());
 map.put("filepath", obj.getFilepath());
 map.put("filescore", obj.getFilescore());
 map.put("nsfw", obj.getNsfw());
 map.put("ispublic", obj.getIspublic());
 map.put("isdelete", obj.getIsdelete());
 map.put("isparticipating", obj.getIsparticipating());
 map.put("isportrait", obj.getIsportrait());
 map.put("isdouble", obj.getIsdouble());
 map.put("fileviewcount", obj.getFileviewcount());
 map.put("filegoodcount", obj.getFilegoodcount());
 map.put("filekeepcount", obj.getFilekeepcount());
 map.put("filecommentscount", obj.getFilecommentscount());
 map.put("filepaycount", obj.getFilepaycount());
 map.put("fileserverid", obj.getFileserverid());
 map.put("storageformat", obj.getStorageformat());
 map.put("actityforwarcount", obj.getActityforwarcount());
			item.add(map);
		}
		return item;
	}

}
