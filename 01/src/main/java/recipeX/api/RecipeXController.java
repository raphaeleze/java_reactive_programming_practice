package recipeX.api;

import recipeX.db.DbPostLiveUser;
import recipeX.db.DbUserPost;
import recipeX.domain.Ids;
import recipeX.mapper.DbMapper;
import recipeX.mapper.RestMapper;
import recipeX.mapper.UuidMapper;
import recipeX.mongo.DbPostRepository;
import recipeX.mongo.DbUserRepository;
import recipeX.rest.RestPostLiveUser;
import recipeX.rest.RestUserPost;
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

  private static final String USER_DELETED_MESSAGE = "User {} deleted";
  private static final String POST_DELETED_MESSAGE = "Posts associated with user {} deleted";
  private static final String USER_NOT_DELETED_MESSAGE = "Error deleting user {}";
  private static final String POST_NOT_DELETED_MESSAGE = "Error deleting post {}";
  private static final String USER_NOT_SAVED_MESSAGE = "Error saving user {}";
  private static final String POST_NOT_SAVED_MESSAGE = "Error saving user {} post {} ";
  private final DbUserRepository dbUserRepository;
  private final DbPostRepository dbPostRepository;
  private final DbMapper dbMapper;
  private final UuidMapper uuidMapper;
  private final RestMapper restMapper;


  @Override
  public Mono<RestPostLiveUser> createUser() {
    var user = new RestPostLiveUser()
        .setId(UUID.randomUUID());

    return Mono.just(user)
        .map(dbMapper::toDbDto)
        .flatMap(dbUserRepository::save)
        .doOnError(error -> log.error(USER_NOT_SAVED_MESSAGE, user.getId()))
        .map(restMapper::toRestDto);
  }

  @Override
  public Mono<RestPostLiveUser> getUser(UUID userId) {
    var user = uuidMapper.toString(userId);

    return dbUserRepository.findById(user)
        .map(restMapper::toRestDto);
  }

  @Override
  public Mono<List<DbUserPost>> createUserPosts(UUID userId, List<RestUserPost> restUserPosts) {
    var dbUserPosts = restUserPosts.stream()
        .map(restUserPost -> dbMapper.toDbDto(restUserPost
                .setUserId(userId)
                .setPostId(UUID.randomUUID()))
            .setCreatedAt(LocalDateTime.now()))
        .toList();

    return dbPostRepository.saveAll(dbUserPosts)
        .collectList()
        .flatMap(dbUserPostsSaved -> mapToDbDto(userId, restUserPosts))
        .doOnError(error -> log.error(POST_NOT_SAVED_MESSAGE, userId, dbUserPosts))
        .thenReturn(dbUserPosts);
  }

  @Override
  public Mono<Void> deleteUserPost(Ids ids) {

    return dbPostRepository.findById(ids.getPostId())
        .filter(dbUserPost -> dbUserPost.getUserId().equals(ids.getUserId()))
        .then(dbPostRepository.deleteById(ids.getPostId()))
        .doOnError(error -> log.error(POST_NOT_DELETED_MESSAGE, ids.getPostId()));
  }

  @Override
  public Mono<Void> deleteUser(UUID userId) {
    var user = uuidMapper.toString(userId);
    return dbUserRepository.deleteById(user)
        .doOnError(info -> log.error(USER_NOT_DELETED_MESSAGE, user))
        .doOnSuccess(info -> log.info(USER_DELETED_MESSAGE, user))
        .then(dbPostRepository.deleteById(user))
        .doOnError(info -> log.error(POST_NOT_DELETED_MESSAGE, user))
        .doOnSuccess(info -> log.info(POST_DELETED_MESSAGE, user));
  }

  private Mono<DbPostLiveUser> mapToDbDto(UUID userId, List<RestUserPost> restUserPosts) {
    var user = uuidMapper.toString(userId);
    var mappedUser = new DbPostLiveUser()
        .setId(user)
        .setUserPosts(dbMapper.toDbDto(restUserPosts));

    return dbUserRepository.save(mappedUser);
  }
}