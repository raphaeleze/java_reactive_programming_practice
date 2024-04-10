package recipeX.mongo;

import java.util.List;
import reactor.core.publisher.Flux;
import recipeX.db.DbUserRecipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbRecipeRepository extends ReactiveMongoRepository<DbUserRecipe, String> {
  Flux<DbUserRecipe> findByTitle(String title);
  Flux<DbUserRecipe> findByTagsContaining(List<String> tags);

}
