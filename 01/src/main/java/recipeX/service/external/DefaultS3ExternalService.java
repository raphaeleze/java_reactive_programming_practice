package recipeX.service.external;

import reactor.core.publisher.Mono;
import recipeX.rest.RestUserRecipeImage;

public interface DefaultS3ExternalService {
  Mono<RestUserRecipeImage> uploadImage(String recipeId);
  Mono<RestUserRecipeImage> downloadImage(String recipeId);
}
