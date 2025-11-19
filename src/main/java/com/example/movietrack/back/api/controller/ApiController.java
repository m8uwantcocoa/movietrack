package com.example.movietrack.back.api.controller;

import com.example.movietrack.back.api.model.MovieDetails;
import service.OMDbService;
import service.SpotifyAlbum;
import service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TMDBService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adam, Mojtaba,  Aleksander & Emre
 */

@RestController
@RequestMapping("/api")
public class ApiController {

    private final OMDbService omdbService;
    private final SpotifyService spotifyService;
    private final TMDBService tmdbService;

    @Autowired
    public ApiController(OMDbService omdbService, SpotifyService spotifyService, TMDBService tmdbService) {
        this.omdbService = omdbService;
        this.spotifyService = spotifyService;
        this.tmdbService = tmdbService;
    }

    @GetMapping("/movies/{title}")
    public ResponseEntity<Map<String, Object>> getMovieAndTracks(@PathVariable String title) {
        Map<String, Object> response = new HashMap<>();

        if (title == null || title.trim().isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Movie title cannot be empty");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        List<MovieDetails> movieDetailsList = omdbService.searchMovies(title);

        if (movieDetailsList == null || movieDetailsList.get(0) == null) {
            MovieDetails movieDetailsWithId = omdbService.getMovieDetailsWithId(title);
            if (movieDetailsWithId == null || movieDetailsWithId.getTitle() == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Movie not found");
            }else{
                response.put("movie", movieDetailsWithId);
                List<SpotifyAlbum> albums = spotifyService.getAlbumsForMovie(movieDetailsWithId.getTitle() + " soundtrack");
                response.put("albums", albums);
            }
        }else{
            response.put("movie", movieDetailsList);
            List<SpotifyAlbum> albums = spotifyService.getAlbumsForMovie(title+ " soundtrack");
            response.put("albums", albums);
        }
        if(response.get("albums") == null){
            response.put("albums", spotifyService.getAlbumsForMovie(title + " soundtrack"));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tv-shows")
    public List<Map<String, Object>> getTopRatedTVShows() {
        return tmdbService.getTopRatedTVShows();
    }
    @GetMapping("/movies")
    public List<Map<String, Object>> getTopRatedMovies() {
        return tmdbService.getTopRatedMovies();
    }
}
