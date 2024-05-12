package recipeX.boot.config.properties;

import java.time.Duration;
import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "s3Properties")
public class S3Properties {

  private @NonNull String region;
  private @NonNull String bucketName;
  private @NonNull String accessKey;
  private @NonNull String secretKey;
  private String publicEndpoint;
  private @NonNull Duration urlExpiration;
}
