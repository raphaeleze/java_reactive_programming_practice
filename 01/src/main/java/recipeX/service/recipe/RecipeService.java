package recipeX.service.recipe;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import recipeX.db.DbRecipeXUser;
import recipeX.db.DbUserRecipe;
import recipeX.domain.Ids;
import recipeX.mapper.DbMapper;
import recipeX.mapper.RestMapper;
import recipeX.mapper.UuidMapper;
import recipeX.mongo.DbRecipeRepository;
import recipeX.mongo.DbUserRepository;
import recipeX.rest.RestUserRecipe;

@Slf4j
@Service
@AllArgsConstructor
public class RecipeService implements DefaultRecipeService {
  private static final String POST_NOT_DELETED_MESSAGE = "Error deleting post {}";
  private static final String POST_NOT_SAVED_MESSAGE = "Error saving user {} post {} ";
  private final DbUserRepository dbUserRepository;
  private final DbRecipeRepository dbRecipeRepository;
  private final DbMapper dbMapper;
  private final UuidMapper uuidMapper;
  private final RestMapper restMapper;

  @Override
  public Mono<List<DbUserRecipe>> createRecipes(UUID userId, List<RestUserRecipe> recipes) {
    var dbUserPosts = recipes.stream()
        .map(restUserPost -> dbMapper.toDbDto(restUserPost
                .setUserId(userId)
                .setRecipeId(UUID.randomUUID()))
            .setCreatedAt(LocalDateTime.now()))
        .toList();

    return dbRecipeRepository.saveAll(dbUserPosts)
        .collectList()
        .flatMap(dbUserPostsSaved -> mapToDbDto(userId, recipes))
        .doOnError(error -> log.error(POST_NOT_SAVED_MESSAGE, userId, dbUserPosts))
        .thenReturn(dbUserPosts);
  }

  @Override
  public Mono<RestUserRecipe> getRecipe(String recipeId) {

    return dbRecipeRepository.findById(recipeId)
        .map(restMapper::toRestDto);
  }

  @Override
  public Flux<RestUserRecipe> getRecipeByName(String name) {
    return dbRecipeRepository.findByTitle(name)
        .map(restMapper::toRestDto);
  }

  @Override
  public Flux<RestUserRecipe> getRecipeByTags(List<String> tags) {
    return dbRecipeRepository.findByTagsContaining(tags)
        .map(restMapper::toRestDto);
  }

  @Override
  public Mono<Void> deleteRecipe(Ids ids) {

    return dbRecipeRepository.findById(ids.getRecipeId())
        .filter(dbUserPost -> dbUserPost.getUserId().equals(ids.getUserId()))
        .then(dbRecipeRepository.deleteById(ids.getRecipeId()))
        .doOnError(error -> log.error(POST_NOT_DELETED_MESSAGE, ids.getRecipeId()));
  }

  private Mono<DbRecipeXUser> mapToDbDto(UUID userId, List<RestUserRecipe> restUserRecipes) {
    var user = uuidMapper.toString(userId);
    var mappedUser = new DbRecipeXUser()
        .setId(user)
        .setUserPosts(dbMapper.toDbDto(restUserRecipes));

    return dbUserRepository.save(mappedUser);
  }
}
