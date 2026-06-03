package com.spring.chatApp.Controller;

import com.cloudinary.Cloudinary;

import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/files")

@RequiredArgsConstructor
@CrossOrigin("*")
public class FileUploadController {

    private final Cloudinary cloudinary;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(

            @RequestParam("file")
            MultipartFile file
    ) {

        try {

            Map uploadResult =
                    cloudinary.uploader().upload(

                            file.getBytes(),

                            ObjectUtils.emptyMap()
                    );

            String fileUrl =
                    uploadResult.get("url")
                            .toString();

            return ResponseEntity.ok(
                    fileUrl
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            e.getMessage()
                    );
        }
    }
}