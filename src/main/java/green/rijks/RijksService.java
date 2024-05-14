package green.rijks;

import green.rijks.json.ArtObjects;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RijksService {

    @GET("/api/en/collection")
    Single<ArtObjects> getArtObjects(
            @Query("key") String appId
    );

    @GET("/api/en/collection")
    Single<ArtObjects> getPage(
            @Query("key") String appId,
            @Query("p") int pageNumber
    );

    @GET("/api/en/collection")
    Single<ArtObjects> getField(
            @Query("key") String appId,
            @Query("q") String query,
            @Query("p") int pageNumber
    );

    @GET("/api/en/collection")
    Single<ArtObjects> getArtist(
            @Query("key") String appId,
            @Query("p") int pageNumber,
            @Query("involvedMaker") String artist
    );


}
