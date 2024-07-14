package com.back.controller;

import com.back.pojo.Result;
import com.back.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<String> upload(final MultipartFile file) throws IOException {
        final String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        final String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        final String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }
}
