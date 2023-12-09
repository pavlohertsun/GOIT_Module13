import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import user.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HTTPMethods {
    private static final String URL = "https://jsonplaceholder.typicode.com/users";
    private final HttpClient httpClient;
    private final Gson gson;

    public HTTPMethods() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }
    public List<User> getAllUsers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        Type userListType = new TypeToken<List<User>>(){}.getType();

        List<User> users = gson.fromJson(responseBody, userListType);
        return users;
    }
    public User getUserById(int userId) throws Exception {
        String url = URL + "/" + userId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        User user = gson.fromJson(responseBody, User.class);
        return user;
    }
    public User getUserByName(String username) throws Exception{
        String url = URL + "?username=" + username;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        Type userListType = new TypeToken<List<User>>(){}.getType();

        List<User> userList = gson.fromJson(responseBody, userListType);

        return userList.get(0);

    }
    public User createUser(User user) throws Exception {
        String userJson = gson.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        User createdUser = gson.fromJson(responseBody, User.class);
        return createdUser;
    }
    public User updateUser(int userId, User userToUpdate) throws Exception {
        String userJson = gson.toJson(userToUpdate);

        String apiUrl = URL + "/" + userId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(userJson))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());

        String responseBody = response.body();
        User updatedUser = gson.fromJson(responseBody, User.class);

        return updatedUser;
    }
    public int deleteUser(int userId) throws Exception {
        String apiUrl = URL + "/" + userId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.statusCode();
    }
}
