package recipeX.api;

import recipeX.db.DbUserRecipe;
import recipeX.domain.Ids;
import recipeX.rest.RestRecipeXUser;
import recipeX.rest.RestUserRecipe;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface RecipeXApi {

  @PostMapping("/user")
  Mono<RestRecipeXUser> createUser();

  @PostMapping("/recipe/{userId}")
  Mono<List<DbUserRecipe>> createRecipes(@PathVariable("userId") UUID userId,
                                           @RequestBody List<RestUserRecipe> restUserRecipes);

  @GetMapping("/user/{userId}")
  Mono<RestRecipeXUser> getUser(@PathVariable("userId") UUID userId);
  @GetMapping("/recipe/{recipeId}")
  Mono<DbUserRecipe> getRecipe(@RequestBody UUID recipeId);

  @GetMapping("/recipe/{tag}")
  Mono<List<DbUserRecipe>> getRecipeByName(@RequestBody String recipeName);

  @DeleteMapping("/recipe")
  Mono<Void> deleteRecipe(@RequestBody Ids ids);

  @DeleteMapping("/user/{userId}")
  Mono<Void> deleteUser(@PathVariable("userId") UUID userId);
}