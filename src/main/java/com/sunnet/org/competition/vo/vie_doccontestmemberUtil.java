package com.sunnet.org.competition.vo;

import java.util.*;

import com.sunnet.org.competition.model.vie_doccontestmember;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateFormatUtil;

/**
 * vie_doccontestmember 返回数据的加载
 * @author 强强
 *
 */
public class vie_doccontestmemberUtil {
	
	/**
	 * 返回分页list
	 * @param stuList
	 * @return
	 */
	public static List getControllerList(List stuList){
        if (stuList != null && stuList.size() > 0) {
            List list = new LinkedList();
            Map<String, Object> st;
            for (int i = 0; i < stuList.size(); i++) {
                st = new HashMap<String, Object>();
                Object[] object = (Object[]) stuList.get(i);// 每行记录不在是一个对象 而是一个数组
//				System.out.println("obj="+object[0].toString()+","+object.length);
                String id = object[0].toString();
                String doctitle =  object[1].toString();
                String filegoodcount =  object[2].toString();
                String filecommentscount = object[3].toString();
                String filepath=object[4].toString();
                String height =  object[5].toString();
                String iwidht =  object[6].toString();
                String iheight =  object[7].toString();
                String filetype = object[8].toString();
                Object phonethumbnailpathimg = object[9];
                String view_num = object[10].toString();
                String isDouble = object[11].toString();
				System.out.println("obj="+id+","+doctitle+","+filegoodcount+","+filecommentscount+","+filepath+","+height);
                // 重新封装在一个javabean里面
                st.put("imgId", id);
                st.put("title", doctitle);
                st.put("good_num", filegoodcount);
                st.put("comment", filecommentscount);
                if(filetype.equals("0") || null == phonethumbnailpathimg){
                    st.put("pic", filepath+ Constants.DOC_PATH_END);
                }else{
                    st.put("pic", phonethumbnailpathimg);
                }
                st.put("height", height);
                st.put("iwidht",iwidht);
                st.put("iheight",iheight);
                st.put("view_num",view_num);
                st.put("isDouble",isDouble);
                list.add(st); // 最终封装在list中 传到前台。
            }
            return list;
        }
        return null;
	}

    /**
     * 两个list合并
     * @param stuList1
     * @param stuList2
     * @return
     */
    public static List getControllerDoubleList(List stuList1,List stuList2){
        if (stuList1 != null && stuList1.size() > 0) {
            List list = new LinkedList();
            Map<String, Object> st;
            for (int i = 0; i < stuList1.size(); i++) {
                st = new HashMap<String, Object>();
                Object[] object = (Object[]) stuList1.get(i);// 每行记录不在是一个对象 而是一个数组
//				System.out.println("obj="+object[0].toString()+","+object.length);
                String id = object[0].toString();
                String doctitle =  object[1].toString();
                String filegoodcount =  object[2].toString();
                String filecommentscount = object[3].toString();
                String filepath=object[4].toString();
                String height =  object[5].toString();
                String iwidht =  object[6].toString();
                String iheight =  object[7].toString();
                String filetype = object[8].toString();
                Object phonethumbnailpathimg = object[9];
                String view_num = object[10].toString();
                String isDouble = object[11].toString();
                System.out.println("obj="+id+","+doctitle+","+filegoodcount+","+filecommentscount+","+filepath+","+height);
                // 重新封装在一个javabean里面
                st.put("imgId", id);
                st.put("title", doctitle);
                st.put("good_num", filegoodcount);
                st.put("comment", filecommentscount);
                if(filetype.equals("0") || null == phonethumbnailpathimg){
                    st.put("pic", filepath+ Constants.DOC_PATH_END);
                }else{
                    st.put("pic", phonethumbnailpathimg.toString());
                }
                st.put("height", height);
                st.put("iwidht",iwidht);
                st.put("iheight",iheight);
                st.put("view_num",view_num);
                st.put("isDouble",isDouble);
                list.add(st); // 最终封装在list中 传到前台。
            }
            for (int i = 0; i < stuList2.size(); i++) {
                st = new HashMap<String, Object>();
                Object[] object = (Object[]) stuList2.get(i);// 每行记录不在是一个对象 而是一个数组
//				System.out.println("obj="+object[0].toString()+","+object.length);
                String id = object[0].toString();
                String doctitle =  object[1].toString();
                String filegoodcount =  object[2].toString();
                String filecommentscount = object[3].toString();
                String filepath=object[4].toString();
                String height =  object[5].toString();
                String iwidht =  object[6].toString();
                String iheight =  object[7].toString();
                String filetype = object[8].toString();
                Object phonethumbnailpathimg = object[9];
                String view_num = object[10].toString();
                String isDouble = object[11].toString();
//                System.out.println("obj="+id+","+doctitle+","+filegoodcount+","+filecommentscount+","+filepath+","+height);
                // 重新封装在一个javabean里面
                st.put("imgId", id);
                st.put("title", doctitle);
                st.put("good_num", filegoodcount);
                st.put("comment", filecommentscount);
                if(filetype.equals("0")|| null == phonethumbnailpathimg){
                    st.put("pic", filepath+ Constants.DOC_PATH_END);
                }else{
                    st.put("pic", phonethumbnailpathimg);
                }
                st.put("height", height);
                st.put("iwidht",iwidht);
                st.put("iheight",iheight);
                st.put("view_num",view_num);
                st.put("isDouble",isDouble);
                list.add(st); // 最终封装在list中 传到前台。
            }
            return list;
        }
        return null;
    }
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(vie_doccontestmember obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 if (obj.getContest_id() >0) {
 map.put("contest_fdId", obj.getContest_id());
 } else {
 map.put("contest_fdId", "");
 }
 map.put("contest_id", obj.getContest_id());
 map.put("id", obj.getId());
 map.put("usersname", obj.getUsersname());
 map.put("uploadtime", obj.getUploadtime());
 map.put("memberid", obj.getMemberid());
 map.put("iwidht", obj.getIwidht());
 map.put("iheight", obj.getIheight());
 map.put("fileviewcount", obj.getFileviewcount());
 map.put("filegoodcount", obj.getFilegoodcount());
 map.put("filescore", obj.getFilescore());
 map.put("filepath", obj.getFilepath());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<vie_doccontestmember> list) {
		List item = new ArrayList();
		for (vie_doccontestmember obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 if (obj.getContest_id() >0) {
 map.put("contest_fdId", obj.getContest_id());
 } else {
 map.put("contest_fdId", "");
 }
 map.put("contest_id", obj.getContest_id());
 map.put("id", obj.getId());
 map.put("usersname", obj.getUsersname());
 map.put("uploadtime", obj.getUploadtime());
 map.put("memberid", obj.getMemberid());
 map.put("iwidht", obj.getIwidht());
 map.put("iheight", obj.getIheight());
 map.put("fileviewcount", obj.getFileviewcount());
 map.put("filegoodcount", obj.getFilegoodcount());
 map.put("filescore", obj.getFilescore());
 map.put("filepath", obj.getFilepath());
			item.add(map);
		}
		return item;
	}

}
