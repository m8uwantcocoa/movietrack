package service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @author Mojtaba & Aleksander
 */
public class MovieSearchResponse {
    @JsonProperty("Search")
    private List<MovieSummary> search;

    @JsonProperty("totalResults")
    private int totalResults;

    @JsonProperty("Response")
    private String response;

    public List<MovieSummary> getSearch() {
        return search;
    }

    public void setSearch(List<MovieSummary> search) {
        this.search = search;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}