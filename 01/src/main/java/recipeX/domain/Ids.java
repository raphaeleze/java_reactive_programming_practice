package recipeX.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Ids {

  private String postId;
  private String userId;
}
