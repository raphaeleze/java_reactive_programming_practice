package recipeX.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Ids {

  private String recipeId;
  private String userId;
}
