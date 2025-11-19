package service;

import com.example.movietrack.back.api.model.MovieDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Adam & Mojtaba & Aleksander
 */

@Service
public class OMDbService {
    @Value("${omdb.api.key}")
    private String omdbApiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public OMDbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MovieDetails> searchMovies(String movieTitle) {
        String url = "http://www.omdbapi.com/?s=" + movieTitle + "&page=1&apikey=" + omdbApiKey;
        MovieSearchResponse response = restTemplate.getForObject(url, MovieSearchResponse.class);

        if (response != null && response.getSearch() != null) {
            return response.getSearch().stream()
                    .map(movie -> {
                        String detailsUrl = "http://www.omdbapi.com/?i=" + movie.getImdbID() + "&apikey=" + omdbApiKey;
                        return restTemplate.getForObject(detailsUrl, MovieDetails.class);
                    })
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public MovieDetails getMovieDetailsWithId(String movieId) {
        String url = "http://www.omdbapi.com/?i=" + movieId + "&apikey=" + omdbApiKey;
        return restTemplate.getForObject(url, MovieDetails.class);
    }
}