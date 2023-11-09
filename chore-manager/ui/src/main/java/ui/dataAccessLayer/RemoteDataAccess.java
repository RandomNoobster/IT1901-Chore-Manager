package ui.dataAccessLayer;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import core.data.Chore;
import core.data.Collective;
import core.data.Password;
import core.data.Person;
import core.data.RestrictedCollective;
import core.data.RestrictedPerson;
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

    @Override
    public RestrictedCollective getCollective(String joinCode) {
        if (joinCode == null || joinCode.isEmpty())
            return null;

        final URI endpoint = this.buildURI(String.format("storage/collectives/%s", joinCode));

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();

            if (responseBody == null || responseBody.isEmpty())
                return null;

            // Deserialize the response body
            JSONObject jsonObject = JSONValidator.decodeFromJSONString(responseBody);
            return RestrictedCollective.decodeFromJSON(jsonObject);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RestrictedCollective getLimboCollective() {
        return this.getCollective(RestrictedCollective.LIMBO_COLLECTIVE_JOIN_CODE);
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
        if (username == null || username.isEmpty())
            return null;

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

            if (responseBody == null || responseBody.isEmpty())
                return null;

            // Deserialize the response body
            JSONObject jsonObject = JSONValidator.decodeFromJSONString(responseBody);
            return Person.decodeFromJSON(jsonObject);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addPerson(Person person, String joinCode) {
        final URI endpoint = this
                .buildURI(String.format("storage/persons/%s", person.getUsername()));

        JSONObject requestBody = new JSONObject();
        requestBody.put("person", Person.encodeToJSONObject(person));
        requestBody.put("joinCode", joinCode);

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString())).build();

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
    public boolean movePersonToAnotherCollective(String username, Password password,
            String oldJoinCode, String newJoinCode) {
        if (username == null || username.isEmpty())
            return false;

        final URI endpoint = this.buildURI(String.format("storage/persons/%s", username));

        JSONObject requestBody = new JSONObject();
        requestBody.put("password", password.getPasswordString());
        requestBody.put("oldJoinCode", oldJoinCode);
        requestBody.put("newJoinCode", newJoinCode);

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody.toString())).build();
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
    public boolean logIn(Person user, Password userPassword, RestrictedCollective collective) {
        final URI endpoint = this.buildURI("state/log-in");

        JSONObject requestBody = new JSONObject();
        requestBody.put("username", user.getUsername());
        requestBody.put("password", userPassword.getPasswordString());
        requestBody.put("joinCode", collective.getJoinCode());

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString())).build();
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
    public boolean logOut() {
        final URI endpoint = this.buildURI("state/log-out");

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).POST(HttpRequest.BodyPublishers.noBody())
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
    public Person getLoggedInUser() {
        final URI endpoint = this.buildURI("state/logged-in-user");

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();

            if (responseBody == null || responseBody.isEmpty()) {
                System.out.println("No user was logged in");
                return null;
            }

            // Deserialize the response body
            JSONObject jsonObject = JSONValidator.decodeFromJSONString(responseBody);
            return Person.decodeFromJSON(jsonObject);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RestrictedCollective getCurrentCollective() {
        final URI endpoint = this.buildURI("state/current-collective");

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();

            if (responseBody == null || responseBody.isEmpty())
                return null;

            // Deserialize the response body
            JSONObject jsonObject = JSONValidator.decodeFromJSONString(responseBody);
            return RestrictedCollective.decodeFromJSON(jsonObject);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addChore(Chore chore, RestrictedPerson assignedPerson) {
        final URI endpoint = this.buildURI(String.format("state/chores/%s", chore.getUUID()));

        JSONObject requestBody = new JSONObject();
        requestBody.put("chore", Chore.encodeToJSONObject(chore));
        requestBody.put("assignedPerson", assignedPerson.getUsername());

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString())).build();
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
    public boolean removeChore(Chore chore) {
        final URI endpoint = this.buildURI(String.format("state/chores/%s", chore.getUUID()));

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON).DELETE().build();
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
    public boolean updateChoreChecked(Chore chore, boolean checked) {
        final URI endpoint = this.buildURI(String.format("state/chores/%s", chore.getUUID()),
                Map.of("checked", String.valueOf(checked)));

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
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
    public List<Chore> getChores() {
        final URI endpoint = this.buildURI("state/chores");

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();

        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();

            if (responseBody == null || responseBody.isEmpty())
                return null;

            // Deserialize the response body
            JSONArray jsonArray = JSONValidator.decodeFromJSONStringArray(responseBody);
            List<Chore> chores = new ArrayList<Chore>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                chores.add(Chore.decodeFromJSON(jsonObject));
            }

            return chores;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<String, RestrictedPerson> getPersons() {
        final URI endpoint = this.buildURI("state/persons");

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();

        System.out.println("REQUEST: " + request.toString());
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();

            if (responseBody == null || responseBody.isEmpty())
                return null;

            // Deserialize the response body
            JSONObject jsonObject = JSONValidator.decodeFromJSONString(responseBody);
            HashMap<String, RestrictedPerson> persons = new HashMap<String, RestrictedPerson>();

            for (String username : jsonObject.keySet()) {
                JSONObject personJSONObject = jsonObject.getJSONObject(username);
                persons.put(username, RestrictedPerson.decodeFromJSON(personJSONObject));
            }

            return persons;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterStandardMode() {
        final URI endpoint = this.buildURI("storage/mode/enter-standard-mode");

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).POST(HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterTestMode() {
        final URI endpoint = this.buildURI("storage/mode/enter-test-mode");

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header(ACCEPT_HEADER, APPLICATION_JSON).POST(HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
