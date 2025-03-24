package sfwe302.assignment8.part3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Protocol {

    enum State {COUNTRY, UNIVERSITY}
    private State state = State.COUNTRY;

    public String processInput(String input) throws Exception {
        String output = null;

        switch (state) {
            case COUNTRY:
                output = "Specify the country";
                state = State.UNIVERSITY;
                break;
            case UNIVERSITY:
                output = getData(java.net.URLEncoder.encode(input, StandardCharsets.UTF_8));
                state = State.COUNTRY;
                break;
        }
        return output;
    }

    private static String getData(String country) throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://universities.hipolabs.com/search?country=" + country))
            .header("Content-Type", "application/json").GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());
        
        return response.body();
    }
}
