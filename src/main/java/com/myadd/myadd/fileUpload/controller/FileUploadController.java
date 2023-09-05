package com.myadd.myadd.fileUpload.controller;

import com.myadd.myadd.fileUpload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping("/uploadTest")
    public String uploadFile(@RequestParam("image")MultipartFile multipartFile) throws IOException {

        return fileUploadService.upload(multipartFile);
    }
}
