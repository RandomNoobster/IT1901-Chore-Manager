package ui.dataAccessLayer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import core.data.Collective;

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
        System.out.println("TEST: " + this.API_BASE_ENDPOINT.resolve(endpoint));
        HttpRequest request = HttpRequest.newBuilder(this.API_BASE_ENDPOINT.resolve(endpoint))
                .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();
            System.out.println(responseBody);
            // Deserialize the response body to a HashMap<String, Collective>
            return null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
