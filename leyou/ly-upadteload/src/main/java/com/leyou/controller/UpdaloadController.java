package com.leyou.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("upload")
public class UpdaloadController {

    public static  final List<String> FILENAEM_TYPE = Arrays.asList("jpg","png","jpeg");

//      @Value("${user.httmImageYuming}")
//     private String httmImage;

    @RequestMapping("image")
    public String upload( @RequestParam("file")  MultipartFile file){

        /**
         * 1.验证图片的格式
         * 2、验证图片带的内容
         * 3、验证图片的大小
         *
         * */
        try {
            //获取字符串
            String fileName = file.getOriginalFilename();
            //截取
            String filenameType = StringUtils.substringAfterLast(fileName, ".");
            //判断图片的格式是否包含
            if(!FILENAEM_TYPE.contains(filenameType)){
                return  null;
            }
            //判断图片带的内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage==null){
                return null;
            }

             String filePath=System.currentTimeMillis()+fileName;
            file.transferTo(new File("D:/photo/"+filePath));

            //加载客户端配置文件，配置文件中指明了tracker服务器的地址
//            ClientGlobal.init("fastdfs.conf");
//            //验证配置文件是否加载成功
//            System.out.println(ClientGlobal.configInfo());
//
//            //创建TrackerClient对象，客户端对象
//            TrackerClient trackerClient = new TrackerClient();
//
//            //获取到调度对象，也就是与Tracker服务器取得联系
//            TrackerServer trackerServer = trackerClient.getConnection();
//
//            //创建存储客户端对象
//            StorageClient storageClient = new StorageClient(trackerServer,null);
//
//            String[] upload_file = storageClient.upload_appender_file(file.getBytes(), filenameType, null);
//
//
//            for (String string : upload_file) {
//                System.out.println(string);
//            }
//
//            return  httmImage + upload_file[0]+"/"+upload_file[1];

            return  "http://image.leyou.com/" +filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }


           return null;
    }
}
