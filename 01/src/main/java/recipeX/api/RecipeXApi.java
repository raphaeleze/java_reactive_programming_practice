package recipeX.api;

import recipeX.db.DbUserPost;
import recipeX.domain.Ids;
import recipeX.rest.RestPostLiveUser;
import recipeX.rest.RestUserPost;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface RecipeXApi {

  @PostMapping("/createUser")
  Mono<RestPostLiveUser> createUser();

  @PostMapping("/createUserPosts/{userId}")
  Mono<List<DbUserPost>> createUserPosts(@PathVariable("userId") UUID userId,
                                         @RequestBody List<RestUserPost> restUserPosts);

  @GetMapping("/getUser/{userId}")
  Mono<RestPostLiveUser> getUser(@PathVariable("userId") UUID userId);

  @DeleteMapping("/deleteUserPost")
  Mono<Void> deleteUserPost(@RequestBody Ids ids);

  @DeleteMapping("/deleteUser/{userId}")
  Mono<Void> deleteUser(@PathVariable("userId") UUID userId);
}