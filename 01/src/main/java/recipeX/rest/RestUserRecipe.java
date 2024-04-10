package recipeX.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(Include.NON_NULL)
public class RestUserRecipe {

  private UUID recipeId;
  private UUID userId;
  private String title;
  private String description;
  private List<String> ingredients;
  private List<String> instructions;
  private List<String> tags;
  private LocalDateTime createdAt;
}
