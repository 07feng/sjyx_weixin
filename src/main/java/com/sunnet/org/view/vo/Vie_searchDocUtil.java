package com.sunnet.org.view.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.view.model.Vie_searchdoc;

/**
 * vie_searchDoc 返回数据的加载
 * 
 * @author  
 *
 */
public class Vie_searchDocUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Vie_searchdoc> list) {
		List item = new ArrayList();
		for (Vie_searchdoc obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("rowid", obj.getRowid());
			map.put("id", obj.getId());
			map.put("membername", obj.getMembername());
		 
			if (obj.getType_name() != null) {
				map.put("type_name", obj.getType_name());
			} else {
				map.put("type_name", "");
			}
			map.put("filetypename", obj.getFiletypename());
			map.put("fid", obj.getFid());
			map.put("isboutique", obj.getIsboutique());
			map.put("doctitle", obj.getDoctitle());
			map.put("filedescribe", obj.getFiledescribe());
			map.put("filetype", obj.getFiletype());
			map.put("uploadtime", obj.getUploadtime());
			map.put("shootingtime", obj.getShootingtime());
			map.put("publictime", obj.getPublictime());
			map.put("createdate", obj.getCreatedate());
			map.put("filetypeid", obj.getFiletypeid().getId());
			if(obj.getMemberid()!=null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
			}else{
				map.put("memberid","");
				map.put("usersname", "");
				map.put("headimg", "");
			}
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
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Vie_searchdoc obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rowid", obj.getRowid());
		map.put("id", obj.getId());
		map.put("membername", obj.getMembername());
		 
		if (obj.getType_name() != null) {
			map.put("type_name", obj.getType_name());
		} else {
			map.put("type_name", "");
		}
		map.put("filetypename", obj.getFiletypename());
		map.put("fid", obj.getFid());
		map.put("isboutique", obj.getIsboutique());
		map.put("doctitle", obj.getDoctitle());
		map.put("filedescribe", obj.getFiledescribe());
		map.put("filetype", obj.getFiletype());
		map.put("uploadtime", obj.getUploadtime());
		map.put("shootingtime", obj.getShootingtime());
		map.put("publictime", obj.getPublictime());
		map.put("createdate", obj.getCreatedate());
		map.put("filetypeid", obj.getFiletypeid().getId());
		if(obj.getMemberid()!=null){
			map.put("memberid", obj.getMemberid().getId());
			map.put("usersname", obj.getMemberid().getUsersname());
			map.put("headimg", obj.getMemberid().getHeadimg());
		}else{
			map.put("memberid","");
			map.put("usersname", "");
			map.put("headimg", "");
		}
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
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Vie_searchdoc> list) {
		List item = new ArrayList();
		for (Vie_searchdoc obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("rowid", obj.getRowid());
			map.put("id", obj.getId());
			map.put("membername", obj.getMembername());
		 
			if (obj.getType_name() != null) {
				map.put("type_name", obj.getType_name());
			} else {
				map.put("type_name", "");
			}
			map.put("filetypename", obj.getFiletypename());
			map.put("fid", obj.getFid());
			map.put("isboutique", obj.getIsboutique());
			map.put("doctitle", obj.getDoctitle());
			map.put("filedescribe", obj.getFiledescribe());
			map.put("filetype", obj.getFiletype());
			map.put("uploadtime", obj.getUploadtime());
			map.put("shootingtime", obj.getShootingtime());
			map.put("publictime", obj.getPublictime());
			map.put("createdate", obj.getCreatedate());
			map.put("filetypeid", obj.getFiletypeid().getId());
			 
			if(obj.getMemberid()!=null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
			}else{
				map.put("memberid","");
				map.put("usersname", "");
				map.put("headimg", "");
			}
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
