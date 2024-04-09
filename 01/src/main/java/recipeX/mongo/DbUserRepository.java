package recipeX.mongo;

import recipeX.db.DbPostLiveUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbUserRepository extends ReactiveMongoRepository<DbPostLiveUser, String> {

}
