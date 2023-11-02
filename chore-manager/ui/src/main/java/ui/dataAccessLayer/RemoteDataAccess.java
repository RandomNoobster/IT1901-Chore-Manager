package ui.dataAccessLayer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.JSONObject;

import core.data.Collective;
import core.json.JSONValidator;

/**
 * Class that centralizes access to the Storage API. Makes it easier to support transparent use of a
 * REST API.
 */
public class RemoteDataAccess implements DataAccess {

    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private final URI API_BASE_ENDPOINT;

    public RemoteDataAccess(URI apiBaseEndpoint) {
        this.API_BASE_ENDPOINT = apiBaseEndpoint;
    }

    @Override
    public HashMap<String, Collective> getCollectives() {
        final String endpoint = "storage/collectives";

        HttpRequest request = HttpRequest.newBuilder(this.API_BASE_ENDPOINT.resolve(endpoint))
                .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();
            System.out.println(responseBody);

            // Deserialize the response body
            JSONObject jsonObject = JSONValidator.decodeFromJSON(responseBody);
            HashMap<String, Collective> collectives = new HashMap<String, Collective>();

            for (Object key : jsonObject.keySet()) {
                String joinCode = (String) key;
                JSONObject collectiveJSONObject = (JSONObject) jsonObject.get(joinCode);
                Collective collective = Collective.decodeFromJSON(collectiveJSONObject);
                collectives.put(joinCode, collective);
            }

            return collectives;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
