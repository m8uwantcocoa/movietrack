package service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TMDBService {

    /**
     * @author Emre
     */

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> getTopRatedTVShows() {
        String url = "https://api.themoviedb.org/3/tv/top_rated?api_key=" + tmdbApiKey;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (List<Map<String, Object>>) response.get("results");
    }
    public List<Map<String, Object>> getTopRatedMovies() {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + tmdbApiKey;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (List<Map<String, Object>>) response.get("results");
    }
}