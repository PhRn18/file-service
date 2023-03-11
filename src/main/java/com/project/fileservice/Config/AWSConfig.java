package com.project.fileservice.Config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Configuration
public class AWSConfig {
    @Value("${aws.bucket.name}")
    private String bucketName;

    @Bean
    public AmazonS3 AutheticationS3() {
        log.info("Autenticando S3...");
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }
    @Bean
    public ListObjectsRequest ListS3ObjectsRequest() {
        log.info("Montando objeto ListObjectsRequest");
        return new ListObjectsRequest()
                .withBucketName("filemanagerpauloneves");
    }

}
