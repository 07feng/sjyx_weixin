package com.sunnet.framework.util;

import com.sunnet.org.app.entity.AddFilm;
import net.sf.json.JSONArray;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VideoUtil {
    public static void main(String[] args){
////        String imgList="[{id: \"15264637199039dc81\", image: \"\", desc: \"早餐\"}]";
//        String imgList ="[{id: \"1505728258033BEEMO6\", image: \"\", desc: \"描述\"},{id: \"1505728260986W4IVLP\", image: \"\", desc: \"描述\"}," +
//                "{id: \"1505728266892PZPUGG\", image: \"\", desc: \"描述\"},{id: \"15058838443087QQM82\", image: \"\", desc: \"描述\"}," +
//                "{id: \"15057282686731BGPTE\", image: \"\", desc: \"描述\"},{id: \"15058838447779EJLW2\", image: \"\", desc: \"描述\"}]";
//        List<AddFilm> list= (List<AddFilm>)JSONArray.toList(JSONArray.fromObject(imgList), AddFilm.class);		//解析json数据
//        System.out.println(list.get(0).getId());
////        IFilmfestivalService ffs = new FilmfestivalServiceImpl();
        try {
            Map test = randomGrabberFFmpegImage("F:/aaa.mp4","F:/","jinhao2");
            System.out.println(test.get("filePath"));
            File file = new File((String)test.get("filePath"));
            if (file.exists()) {
                System.out.println("图片存在！");
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
    public static Map randomGrabberFFmpegImage(String filePath, String targerFilePath, String targetFileName)
        throws Exception {
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();
        String rotate =ff.getVideoMetadata("rotate");
        Frame f;
        int i = 0;
        Map map = null;
        while (i <1) {
            f =ff.grabImage();
            IplImage src = null;
            if(null !=rotate &&rotate.length() > 1) {
                OpenCVFrameConverter.ToIplImage converter =new OpenCVFrameConverter.ToIplImage();
                src =converter.convert(f);
                f =converter.convert(rotate(src, Integer.valueOf(rotate)));
            }
            map = doExecuteFrame(f,targerFilePath,targetFileName);
            i++;
        }
        ff.stop();
        return map;
    }
    /*
     * 旋转角度的
     */
    public static IplImage rotate(IplImage src, int angle) {
        IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }
    public static Map doExecuteFrame(Frame f, String targerFilePath, String targetFileName) {
        if (null ==f ||null ==f.image) {
            return null;
        }
        Java2DFrameConverter converter =new Java2DFrameConverter();
        String imageMat ="jpg";
        String FileName =targerFilePath + File.separator +targetFileName +"." +imageMat;
        BufferedImage bi =converter.getBufferedImage(f);
        File output =new File(FileName);
        Map map = null;
        try {
            ImageIO.write(bi,imageMat,output);
            map = new HashMap();
            map.put("filePath",FileName);
            map.put("width",bi.getWidth());
            map.put("height",bi.getHeight());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


}
