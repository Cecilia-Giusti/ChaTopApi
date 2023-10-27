package com.ChaTopApiSpring2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


import java.io.IOException;
import java.util.UUID;

/**
 * Service class responsible for managing AWS S3 bucket operations.
 * This service offers methods to handle S3-related logic, such as uploading and deleting files,
 * leveraging the AWS SDK for Java v2.
 */
@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    @Value("${aws.region}")
    private String region;


    /**
     * Constructor for the S3Service, initializing the S3 client and required properties.
     *
     * @param accessKey AWS Access Key ID.
     * @param secretKey AWS Secret Access Key.
     * @param region AWS region where the S3 bucket resides.
     * @param bucketName Name of the S3 bucket.
     */
    @Autowired
    public S3Service(@Value("${aws.accessKeyId}") String accessKey,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.region}") Region region,
                     @Value("${aws.s3.bucket}") String bucketName) {
        this.s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        this.bucketName = bucketName;
    }

    /**
     * Upload a file to the specified S3 bucket.
     *
     * @param file The MultipartFile to be uploaded.
     * @return A string containing the full URL of the uploaded file.
     * @throws IOException If there's an error during file operations.
     */
    public String uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String extension = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(uniqueFileName).build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + uniqueFileName;
    }

    /**
     * Delete a file from the S3 bucket using its URL.
     *
     * @param imageUrl The URL of the file to be deleted.
     */
    public void deleteFile(String imageUrl) {
            String objectKey = extractObjectKeyFromUrl(imageUrl);
            s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build());
    }


    /**
     * Extract the object key (file name) from the given S3 URL.
     *
     * @param imageUrl The S3 URL from which the object key will be extracted.
     * @return A string containing the object key.
     */
    private String extractObjectKeyFromUrl(String imageUrl) {
        String prefix = "https://" + bucketName + ".s3." + region + ".amazonaws.com/";
        if (imageUrl.startsWith(prefix)) {
            return imageUrl.substring(prefix.length());
        }
        throw new RuntimeException("Invalid S3 URL: " + imageUrl);
    }



}

