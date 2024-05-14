package green.rijks;

import green.rijks.json.ArtObject;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RijksService {

    @GET("/api/en/collection")
    Single<ArtObject> artObject(
            @Query("key") String appId
    );
}
