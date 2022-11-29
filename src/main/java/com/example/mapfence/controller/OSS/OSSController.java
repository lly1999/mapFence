package com.example.mapfence.controller.OSS;

import com.example.mapfence.service.OSS.OSSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author xavi
 * @date 2022/11/29
 */
@Api(tags = "上传图片相关接口")
@RestController
@RequestMapping("/oss")
public class OSSController {

    @Resource
    private OSSService ossService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/uploadFiles")
    public String uploadFiles(@RequestParam("file") MultipartFile file) {
        return ossService.uploadFile(file);
    }
}
