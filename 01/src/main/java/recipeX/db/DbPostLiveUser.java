package recipeX.db;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "post_live_users")
public class DbPostLiveUser {

  @Id
  private String id;
  private List<DbUserPost> userPosts;
}