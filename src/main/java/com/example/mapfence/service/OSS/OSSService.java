package com.example.mapfence.service.OSS;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.example.mapfence.config.OSSConfiguration;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class OSSService {

    public static Logger log = LoggerFactory.getLogger(OSSService.class);

    @Value("${expDays}")
    private String expDays;

    @Autowired
    private OSSConfiguration ossConfiguration;

    @Autowired
    private OSS ossClient;

    /**
     * 上传文件到阿里云OSS
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) {
        String fileName = "";
        try {
            // 创建一个唯一的文件名UUID，就是保存在OSS服务器上文件的文件名
            fileName = UUID.randomUUID().toString();
            InputStream inputStream = file.getInputStream();
            // 设置对象
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置数据流里有多少个字节可以读取
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
//            fileName = storagePath + "/" + fileName;
            // 上传文件
            ossClient.putObject(ossConfiguration.getBucketName(), fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            log.error("Error occurred: {}", e.getMessage(), e);
        }
        return fileName;
    }

    /**
     * 获取图片URL
     * @param filename
     * @return
     */
    public String getURL(String filename) {
        // 设置过期时间
        Date expiration = new Date(System.currentTimeMillis() + Integer.parseInt(expDays) * 24 * 60 * 60 * 1000L);
        URL url = ossClient.generatePresignedUrl(ossConfiguration.getBucketName(), filename, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
}
