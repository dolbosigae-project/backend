package com.gae.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gae.exception.ErrorCode;
import com.gae.exception.S3Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.UUID;

@Service
public class S3ImageService {

    private static final Logger logger = LoggerFactory.getLogger(S3ImageService.class);

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public S3ImageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadImageBytes(byte[] imageBytes, String mimeType, String extension) {
        String s3FileName = UUID.randomUUID().toString() + "." + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(mimeType);
        metadata.setContentLength(imageBytes.length);

        logger.info("Uploading image to S3 bucket: {}, filename: {}", bucketName, s3FileName);

        try (InputStream is = new ByteArrayInputStream(imageBytes)) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, is, metadata);
            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            logger.error("IOException while uploading image to S3", e);
            throw new S3Exception(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD);
        } catch (Exception e) {
            logger.error("Unexpected exception while uploading image to S3", e);
            throw new S3Exception(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD);
        }

        String s3Url = amazonS3.getUrl(bucketName, s3FileName).toString();
        logger.info("Image successfully uploaded to S3. URL: {}", s3Url);
        return s3Url;
    }

    public void deleteImageFromS3(String imageAddress) {
        String key = getKeyFromImageAddress(imageAddress);
        logger.info("Deleting image from S3 bucket: {}, key: {}", bucketName, key);
        try {
            amazonS3.deleteObject(bucketName, key);
            logger.info("Image successfully deleted from S3. key: {}", key);
        } catch (Exception e) {
            logger.error("Exception while deleting image from S3", e);
            throw new S3Exception(ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE);
        }
    }

    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            logger.error("Exception while decoding image address: {}", imageAddress, e);
            throw new S3Exception(ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE);
        }
    }
}
