package com.sunnet.org.scenicSpot.vo;

import com.sunnet.org.app.entity.SortDocTime;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ScenicSopt相关住居处理
 * author jinhao
 * 时间 2018.6/22
 */
public class ScenicSoptUtil {

    /**
     * 返回关注用户的作品影集数据集合信息
     * @return
     */
    public static List<Object[]> getFilmList(List<Object[]> friendFilm,List<Object[]> friendFilmDoc,List<Object[]> data){
        for (int i = 0 ; i<friendFilm.size(); i++) {
            Object[] obj = new Object[23];
            Map<String, Object> map = new HashMap<>();
            obj[0] = friendFilm.get(i)[0];    //doc_id	vip_id
            obj[1] = friendFilm.get(i)[4];    //doc_title
            obj[2] = friendFilm.get(i)[12];    //describe
            obj[3] = 0;    //fileType
            obj[4] = "";    //doc_path one
            obj[5] = friendFilmDoc.get(i)[1];    //doc_path
            obj[6] = friendFilm.get(i)[6];    //view_num
            obj[7] = friendFilmDoc.get(i)[3];    //iheight
            obj[8] = friendFilmDoc.get(i)[2];    //iwidth
            obj[9] = friendFilm.get(i)[2];    //good_num
            obj[10] = friendFilm.get(i)[3];    //comment_num
            String upload_time = null;
            if (-1 != friendFilm.get(i)[11].toString().lastIndexOf("."))
                upload_time = friendFilm.get(i)[11].toString().substring(0, friendFilm.get(i)[11].toString().lastIndexOf("."));
            else
                upload_time = friendFilm.get(i)[11].toString();
            obj[11] = upload_time;    //upload_time
            obj[12] = friendFilm.get(i)[7];    //author_id
            obj[13] = friendFilm.get(i)[8];    //author_name
            obj[14] = friendFilm.get(i)[9];    //author_portrait
            obj[15] = friendFilm.get(i)[10];    //rank
            obj[16] = 2;    //isDouble
            obj[17] = 2;    //fid
            obj[18] = friendFilm.get(i)[13];    //ssid
            obj[19] = friendFilm.get(i)[14];    //AreaName
            obj[20] = friendFilm.get(i)[15];    //CityName
            obj[21] = friendFilm.get(i)[16];    //ScenicSpotName
//            System.out.println("00000000000000000000000000000000000000");
            obj[22] = friendFilm.get(i)[17];    //CoverPicUrl

            data.add(obj);
        }

        System.out.println("data="+data.size());
        List<SortDocTime> sdtList = new ArrayList<>();
        for (int i =0; i<data.size(); i++){
            SortDocTime sdt = new SortDocTime(i,data.get(i)[11].toString());
            sdtList.add(sdt);
        }
//		System.out.println("开始排序了");
        ListSort(sdtList);
//		System.out.println("结束排序了");
        List<Object[]> result = new ArrayList<>();
        for (int i =sdtList.size()-1; i>=0; i--){
            result.add(data.get(sdtList.get(i).getId()));
        }
        System.out.println("返回去了");
        return result;
    }


    private static void ListSort(List<SortDocTime> list) {
        Collections.sort(list, new Comparator<SortDocTime>() {
            @Override
            public int compare(SortDocTime o1, SortDocTime o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getTime());
                    Date dt2 = format.parse(o2.getTime());
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
