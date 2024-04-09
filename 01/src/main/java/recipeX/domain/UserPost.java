package recipeX.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserPost {

  private UUID postId;
  private UUID userId;
  private String content;
  private LocalDateTime createdAt;
}
