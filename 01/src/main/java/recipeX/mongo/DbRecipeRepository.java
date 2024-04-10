package recipeX.mongo;

import recipeX.db.DbUserRecipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbRecipeRepository extends ReactiveMongoRepository<DbUserRecipe, String> {

}
