package green.rijks;

import com.andrewoid.ApiKey;
import green.rijks.json.ArtObject;
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
        ArtObject artObj = service.artObject(apiKey.get()).blockingGet();

        //then
        assertNotNull(artObj.title);

    }
}
