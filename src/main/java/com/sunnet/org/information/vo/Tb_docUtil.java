package com.sunnet.org.information.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.information.model.Tb_doc;

/**
 * tb_doc 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_docUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_doc> list) {
		List item = new ArrayList();
		for (Tb_doc obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			
			if(obj.getFid()!=null){
				map.put("fid", obj.getFid());
			}else{
				map.put("fid", "");
			}
			if(obj.getDoctitle()!=null){
				map.put("doctitle", obj.getDoctitle());
			}else{
				map.put("doctitle", "");
			}
			if(obj.getFiledescribe()!=null){
				map.put("filedescribe", obj.getFiledescribe());
			}else{
				map.put("filedescribe", "");
			}
			if(obj.getIsboutique()>0){
				map.put("isboutique", obj.getIsboutique());
			}else{
				map.put("isboutique", 0);
			}
			
			map.put("filetype", obj.getFiletype());
			map.put("uploadtime", obj.getUploadtime());
			map.put("shootingtime", obj.getShootingtime());
			map.put("publictime", obj.getPublictime());
			//map.put("filetypeid", obj.getFiletypeid());
			//map.put("memberid", obj.getMemberid());
			//map.put("photoalbumid", obj.getPhotoalbumid());
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
			map.put("storageformat", obj.getStorageformat());
		    map.put("actityforwarcount", obj.getActityforwarcount());

			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				if(obj.getMemberid().getHeadimg()!=null){
					map.put("headimg",  obj.getMemberid().getHeadimg());
				}else{
					map.put("headimg",  "");
				}
				
				if(obj.getMemberid().getLevelid() !=null){
					map.put("levelid", obj.getMemberid().getLevelid().getId());
					map.put("levelname", obj.getMemberid().getLevelid().getLevelname());
				}
			} else {
				map.put("memberid", "");
			}

			if (obj.getPhotoalbumid() != null) {
				map.put("photoalbumid", obj.getPhotoalbumid().getId());
				map.put("photoalbumname", obj.getPhotoalbumid().getPhotoalbumname());
			} else {
				map.put("photoalbumid", "");
			}

			if (obj.getFiletypeid() != null) {
				map.put("filetypeid", obj.getFiletypeid().getId());
				map.put("typeName", obj.getFiletypeid().getTypeName());
			} else {
				map.put("filetypeid", "");
				map.put("typeName", "");
			}

			item.add(map);
		}
		return item;
	}
	
	 
	public static List getControllerListAPP(List<Tb_doc> list) {
		List item = new ArrayList();
		for (Tb_doc obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
			map.put("id", obj.getId());
			if(obj.getIsboutique()>=0){
				map.put("isboutique", obj.getIsboutique());
			}else{
				map.put("isboutique", 0);
			}
			if(obj.getFid()!=null){
				map.put("fid", obj.getFid());
			}else{
				map.put("fid", "");
			}
			if(obj.getDoctitle()!=null){
				map.put("doctitle", obj.getDoctitle());
			}else{
				map.put("doctitle", "");
			}
			if(obj.getFiledescribe()!=null){
				map.put("filedescribe", obj.getFiledescribe());
			}else{
				map.put("filedescribe", "");
			}
			map.put("filetype", obj.getFiletype());
			map.put("uploadtime", obj.getUploadtime());
			map.put("shootingtime", obj.getShootingtime());
			map.put("publictime", obj.getPublictime());
			//map.put("filetypeid", obj.getFiletypeid());
			//map.put("memberid", obj.getMemberid());
			//map.put("photoalbumid", obj.getPhotoalbumid());
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
			map.put("storageformat", obj.getStorageformat());
			map.put("actityforwarcount", obj.getActityforwarcount());
			
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				if(obj.getMemberid().getHeadimg()!=null){
					map.put("headimg",  obj.getMemberid().getHeadimg());
				}else{
					map.put("headimg",  "");
				}
				
				if(obj.getMemberid().getLevelid() !=null){
					map.put("levelid", obj.getMemberid().getLevelid().getId());
					map.put("levelname", obj.getMemberid().getLevelid().getLevelname());
				}
			} else {
				map.put("memberid", "");
			}
			
			if (obj.getPhotoalbumid() != null) {
				map.put("photoalbumid", obj.getPhotoalbumid().getId());
				map.put("photoalbumname", obj.getPhotoalbumid().getPhotoalbumname());
			} else {
				map.put("photoalbumid", "");
			}
			
			if (obj.getFiletypeid() != null) {
				map.put("filetypeid", obj.getFiletypeid().getfId());
				map.put("typeName", obj.getFiletypeid().getTypeName());
			} else {
				map.put("filetypeid", "");
				map.put("typeName", "");
			}
			
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
	public static Map getControllerMap(Tb_doc obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		 
		if(obj.getFid()!=null){
			map.put("fid", obj.getFid());
		}else{
			map.put("fid", "");
		}
		if(obj.getIsboutique()>=0 ){
			map.put("isboutique", obj.getIsboutique());
		}else{
			map.put("isboutique", 0);
		}
		if(obj.getDoctitle()!=null){
			map.put("doctitle", obj.getDoctitle());
		}else{
			map.put("doctitle", "");
		}
		if(obj.getFiledescribe()!=null){
			map.put("filedescribe", obj.getFiledescribe());
		}else{
			map.put("filedescribe", "");
		}
		map.put("filetype", obj.getFiletype());
		map.put("uploadtime", obj.getUploadtime());
		map.put("shootingtime", obj.getShootingtime());
		map.put("publictime", obj.getPublictime());
		 
	/*	map.put("memberid", obj.getMemberid());
		map.put("photoalbumid", obj.getPhotoalbumid());*/
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
		map.put("storageformat", obj.getStorageformat());
		map.put("fileserverid", obj.getFileserverid());
		map.put("actityforwarcount", obj.getActityforwarcount());
		if (obj.getMemberid() != null) {
			map.put("memberid", obj.getMemberid().getId());
			map.put("usersname", obj.getMemberid().getUsersname());
			if(obj.getMemberid().getHeadimg()!=null){
				map.put("headimg",  obj.getMemberid().getHeadimg());
			}else{
				map.put("headimg",  "");
			}
			if(obj.getMemberid().getLevelid() !=null){
				map.put("levelid", obj.getMemberid().getLevelid().getId());
				map.put("levelname", obj.getMemberid().getLevelid().getLevelname());
			}
		} else {
			map.put("memberid", "");
			map.put("usersname", "");
		}

		if (obj.getPhotoalbumid() != null) {
			map.put("photoalbumid", obj.getPhotoalbumid().getId());
			map.put("photoalbumname", obj.getPhotoalbumid()
					.getPhotoalbumname());
		} else {
			map.put("photoalbumid", "");
			map.put("photoalbumname", "");
		}

		if (obj.getFiletypeid() != null) {
			map.put("filetypeid", obj.getFiletypeid().getfId());
			map.put("typeName", obj.getFiletypeid().getTypeName());
		} else {
			map.put("filetypeid", "");
			map.put("typeName", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_doc> list) {
		List item = new ArrayList();
		for (Tb_doc obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if(obj.getFid()!=null){
				map.put("fid", obj.getFid());
			}else{
				map.put("fid", "");
			}
			if(obj.getDoctitle()!=null){
				map.put("doctitle", obj.getDoctitle());
			}else{
				map.put("doctitle", "");
			}
			if(obj.getFiledescribe()!=null){
				map.put("filedescribe", obj.getFiledescribe());
			}else{
				map.put("filedescribe", "");
			}
			map.put("filetype", obj.getFiletype());
			map.put("uploadtime", obj.getUploadtime());
			map.put("shootingtime", obj.getShootingtime());
			map.put("publictime", obj.getPublictime());
			/* 
			map.put("memberid", obj.getMemberid());
			map.put("photoalbumid", obj.getPhotoalbumid());*/
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
			map.put("storageformat", obj.getStorageformat());
			map.put("fileserverid", obj.getFileserverid());
			map.put("actityforwarcount", obj.getActityforwarcount());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				if(obj.getMemberid().getHeadimg()!=null){
					map.put("headimg",  obj.getMemberid().getHeadimg());
				}else{
					map.put("headimg",  "");
				}
				if(obj.getMemberid().getLevelid() !=null){
					map.put("levelid", obj.getMemberid().getLevelid().getId());
					map.put("levelname", obj.getMemberid().getLevelid().getLevelname());
				}
			} else {
				map.put("memberid", "");
			}

			if (obj.getPhotoalbumid() != null) {
				map.put("photoalbumid", obj.getPhotoalbumid()
						.getPhotoalbumname());
			} else {
				map.put("photoalbumname", "");
			}

			if (obj.getFiletypeid() != null) {
				map.put("filetypeid", obj.getFiletypeid().getfId());
				map.put("typeName", obj.getFiletypeid().getTypeName());
			} else {
				map.put("filetypeid", "");
				map.put("typeName", "");
			}
			item.add(map);
		}
		return item;
	}

}
