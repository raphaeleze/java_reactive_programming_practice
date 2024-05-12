package recipeX.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRecipeImage {
  private String imageUploadUrl;
  private String imageDownloadUrl;
}
