package sfwe302.assignment8.part2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class RESTCountries {
    public static void main(String[] argv) throws Exception {
        String country = argv[0];
        // "Czechia"
        // "Germany"
        // "United States of America"
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://restcountries.com/v3.1/name/" +
                        country.replace(" ", "%20") + "?fullText=true"))
                .header("content-type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String result = response.body();

        JSONArray ja = new JSONArray(result);
        JSONObject jo = ja.getJSONObject(0);
        System.out.println(jo);
        System.out.println(jo.get("cca2"));
        System.out.println(jo.getJSONArray("capital").get(0));
        System.out.println(jo.getJSONObject("languages"));
        for (String key : jo.getJSONObject("languages").keySet()) {
            System.out.println(key);
        }
        System.out.println(jo.getJSONObject("name").getJSONObject("nativeName").getJSONObject(
                jo.getJSONObject("name").getJSONObject("nativeName").keys().next()).get("official"));
    }
}