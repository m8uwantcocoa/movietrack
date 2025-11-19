package service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Adam & Mojtaba
 */

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String spotifyClientId;

    @Value("${spotify.client.secret}")
    private String spotifyClientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private String accessToken;

    private String fetchAccessToken() {
        String auth = spotifyClientId + ":" + spotifyClientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                "https://accounts.spotify.com/api/token",
                HttpMethod.POST,
                request,
                Map.class
        );
        return (String) response.getBody().get("access_token");
    }

    public List<SpotifyAlbum> getAlbumsForMovie(String movieTitle) {
        if (accessToken == null) {
            accessToken = fetchAccessToken();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        String url = "https://api.spotify.com/v1/search?q=" + movieTitle + "&type=album&limit=5";

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("albums")) {
            Map<String, Object> albumsData = (Map<String, Object>) responseBody.get("albums");
            List<Map<String, Object>> albums = (List<Map<String, Object>>) albumsData.get("items");

            return albums.stream()
                    .map(this::mapToSpotifyAlbum)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    /**
     * @author Aleksander
     */
    private SpotifyAlbum mapToSpotifyAlbum(Map<String, Object> albumData) {
        SpotifyAlbum album = new SpotifyAlbum();
        album.setName((String) albumData.get("name"));
        album.setReleaseDate((String) albumData.get("release_date"));
        album.setTotalTracks((Integer) albumData.get("total_tracks"));
        album.setSpotifyUri((String) albumData.get("uri"));
        album.setImageUrl((String) ((List<Map<String, Object>>) albumData.get("images")).get(0).get("url"));
        return album;
    }
}