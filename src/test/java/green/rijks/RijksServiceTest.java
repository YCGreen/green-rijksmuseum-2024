package green.rijks;

import com.andrewoid.ApiKey;
import green.rijks.json.ArtObjects;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RijksServiceTest {

    @Test
    void artObject() {
        //given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        //when
        ArtObjects artObjs = service.getArtObjects(apiKey.get()).blockingGet();

        //then
        assertNotNull(artObjs.artObjects[0].title);
        assertNotNull(artObjs.artObjects[0].longTitle);
        assertNotNull(artObjs.artObjects[0].webImage);
        assertNotNull(artObjs.artObjects[0].principalOrFirstMaker);

    }

    @Test
    void getPage() {
        //given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        //when
        ArtObjects artObjs = service.getPage(apiKey.get(), 5).blockingGet();

        //then
        assertNotNull(artObjs.artObjects[0].title);
        assertNotNull(artObjs.artObjects[0].longTitle);
        assertNotNull(artObjs.artObjects[0].webImage);
        assertNotNull(artObjs.artObjects[0].principalOrFirstMaker);

    }

    @Test
    void getField() {
        //given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        //when
        ArtObjects artObjs = service.getField(apiKey.get(), "Nachtwacht", 5).blockingGet();

        //then
        assertNotNull(artObjs.artObjects[0].title);
        assertNotNull(artObjs.artObjects[0].longTitle);
        assertNotNull(artObjs.artObjects[0].webImage);
        assertNotNull(artObjs.artObjects[0].principalOrFirstMaker);

    }

    @Test
    void getArtist() {
        //given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        //when
        ArtObjects artObjs = service.getArtist(apiKey.get(), 5, "Rembrandt van Rijn").blockingGet();

        //then
        assertNotNull(artObjs.artObjects[0].title);
        assertNotNull(artObjs.artObjects[0].longTitle);
        assertNotNull(artObjs.artObjects[0].webImage);
        assertNotNull(artObjs.artObjects[0].principalOrFirstMaker);

    }
}
