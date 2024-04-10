package recipeX.api;

import org.springframework.beans.factory.annotation.Autowired;
import recipeX.db.DbRecipeXUser;
import recipeX.db.DbUserRecipe;
import recipeX.domain.Ids;
import recipeX.mapper.DbMapper;
import recipeX.mapper.RestMapper;
import recipeX.mapper.UuidMapper;
import recipeX.mongo.DbRecipeRepository;
import recipeX.mongo.DbUserRepository;
import recipeX.rest.RestRecipeXUser;
import recipeX.rest.RestUserRecipe;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeXController implements RecipeXApi {
  @Autowired
  private static final String USER_DELETED_MESSAGE = "User {} deleted";
  private static final String POST_DELETED_MESSAGE = "Posts associated with user {} deleted";
  private static final String USER_NOT_DELETED_MESSAGE = "Error deleting user {}";
  private static final String POST_NOT_DELETED_MESSAGE = "Error deleting post {}";
  private static final String USER_NOT_SAVED_MESSAGE = "Error saving user {}";
  private static final String POST_NOT_SAVED_MESSAGE = "Error saving user {} post {} ";
  private final DbUserRepository dbUserRepository;
  private final DbRecipeRepository dbRecipeRepository;
  private final DbMapper dbMapper;
  private final UuidMapper uuidMapper;
  private final RestMapper restMapper;


  @Override
  public Mono<RestRecipeXUser> createUser() {
    var user = new RestRecipeXUser()
        .setId(UUID.randomUUID());

    return Mono.just(user)
        .map(dbMapper::toDbDto)
        .flatMap(dbUserRepository::save)
        .doOnError(error -> log.error(USER_NOT_SAVED_MESSAGE, user.getId()))
        .map(restMapper::toRestDto);
  }

  @Override
  public Mono<List<DbUserRecipe>> createRecipes(UUID userId, List<RestUserRecipe> restUserRecipes) {
    var dbUserPosts = restUserRecipes.stream()
        .map(restUserPost -> dbMapper.toDbDto(restUserPost
                .setUserId(userId)
                .setRecipeId(UUID.randomUUID()))
            .setCreatedAt(LocalDateTime.now()))
        .toList();

    return dbRecipeRepository.saveAll(dbUserPosts)
        .collectList()
        .flatMap(dbUserPostsSaved -> mapToDbDto(userId, restUserRecipes))
        .doOnError(error -> log.error(POST_NOT_SAVED_MESSAGE, userId, dbUserPosts))
        .thenReturn(dbUserPosts);
  }

  @Override
  public Mono<RestRecipeXUser> getUser(UUID userId) {
    var user = uuidMapper.toString(userId);

    return dbUserRepository.findById(user)
        .map(restMapper::toRestDto);
  }

  @Override
  public Mono<DbUserRecipe> getRecipe(UUID recipeId) {
    var recipe = uuidMapper.toString(recipeId);

    return dbRecipeRepository.findById(recipe);
  }

  @Override
  public Mono<List<DbUserRecipe>> getRecipeByName(String recipeName) {
    return null;
  }

  @Override
  public Mono<Void> deleteRecipe(Ids ids) {

    return dbRecipeRepository.findById(ids.getRecipeId())
        .filter(dbUserPost -> dbUserPost.getUserId().equals(ids.getUserId()))
        .then(dbRecipeRepository.deleteById(ids.getRecipeId()))
        .doOnError(error -> log.error(POST_NOT_DELETED_MESSAGE, ids.getRecipeId()));
  }

  @Override
  public Mono<Void> deleteUser(UUID userId) {
    var user = uuidMapper.toString(userId);
    return dbUserRepository.deleteById(user)
        .doOnError(info -> log.error(USER_NOT_DELETED_MESSAGE, user))
        .doOnSuccess(info -> log.info(USER_DELETED_MESSAGE, user))
        .then(dbRecipeRepository.deleteById(user))
        .doOnError(info -> log.error(POST_NOT_DELETED_MESSAGE, user))
        .doOnSuccess(info -> log.info(POST_DELETED_MESSAGE, user));
  }

  private Mono<DbRecipeXUser> mapToDbDto(UUID userId, List<RestUserRecipe> restUserRecipes) {
    var user = uuidMapper.toString(userId);
    var mappedUser = new DbRecipeXUser()
        .setId(user)
        .setUserPosts(dbMapper.toDbDto(restUserRecipes));

    return dbUserRepository.save(mappedUser);
  }
}