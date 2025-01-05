package com.project.aws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;



@Configuration
public class S3Config {
	
	
	  @Value("${aws.accessKeyId}") private String awsId;
	  
	  @Value("${aws.secretAccessKey}") private String awsKey;
	  
	  @Value("${aws.s3.region}") private String region;
	  
	  @Bean 
	  public AmazonS3 s3client() { 
	  BasicAWSCredentials awsCred = new BasicAWSCredentials(awsId,awsKey);
	  AmazonS3 s3client =  AmazonS3ClientBuilder.standard().withRegion(region)
	  .withCredentials(new  AWSStaticCredentialsProvider(awsCred)).build();
	  
	  return s3client; }
	 

}
