package com.loggerproject.coreservice.service.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AmazonS3BucketService {

    @Autowired
    AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    /**
     * Uploads a MultipartFile with key name to the AWS bucket
     * @param keyName - the specified key name of the multipart file
     * @param multipartFile - the multipart file
     * @return String - url of file uploaded, null if upload failed
     */
    public String uploadMultipartFile(String keyName, MultipartFile multipartFile) {
        String url = null;

        File file = multipartToFile(multipartFile);
        if(file != null) {
            url = uploadFile(keyName, file);
        }

        return url;
    }

    /**
     * Uploads a File with key name to the AWS bucket
     * @param key - the specified key name of the file
     * @param file - the file
     * @return String - url of file uploaded, null if upload failed
     */
    public String uploadFile(String key, File file) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, key, file));
//        TODO returns https but isn't secure
//        return amazonS3Client.getResourceUrl(bucketName, keyName);
        return "https://s3.amazonaws.com/" + bucketName + "/" + key;
    }

    /**
     * Delete file from AWS bucket by keyName
     * @param keyName - the keyName
     */
    public void deleteFile(String keyName) {
        amazonS3Client.deleteObject(bucketName, keyName);
    }

    /**
     * Converts MultipartFile to File
     * @param multipartFile - the multipart file to transform
     * @return File - the file requested
     */
    private File multipartToFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            file = null;
        }

        return file;
    }
}
