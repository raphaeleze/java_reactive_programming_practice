package recipeX.mapper;


import recipeX.db.DbPostLiveUser;
import recipeX.db.DbUserPost;
import recipeX.rest.RestPostLiveUser;
import recipeX.rest.RestUserPost;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DbMapper extends UuidMapper {

  DbPostLiveUser toDbDto(RestPostLiveUser restPostLiveUser);


  DbUserPost toDbDto(RestUserPost restUserPost);

  List<DbUserPost> toDbDto(List<RestUserPost> restUserPost);
}
