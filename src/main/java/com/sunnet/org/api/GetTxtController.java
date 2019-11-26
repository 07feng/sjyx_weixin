package com.sunnet.org.api;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@Controller
public class GetTxtController extends BaseController {

    @RequestMapping(value = "/{txtName}.txt",method = RequestMethod.GET)
    @ResponseBody
    public String getTxt(@PathVariable String txtName, HttpServletResponse response, HttpServletRequest request) {
//        System.out.println("txtName="+txtName);
        String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
//        System.out.println("webappRoot="+webappRoot);
        File file=new File(webappRoot+"file/"+txtName+".txt");
        String strTxt="";
        if(file.exists()){
            BufferedReader br=null;
            StringBuffer sb = new StringBuffer();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
                String line = null;
                while((line = br.readLine())!=null){
                    sb.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    if(br!=null){
                        br.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            strTxt=sb.toString();
//            System.out.println("strTxt="+JSONObject.toJSONString(strTxt));
            return ajaxJson(strTxt, response);
        }else{
            strTxt="文件不存在";
            return ajaxJson(strTxt, response);
        }
    }

}
