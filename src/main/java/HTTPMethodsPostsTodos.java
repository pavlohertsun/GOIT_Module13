import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import post.Comment;
import post.Post;
import todos.Todo;
import user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HTTPMethodsPostsTodos {
    private static final String URL = "https://jsonplaceholder.typicode.com/";
    private final HttpClient httpClient;
    private final Gson gson;

    public HTTPMethodsPostsTodos() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }
    private Post getLastPostOfUser(int userId) throws Exception{
        String url = URL + "users/" + userId + "/posts";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        Type postListType = new TypeToken<List<Post>>(){}.getType();

        List<Post> posts = gson.fromJson(responseBody, postListType);

        Collections.reverse(posts);

        return posts.get(0);
    }
    public List<Comment> getAllCommentsOfPost(int userId) throws Exception{
        Post post = getLastPostOfUser(userId);
        String newUrl = URL + "posts/" + post.getId() + "/comments";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(newUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        Type postListType = new TypeToken<List<Comment>>(){}.getType();

        List<Comment> comments = gson.fromJson(responseBody, postListType);

        String commentsJson = gson.toJson(comments);
        try (FileWriter writer = new FileWriter("user-" + userId + "-post-" + post.getId() + "-comments.json")) {
            writer.write(commentsJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return comments;
    }
    public List<Todo> getAllNotCompletedTodosOfUser(int userId) throws Exception{
        String url = URL + "users/" + userId + "/todos";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        Type postListType = new TypeToken<List<Todo>>(){}.getType();

        List<Todo> todos = gson.fromJson(responseBody, postListType);

        return todos.stream().filter(t -> !t.isCompleted()).collect(Collectors.toList());
    }

}
