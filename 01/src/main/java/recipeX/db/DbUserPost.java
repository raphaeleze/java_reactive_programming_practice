package recipeX.db;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "posts")
public class DbUserPost {

  @Id
  private String postId;
  private String userId;
  private String content;
  private LocalDateTime createdAt;
}
