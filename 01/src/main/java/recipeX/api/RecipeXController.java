package recipeX.api;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import recipeX.db.DbUserRecipe;
import recipeX.domain.Ids;
import recipeX.domain.Username;
import recipeX.rest.RestRecipeXUser;
import recipeX.rest.RestUserRecipe;
import recipeX.service.recipe.DefaultRecipeService;
import recipeX.service.user.DefaultUserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeXController implements RecipeXApi {
  private final DefaultUserService userService;
  private final DefaultRecipeService recipeService;

  @Override
  public Mono<RestRecipeXUser> createUser(Username username) {
    return userService.createUser(username);
  }

  @Override
  public Mono<RestRecipeXUser> getUser(UUID userId) {
    return userService.getUser(userId);
  }

  @Override
  public Mono<List<DbUserRecipe>> createRecipes(UUID userId, List<RestUserRecipe> recipes) {
    return recipeService.createRecipes(userId, recipes);
  }

  @Override
  public Mono<RestUserRecipe> getRecipe(String recipeId) {
    return recipeService.getRecipe(recipeId);
  }

  @Override
  public Flux<RestUserRecipe> getRecipeByName(String name) {
    return recipeService.getRecipeByName(name);
  }

  @Override
  public Flux<RestUserRecipe> getRecipeByTags(List<String> tags) {
    return recipeService.getRecipeByTags(tags);
  }

  @Override
  public Mono<Void> deleteRecipe(Ids ids) {
    return recipeService.deleteRecipe(ids);
  }

  @Override
  public Mono<Void> deleteUser(UUID userId) {
    return userService.deleteUser(userId);
  }
}