package com.loggerproject.coreservice.server.configuration.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The default credential profiles file– typically located at ~/.aws/credentials (location can vary per platform),
 * and shared by many of the AWS SDKs and by the AWS CLI. The AWS SDK for Java uses the ProfileCredentialsProvider
 * to load these credentials.
 */
@Configuration
public class AwsConfiguration {

    @Bean
    public AmazonS3Client amazonS3() {
        return (AmazonS3Client) AmazonS3ClientBuilder.defaultClient();
    }
}
