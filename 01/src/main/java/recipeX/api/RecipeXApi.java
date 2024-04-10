package recipeX.api;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import recipeX.db.DbUserRecipe;
import recipeX.domain.Ids;
import recipeX.domain.Username;
import recipeX.rest.RestRecipeXUser;
import recipeX.rest.RestUserRecipe;

public interface RecipeXApi {

  @PostMapping("/user")
  Mono<RestRecipeXUser> createUser(@Valid @RequestBody Username username);

  @PostMapping("/recipes/{userId}")
  Mono<List<DbUserRecipe>> createRecipes(@PathVariable("userId") UUID userId,
                                         @RequestBody List<RestUserRecipe> restUserRecipes);

  @GetMapping("/user/{userId}")
  Mono<RestRecipeXUser> getUser(@PathVariable("userId") UUID userId);

  @GetMapping("/recipe/{recipeId}")
  Mono<RestUserRecipe> getRecipe(@PathVariable("recipeId") String recipeId);

  @GetMapping("/recipes/by-title/{title}")
  Flux<RestUserRecipe> getRecipeByName(@PathVariable("title") String title);

  @GetMapping("/recipes/by-tags")
  Flux<RestUserRecipe> getRecipeByTags(@RequestParam List<String> tags);

  @DeleteMapping("/recipe")
  Mono<Void> deleteRecipe(@Valid @RequestBody Ids ids);

  @DeleteMapping("/user/{userId}")
  Mono<Void> deleteUser(@PathVariable("userId") UUID userId);
}