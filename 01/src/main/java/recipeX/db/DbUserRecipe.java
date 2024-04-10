package recipeX.db;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "posts")
public class DbUserRecipe {

  @Id
  private String recipeId;
  private String userId;
  private String title;
  private String description;
  private List<String> ingredients;
  private List<String> instructions;
  private List<String> tags;
  private LocalDateTime createdAt;
}
