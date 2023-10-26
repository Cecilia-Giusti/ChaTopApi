package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller handling file uploads.
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private S3Service s3Service;

    /**
     * Handles file upload and returns the uploaded file's URL.
     *
     * @param file The file to be uploaded.
     * @return A ResponseEntity containing the URL of the uploaded file.
     * @throws IOException If there's an I/O error during the file upload.
     */
    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = s3Service.uploadFile(file);
        return ResponseEntity.ok(imageUrl);
    }
}
