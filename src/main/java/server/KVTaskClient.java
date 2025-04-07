package server;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final String urlKVServer;
    private final String apiToken;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public KVTaskClient(String urlKVServer) throws IOException, InterruptedException {
        this.urlKVServer = urlKVServer;
        this.apiToken = getApiToken(urlKVServer);
    }

    private String getApiToken(String urlKVServer) throws IOException, InterruptedException {
        URI url = URI.create(urlKVServer + "/register");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        URI url = URI.create(urlKVServer + "/save/" + key + "?API_TOKEN=" + apiToken);
        Gson gson = new Gson();
        String newJson = gson.toJson(json);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(newJson);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    public String load(String key) throws IOException, InterruptedException {
        URI url = URI.create(urlKVServer + "/load/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void delete(String key) throws IOException, InterruptedException {
        URI url = URI.create(urlKVServer + "/delete/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }



}
