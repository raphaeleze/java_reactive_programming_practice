package recipeX.service.external;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import recipeX.boot.config.properties.S3Properties;
import recipeX.rest.RestUserRecipeImage;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Slf4j
@Service
@AllArgsConstructor
public class S3ExternalService implements DefaultS3ExternalService {

  private final S3Presigner s3Presigner;

  private final S3Properties properties;

  @Override
  public Mono<RestUserRecipeImage> uploadImage(String recipeId) {

    var putObjectRequest = PutObjectRequest.builder()
        .bucket(properties.getBucketName())
        .key(recipeId)
        .build();

    var putObjectPresignRequest = PutObjectPresignRequest.builder()
        .signatureDuration(properties.getUrlExpiration())
        .putObjectRequest(putObjectRequest)
        .build();

    var imageUploadUrl = s3Presigner.presignPutObject(putObjectPresignRequest);

    return Mono.just(new RestUserRecipeImage()
        .setImageUploadUrl(imageUploadUrl.toString()));
  }

  @Override
  public Mono<RestUserRecipeImage> downloadImage(String recipeId) {
    var getObjectRequest = GetObjectRequest.builder()
        .bucket(properties.getBucketName())
        .key(recipeId)
        .build();

    var getObjectPresignRequest = GetObjectPresignRequest.builder()
        .signatureDuration(properties.getUrlExpiration())
        .getObjectRequest(getObjectRequest)
        .build();

    var imageDownloadUrl = s3Presigner.presignGetObject(getObjectPresignRequest);

    return Mono.just(new RestUserRecipeImage()
        .setImageDownloadUrl(imageDownloadUrl.toString()));
  }
}
