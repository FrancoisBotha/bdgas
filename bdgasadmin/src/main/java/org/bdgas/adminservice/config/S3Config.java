package org.bdgas.adminservice.config;

/*******************************
 * Courtesy of:
 * http://javasampleapproach.com/
 * spring-framework/spring-cloud/
 * amazon-s3-uploaddownload-files-springboot-amazon-s3-application
 ****************************/

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
    @Value("${aws.s3.profile}")
    private String awsProfileName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${active.profile}")
    private String activeProfile;

    @Bean
    public AmazonS3 s3client() {

        // DEVL / PROD Profile...
        if (activeProfile.equals("prod")) {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                     .withRegion(Regions.fromName(region))
                    .build();
            return s3Client;
        } else {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider(awsProfileName))
                    .withRegion(Regions.fromName(region))
                    .build();
            return s3Client;
        }


    }
}

