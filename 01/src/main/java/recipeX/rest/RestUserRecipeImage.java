package recipeX.rest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestUserRecipeImage {
  @NotNull
  private String imageUploadUrl;
  private String imageDownloadUrl;
}
