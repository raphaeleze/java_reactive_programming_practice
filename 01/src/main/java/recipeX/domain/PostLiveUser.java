package recipeX.domain;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PostLiveUser {

  private UUID id;
  private List<UserPost> userPosts;
}
