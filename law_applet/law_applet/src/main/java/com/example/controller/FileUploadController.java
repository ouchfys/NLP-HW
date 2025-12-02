package com.example.controller;

import com.example.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@Slf4j
@RequestMapping("/upload")
public class FileUploadController {

    // 获取配置文件中的上传路径
    @Value("${upload.file-path}")
    private String uploadDirectory;

    @PostMapping("file")
    public R<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.fail("上传文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase();
        if (suffix == null || !suffix.equals(".jpg") && !suffix.equals(".png") && !suffix.equals(".jpeg")){
            return R.fail("文件上传失败");
        }
        String fileName = System.currentTimeMillis() + suffix;
        String filePath = uploadDirectory + fileName;
        try {
            file.transferTo(new File(filePath));
            return R.ok("file/"+fileName);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return R.fail("文件上传失败");
        }
    }

}
