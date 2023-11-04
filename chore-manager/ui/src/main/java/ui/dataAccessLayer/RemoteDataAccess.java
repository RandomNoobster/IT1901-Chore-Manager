package ui.dataAccessLayer;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONObject;

import core.data.Collective;
import core.data.Password;
import core.data.Person;
import core.json.JSONValidator;

/**
 * Class that centralizes access to the Storage API. Makes it easier to support transparent use of a
 * REST API.
 */
public class RemoteDataAccess implements DataAccess {

    private static final String ACCEPT_HEADER = "Accept";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private final URI API_BASE_ENDPOINT;

    public RemoteDataAccess(URI apiBaseEndpoint) {
        this.API_BASE_ENDPOINT = apiBaseEndpoint;
    }

    private URI buildURI(String endpoint) {
        return this.buildURI(endpoint, Map.of());
    }

    private URI buildURI(String endpoint, Map<String, String> queryParameters) {
        StringBuilder builder = new StringBuilder(this.API_BASE_ENDPOINT.toString());
        builder.append(endpoint);

        if (!queryParameters.isEmpty()) {
            builder.append("?");
            boolean firstParam = true;
            for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
                if (!firstParam) {
                    builder.append("&");
                }
                builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                firstParam = false;
            }
        }

        return URI.create(builder.toString());
    }

    // @Override
    // public HashMap<String, Collective> getCollectives() {
    // final URI endpoint = this.storageURI("collectives");

    // HttpRequest request = HttpRequest.newBuilder(endpoint)
    // .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
    // System.out.println("REQUEST: " + request.toString());
    // try {
    // final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
    // HttpResponse.BodyHandlers.ofString());
    // final String responseBody = response.body();

    // // Deserialize the response body
    // JSONObject jsonObject = JSONValidator.decodeFromJSONString(responseBody);
    // HashMap<String, Collective> collectives = new HashMap<String, Collective>();

    // for (Object key : jsonObject.keySet()) {
    // String joinCode = (String) key;
    // JSONObject collectiveJSONObject = (JSONObject) jsonObject.get(joinCode);
    // Collective collective = Collective.decodeFromJSON(collectiveJSONObject);
    // collectives.put(joinCode, collective);
    // }

    // return collectives;
    // } catch (IOException | InterruptedException e) {
    // throw new RuntimeException(e);
    // }
    // }

    @Override
    public Collective getCollective(String joinCode) {
        final URI endpoint = this.buildURI(String.format("storage/collectives/%s", joinCode));

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();

            // Deserialize the response body
            JSONObject jsonObject = JSONValidator.decodeFromJSONString(responseBody);
            return Collective.decodeFromJSON(jsonObject);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collective getLimboCollective() {
        return this.getCollective(Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    }

    @Override
    public boolean addCollective(Collective collective) {
        final URI endpoint = this.buildURI("storage/collectives");

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON).POST(HttpRequest.BodyPublishers
                        .ofString(Collective.encodeToJSONObject(collective).toString()))
                .build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();
            return Boolean.parseBoolean(responseBody);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeCollective(Collective collective) {
        final URI endpoint = this
                .buildURI(String.format("storage/collectives/%s", collective.getJoinCode()));

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).DELETE().build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();
            return Boolean.parseBoolean(responseBody);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person getPerson(String username, Password password) {
        final URI endpoint = this.buildURI(String.format("storage/persons/%s", username),
                Map.of("password", password.getPasswordString()));

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED).GET().build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();
            System.out.println(responseBody);

            return null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public HashMap<String, Person> getAllPersons() {
    // HashMap<String, Person> persons = new HashMap<String, Person>();
    // for (Collective collective : this.getCollectives().values()) {
    // persons.putAll(collective.getPersons());
    // }
    // return persons;
    // final URI endpoint = this.storageURI("persons");

    // HttpRequest request = HttpRequest.newBuilder(endpoint)
    // .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
    // System.out.println("REQUEST: " + request.toString());
    // try {
    // final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
    // HttpResponse.BodyHandlers.ofString());
    // final String responseBody = response.body();

    // // Deserialize the response body
    // JSONObject jsonObject = JSONValidator.decodeFromJSONString(responseBody);
    // HashMap<String, Person> persons = new HashMap<String, Person>();

    // for (Object key : jsonObject.keySet()) {
    // String username = (String) key;
    // JSONObject personJSONObject = (JSONObject) jsonObject.get(username);
    // Person person = Person.decodeFromJSON(personJSONObject);
    // persons.put(username, person);
    // }

    // return persons;
    // } catch (IOException | InterruptedException e) {
    // throw new RuntimeException(e);
    // }
    // }

}
