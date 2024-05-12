package recipeX.boot.config;

import java.net.URI;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import recipeX.boot.config.properties.S3Properties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class AppConfig {
  @Bean
  public S3AsyncClient s3AsyncClient(S3Properties s3Properties, AwsCredentialsProvider awsCredentialsProvider) {
    return S3AsyncClient.builder()
        .region(Region.of(s3Properties.getRegion()))
        .credentialsProvider(awsCredentialsProvider)
        .endpointOverride(URI.create(s3Properties.getPublicEndpoint()))
        .build();
  }

  @Bean
  public S3Presigner s3Presigner(S3Properties s3Properties, AwsCredentialsProvider awsCredentialsProvider) {
    return S3Presigner.builder()
        .region(Region.of(s3Properties.getRegion()))
        .credentialsProvider(awsCredentialsProvider)
        .endpointOverride(URI.create(s3Properties.getPublicEndpoint()))
        .build();
  }

  @Bean
  public S3Utilities s3Utilities(S3AsyncClient s3AsyncClient) {
    return S3Utilities.builder().build();
  }

  @Bean
  public AwsCredentialsProvider awsCredentialsProvider(S3Properties properties) {
    var awsCredentials = AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey());
    return StaticCredentialsProvider.create(awsCredentials);
  }
}
