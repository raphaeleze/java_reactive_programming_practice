package recipeX.mongo;

import recipeX.db.DbUserPost;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbPostRepository extends ReactiveMongoRepository<DbUserPost, String> {

}
