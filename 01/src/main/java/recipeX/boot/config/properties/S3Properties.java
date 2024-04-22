package recipeX.boot.config.properties;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "recipeX.aws.s3")
public class S3Properties {

  private @NotNull String accessKey;
  private @NotNull String secretKey;
  private String region;
  private String publicEndpoint;
  private Duration urlExpiration;
}
